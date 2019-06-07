/** @file MyController.java
 *  @brief Controller
 *  @authors
 *  Name          | Surname         | Email                                |
 *  ------------- | -------------- | ------------------------------------ |
 *  Iker	      | Orive          | iker.orive@alumni.mondragon.edu     |
 *  @date 07/06/2019
 */

/** @brief package controller
 */
package controller;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.file.Files;
import java.sql.Blob;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;
import javax.validation.Valid;
import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriBuilder;

import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.impl.conn.SchemeRegistryFactory;
import org.apache.http.params.HttpParams;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileItemFactory;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.plugins.providers.jackson.ResteasyJacksonProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;
import com.sun.jersey.api.client.ClientResponse;

import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.core.util.MultivaluedMapImpl;

import json.Categoria;
import json.Formulario;
import json.ResposeList;
import json.Respuesta;
import json.UserCaracteristics;
import model.Evento;
import model.Questions;
import model.User;
import model.UserCredential;
import service.EventoService;
import service.QuestionsService;
import service.UserService;

@Controller
public class MyController {
	final ResteasyClient client = new ResteasyClientBuilder().connectionPoolSize(10).maxPooledPerRoute(5)
			.register(ResteasyJacksonProvider.class).build();

	@Autowired
	private UserService userService;

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public UserService getUserService() {
		return userService;
	}

	@Autowired
	private EventoService eventoService;

	public EventoService getEventoService() {
		return eventoService;
	}

	public void setEventoService(EventoService eventoService) {
		this.eventoService = eventoService;
	}

	@Autowired
	private QuestionsService questionsService;

	public QuestionsService getQuestionsService() {
		return questionsService;
	}

	public void setQuestionsService(QuestionsService questionsService) {
		this.questionsService = questionsService;
	}

	///////////////////////////////////////////////////////////////////
	/*
	 * ! \brief Redirigir a home
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String homePage(HttpSession session) {

		return "home";
	}

	/*
	 * ! \brief Redirigir a login, se añade las credenciales de usuario al modelo
	 * (vacio) para añadirlo despues
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginPage(Model model) {
		model.addAttribute("userCredential", new UserCredential());
		return "login";
	}

	/*
	 * ! \brief Si el inicio de sesion es correcto se añaden el usuario y avatar (si
	 * tiene) como atributos de sesion
	 */
	@RequestMapping(value = "/loginSuccess", method = RequestMethod.POST)
	public ModelAndView loginSuccess(@Valid @ModelAttribute("userCredential") UserCredential userCredential,
			BindingResult bindingResult, HttpSession session) {
		if (bindingResult.hasErrors()) {
			return new ModelAndView("login");
		}

		ModelAndView modelAndView = new ModelAndView("welcome");
		User user = getUserService().validateUserCredential(userCredential.getUsername(), userCredential.getPassword());
		session.setAttribute("avatar", null);
		session.setAttribute("user", null);

		if (user != null) {
			session.setAttribute("user", user);
			if (user.getAvatar() != null) {
				try {
					InputStream in = user.getAvatar().getBinaryStream();
					BufferedImage image = ImageIO.read(in);
					session.setAttribute("avatar", image);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					System.out.println("Caught an IllegalArgumentException..." + e.getMessage());
				}
			}

			return modelAndView;
		} else {
			modelAndView = new ModelAndView("notFound");
		}

		return modelAndView;
	}

	/*
	 * ! \brief Añade mensaje de bienvenida
	 */
	@ModelAttribute
	public void headerMessage(Model model) {
		model.addAttribute("headerMessage", "Welcome");

	}

	/*
	 * ! \brief Si el registro ha sido correcto desde aqui se llama a crear el
	 * passhash y el salt. Se añade el user a la DB. Tambien se redirige a las
	 * preguntas o a home dependiendo del tipo de usuario
	 */
	@RequestMapping(value = "/registerSuccess", method = RequestMethod.POST)
	public ModelAndView registerSuccess(@Valid @ModelAttribute("user") User user, BindingResult bindingResult,
			HttpSession session) {
		if (bindingResult.hasErrors()) {
			return new ModelAndView("register");
		}
		PassHash ph = new PassHash();
		user.setSalt(ph.generateSalt());
		user.setPassword(ph.getHashedPass(user.getPassword(), user.getSalt()));

		getUserService().registerUser(user);
		session.setAttribute("user", user);
		System.out.println(user);
		ModelAndView maw;
		if (user.getTipoUsuario().equals("1")) {
			List<Questions> listQuestions = getQuestionsService().getAllQuestions();
			System.out.println(listQuestions);
			session.setAttribute("questions", listQuestions);
			maw = new ModelAndView("questions");
		} else {
			maw = new ModelAndView("home");
		}
		return maw;

	}

	/*
	 * ! \brief Si el evento se ha creado correctamente se añade a la DB
	 */
	@RequestMapping(value = "/createEventoSuccess", method = RequestMethod.POST)
	public ModelAndView createEventoSuccess(@Valid @ModelAttribute("evento") Evento evento, BindingResult bindingResult,
			HttpSession session) {
		if (bindingResult.hasErrors()) {
			return new ModelAndView("createEvento");
		}
		User user = (User) session.getAttribute("user");

		evento.setDate(evento.getStrDate() + " " + evento.getStrHour());
		evento.setCreador(Integer.toString(user.getIdUser()));
		/*
		 * public void crearEvento(@QueryParam("creador") String creador,
		 * 
		 * @QueryParam("description") String description, @QueryParam("name") String
		 * name,
		 * 
		 * @QueryParam("imgUrl") String imgUrl, @QueryParam("maxSize") String maxSize,
		 * 
		 * @QueryParam("date") String date, @QueryParam("latitude") String
		 * latitude, @QueryParam("longitude") String longitude, @QueryParam("eventType")
		 * int eventType) {
		 */
		/*
		 * client.target(
		 * "http://localhost:8080//Muffin.v.2/webresources/generic/crearEvento")
		 * .queryParam("creador", evento.getCreador()).queryParam("description",
		 * evento.getDescription()) .queryParam("name",
		 * evento.getName()).queryParam("imgUrl", evento.getImgUrl())
		 * .queryParam("maxSize", evento.getMaxSize()).queryParam("date",
		 * evento.getDate()) .queryParam("latitude",
		 * evento.getLatitude()).queryParam("longitude", evento.getLongitude())
		 * .queryParam("eventType", 1);
		 */

		System.out.println("Selected Category " + evento.getEventType());
		ModelAndView maw = new ModelAndView("home");

		return maw;

	}

	/*
	 * ! \brief El evento clickado es añadido a la sesion para usarlo en el chat del
	 * evento y la pagina de informacion del evento
	 */
	@PostMapping("/selectEventSuccess")
	public String selectEventSuccess(@RequestParam("eventId") int id, RedirectAttributes redirectAttributes,
			HttpSession session) {

		Evento evt = getEventoService().getEvento(id);
		session.setAttribute("selectedEvent", evt);

		return "chat";

	}

	/*
	 * ! \brief Una vez respondidas las preguntas se llama a la IA para categorizar
	 * al usuario
	 */
	@PostMapping("/formularioSuccess")
	public String formularioSuccess(@RequestParam("answers") String answers, RedirectAttributes redirectAttributes,
			HttpSession session) {
		List<String> items = Arrays.asList(answers.split(","));
		System.out.println("RESPUESTAS   ----> " + items);
		Formulario form = new Formulario();
		User us = (User) session.getAttribute("user");
		form.setId(us.getIdUser());
		LinkedHashMap<String, ArrayList<Integer>> hm = new LinkedHashMap<>();
		form.setCategorias(new ArrayList<>());
		for (String respuesta : items) {
			String[] rp = respuesta.split("-");
			String key = rp[0];
			int punt = Integer.parseInt(rp[1]);
			if (!hm.containsKey(key)) {
				hm.put(key, new ArrayList<Integer>());
			}
			hm.get(key).add(punt);
		}
		Categoria c;
		ArrayList<Categoria> arraycat = new ArrayList<>();
		Client client = ClientBuilder.newClient();
		ArrayList<Double> bal = new ArrayList<>();
		ArrayList<Float> aux = new ArrayList<>();
		int i = 0;
		// LinkedHashMap<String,Float> mapa = new LinkedHashMap<>();
		UserCaracteristics user;
		char inicialCategoria;
		ArrayList<UserCaracteristics> listaUserCaracteristics = new ArrayList<>();
		for (String key : hm.keySet()) {
			ArrayList<Respuesta> arrayResp = new ArrayList<>();
			Respuesta resp = new Respuesta();
			ArrayList<Integer> arrayInt = hm.get(key);
			resp.setPregunta1(arrayInt.get(0));
			resp.setPregunta2(arrayInt.get(1));
			resp.setPregunta3(arrayInt.get(2));
			resp.setPregunta4(arrayInt.get(3));
			resp.setPregunta5(arrayInt.get(4));
			arrayResp.add(resp);
			c = new Categoria();
			c.setNombre(key);
			inicialCategoria = c.getNombre().toUpperCase().charAt(0);
			System.out.println("La inicial de "+c.getNombre() + "es " + inicialCategoria);
			c.setRespuestas(arrayResp);
			System.out.println("Categoria: " + c.toString());
			form.getCategorias().add(c);
			bal = client
					.target("http://localhost:8000/bayes"+ inicialCategoria+"?a="
							+ form.getCategorias().get(i).getRespuestas().get(0).getPregunta1() + "&b="
							+ form.getCategorias().get(i).getRespuestas().get(0).getPregunta2() + "&c="
							+ form.getCategorias().get(i).getRespuestas().get(0).getPregunta3() + "&d="
							+ form.getCategorias().get(i).getRespuestas().get(0).getPregunta4() + "&e="
							+ form.getCategorias().get(i).getRespuestas().get(0).getPregunta5())
					.request(MediaType.APPLICATION_JSON).get(ArrayList.class);
			System.out.println(c.getNombre() + ": " + bal);
			// String porcentaje = bal.get(0).toString();
			// aux.add(Float.parseFloat(porcentaje));
			// System.out.println("VALOR----> "+bal.get(0).getClass());
			Float f = bal.get(0).floatValue() * 100;

			aux.add(f);
			user = new UserCaracteristics();
			user.setPorcentaje(f);
			user.setIdCategoria(++i);
			user.setIdUser(us.getIdUser());
			listaUserCaracteristics.add(user);

			// mapa.put(c.getNombre(), f);
		}
		System.out.println(listaUserCaracteristics);

		WebTarget webTarget2 = client
				.target("http://localhost:8080//cambiarEventosServer/webresources/generic/insertarCaracteristicas");

		Invocation.Builder invocationBuilder2 = webTarget2.request(MediaType.APPLICATION_JSON);
		invocationBuilder2.post(Entity.entity(listaUserCaracteristics, MediaType.APPLICATION_JSON));
		// System.out.println(listaUserCaracteristics);

		Gson gson = new Gson();
		String json = gson.toJson(form);
		System.out.println(json);
		return "redirect:/";

	}

	/*
	 * ! \brief Se añade un atributo de usuario vacio para llenarlo al registrar
	 */
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String registerPage(Model model) {
		model.addAttribute("user", new User());
		return "register";
	}

	/*
	 * ! \brief Se añade un atributo de evento vacio para llenarlo al crear eventos
	 */
	@RequestMapping(value = "/createEvento", method = RequestMethod.GET)
	public String registerEventoPage(Model model) {
		model.addAttribute("evento", new Evento());
		return "createEvento";
	}

	/*
	 * ! \brief Se añaden todas las preguntas como atributo para usarlas en el
	 * formulario
	 */
	@RequestMapping(value = "/questions", method = RequestMethod.GET)
	public String questions(Model model, HttpSession session) {
		// System.out.println(getEventoService().getAllEventos());

		// Client client = ClientBuilder.newClient();

		// String bal =
		// client.target("http://localhost:8080/CalculadoraREST/calculadora/suma").queryParam("oper1",
		// 4)
		// .queryParam("oper2", 5).request("text/plain").get(String.class);
		// System.out.println("Respuesta WS ----> " + bal);

		List<Questions> listQuestions = getQuestionsService().getAllQuestions();
		System.out.println(listQuestions);
		session.setAttribute("questions", listQuestions);
		// client.close();
		return "questions";
	}

	/*
	 * ! \brief Se ponen los atributos de usuario y avatar a null para cerrar
	 * sesions, tambien redirigimos a home
	 */
	@RequestMapping(value = "logOff", method = RequestMethod.GET)
	public String logOff(HttpSession session) {
		session.setAttribute("user", null);
		session.setAttribute("avatar", null);
		return "home";
	}

	/*
	 * ! \brief Se redirige a uploadAvatar
	 */
	@RequestMapping(value = "uploadAvatar", method = RequestMethod.GET)
	public String uploadAvatar(HttpSession session) {

		return "uploadAvatar";
	}

	/*
	 * ! \brief Se redirige a eventoInfo
	 */
	@RequestMapping(value = "eventoInfo", method = RequestMethod.GET)
	public String eventoInfo(HttpSession session) {

		return "eventoInfo";
	}

	/*
	 * ! \brief El usuario se suscribe a un evento y se llama a los web services
	 * para cambiar los porcentajes
	 */
	@RequestMapping(value = "suscribeToEvent", method = RequestMethod.GET)
	public String suscribeToEvent(HttpSession session) {
		System.out.println("SE SUSCRIBE A EVENTO");
		User user = (User) session.getAttribute("user");
		Evento evto = (Evento) session.getAttribute("selectedEvent");
		System.out.println("USER " + user.getIdUser());
		System.out.println("EVENTO " + evto.getName());
		// Client client =
		// ClientBuilder.newBuilder().register(ResteasyJacksonProvider.class).build();
		UserCaracteristics userCaracteristics = client
				.target("http://localhost:8080/cambiarEventosServer/webresources/generic/uno")
				.queryParam("idUser", user.getIdUser()).queryParam("idCategoria", evto.getEventType())
				.request(MediaType.APPLICATION_JSON).get(UserCaracteristics.class);

		ArrayList<UserCaracteristics> listUserCar = client
				.target("http://localhost:8080/cambiarEventosServer/webresources/generic/muchos")
				.queryParam("idUser", user.getIdUser()).queryParam("idCategoria", evto.getEventType())
				.request(MediaType.APPLICATION_JSON).get(ArrayList.class);
		System.out.println("LISTA . -> " + listUserCar);
		// Client cliente = ClientBuilder.newClient(new
		// ClientConfig().register(LoggingFilter.class));
		WebTarget webTarget = client
				.target("http://localhost:8080/cambiarEventosServer/webresources/generic/aplicacion");

		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
		invocationBuilder.put(Entity.entity(userCaracteristics, MediaType.APPLICATION_JSON));

		WebTarget webTarget2 = client
				.target("http://localhost:8080/cambiarEventosServer/webresources/generic/aplicacionMuchos");

		Invocation.Builder invocationBuilder2 = webTarget2.request(MediaType.APPLICATION_JSON);
		invocationBuilder2.put(Entity.entity(listUserCar, MediaType.APPLICATION_JSON));
		// client.close();
		return "chat";
	}

	/*
	 * ! \brief Se redirige a pruebas (se usa para realizar pruebas durante el
	 * desarrollo)
	 */
	@RequestMapping(value = "pruebas", method = RequestMethod.GET)
	public String pruebas(HttpSession session) {

		// Client client = ClientBuilder.newClient();
		User user = new User();
		user.setIdUser(44);
		user.setUsername("userPruebas");
		String s = client.target("http://localhost:8080/MuffinRMQ/webresources/generic/crear").queryParam("id", 4)
				.queryParam("descripcion", "asdasdasddsasdads").queryParam("user", user).request("text/plain")
				.get(String.class);
		System.out.println(s);
		return "pruebas";
	}

	/*
	 * ! \brief Se redirige a evento Lista y se ponen como atributo los eventos
	 * ordenados según los datos devueltos por el web service
	 */
	@RequestMapping(value = "eventoLista", method = RequestMethod.GET)
	public String eventoLista(HttpSession session, Model model) {

		// Client client = ClientBuilder.newClient().; ///
		// Muffin.v.2/webresources/generic

		// Client client =
		// ClientBuilder.newBuilder().register(ResteasyJacksonProvider.class).build();

		ResposeList orden = client.target("http://localhost:8080/cambiarEventosServer/webresources/generic/ordenar")
				.queryParam("id", 2).request(MediaType.APPLICATION_JSON).get(ResposeList.class);
		System.out.println("RESPUESTA WS----> " + orden.getList());

		List<Evento> listEventos = getEventoService().getAllEventos();
		LinkedHashMap<Integer, ArrayList<Evento>> hm = new LinkedHashMap<>();
		for (Integer i : orden.getList()) {
			System.out.println("Numero orden: " + i);
			ArrayList<Evento> ar = new ArrayList<>();
			hm.put(i, ar);
		}
		for (Evento e : listEventos) {
			System.out.println("EVENT TYPE -----> " + e.getEventType());
			// System.out.println(e.ge);

			hm.get(Integer.parseInt(e.getEventType())).add(e);
		}

		ArrayList<Evento> evOrdenados = new ArrayList<>();
		for (Map.Entry<Integer, ArrayList<Evento>> entry : hm.entrySet()) {
			System.out.println("ORDEN HASHMAP----> " + entry.getKey());
			evOrdenados.addAll(entry.getValue());

			// System.out.println("clave=" + entry.getKey() + ", valor=" +
			// entry.getValue());
		}
		session.setAttribute("eventos", evOrdenados);
		// model.addAttribute("evento", new Evento());
		return "eventoLista";
	}

	/*
	 * ! \brief Se añaden como atributo los eventos creador por el usuario activo
	 */
	@RequestMapping(value = "misEventos", method = RequestMethod.GET)
	public String misEventos(HttpSession session, Model model) {
		User user = (User) session.getAttribute("user");
		List<Evento> listEventos = getEventoService().getEventosByCreator(user.getIdUser());
		session.setAttribute("eventos", listEventos);
		// model.addAttribute("evento", new Evento());
		return "eventoLista";
	}

	/*
	 * ! \brief Se recoge el avatar desde la web y se guarda en la DB
	 */
	@PostMapping("/uploadAction")
	public String handleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes,
			HttpSession session) {
		System.out.println("FILE ---->" + file);
		FileOutputStream outputStream;
		try {
			byte[] strToBytes = file.getBytes();
			User user = (User) session.getAttribute("user");
			user.setAvatar(new SerialBlob(file.getBytes()));
			InputStream in = user.getAvatar().getBinaryStream();
			BufferedImage image = ImageIO.read(in);
			session.setAttribute("avatar", image);
			getUserService().addAvatar(user);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SerialException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "redirect:/";
	}

	/*
	 * ! \brief Convierte un MultipartFile a File
	 */
	public File convert(MultipartFile file) throws IOException {
		File convFile = new File(file.getOriginalFilename());
		convFile.createNewFile();
		try (InputStream is = file.getInputStream()) {
			Files.copy(is, convFile.toPath());
		}
		return convFile;
	}

	/*
	 * ! \brief Funcion que se ejecuta al iniciar el servlet
	 */
	public void init() throws ServletException {
		// Initialization code like set up database etc....
		System.out.println("SERVLET INITIALIZATION");

	}

}
