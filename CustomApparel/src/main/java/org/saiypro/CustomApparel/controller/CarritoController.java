package org.saiypro.CustomApparel.controller;

import java.text.DecimalFormat;
import java.util.Date;

import org.saiypro.CustomApparel.entity.Cliente;
import org.saiypro.CustomApparel.entity.DetalleOrden;
import org.saiypro.CustomApparel.entity.Orden;
import org.saiypro.CustomApparel.entity.Producto;
import org.saiypro.CustomApparel.entity.Usuario;
import org.saiypro.CustomApparel.service.IntServiceClientes;
import org.saiypro.CustomApparel.service.IntServiceDetallesOrdenes;
import org.saiypro.CustomApparel.service.IntServiceOrdenes;
import org.saiypro.CustomApparel.service.IntServiceProductos;
import org.saiypro.CustomApparel.service.IntServiceUsuarios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/carrito")
public class CarritoController {

	@Autowired
	private IntServiceDetallesOrdenes serviceDetallesOrdenes;

	@Autowired
	private IntServiceOrdenes serviceOrdenes;

	@Autowired
	private IntServiceProductos serviceProductos;

	@Autowired
	private IntServiceUsuarios serviceUsuarios;

	@Autowired
	private IntServiceClientes serviceClientes;

	DecimalFormat df = new DecimalFormat("0.00");

	@PostMapping("/cambiar")
	public String aumentarCantidadProductos(DetalleOrden detalleOrden, Integer cantidad) {
		detalleOrden.setCantidad(cantidad);
		detalleOrden.setTotal(Double.parseDouble(df.format(detalleOrden.getPrecio()*cantidad)));
		serviceDetallesOrdenes.agregarDetalle(detalleOrden);
		return "redirect:/carrito/";
	}
	
	@PostMapping("/confirmar")
	public String confirmarOrden(Cliente cliente, RedirectAttributes model) {
		Cliente c = serviceClientes.buscarPorId(cliente.getId());
		c.setTelefono(cliente.getTelefono());
		c.setCalle(cliente.getCalle());
		c.setMunicipio(cliente.getMunicipio());
		c.setColonia(cliente.getColonia());
		c.setCiudad(cliente.getCiudad());
		c.setEstado(cliente.getEstado());
		c.setCp(cliente.getCp());
		serviceClientes.agregarCliente(c);
		model.addFlashAttribute("msg",
				"La información de dirección se guardó correctamente, ya está listo para ordenar.");
		return "redirect:/carrito/";
	}

	@GetMapping("/direccion")
	public String mostrarFormDireccion(@AuthenticationPrincipal(expression = "username") String username, Model model) {
		Usuario usuario = serviceUsuarios.buscarPorUsername(username);
		Cliente cliente = serviceClientes.buscarPorUsuario(usuario);
		model.addAttribute("cliente", cliente);
		return "formDireccion";
	}
	
	@GetMapping()
	public Integer contarItems(@AuthenticationPrincipal(expression = "username") String username) {
		Usuario usuario = serviceUsuarios.buscarPorUsername(username);
		Cliente cliente = serviceClientes.buscarPorUsuario(usuario);
		return serviceDetallesOrdenes.contarDetallesPorCliente(cliente.getId());
	}

	@GetMapping("/orden")
	public String mostrarOrden(RedirectAttributes model, org.springframework.security.core.Authentication auth) {
		Usuario usuario = serviceUsuarios.buscarPorUsername(auth.getName());
		Cliente cliente = serviceClientes.buscarPorUsuario(usuario);
		Orden orden = new Orden();
		double sumaTotal = serviceDetallesOrdenes.buscarPorCliente(cliente).stream().mapToDouble(dt -> dt.getTotal())
				.sum();
		Date fechaCreacion = new Date();
		orden.setFechaCreacion(fechaCreacion);
		orden.setNumero(serviceOrdenes.generarNumeroOrden());

		// Usuario
		orden.setUsuario(usuario);
		orden.setTotal(Double.parseDouble(df.format(sumaTotal * .16 + sumaTotal)));
		serviceOrdenes.agregarOrden(orden);
		
		// Guardar detalles
		for (DetalleOrden dt : serviceDetallesOrdenes.buscarPorCliente(cliente)) {
			dt.setOrden(orden);
			Producto producto = dt.getProducto();
			producto.setStock(producto.getStock() - dt.getCantidad());
			if (producto.getStock() == 0) {
				producto.setEstatus(0);
			}
			serviceProductos.agregarProducto(producto);
			//serviceDetallesOrdenes.agregarDetalle(dt);
		}
		serviceDetallesOrdenes.eliminarPorIdCliente(cliente.getId());

		model.addFlashAttribute("msg", "La orden se realizó correctamente.");
		return "redirect:/carrito/";
	}

	@GetMapping("/eliminar")
	public String eliminarCarrito(DetalleOrden detalleOrd, RedirectAttributes model,
			org.springframework.security.core.Authentication auth) {
		Usuario usuario = serviceUsuarios.buscarPorUsername(auth.getName());
		Cliente cliente = serviceClientes.buscarPorUsuario(usuario);
		if (detalleOrd.getId() != null) {
			serviceDetallesOrdenes.eliminarPorId(detalleOrd.getId());
			model.addFlashAttribute("msg", "El item se eliminó correctamente del carrito.");
		} else {
			if (!serviceDetallesOrdenes.obtenerDetalles().isEmpty()) {
				serviceDetallesOrdenes.eliminarPorIdCliente(cliente.getId());
				model.addFlashAttribute("msg", "Se eliminaron correctamente todos los items del carrito.");
			}
		}
		return "redirect:/carrito/";
	}

	@GetMapping("/agregar")
	public String agregarCarrito(@RequestParam("id") Integer idProducto, RedirectAttributes model,
			org.springframework.security.core.Authentication auth) {
		Usuario usuario = serviceUsuarios.buscarPorUsername(auth.getName());
		Cliente cliente = serviceClientes.buscarPorUsuario(usuario);
		DetalleOrden detalleOrden = new DetalleOrden();
		Producto producto = serviceProductos.buscarPorId(idProducto);
		detalleOrden.setCantidad(1);
		detalleOrden.setPrecio(producto.getPrecio());
		detalleOrden.setNombre(producto.getNombre());
		detalleOrden.setTotal(Double.parseDouble(df.format(producto.getPrecio() * 1)));
		detalleOrden.setProducto(producto);
		detalleOrden.setCliente(cliente);
		boolean ingresado = serviceDetallesOrdenes.buscarPorCliente(cliente).stream()
				.anyMatch(p -> p.getProducto().getId() == idProducto);
		if (!ingresado) {
			serviceDetallesOrdenes.agregarDetalle(detalleOrden);
			model.addFlashAttribute("msg", "El item se agregó correctamente al carrito.");
		} else {
			for (DetalleOrden dorden : serviceDetallesOrdenes.buscarPorCliente(cliente)) {
				if (dorden.getProducto().getId().compareTo(idProducto) == 0) {
					if((1+dorden.getCantidad()) > dorden.getProducto().getStock()) {
						model.addFlashAttribute("msg2", "Ya tienes la cantidad límite de compra para este producto.");
						return "redirect:/carrito/";
					} else {
						detalleOrden.setId(dorden.getId());
						detalleOrden.setCantidad(1 + dorden.getCantidad());
						detalleOrden.setTotal(Double.parseDouble(df.format(producto.getPrecio() * (1 + dorden.getCantidad()))));
						serviceDetallesOrdenes.agregarDetalle(detalleOrden);
						model.addFlashAttribute("msg", "El item se agregó correctamente al carrito.");
					}
				}
			}
		}
		return "redirect:/carrito/";
	}

	@GetMapping("/")
	public String carrito(Model model, org.springframework.security.core.Authentication auth) {
		Usuario usuario = serviceUsuarios.buscarPorUsername(auth.getName());
		Cliente cliente = serviceClientes.buscarPorUsuario(usuario);
		double sumaTotal = serviceDetallesOrdenes.buscarPorCliente(cliente).stream().mapToDouble(dt -> dt.getTotal())
				.sum();
		model.addAttribute("carrito", serviceDetallesOrdenes.buscarPorCliente(cliente));
		model.addAttribute("items", contarItems(cliente.getUsername()));
		model.addAttribute("orden", sumaTotal);
		model.addAttribute("cliente", cliente);
		model.addAttribute("iva", df.format(sumaTotal * .16));
		model.addAttribute("totalIva", df.format(sumaTotal * .16 + sumaTotal));
		return "carrito";
	}
}