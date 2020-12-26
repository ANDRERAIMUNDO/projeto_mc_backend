package com.andre.mc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.andre.mc.domain.Categoria;
import com.andre.mc.domain.Produto;
import com.andre.mc.repositories.CategoriaRepository;
import com.andre.mc.repositories.ProdutoRepository;
import com.andre.mc.services.exception.ObjectNotFoundException;


@Service
public class ProdutoService {
	
	@Autowired
	private ProdutoRepository repo;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	public Produto find(Integer id) {
		Optional <Produto> obj = repo.findById(id);
		return obj.orElseThrow(()-> new ObjectNotFoundException("Objeto não encontrado ! Id; " + id +"Tipo: "+ Produto.class.getName()));
	}
		
	public Page <Produto> search(String nome,List<Integer>ids, Integer page, Integer linesPerPages, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPages,Direction.valueOf(direction), orderBy);
		List<Categoria> categorias = categoriaRepository.findAllById(ids);
		return repo.search(nome, categorias, pageRequest);
	}
}


