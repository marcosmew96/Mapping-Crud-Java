package app.pojo;

import java.math.BigDecimal;

import app.annotations.Column;

import app.annotations.Table;

@Table(name ="Animal")
public class Animal {

	@Column(name = "id_animal", pk = true)
	private Integer id;

	@Column(name = "Nome")
	private String nome;

	@Column(name = "Peso")
	private BigDecimal peso;

	@Column(name = "Raca")
	private String raca;

	
	
	

}
