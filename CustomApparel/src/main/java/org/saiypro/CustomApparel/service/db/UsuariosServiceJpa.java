package org.saiypro.CustomApparel.service.db;

import java.util.List;
import java.util.Optional;

import org.saiypro.CustomApparel.entity.Usuario;
import org.saiypro.CustomApparel.repository.UsuariosRepository;
import org.saiypro.CustomApparel.service.IntServiceUsuarios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UsuariosServiceJpa implements IntServiceUsuarios {

	@Autowired
	private UsuariosRepository repoUsuarios;

	@Override
	public List<Usuario> obtenerUsuarios() {
		return repoUsuarios.findAll();
	}

	@Override
	public void agregarUsuario(Usuario usuario) {
		repoUsuarios.save(usuario);
	}

	@Override
	public Usuario buscarPorId(Integer idUsuario) {
		Optional<Usuario> optional = repoUsuarios.findById(idUsuario);
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	@Override
	public Usuario buscarPorUsername(String username) {
		return repoUsuarios.findByUsername(username);
	}

	@Override
	public void eliminarPorId(Integer idUsuario) {
		repoUsuarios.deleteById(idUsuario);
	}

	@Override
	public Integer contarUsuarios() {
		return repoUsuarios.cantidadUsuarios();
	}

	@Override
	public Page<Usuario> buscarTodas(Pageable page) {
		return repoUsuarios.findAll(page);
	}

}
