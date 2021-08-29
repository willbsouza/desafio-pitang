package com.projeto.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;	
import com.projeto.util.excecoes.ErrosSistema;

public class FabricaConexao {

	private static Connection conexao;
	private static final String URL_CONEXAO = "jdbc:mysql://localhost:3305/cadastro-usuarios";
	private static final String USUARIO = "developer";
	private static final String SENHA = "12345678";
	
	public static Connection getConexao() throws ErrosSistema {
		if(conexao == null) {
			try {
				conexao = DriverManager.getConnection(URL_CONEXAO, USUARIO, SENHA);
			} catch (SQLException e) {
				throw new ErrosSistema("Erro ao conectar ao banco de dados!", e);
			}
		}
		return conexao;
	}
	
	public static void fecharConexao() throws ErrosSistema {
		if(conexao != null) {
			try {
				conexao.close();
				conexao = null;
			} catch (SQLException e) {
				throw new ErrosSistema("Erro ao fechar conexão com o BD!", e);
			}
		}
	}
}
