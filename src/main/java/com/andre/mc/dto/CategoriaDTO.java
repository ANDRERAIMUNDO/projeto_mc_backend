package com.andre.mc.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.andre.mc.domain.Categoria;

public class CategoriaDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	@NotEmpty(message="Nome da Categoria é campo obrigatorio. ")
	@Size(min=8, max=80, message="Tamanho minimo é 8 caracter, tamanho maximo é 80 caracter")
	private String nome;
	
	public CategoriaDTO() {
		
	}
	//intanciar um DTO para uma entidade
	public CategoriaDTO( Categoria obj) {
		id = obj.getId();
		nome = obj.getNome();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
}
