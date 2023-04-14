package org.saiypro.CustomApparel.service;

import java.util.List;

import org.saiypro.CustomApparel.entity.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IntServiceUsuarios {
	public List<Usuario> obtenerUsuarios();

	public void agregarUsuario(Usuario usuario);

	public Usuario buscarPorId(Integer idUsuario);

	public Usuario buscarPorUsername(String username);

	public void eliminarPorId(Integer idUsuario);

	public Integer contarUsuarios();

	public Page<Usuario> buscarTodas(Pageable page);
}
