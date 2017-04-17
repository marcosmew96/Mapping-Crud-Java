package app.pojo;

import app.annotations.Column;
import app.annotations.Table;

@Table(name = "CAD_PROFESSOR")
public class Professor {
	
	
	@Column(name = "cod_prof", pk=true)
	private int codigo;
	@Column(name = "Saldo")
	double saldo;
	@Column(name = "Nome")
	private String nome;
	

}
