package com.cinyema.app.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.cinyema.app.entidades.Imagen;

@Repository
public interface ImagenRepositorio extends JpaRepository<Imagen, Long>{

}
