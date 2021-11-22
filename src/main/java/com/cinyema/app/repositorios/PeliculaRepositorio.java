package com.cinyema.app.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cinyema.app.entidades.Pelicula;
import com.cinyema.app.enumeraciones.Formato;
import com.cinyema.app.enumeraciones.Genero;

@Repository
public interface PeliculaRepositorio extends JpaRepository<Pelicula,Long>{
	
	@Query("SELECT p FROM Pelicula p WHERE p.alta = true")
	public List<Pelicula> buscarPeliculasActivas();
	
	@Query("SELECT p FROM Pelicula p WHERE p.titulo LIKE %:titulo%")
	public List<Pelicula> buscarPeliculaPorTitulo(@Param("titulo") String titulo);
	
	@Query("SELECT p FROM Pelicula p WHERE p.titulo LIKE :titulo")
	public Pelicula validarTituloPelicula(@Param("titulo") String titulo);
	
	@Query("SELECT p FROM Pelicula p WHERE p.genero = :genero")
	public List<Pelicula> buscarPeliculaPorGenero(@Param("genero") Genero genero);

	@Query("SELECT p FROM Pelicula p WHERE p.formato = :formato")
	public List<Pelicula> buscarPeliculaPorFormato(@Param("formato") Formato formato);
	
	@Query(value = "SELECT COUNT(p) FROM Pelicula p WHERE p.alta = true")
	public long cantidadAlta();
	
	@Query(value = "SELECT COUNT(p) FROM Pelicula p WHERE p.alta = false")
	public long cantidadBaja();

	@Query(value = "SELECT COUNT(p) FROM Pelicula p")
	public long cantidadTotal();
	
}
