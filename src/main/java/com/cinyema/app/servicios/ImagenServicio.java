package com.cinyema.app.servicios;

import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.cinyema.app.entidades.Imagen;
import com.cinyema.app.repositorios.ImagenRepositorio;

@Service
public class ImagenServicio {
	
	@Autowired
	private ImagenRepositorio imagenRepositorio;
	
	@Transactional
	public Imagen guardarImagen(MultipartFile archivo) throws Exception{
		if(archivo != null) {
			try {
				Imagen imagen = new Imagen();
				imagen.setMime(archivo.getContentType());
				imagen.setNombre(archivo.getName());
				imagen.setContenido(archivo.getBytes());
				
				return imagenRepositorio.save(imagen);
				
			}catch(Exception e) {
				System.out.println(e.getMessage());
			}
		}
		
		return null;
	}
	
	@Transactional
	public Imagen actualizarImagen(Long idImagen, MultipartFile archivo) throws Exception{
		if(archivo != null) {
			try {
				Imagen imagen = new Imagen();
				
				if(idImagen != null) {
					Optional<Imagen> result = imagenRepositorio.findById(idImagen);
					if(result.isPresent()) {
						imagen = result.get();
					}
				}
				
				imagen.setMime(archivo.getContentType());
				imagen.setNombre(archivo.getName());
				imagen.setContenido(archivo.getBytes());
				
				return imagenRepositorio.save(imagen);
				
			}catch(Exception e) {
				System.out.println(e.getMessage());
			}
		}
		
		return null;
	}
	

}
