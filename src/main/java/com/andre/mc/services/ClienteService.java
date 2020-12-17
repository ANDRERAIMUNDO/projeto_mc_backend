package com.andre.mc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import com.andre.mc.domain.Cliente;
import com.andre.mc.repositories.ClienteRepository;
import com.andre.mc.services.exception.ObjectNotFoundException;


@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repo;
	
	public Cliente buscar(Integer id) {
		Optional <Cliente> obj = repo.findById(id);
		return obj.orElseThrow(()-> new ObjectNotFoundException("Objeto não encontrado ! Id; " + id +"Tipo: "+ Cliente.class.getName()));
	}
}

