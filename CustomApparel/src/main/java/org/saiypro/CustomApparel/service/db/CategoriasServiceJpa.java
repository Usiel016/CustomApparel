package org.saiypro.CustomApparel.service.db;

import java.util.List;
import java.util.Optional;

import org.saiypro.CustomApparel.entity.Categoria;
import org.saiypro.CustomApparel.repository.CategoriasRepository;
import org.saiypro.CustomApparel.service.IntServiceCategorias;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Primary
@Service
public class CategoriasServiceJpa implements IntServiceCategorias {

	@Autowired
	private CategoriasRepository repoCategorias;

	@Override
	public List<Categoria> obtenerCategorias() {
		return repoCategorias.findAll();
	}

	@Override
	public Categoria buscarPorId(Integer idCategoria) {
		Optional<Categoria> optional = repoCategorias.findById(idCategoria);
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	@Override
	public void guardarCategoria(Categoria categoria) {
		repoCategorias.save(categoria);
	}

	@Override
	public void eliminarPorId(Integer idCategoria) {
		repoCategorias.deleteById(idCategoria);
	}

	@Override
	public int contarCategorias() {
		return (int) repoCategorias.count();
	}

	@Override
	public Page<Categoria> buscarTodas(Pageable page) {
		return repoCategorias.findAll(page);
	}

}
