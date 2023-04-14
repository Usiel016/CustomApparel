package org.saiypro.CustomApparel.repository;

import org.saiypro.CustomApparel.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CategoriasRepository extends JpaRepository<Categoria, Integer> {
	@Query(value = "select count(*) from Categorias", nativeQuery = true)
	public Integer cantidadCategorias();
}