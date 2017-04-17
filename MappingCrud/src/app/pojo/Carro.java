package app.pojo;

import java.math.BigDecimal;

import app.annotations.Column;

import app.annotations.Table;

@Table(name="Carro")
public class Carro {
	
	@Column(name = "cod_carro" , pk = true)
	private int codigo;
	
	@Column(name = "Nome")
	private String nome;
	

}
