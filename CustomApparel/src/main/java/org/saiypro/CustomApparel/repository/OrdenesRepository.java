package org.saiypro.CustomApparel.repository;

import java.util.List;

import org.saiypro.CustomApparel.entity.Orden;
import org.saiypro.CustomApparel.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdenesRepository extends JpaRepository<Orden, Integer> {
	public List<Orden> findByUsuario(Usuario usuario);
}
