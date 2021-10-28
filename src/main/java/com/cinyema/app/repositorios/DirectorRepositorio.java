package com.cinyema.app.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cinyema.app.entidades.Director;

@Repository
public interface DirectorRepositorio extends JpaRepository<Director, Long>{
	
	@Query("SELECT d FROM Director d WHERE d.nombre = :nombre")
	public Director buscarDirectorPorNombre(@Param("nombre")String nombre);

}
