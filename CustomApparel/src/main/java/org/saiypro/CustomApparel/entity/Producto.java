package org.saiypro.CustomApparel.entity;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Productos")
public class Producto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String nombre;
	private String marca;
	private Integer estatus;
	private Double precio;
	private Integer stock;
	@ElementCollection
	private List<String> colores;
	@ElementCollection
	private List<String> tallas;
	private String descripcion;
	private LocalDate fechaIngreso = LocalDate.now();
	private String imagen = "no-image.png";

	@OneToOne
	@JoinColumn(name = "idCategoria")
	private Categoria categoria;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public Integer getEstatus() {
		return estatus;
	}

	public void setEstatus(Integer estatus) {
		this.estatus = estatus;
	}

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public List<String> getColores() {
		return colores;
	}

	public void setColores(List<String> colores) {
		this.colores = colores;
	}

	public List<String> getTallas() {
		return tallas;
	}

	public void setTallas(List<String> tallas) {
		this.tallas = tallas;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public LocalDate getFechaIngreso() {
		return fechaIngreso;
	}

	public void setFechaIngreso(LocalDate fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	@Override
	public String toString() {
		return "Producto [id=" + id + ", nombre=" + nombre + ", marca=" + marca + ", estatus=" + estatus + ", precio="
				+ precio + ", stock=" + stock + ", colores=" + colores + ", tallas=" + tallas + ", descripcion="
				+ descripcion + ", fechaIngreso=" + fechaIngreso + ", imagen=" + imagen + ", categoria=" + categoria
				+ "]";
	}

}
