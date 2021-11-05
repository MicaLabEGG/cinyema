package com.cinyema.app.repositorios;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.cinyema.app.entidades.Cine;

@Repository
public interface CineRepositorio extends JpaRepository<Cine, Long>{
	
	@Query("SELECT c FROM Cine c WHERE c.nombre = :nombre")
	public List<Cine> buscarCinePorNombre(@Param("nombre") String nombre);
	

}
