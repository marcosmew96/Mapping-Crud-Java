package app.pojo;

import app.annotations.Column;
import app.annotations.Label;
import app.annotations.Table;
import app.database.DataAcess;
import app.database.MappedSql;

@Table(name = "Usuario")
public class User {

	@Column(pk = true, name = "ID")
	@Label(nameLbl = "Id")
	private int id;
	@Label(nameLbl = "Name")
	private String nome;
	@Label(nameLbl = "Idade")
	private int age;
	@Label(nameLbl = "Salario")
	private double salario;
	
	
	
	

}
