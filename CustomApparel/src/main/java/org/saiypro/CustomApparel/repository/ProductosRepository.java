package org.saiypro.CustomApparel.repository;

import org.saiypro.CustomApparel.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductosRepository extends JpaRepository<Producto, Integer> {

}
