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
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import model.Evento;
import model.User;
import model.UserCredential;
import service.EventoService;
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
		}
		if (user != null) {
			session.setAttribute("user", user);
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

		evento.setCreador(Integer.toString(user.getIdUser()));
		System.out.println(evento);
		getEventoService().registerEvento(evento);
		ModelAndView maw = new ModelAndView("home");

		return maw;

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
	public String questions(Model model) {

		return "questions";
	}

	@RequestMapping(value = "logOff", method = RequestMethod.GET)
	public String logOff(HttpSession session) {
		session.setAttribute("user", null);
		return "home";
	}
}
