package com.cinyema.app.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cinyema.app.entidades.Cine;

@Repository
public interface CineRepositorio extends JpaRepository<Cine, Long>{
	

}
