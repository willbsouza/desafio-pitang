package com.projeto.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import com.projeto.model.Usuario;
import com.projeto.model.dao.UsuarioDAO;
import com.projeto.util.excecoes.ErrosSistema;

@SessionScoped
@Named
public class UsuarioController implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Inject
	private Usuario usuario;
	private List<Usuario> usuarios = new ArrayList<>();
	private UsuarioDAO usuarioDAO = new UsuarioDAO();
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public List<Usuario> getUsuarios() {
		return usuarios;
	}	
	
	public void adicionarUsuario() {
		try {
			usuarioDAO.salvar(usuario);
			usuario = new Usuario();
			adicionarStatus("Sucesso!", "Usuário cadastrado com sucesso!", FacesMessage.SEVERITY_INFO);
		} catch (ErrosSistema e) {
			
			adicionarStatus(e.getMessage(), e.getCause().getMessage(), FacesMessage.SEVERITY_ERROR);
		}
	}
	
	public void listar() {
		try {
			usuarios = usuarioDAO.buscar();
			if(usuarios == null || usuarios.size() == 0) {
				adicionarStatus("Sem retorno!", "Nenhum usuário encontrado.", FacesMessage.SEVERITY_WARN);
			}
		} catch (ErrosSistema e) {
			adicionarStatus(e.getMessage(), e.getCause().getMessage(), FacesMessage.SEVERITY_ERROR);
		}
	}
	
	public void deletar(Usuario usuario) {
		try {
			usuarioDAO.deletar(usuario.getId());
			adicionarStatus("Sucesso!", "Usuário foi deletado com sucesso!", FacesMessage.SEVERITY_INFO);
		} catch (ErrosSistema e) {
			adicionarStatus(e.getMessage(), e.getCause().getMessage(), FacesMessage.SEVERITY_ERROR);
		}
	}
	
	public void editar(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public void adicionarStatus(String titulo, String detalhe, FacesMessage.Severity tipo) {
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage message = new FacesMessage(tipo, titulo, detalhe);
		context.addMessage(null, message);
	}
}
