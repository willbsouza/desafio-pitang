package com.projeto.model.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.projeto.model.Usuario;
import com.projeto.util.FabricaConexao;
import com.projeto.util.excecoes.ErrosSistema;

public class UsuarioDAO implements Serializable {
	private static final long serialVersionUID = 1L;

	public void salvar(Usuario usuario) throws ErrosSistema {
		try {
			Connection conexao = FabricaConexao.getConexao();
			PreparedStatement st;
			if (usuario.getId() == null) {
				st = conexao.prepareStatement(
						"INSERT INTO usuario " 
						+ "(nome, email, senha, fone, tipoFone) " 
						+ "VALUES (?, ?, ?, ?, ?)");
			} else {
				st = conexao.prepareStatement(
						"UPDATE usuario SET "
						+ "nome=?, email=?, senha=?, fone=?, tipoFone=? "
						+ "WHERE id=?");
				st.setInt(6, usuario.getId());
			}

			st.setString(1, usuario.getNome());
			st.setString(2, usuario.getEmail());
			st.setString(3, usuario.getSenha());
			st.setString(4, usuario.getFone());
			st.setString(5, usuario.getTipoFone());
			st.execute();
			FabricaConexao.fecharConexao();
		} catch (SQLException e) {
			throw new ErrosSistema("Erro. Usuário não foi cadastrado!",e);
		}
	}

	public List<Usuario> buscar() throws ErrosSistema {
		Connection conexao = FabricaConexao.getConexao();
		try {
			PreparedStatement st = conexao.prepareStatement("SELECT * FROM usuario");
			ResultSet rs = st.executeQuery();
			List<Usuario> usuarios = new ArrayList<>();
			while (rs.next()) {
				Usuario usuario = new Usuario();
				usuario.setId(rs.getInt("id"));
				usuario.setNome(rs.getString("nome"));
				usuario.setEmail(rs.getString("email"));
				usuario.setSenha(rs.getString("senha"));
				usuario.setFone(rs.getString("fone"));
				usuario.setTipoFone(rs.getString("tipoFone"));
				usuarios.add(usuario);
			}
			FabricaConexao.fecharConexao();
			return usuarios;
		} catch (SQLException e) {
			throw new ErrosSistema("Erro ao buscar usuários!",e);
		}
	}
	
	public void deletar(Integer id) throws ErrosSistema {
		try {
			Connection conexao = FabricaConexao.getConexao();
			PreparedStatement st = conexao.prepareStatement("DELETE FROM usuario WHERE id=?");
			st.setInt(1, id);
			st.execute();
		} catch (SQLException e) {
			throw new ErrosSistema("Não foi possível deletar!",e);
		}
		
	}
}
