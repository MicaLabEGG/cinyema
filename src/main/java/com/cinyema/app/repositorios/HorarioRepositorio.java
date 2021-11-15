package com.cinyema.app.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cinyema.app.entidades.Horario;

@Repository
public interface HorarioRepositorio extends JpaRepository<Horario, Long>{

}
