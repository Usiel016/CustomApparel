package org.saiypro.CustomApparel.repository;

import org.saiypro.CustomApparel.entity.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EmpleadosRepository extends JpaRepository<Empleado, Integer> {
	@Query(value = "select count(*) from Empleados", nativeQuery = true)
	public Integer cantidadEmpleados();
}
