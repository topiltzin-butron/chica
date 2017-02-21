package xyz.cafeconleche.web.chica.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value="/social")
public class SocialGoogleController {

	
	@RequestMapping(value="/google")
	public ModelAndView show() {
		
		System.out.println("google...");
		
		ModelAndView modelAndView = new ModelAndView("social.google.show");
		modelAndView.addObject("socialMediaName", "Google");
		return modelAndView;
	}
	
}
