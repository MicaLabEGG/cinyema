package com.cinyema.app.servicios;

import java.util.List;

public interface ServicioBase<E> {
	List<E> listar() throws Exception;
	E registrar(E entity) throws Exception;
	E editar(E entity) throws Exception;
	E obtenerPorId(Long id) throws Exception;
	void eliminar(Long id) throws Exception;

}
