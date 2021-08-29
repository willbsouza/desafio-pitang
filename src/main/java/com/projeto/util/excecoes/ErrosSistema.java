package com.projeto.util.excecoes;

public class ErrosSistema extends Exception{
	private static final long serialVersionUID = 1L;
	
	public ErrosSistema(String msg) {
		super(msg);
	}
	
	public ErrosSistema(String msg, Throwable th) {
		super(msg, th);
	}

}
