package xyz.cafeconleche.web.chica.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import xyz.cafeconleche.web.chica.service.NosesService;

@Service("nosesService")
public class NosesServicePojoImpl implements NosesService{

	@Override
	public List<String> list() {
		List<String> list = new ArrayList<>();
		list.add("nose_001");
		list.add("nose_002");
		list.add("nose_003");
		return list;
	}

}
