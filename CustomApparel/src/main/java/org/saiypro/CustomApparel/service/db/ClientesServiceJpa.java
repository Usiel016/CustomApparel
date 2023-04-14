package org.saiypro.CustomApparel.service.db;

import java.util.List;
import java.util.Optional;

import org.saiypro.CustomApparel.entity.Cliente;
import org.saiypro.CustomApparel.entity.Usuario;
import org.saiypro.CustomApparel.repository.ClientesRepository;
import org.saiypro.CustomApparel.service.IntServiceClientes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ClientesServiceJpa implements IntServiceClientes {

	@Autowired
	private ClientesRepository repoClientes;

	@Override
	public List<Cliente> obtenerClientes() {
		return repoClientes.findAll();
	}

	@Override
	public void agregarCliente(Cliente cliente) {
		repoClientes.save(cliente);
	}

	@Override
	public Cliente buscarPorId(Integer idCliente) {
		Optional<Cliente> optional = repoClientes.findById(idCliente);
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	@Override
	public Cliente buscarPorUsuario(Usuario usuario) {
		return repoClientes.findByUsuario(usuario);
	}

	@Override
	public void eliminarPorId(Integer idCliente) {
		repoClientes.deleteById(idCliente);
	}

	@Override
	public Integer contarClientes() {
		return repoClientes.cantidadClientes();
	}

	@Override
	public Page<Cliente> buscarTodas(Pageable page) {
		return repoClientes.findAll(page);
	}

}
