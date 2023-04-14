package org.saiypro.CustomApparel.service.db;

import java.util.List;
import java.util.Optional;

import org.saiypro.CustomApparel.entity.Empleado;
import org.saiypro.CustomApparel.repository.EmpleadosRepository;
import org.saiypro.CustomApparel.service.IntServiceEmpleados;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EmpleadosServiceJpa implements IntServiceEmpleados {

	@Autowired
	private EmpleadosRepository repoEmpleados;

	@Override
	public List<Empleado> obtenerEmpleados() {
		return repoEmpleados.findAll();
	}

	@Override
	public void agregarEmpleado(Empleado empleado) {
		repoEmpleados.save(empleado);
	}

	@Override
	public Empleado buscarPorId(Integer idEmpleado) {
		Optional<Empleado> optional = repoEmpleados.findById(idEmpleado);
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	@Override
	public void eliminarPorId(Integer idEmpleado) {
		repoEmpleados.deleteById(idEmpleado);
	}

	@Override
	public Integer contarEmpleados() {
		return repoEmpleados.cantidadEmpleados();
	}

	@Override
	public Page<Empleado> buscarTodas(Pageable page) {
		return repoEmpleados.findAll(page);
	}

}
