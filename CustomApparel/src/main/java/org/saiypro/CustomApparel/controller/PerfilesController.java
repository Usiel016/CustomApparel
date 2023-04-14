package org.saiypro.CustomApparel.controller;

import org.saiypro.CustomApparel.service.IntServicePerfiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/perfiles")
public class PerfilesController {

	@Autowired
	private IntServicePerfiles servicePerfiles;

	@GetMapping("/index")
	public String mostrarIndex(Model model) {
		model.addAttribute("perfiles", servicePerfiles.obtenerPerfiles());
		return "perfiles/listaPerfiles";
	}
}
