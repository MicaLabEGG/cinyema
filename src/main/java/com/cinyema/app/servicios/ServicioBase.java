package com.cinyema.app.servicios;

import java.util.List;

public interface ServicioBase<E> {
	List<E> listar() throws Exception;
	E crear(E entity) throws Exception;
	E modificar(E entity,Long id) throws Exception;
	E buscarPorId(Long id) throws Exception;
	void eliminarPorId(Long id) throws Exception;

}
