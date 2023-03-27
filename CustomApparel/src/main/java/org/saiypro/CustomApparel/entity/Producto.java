package org.saiypro.CustomApparel.entity;

import java.time.LocalDate;
import java.util.List;

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
	private List<String> colores;
	private List<String> tallas;
	private String descripcion;
	private LocalDate fechaIngreso = LocalDate.now();
	private String imagen = "img0.png";

	@OneToOne
	@JoinColumn(name = "idCategoria")
	private Categoria categoria;

	public Producto() {

	}

	public Producto(Integer id, String nombre, String marca, Integer estatus, Double precio, List<String> colores,
			List<String> tallas, String descripcion, LocalDate fechaIngreso, String imagen, Categoria categoria) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.marca = marca;
		this.estatus = estatus;
		this.precio = precio;
		this.colores = colores;
		this.tallas = tallas;
		this.descripcion = descripcion;
		this.fechaIngreso = fechaIngreso;
		this.imagen = imagen;
		this.categoria = categoria;
	}

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
				+ precio + ", colores=" + colores + ", tallas=" + tallas + ", descripcion=" + descripcion
				+ ", fechaIngreso=" + fechaIngreso + ", imagen=" + imagen + ", categoria=" + categoria + "]";
	}
}
