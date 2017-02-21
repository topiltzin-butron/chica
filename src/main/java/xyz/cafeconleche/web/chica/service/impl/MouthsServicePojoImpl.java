package xyz.cafeconleche.web.chica.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import xyz.cafeconleche.web.chica.service.MouthsService;

@Service("mouthsService")
public class MouthsServicePojoImpl implements MouthsService {

	@Override
	public List<String> list() {
		List<String> list = new ArrayList<>();
		list.add("mouth_001");
		list.add("mouth_002");
		list.add("mouth_003");
		list.add("mouth_004");
		list.add("mouth_005");
		return list;
	}

}
