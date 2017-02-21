package xyz.cafeconleche.web.chica.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SocialFacebookController {

//	private final Facebook facebook;
//	
//	@Inject
//	public SocialFacebookController(Facebook facebook) {
//        this.facebook = facebook;
//    }
	
	@RequestMapping(value = "/social/facebook")
	public String social(Model model) {

		System.out.println("social facebook...");
		
		
//		
//		boolean authorized = false;
//		try {
//			authorized = facebook.isAuthorized();
//		} catch (Exception e) {
//			System.err.println("ERROR: " + e.getMessage());
//		}
//		
//		if (!authorized) {
//			System.out.println("not authorized...");
//			return "redirect:/connect/facebook";
//		}
//		
//		User user = facebook.userOperations().getUserProfile();
//		model.addAttribute("user", user);
		
		
		
		model.addAttribute("socialMediaName", "Facebook");
		return "social.facebook.show";
	}
	
	
	// ========= original ========
	
	/*
	private Facebook facebook;
	private ConnectionRepository connectionRepository;
	
	public SocialFacebookController(Facebook facebook, ConnectionRepository connectionRepository) {
        this.facebook = facebook;
        this.connectionRepository = connectionRepository;
    }

	@RequestMapping(value = "/social/facebook2")
	public ModelAndView show() {

		System.out.println("facebook...");

		ModelAndView modelAndView = new ModelAndView("social.facebook.show");
		modelAndView.addObject("socialMediaName", "Facebook");
		return modelAndView;
	}

	@RequestMapping(value = "/social/facebook")
	public String social(Model model) {

		System.out.println("facebook social...");
		System.out.println("connectionRepository: " + connectionRepository);
		MultiValueMap<String, Connection<?>> connections = connectionRepository.findAllConnections();
		
		System.out.println("connections: " + connections.keySet());
		for (String key : connections.keySet()) {
			System.out.println("["+key+"]: " + connections.get(key));
		}
		
		if (connectionRepository.findPrimaryConnection(Facebook.class) == null) {
			System.out.println("redirecting to facebook authentication");
			return "redirect:/connect/facebook";
		}
		
		System.out.println("Get the feed baby!");
		PagedList<Post> feed = facebook.feedOperations().getFeed();
		
		model.addAttribute("socialMediaName", facebook.getApplicationNamespace());
		model.addAttribute("facebookProfile", facebook.userOperations().getUserProfile());
		model.addAttribute("feed", feed);

		// hello
		return "social.facebook.show";
	}
	
//	facebook.com/topiltzin.dominguez@gmail.com
*/
}
