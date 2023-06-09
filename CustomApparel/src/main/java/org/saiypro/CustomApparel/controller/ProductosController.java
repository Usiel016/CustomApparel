package org.saiypro.CustomApparel.controller;

import java.beans.PropertyEditorSupport;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.saiypro.CustomApparel.entity.Producto;
import org.saiypro.CustomApparel.service.IntServiceCategorias;
import org.saiypro.CustomApparel.service.IntServiceProductos;
import org.saiypro.CustomApparel.util.Utileria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/productos")
public class ProductosController {

	@Autowired
	private IntServiceProductos serviceProductos;

	@Autowired
	private IntServiceCategorias serviceCategorias;

	@Autowired
	private Utileria util;

	@GetMapping("/detalle")
	public String consultarDetalleVacante(@RequestParam("id") int idProducto, Model model) {
		Producto producto = serviceProductos.buscarPorId(idProducto);
		model.addAttribute("producto", producto);
		return "productos/detalle";
	}

	@GetMapping("/buscar")
	public String modificarProducto(@RequestParam("id") int idProducto, Model model) {
		Producto producto = serviceProductos.buscarPorId(idProducto);
		model.addAttribute("producto", producto);
		model.addAttribute("categorias", serviceCategorias.obtenerCategorias());
		return "productos/formProducto";
	}

	@GetMapping("/eliminar")
	public String eliminarProducto(Producto producto, RedirectAttributes model) {
		serviceProductos.eliminarPorId(producto.getId());
		model.addFlashAttribute("msg", "Producto Eliminado");
		return "redirect:/productos/indexPaginado";
	}

	@PostMapping("/agregar")
	public String agregarProducto(Producto producto, BindingResult result, Model model, RedirectAttributes model2,
			@RequestParam("archivoImagen") MultipartFile file) {
		if (result.hasErrors()) {
			for (ObjectError error : result.getAllErrors()) {
				System.out.println("Ocurrio un error: " + error.getDefaultMessage());
			}
			model.addAttribute("categorias", serviceCategorias.obtenerCategorias());
			return "productos/formProducto";
		}
		if (producto.getId() == null) {
			if (!file.isEmpty()) {
				String fileName = util.uploadImage(file);
				if (fileName != null) {
					producto.setImagen(fileName);
				}
			}
			model2.addFlashAttribute("msg", "Producto Agregado");
		} else {
			if (!file.isEmpty()) {
				String fileName = util.uploadImage(file);
				if (fileName != null) {
					producto.setImagen(fileName);
				}
			} else {
				Producto p = serviceProductos.buscarPorId(producto.getId());
				producto.setImagen(p.getImagen());
			}
			model2.addFlashAttribute("msg", "Producto Modificado");
		}
		serviceProductos.agregarProducto(producto);
		return "redirect:/productos/indexPaginado";
	}

	@GetMapping("/nuevo")
	public String mostrarFormProducto(Producto producto, Model model) {
		model.addAttribute("categorias", serviceCategorias.obtenerCategorias());
		return "productos/formProducto";
	}

	@GetMapping(value = "/indexPaginado")
	public String mostrarIndexPaginado(Model model, Pageable page) {
		Page<Producto> productos = serviceProductos.buscarTodas(page);
		model.addAttribute("productos", productos);
		model.addAttribute("total", serviceProductos.contarProductos());
		System.out.println(productos.getContent());
		return "productos/listaProductos";
	}

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(LocalDate.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) throws IllegalArgumentException {
				setValue(LocalDate.parse(text, DateTimeFormatter.ofPattern("dd-MM-yyyy")));
			}

			@Override
			public String getAsText() throws IllegalArgumentException {
				return DateTimeFormatter.ofPattern("dd-MM-yyyy").format((LocalDate) getValue());
			}
		});
	}
}
