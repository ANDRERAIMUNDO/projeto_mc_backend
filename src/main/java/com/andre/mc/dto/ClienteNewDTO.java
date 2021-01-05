package com.andre.mc.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.andre.mc.services.validation.ClienteInsert;


@ClienteInsert
public class ClienteNewDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotEmpty(message ="Nome é campo obrigatorio. ")
	@Size(min = 10, max = 100, message="Nome entre 10 e 100 caracter. ")
	private String nome;
	
	@NotEmpty(message="Email é campo obrigatorio. ")
	@Email(message = "Email em formato invalido. ")
	private String email;
	
	@NotEmpty(message ="Campo obrigatorio. ")
	private String cpfOuCnpj;
	

	private Integer tipo;
	
	@NotEmpty(message ="Telefone é campo obrigatorio. ")
	private String telefone1;
	
	private String telefone2;
	
	private String telefone3;
	
	@NotEmpty(message ="Logradouro é campo obrigatorio. ")
	private String logradouro;
	
	@NotEmpty(message ="Número é campo obrigatorio. ")
	private String numero;
	
	private String complemento;
	
	@NotEmpty(message ="Bairro é campo obrigatorio. ")
	private String bairro;
	
	@NotEmpty(message ="Cep é campo obrigatorio. ")
	private String cep;
	
	private Integer cidadeId;
	
	public ClienteNewDTO() {
		
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCpfOuCnpj() {
		return cpfOuCnpj;
	}

	public void setCpfOuCnpj(String cpfOuCnpj) {
		this.cpfOuCnpj = cpfOuCnpj;
	}

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getTelefone1() {
		return telefone1;
	}

	public void setTelefone1(String telefone1) {
		this.telefone1 = telefone1;
	}

	public String getTelefone2() {
		return telefone2;
	}

	public void setTelefone2(String telefone2) {
		this.telefone2 = telefone2;
	}

	public String getTelefone3() {
		return telefone3;
	}

	public void setTelefone3(String telefone3) {
		this.telefone3 = telefone3;
	}

	public Integer getCidadeId() {
		return cidadeId;
	}

	public void setCidadeId(Integer cidadeId) {
		this.cidadeId = cidadeId;
	}
	
}
