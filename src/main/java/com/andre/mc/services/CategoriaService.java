package com.andre.mc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.andre.mc.domain.Categoria;
import com.andre.mc.dto.CategoriaDTO;
import com.andre.mc.repositories.CategoriaRepository;
import com.andre.mc.services.exception.DataIntegrityException;
import com.andre.mc.services.exception.ObjectNotFoundException;


@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository repo;
	
	public Categoria find(Integer id) {
		Optional <Categoria> obj = repo.findById(id);
		return obj.orElseThrow(()-> new ObjectNotFoundException("Objeto não encontrado ! Id; " + id +" Tipo: "+ Categoria.class.getName()));
	}
	
	public Categoria isert (Categoria obj) {
		obj.setId(null);
		return repo.save(obj);
	}
	
	public Categoria update (Categoria obj) {
		Categoria newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}
	public void delete (Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possivel excluir um categoria que possui produtos");
		}
	}
	public List <Categoria>findAll(){
		return repo.findAll();
	}
	
	//criando servico de mapeamento de paginas
	public Page<Categoria> findPage(Integer page, Integer linesPerPages, String orderBy, String direction){
		PageRequest pageResquest = PageRequest.of(page, linesPerPages,Direction.valueOf(direction), orderBy);
		return repo.findAll(pageResquest);
	}
	//metodo auxiliar instacia Categoria em DTO
	public Categoria fromDTO(CategoriaDTO objDto) {
		return new Categoria(objDto.getId(), objDto.getNome());
	}
	private void updateData (Categoria newObj, Categoria obj) {
		newObj.setNome(obj.getNome());
	}
}

