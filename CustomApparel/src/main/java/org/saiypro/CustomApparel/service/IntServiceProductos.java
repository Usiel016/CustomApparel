package org.saiypro.CustomApparel.service;

import java.util.List;

import org.saiypro.CustomApparel.entity.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IntServiceProductos {
	public List<Producto> obtenerEnStock();
	public List<Producto> obtenerProductos();
	public Producto buscarPorId(Integer idProducto);
	public void guardarProducto(Producto producto);
	public void eliminarPorId(Integer idProducto);
	public Integer contarProductos();
	public Page<Producto> buscarTodas(Pageable page);
}
