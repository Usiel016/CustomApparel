package org.saiypro.CustomApparel.service;

import java.util.List;

import org.saiypro.CustomApparel.entity.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IntServiceProductos {
	public List<Producto> obtenerEnVenta();

	public List<Producto> obtenerProductos();

	public void agregarProducto(Producto producto);

	public Producto buscarPorId(Integer idProducto);

	public List<Producto> buscarPorCategoria(Integer idCategoria);

	public List<Producto> buscarPorDescripcion(String descripcion);

	public List<Producto> buscarTodasPorDescripcionYCategoria(String descripcion, Integer idCategoria);

	public void eliminarPorId(Integer idProducto);

	public Page<Producto> buscarTodas(Pageable page);

	public Page<Producto> buscarTodasEnVenta(Pageable page);

	public Page<Producto> buscarTodasPorCategoria(Integer idCategoria, Pageable page);

	public Integer contarProductos();
}
