package xyz.cafeconleche.web.chica.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import xyz.cafeconleche.web.chica.dto.Face;
import xyz.cafeconleche.web.chica.service.EyesService;
import xyz.cafeconleche.web.chica.service.MouthsService;
import xyz.cafeconleche.web.chica.service.NosesService;

@Controller
public class FaceGeneratorController {

	@Autowired
	private EyesService eyesService;
	@Autowired
	private NosesService nosesService;
	@Autowired
	private MouthsService mouthsService;

	@RequestMapping("/face/show")
	public ModelAndView show() {
		System.out.println("--- show ---");
		ModelAndView modelAndView = new ModelAndView("face.show");
		modelAndView.addObject("face", new Face());
		modelAndView.addObject("eyesList", eyesService.list());
		modelAndView.addObject("nosesList", nosesService.list());
		modelAndView.addObject("mouthsList", mouthsService.list());

		return modelAndView;
	}

	@RequestMapping(path = "/face/create", method = RequestMethod.POST)
	public ModelAndView create(@ModelAttribute Face face, BindingResult result, Model model, HttpServletRequest request) {
		System.out.println("--- create ---");
		System.out.println("face: " + face);
		System.out.println("result: " + result);
		System.out.println("model: " + model);
		System.out.println("req: " + request);

		model.addAttribute("face", face);
		model.addAttribute("eyesList", eyesService.list());
		model.addAttribute("nosesList", nosesService.list());
		model.addAttribute("mouthsList", mouthsService.list());

		return new ModelAndView("pdfView", "face", face);
	}

}
