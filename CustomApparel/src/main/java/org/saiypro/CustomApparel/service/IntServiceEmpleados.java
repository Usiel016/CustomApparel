package org.saiypro.CustomApparel.service;

import java.util.List;

import org.saiypro.CustomApparel.entity.Empleado;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IntServiceEmpleados {
	public List<Empleado> obtenerEmpleados();

	public void agregarEmpleado(Empleado empleado);

	public Empleado buscarPorId(Integer idEmpleado);

	public void eliminarPorId(Integer idEmpleado);

	public Integer contarEmpleados();

	public Page<Empleado> buscarTodas(Pageable page);
}
