package org.saiypro.CustomApparel.repository;

import org.saiypro.CustomApparel.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UsuariosRepository extends JpaRepository<Usuario, Integer> {
	public Usuario findByUsername(String username);

	@Query(value = "select count(*) from Usuarios", nativeQuery = true)
	public Integer cantidadUsuarios();
}
