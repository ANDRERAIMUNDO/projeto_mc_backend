package com.andre.mc.services;

import java.awt.image.BufferedImage;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.andre.mc.domain.Cidade;
import com.andre.mc.domain.Cliente;
import com.andre.mc.domain.Endereco;
import com.andre.mc.domain.enums.Perfil;
import com.andre.mc.domain.enums.TipoCliente;
import com.andre.mc.dto.ClienteDTO;
import com.andre.mc.dto.ClienteNewDTO;
import com.andre.mc.repositories.ClienteRepository;
import com.andre.mc.repositories.EnderecoRepository;
import com.andre.mc.security.UserSS;
import com.andre.mc.services.exception.AuthorizationException;
import com.andre.mc.services.exception.DataIntegrityException;
import com.andre.mc.services.exception.ObjectNotFoundException;


@Service
public class ClienteService {
	
	@Autowired
	private BCryptPasswordEncoder pe;
	
	@Autowired
	private ClienteRepository repo;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private S3Service s3Service;
	
	@Value("${img.prefix.client.profile}")
	private String prefix;
	
	@Autowired
	private ImageService imageService; 
	
	public Cliente find(Integer id) {
		UserSS user = UserService.authenticated();
		if (user == null || user.hasRole(Perfil.ADMIN) && !id.equals(user.getId())) {
			throw new AuthorizationException("Acesso negado! ");
		}
		
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}
	
	@Transactional
	public Cliente insert (Cliente obj) {
		obj.setId(null);
		obj = repo.save(obj);
		enderecoRepository.saveAll(obj.getEnderecos());
		return obj;	
	}
	public Cliente update (Cliente obj) {
		Cliente newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}
	public void delete (Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possivel excluir um cliente que possui pedidos");
		}
	}
	public List <Cliente>findAll(){
		return repo.findAll();
	}
	
	//mapeamento de paginas
	public Page<Cliente> findPage(Integer page, Integer linesPerPages, String orderBy, String direction){
		PageRequest pageResquest = PageRequest.of(page, linesPerPages,Direction.valueOf(direction), orderBy);
		return repo.findAll(pageResquest);
	}
	//retorno clienteDTO
	public Cliente fromDTO(ClienteDTO objDto) {
			return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null,null);
		}
	//retorno ClienteNewDTO
	public Cliente fromDTO(ClienteNewDTO objDto) {
		Cliente cli = new Cliente(null, objDto.getNome(),
				objDto.getEmail(), 
				objDto.getCpfOuCnpj(),
				TipoCliente.toEnum(objDto.getTipo()),
				pe.encode(objDto.getSenha()));
		Cidade cid = new Cidade(objDto.getCidadeId(), null, null);
		
		Endereco end = new Endereco(null, objDto.getLogradouro(),
				objDto.getNumero(),
				objDto.getComplemento(), 
				objDto.getBairro(), 
				objDto.getCep(), 
				cli,
				cid
				);
		cli.getEnderecos().add(end);
		cli.getTelefones().add(objDto.getTelefone1());
		if (objDto.getTelefone2()!=null) {
			cli.getTelefones().add(objDto.getTelefone2());
		}
		if (objDto.getTelefone3()!=null) {
			cli.getTelefones().add(objDto.getTelefone3());
		}
		return cli;
	}
	private void updateData (Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}
	
	public URI uploadProfilePicture(MultipartFile multipartFile) {
		UserSS user = UserService.authenticated();
		if (user == null) {
			throw new AuthorizationException("Acesso negado");
		}
		
		BufferedImage JpgImage = imageService.getJpgImageFromFile(multipartFile);
		String fileName = prefix + user.getId() + ".jpg";
		
		return s3Service.uploadFile(imageService.getInputStream(JpgImage, "jpg"), fileName, "iamge");
	}
}

