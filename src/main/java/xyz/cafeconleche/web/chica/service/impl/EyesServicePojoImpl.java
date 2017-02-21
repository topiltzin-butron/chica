package xyz.cafeconleche.web.chica.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import xyz.cafeconleche.web.chica.service.EyesService;

@Service("eyesService")
public class EyesServicePojoImpl implements EyesService{

	@Override
	public List<String> list() {
		List<String> list = new ArrayList<>();
		list.add("eyes_001");
		list.add("eyes_002");
		list.add("eyes_003");
		list.add("eyes_004");
		list.add("eyes_005");
		return list;
	}

}
