/** @file MyController.java
 *  @brief Controller
 *  @authors
 *  Name          | Surname         | Email                                |
 *  ------------- | -------------- | ------------------------------------ |
 *  Iker	      | Orive          | iker.orive@alumni.mondragon.edu     |
 *  @date 12/12/2018
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

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileItemFactory;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
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
import model.Evento;
import model.Questions;
import model.User;
import model.UserCredential;
import service.EventoService;
import service.QuestionsService;
import service.UserService;

@Controller
public class MyController {

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
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String homePage(HttpSession session) {

		return "home";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginPage(Model model) {
		model.addAttribute("userCredential", new UserCredential());
		return "login";
	}

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

	@ModelAttribute
	public void headerMessage(Model model) {
		model.addAttribute("headerMessage", "Welcome");

	}

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

	@RequestMapping(value = "/createEventoSuccess", method = RequestMethod.POST)
	public ModelAndView createEventoSuccess(@Valid @ModelAttribute("evento") Evento evento, BindingResult bindingResult,
			HttpSession session) {
		if (bindingResult.hasErrors()) {
			return new ModelAndView("createEvento");
		}
		User user = (User) session.getAttribute("user");

		evento.setDate(evento.getStrDate() + " " + evento.getStrHour());
		evento.setCreador(Integer.toString(user.getIdUser()));

///http://localhost:8080//Muffin.v.2/webresources/generic/aplicacion/{creador}/{description}/{name}/{imgUrl}/{maxSize}/{date}/{latitude}/{longitude}/
		/*
		 * Client client = ClientBuilder.newClient(); ///
		 * Muffin.v.2/webresources/generic
		 * client.target("http://localhost:8080/Muffin.v.2/webresources/generic")
		 * .queryParam("creador", evento.getCreador()).queryParam("description",
		 * evento.getDescription()) .queryParam("name",
		 * evento.getName()).queryParam("imgUrl", evento.getImgUrl())
		 * .queryParam("maxSize", evento.getMaxSize()).queryParam("date",
		 * evento.getDate()) .queryParam("latitude",
		 * evento.getLatitude()).queryParam("longitude", evento.getLongitude())
		 * .request("text/plain").put(Entity.json(null));
		 * System.out.println("RESPUESTA WS----> " );
		 */
		// getEventoService().registerEvento(evento);
		RestTemplate restTemplate = new RestTemplate();
		String result = restTemplate.getForObject(
				"http://localhost:8080/Muffin.v.2/webresources/generic{imgUrl}{date}{creador}{latitude}{name}{description}{maxSize}{longitude}",
				String.class, evento.getImgUrl(), evento.getDate(), evento.getCreador(), evento.getLatitude(),
				evento.getName(), evento.getDescription(), evento.getMaxSize(), evento.getLongitude());
		System.out.println(result);
		ModelAndView maw = new ModelAndView("home");

		return maw;

	}

	@PostMapping("/selectEventSuccess")
	public String selectEventSuccess(@RequestParam("eventId") int id, RedirectAttributes redirectAttributes,
			HttpSession session) {

		Evento evt = getEventoService().getEvento(id);
		session.setAttribute("selectedEvent", evt);

		return "chat";

	}

	@PostMapping("/formularioSuccess")
	public String formularioSuccess(@RequestParam("answers") String answers, RedirectAttributes redirectAttributes,
			HttpSession session) {
		List<String> items = Arrays.asList(answers.split(","));
		System.out.println("RESPUESTAS   ----> " + items);
		Formulario form = new Formulario();
		User us = (User) session.getAttribute("user");
		form.setId(us.getIdUser());
		HashMap<String, ArrayList<Integer>> hm = new HashMap<>();
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
		ArrayList<Categoria> arraycat = new ArrayList<>();
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
			Categoria c = new Categoria();
			c.setNombre(key);
			c.setRespuestas(arrayResp);
			form.getCategorias().add(c);
		}

		Gson gson = new Gson();
		String json = gson.toJson(form);
		System.out.println(json);
		return "redirect:/";

	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String registerPage(Model model) {
		model.addAttribute("user", new User());
		return "register";
	}

	@RequestMapping(value = "/createEvento", method = RequestMethod.GET)
	public String registerEventoPage(Model model) {
		model.addAttribute("evento", new Evento());
		return "createEvento";
	}

	@RequestMapping(value = "/questions", method = RequestMethod.GET)
	public String questions(Model model, HttpSession session) {
		// System.out.println(getEventoService().getAllEventos());

		Client client = ClientBuilder.newClient();

		String bal = client.target("http://localhost:8080/CalculadoraREST/calculadora/suma").queryParam("oper1", 4)
				.queryParam("oper2", 5).request("text/plain").get(String.class);
		System.out.println("Respuesta WS ----> " + bal);

		List<Questions> listQuestions = getQuestionsService().getAllQuestions();
		System.out.println(listQuestions);
		session.setAttribute("questions", listQuestions);
		return "questions";
	}

	@RequestMapping(value = "logOff", method = RequestMethod.GET)
	public String logOff(HttpSession session) {
		session.setAttribute("user", null);
		session.setAttribute("avatar", null);
		return "home";
	}

	@RequestMapping(value = "uploadAvatar", method = RequestMethod.GET)
	public String uploadAvatar(HttpSession session) {

		return "uploadAvatar";
	}

	@RequestMapping(value = "eventoInfo", method = RequestMethod.GET)
	public String eventoInfo(HttpSession session) {

		return "eventoInfo";
	}

	@RequestMapping(value = "suscribeToEvent", method = RequestMethod.GET)
	public String suscribeToEvent(HttpSession session) {
		System.out.println("SE SUSCRIBE A EVENTO");
		User user=(User) session.getAttribute("user");
		Evento evto=(Evento) session.getAttribute("selectedEvent");
		System.out.println("USER "+user.getIdUser());
		System.out.println("EVENTO "+evto.getName());
		return "chat";
	}

	@RequestMapping(value = "pruebas", method = RequestMethod.GET)
	public String pruebas(HttpSession session) {

		Client client = ClientBuilder.newClient();
		User user = new User();
		user.setIdUser(44);
		user.setUsername("userPruebas");
		String s = client.target("http://localhost:8080/MuffinRMQ/webresources/generic/crear").queryParam("id", 4)
				.queryParam("descripcion", "asdasdasddsasdads").queryParam("user", user).request("text/plain")
				.get(String.class);
		System.out.println(s);
		return "pruebas";
	}

	@RequestMapping(value = "eventoLista", method = RequestMethod.GET)
	public String eventoLista(HttpSession session, Model model) {

		// Client client = ClientBuilder.newClient().; ///
		// Muffin.v.2/webresources/generic

		Client client = ClientBuilder.newBuilder().register(ResteasyJacksonProvider.class).build();

		ResposeList orden = client.target("http://localhost:8080/ordernarEventosServer2/webresources/controller")
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

	@RequestMapping(value = "misEventos", method = RequestMethod.GET)
	public String misEventos(HttpSession session, Model model) {
		User user = (User) session.getAttribute("user");
		List<Evento> listEventos = getEventoService().getEventosByCreator(user.getIdUser());
		session.setAttribute("eventos", listEventos);
		// model.addAttribute("evento", new Evento());
		return "eventoLista";
	}

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

	public File convert(MultipartFile file) throws IOException {
		File convFile = new File(file.getOriginalFilename());
		convFile.createNewFile();
		try (InputStream is = file.getInputStream()) {
			Files.copy(is, convFile.toPath());
		}
		return convFile;
	}
}
