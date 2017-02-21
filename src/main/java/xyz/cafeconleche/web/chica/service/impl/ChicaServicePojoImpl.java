package xyz.cafeconleche.web.chica.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xyz.cafeconleche.web.chica.dao.ChicaDAO;
import xyz.cafeconleche.web.chica.service.ChicaService;

@Service("chicaService")
public class ChicaServicePojoImpl implements ChicaService {

	@Autowired
	private ChicaDAO chicaDAO;

	@Override
	public String getName(int id) {

		return chicaDAO.getName(id);
	}

}
