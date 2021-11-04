package com.cinyema.app.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cinyema.app.entidades.Sala;

@Repository
public interface SalaRepositorio extends JpaRepository<Sala, Long> {

}
