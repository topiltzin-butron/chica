package xyz.cafeconleche.web.chica.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class VacaController {
	
	@RequestMapping(value="/vaca")
	public ModelAndView hello() {
		
		ModelAndView modelAndView = new ModelAndView("vaca");
		modelAndView.addObject("name", "vaca");
		return modelAndView;
	}
	
	@RequestMapping(value="/vaca/admin")
	public ModelAndView admin() {
		
		ModelAndView modelAndView = new ModelAndView("admin");
		modelAndView.addObject("name", "admin");
		return modelAndView;
	}
	
	@RequestMapping(value="/vaca/dba")
	public ModelAndView dba() {
		
		ModelAndView modelAndView = new ModelAndView("dba");
		modelAndView.addObject("name", "dba");
		
		return modelAndView;
	}

}
