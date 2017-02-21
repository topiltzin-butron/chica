package xyz.cafeconleche.web.chica.controller;

import java.util.Map;
import java.util.Random;

import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.mobile.device.site.SitePreference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import xyz.cafeconleche.web.chica.service.ChicaService;

@Controller
public class HelloController {

	@Autowired
	private ChicaService chicaService;
	
	@RequestMapping(value = "/hello", method = RequestMethod.GET)
	public String index(Map<String, Object> model, Device device) {

		System.out.println("- executed index -");
		System.out.println("device: " + device);

		model.put("title", "Mi titulo");
		model.put("msg", "Mi mensaje");
		model.put("country", "Tailandia");
		model.put("chica", chicaService.getName(RandomUtils.nextInt()));
		
		
		return "hello";
	}

	@RequestMapping(value = "/helloMobile", method = RequestMethod.GET)
	public String helloMobile(Map<String, Object> model, Device device, SitePreference sitePreference) {

		System.out.println("- executed index -");
		System.out.println("device: " + device);
		System.out.println("sitePreference: " + sitePreference);

		model.put("title", "Mi titulo");
		model.put("msg", "Mi mensaje");
		model.put("country", "Tailandia");
		model.put("chica", chicaService.getName(RandomUtils.nextInt()));
		
		return "hello";
	}
	
	
}
