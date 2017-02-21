package xyz.cafeconleche.web.chica.dao.impl;

import org.springframework.stereotype.Repository;

import xyz.cafeconleche.web.chica.dao.ChicaDAO;

@Repository("chicaDAO")
public class ChicaDAOPojoImpl implements ChicaDAO {

	@Override
	public String getName(int id) {
		return "Chica with ID: " + id + " from DAO";
	}

}
