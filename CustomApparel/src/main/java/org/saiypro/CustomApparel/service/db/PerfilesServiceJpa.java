package org.saiypro.CustomApparel.service.db;

import java.util.List;

import org.saiypro.CustomApparel.entity.Perfil;
import org.saiypro.CustomApparel.repository.PerfilesRepository;
import org.saiypro.CustomApparel.service.IntServicePerfiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PerfilesServiceJpa implements IntServicePerfiles {

	@Autowired
	private PerfilesRepository repoPerfiles;

	@Override
	public List<Perfil> obtenerPerfiles() {
		return repoPerfiles.findAll();
	}
}
