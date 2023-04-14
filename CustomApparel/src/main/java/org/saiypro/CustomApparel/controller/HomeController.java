package org.saiypro.CustomApparel.controller;

import org.saiypro.CustomApparel.entity.Categoria;
import org.saiypro.CustomApparel.entity.Cliente;
import org.saiypro.CustomApparel.entity.Perfil;
import org.saiypro.CustomApparel.entity.Producto;
import org.saiypro.CustomApparel.entity.Usuario;
import org.saiypro.CustomApparel.service.IntServiceCategorias;
import org.saiypro.CustomApparel.service.IntServiceClientes;
import org.saiypro.CustomApparel.service.IntServiceDetallesOrdenes;
import org.saiypro.CustomApparel.service.IntServiceOrdenes;
import org.saiypro.CustomApparel.service.IntServiceProductos;
import org.saiypro.CustomApparel.service.IntServiceUsuarios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class HomeController {

	@Autowired
	private IntServiceProductos serviceProductos;

	@Autowired
	private IntServiceCategorias serviceCategorias;

	@Autowired
	private IntServiceUsuarios serviceUsuarios;

	@Autowired
	private IntServiceClientes serviceClientes;
	
	@Autowired
	private IntServiceDetallesOrdenes serviceDetallesOrdenes;

	@Autowired
	private IntServiceOrdenes serviceOrdenes;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private CarritoController carritoCtrl;

	@GetMapping("/historialOrdenes")
	public String mostrarHistorialOrdenes(Model model, org.springframework.security.core.Authentication auth) {
		Usuario usuario = serviceUsuarios.buscarPorUsername(auth.getName());
		Cliente cliente = serviceClientes.buscarPorUsuario(usuario);
		model.addAttribute("detalles", serviceDetallesOrdenes.obtenerDetalles());
		model.addAttribute("ordenes", serviceOrdenes.buscarPorUsuario(usuario));
		model.addAttribute("cliente", cliente);
		model.addAttribute("items", carritoCtrl.contarItems(cliente.getUsername()));
		return "historialOrdenes";
	}
	
	@PostMapping("/busqueda")
	public String buscarPorCategoria(Model model, Pageable page, String descripcion, Integer idCategoria,
			org.springframework.security.core.Authentication auth, Categoria categoria) {
		Integer id = 0;
		if (descripcion.equals("") && idCategoria != null) {
			Categoria cat = serviceCategorias.buscarPorId(idCategoria);
			model.addAttribute("categoria", cat);
			model.addAttribute("productos", serviceProductos.buscarPorCategoria(idCategoria));
		} else if (!descripcion.equals("") && idCategoria == null) {
			model.addAttribute("productos", serviceProductos.buscarPorDescripcion(descripcion));
		} else if (descripcion.equals("") && idCategoria == null) {
			return "redirect:/";
		} else {
			Categoria cat = serviceCategorias.buscarPorId(idCategoria);
			model.addAttribute("categoria", cat);
			model.addAttribute("productos",
					serviceProductos.buscarTodasPorDescripcionYCategoria(descripcion, idCategoria));
		}
		if (auth != null && auth.getAuthorities().toString().equals("[Cliente]")) {
			Usuario usuario = serviceUsuarios.buscarPorUsername(auth.getName());
			Cliente cliente = serviceClientes.buscarPorUsuario(usuario);
			model.addAttribute("descripcion", descripcion);
			model.addAttribute("items", carritoCtrl.contarItems(cliente.getUsername()));
			model.addAttribute("categorias", serviceCategorias.obtenerCategorias());
			model.addAttribute("id", id);
			return "home";
		}
		model.addAttribute("descripcion", descripcion);
		model.addAttribute("categorias", serviceCategorias.obtenerCategorias());
		model.addAttribute("id", id);
		return "home";
	}

	@GetMapping("/logout")
	public String logout(HttpServletRequest request) {
		SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
		logoutHandler.logout(request, null, null);
		return "redirect:/";
	}

	@PostMapping("/registrar")
	public String guardarUsuario(Usuario usuario, BindingResult bindingResult, Model model) {
		Cliente cliente = new Cliente();
		cliente.setNombre(usuario.getNombre());
		cliente.setApellidoPaterno(usuario.getApellidoPaterno());
		cliente.setApellidoMaterno(usuario.getApellidoMaterno());
		cliente.setUsername(usuario.getUsername());
		cliente.setEmail(usuario.getEmail());
		if (!usuario.getPassword().equals(usuario.getConfirmPassword())) {
			bindingResult.rejectValue("confirmPassword", "error.confirmPassword", "Las contraseñas no coinciden");
		}
		if (bindingResult.hasErrors()) {
			model.addAttribute("usuario", usuario);
			return "formRegistro";
		}
		usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
		usuario.setConfirmPassword(passwordEncoder.encode(usuario.getConfirmPassword()));
		cliente.setPassword(usuario.getPassword());
		cliente.setFechaRegistro(usuario.getFechaRegistro());
		usuario.setEstatus(1);
		Perfil perfil = new Perfil();
		perfil.setId(3);
		usuario.agregar(perfil);
		cliente.setUsuario(usuario);
		serviceUsuarios.agregarUsuario(usuario);
		serviceClientes.agregarCliente(cliente);
		return "redirect:/";
	}

	@GetMapping("/signup")
	public String mostrarFormRegistro(Usuario usuario) {
		return "formRegistro";
	}

	@GetMapping("/login")
	public String mostrarFormLogin() {
		return "formLogin";
	}

	@GetMapping(value = "/")
	public String mostrarIndex(Model model, Pageable page, org.springframework.security.core.Authentication auth,
			Categoria categoria) {
		if (auth != null) {
			Usuario usuario = serviceUsuarios.buscarPorUsername(auth.getName());
			Cliente cliente = serviceClientes.buscarPorUsuario(usuario);
			for (Perfil perfil : usuario.getPerfiles()) {
				if (perfil.getPerfil().compareTo("Cliente") == 0) {
					Page<Producto> productos = serviceProductos.buscarTodasEnVenta(page);
					model.addAttribute("items", carritoCtrl.contarItems(cliente.getUsername()));
					model.addAttribute("categorias", serviceCategorias.obtenerCategorias());
					model.addAttribute("productos", productos);
					return "home";
				}
			}
		}
		Page<Producto> productos = serviceProductos.buscarTodasEnVenta(page);
		model.addAttribute("categorias", serviceCategorias.obtenerCategorias());
		model.addAttribute("productos", productos);
		return "home";
	}
}
