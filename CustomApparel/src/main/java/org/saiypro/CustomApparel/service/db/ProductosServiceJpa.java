package org.saiypro.CustomApparel.service.db;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.saiypro.CustomApparel.entity.Producto;
import org.saiypro.CustomApparel.repository.ProductosRepository;
import org.saiypro.CustomApparel.service.IntServiceProductos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Primary
@Service
public class ProductosServiceJpa implements IntServiceProductos {

	@Autowired
	private ProductosRepository repoProductos;

	@Override
	public List<Producto> obtenerEnStock() {
		List<Producto> productosEnVenta = new LinkedList<>();
		for (Producto producto : repoProductos.findAll()) {
			if (producto.getEstatus() == 1) {
				productosEnVenta.add(producto);
			}
		}
		return productosEnVenta;
	}

	@Override
	public List<Producto> obtenerProductos() {
		return repoProductos.findAll();
	}

	@Override
	public Producto buscarPorId(Integer idProducto) {
		Optional<Producto> optional = repoProductos.findById(idProducto);
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	@Override
	public void guardarProducto(Producto producto) {
		repoProductos.save(producto);
	}

	@Override
	public void eliminarPorId(Integer idProducto) {
		repoProductos.deleteById(idProducto);
	}

	@Override
	public Integer contarProductos() {
		return (int) repoProductos.count();
	}

	@Override
	public Page<Producto> buscarTodas(Pageable page) {
		return repoProductos.findAll(page);
	}
}
