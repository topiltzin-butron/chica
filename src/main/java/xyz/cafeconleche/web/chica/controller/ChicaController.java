package xyz.cafeconleche.web.chica.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import xyz.cafeconleche.web.chica.base.exception.ChicaException;
import xyz.cafeconleche.web.chica.service.ChicaService;

@Controller
public class ChicaController {

	@Autowired
	private ChicaService chicaService;

	@RequestMapping(value = "/chica/{id}")
	public String getChica(@PathVariable("id") int id, Model model) throws Exception {

		switch (id) {
		case 1:
			throw new ChicaException("ChicaException id=" + id);
		case 2:
			throw new SQLException("SQLException id=" + id);
		case 3:
			throw new IOException("IOException id=" + id);
		case 10:
			model.addAttribute("id", id);
			model.addAttribute("chica", "La chikis: " + chicaService.getName(id));
			return "chica";
		default:
			throw new Exception("Exception id=" + id);
		}

	}

	@ExceptionHandler(ChicaException.class)
	public ModelAndView handleChicaException(HttpServletRequest request, Exception e) {

		e.printStackTrace(); // TODO change to log
		
		ModelAndView modelAndView = new ModelAndView("chica-error");
		modelAndView.addObject("exception", e);
	    modelAndView.addObject("url", request.getRequestURL());
	    return modelAndView;
	}

}
