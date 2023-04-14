package org.saiypro.CustomApparel.service;

import java.util.List;

import org.saiypro.CustomApparel.entity.Orden;
import org.saiypro.CustomApparel.entity.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IntServiceOrdenes {
	public List<Orden> obtenerOrdenes();

	public void agregarOrden(Orden orden);

	public List<Orden> buscarPorUsuario(Usuario usuario);

	public Orden buscarPorId(Integer idOrden);

	public void eliminarTodasPorUsuario(List<Orden> ordenes);

	public Page<Orden> buscarTodas(Pageable page);

	public String generarNumeroOrden();
}
