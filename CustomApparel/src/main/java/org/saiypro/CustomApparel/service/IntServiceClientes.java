package org.saiypro.CustomApparel.service;

import java.util.List;

import org.saiypro.CustomApparel.entity.Cliente;
import org.saiypro.CustomApparel.entity.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IntServiceClientes {
	public List<Cliente> obtenerClientes();

	public void agregarCliente(Cliente cliente);

	public Cliente buscarPorId(Integer idCliente);

	public Cliente buscarPorUsuario(Usuario usuario);

	public void eliminarPorId(Integer idCliente);

	public Integer contarClientes();

	public Page<Cliente> buscarTodas(Pageable page);
}
