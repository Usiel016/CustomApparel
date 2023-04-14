package org.saiypro.CustomApparel.repository;

import org.saiypro.CustomApparel.entity.Cliente;
import org.saiypro.CustomApparel.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ClientesRepository extends JpaRepository<Cliente, Integer> {
	public Cliente findByUsuario(Usuario usuario);

	@Query(value = "select count(*) from Clientes", nativeQuery = true)
	public Integer cantidadClientes();
}
