package org.saiypro.CustomApparel.controller;

import java.util.LinkedList;
import java.util.List;

import org.saiypro.CustomApparel.entity.Categoria;
import org.saiypro.CustomApparel.service.IntServiceCategorias;
import org.saiypro.CustomApparel.service.IntServiceProductos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {
    
	@Autowired
	private IntServiceProductos serviceProductos;
	
	@Autowired
	private IntServiceCategorias serviceCategorias;
	
	@GetMapping("")
	public String mostrarIndex(Model model) {
		List<Categoria> categorias = new LinkedList<>();
		model.addAttribute("productos", serviceProductos.obtenerEnStock());
		for(Categoria categoria : serviceCategorias.obtenerCategorias()) {
			if(categoria.getId().compareTo(1) == 1) {
				categorias.add(categoria);
			}
		}
		model.addAttribute("categoria", serviceCategorias.buscarPorId(1));
		model.addAttribute("categorias", categorias);
		return "home";
	}
}
