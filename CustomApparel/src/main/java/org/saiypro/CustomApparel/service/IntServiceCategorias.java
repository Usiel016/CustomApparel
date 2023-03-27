package org.saiypro.CustomApparel.service;

import java.util.List;

import org.saiypro.CustomApparel.entity.Categoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IntServiceCategorias {
	public List<Categoria> obtenerCategorias();
	public Categoria buscarPorId(Integer idCategoria);
	public void guardarCategoria(Categoria categoria);
	public void eliminarPorId(Integer idCategoria);
	public int contarCategorias();
	public Page<Categoria> buscarTodas(Pageable page);
}
