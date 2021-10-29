package com.cinyema.app.funciones;

import java.util.UUID;

public class RandomId {
	
	public Long randomId() {
		String uuid = UUID.randomUUID().toString();
		Long id = (long) uuid.hashCode();
		id = id<0 ? -id:id;
		return id;
	}

}
