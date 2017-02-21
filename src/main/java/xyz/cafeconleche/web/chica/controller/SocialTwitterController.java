package xyz.cafeconleche.web.chica.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value="/social")
public class SocialTwitterController {

	
	@RequestMapping(value="/twitter")
	public ModelAndView show() {
		
		System.out.println("twitter...");
		
		ModelAndView modelAndView = new ModelAndView("social.twitter.show");
		modelAndView.addObject("socialMediaName", "Twitter");
		return modelAndView;
	}
	
}
