package com.cinyema.app.controladores;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.cinyema.app.entidades.Pelicula;
import com.cinyema.app.servicios.PeliculaServicio;

@Controller
@RequestMapping("/imagen")
public class ImagenControlador {
	
	@Autowired
	private PeliculaServicio peliculaServicio;
	
	@GetMapping("/pelicula/{id}")
	public ResponseEntity<byte[]> ImagenPelicula(@PathVariable Long idPelicula) throws Exception{
		Optional<Pelicula> result = peliculaServicio.buscarPeliculaPorId(idPelicula);
		if(result.isEmpty()) {
			throw new Exception("No hay Pelicula");
		}else {
			Pelicula pelicula = result.get();
			byte[] imagen = pelicula.getImagen().getContenido();
			
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.IMAGE_JPEG);
			
			return new ResponseEntity<>(imagen,headers,HttpStatus.OK);
		}
		
	}

}
