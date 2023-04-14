package org.saiypro.CustomApparel.service.db;

import java.util.List;
import java.util.Optional;

import org.saiypro.CustomApparel.entity.Cliente;
import org.saiypro.CustomApparel.entity.DetalleOrden;
import org.saiypro.CustomApparel.repository.DetallesOrdenesRepository;
import org.saiypro.CustomApparel.service.IntServiceDetallesOrdenes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DetallesOrdenesServiceJpa implements IntServiceDetallesOrdenes {

	@Autowired
	private DetallesOrdenesRepository repoDetallesOrdenes;

	@Override
	public List<DetalleOrden> obtenerDetalles() {
		return repoDetallesOrdenes.findAll();
	}

	@Override
	public void agregarDetalle(DetalleOrden detalleOrden) {
		repoDetallesOrdenes.save(detalleOrden);
	}

	@Override
	public void eliminarPorId(Integer idDetalleOrden) {
		repoDetallesOrdenes.deleteById(idDetalleOrden);
	}

	@Override
	public void eliminarPorIdCliente(Integer idCliente) {
		repoDetallesOrdenes.deleteByIdCliente(idCliente);
	}

	@Override
	public DetalleOrden buscarPorId(Integer idDetalleOrden) {
		Optional<DetalleOrden> optional = repoDetallesOrdenes.findById(idDetalleOrden);
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	@Override
	public List<DetalleOrden> buscarPorCliente(Cliente cliente) {
		return repoDetallesOrdenes.findByCliente(cliente);
	}

	@Override
	public Integer contarDetalles() {
		return repoDetallesOrdenes.cantidadDetalles();
	}

	@Override
	public Integer contarDetallesPorCliente(Integer idCliente) {
		return repoDetallesOrdenes.cantidadDetallesPorCliente(idCliente);
	}

}
