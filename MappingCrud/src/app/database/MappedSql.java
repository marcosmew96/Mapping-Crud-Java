package app.database;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

import javax.swing.JOptionPane;

import app.annotations.Column;
import app.annotations.Table;
import app.pojo.Animal;
import app.pojo.Carro;
import app.pojo.User;

public class MappedSql {

	// **************************

	public String createTable(Object o) {

		StringBuilder sb = new StringBuilder();
		Class<?> clazz = o.getClass();

		String tableName = "";
		if (clazz.isAnnotationPresent(Table.class)) {

			tableName = clazz.getAnnotation(Table.class).name();
		} else {

			tableName = clazz.getSimpleName().toLowerCase();
		}

		sb.append(("CREATE TABLE ")).append(tableName);
		sb.append("(").append("\n");

		Field[] f = clazz.getDeclaredFields();

		for (int i = 0; i < f.length; i++) {

			Field field = f[i];

			String nameColumn = "";
			String typeColumn = "";

			if (field.isAnnotationPresent(Column.class)) {

				nameColumn = field.getAnnotation(Column.class).name();
			} else {

				nameColumn = field.getName();
			}

			// type Column ( integer , varchar , numeric , etc ) !

			if (field.getType().equals(int.class) || field.getType().equals(Integer.class)) {

				typeColumn = "INTEGER";
			} else if (field.getType().equals(BigDecimal.class)) {

				typeColumn = "NUMERIC";
			} else if (field.getType().equals(String.class)) {

				typeColumn = "VARCHAR(255)";
			} else if (field.getType().equals(double.class)) {

				typeColumn = "DECIMAL";
			} else {

				typeColumn = "unknown";
			}

			if (i > 0) {

				sb.append(",");
			}
			// Name VARCHAR(255)
			sb.append("\n\t").append(nameColumn).append(' ').append(typeColumn);

		}
		sb.append(",");
		// Searching my primary keys =D
		sb.append("\n\tPRIMARY KEY(");
		getPkinFields(sb, f);
		sb.append(")");

		sb.append("\n);");

		return sb.toString();
	}

	private void getPkinFields(StringBuilder sb, Field[] f) {
		
		
		for (int i = 0, searchs = 0; i < f.length; i++) {

			Field field = f[i];

			if(field.isAnnotationPresent(Column.class)){
				
				
				Column id = field.getAnnotation(Column.class);
				
				if(id.pk() ==true){
					
					sb.append(id.name());
				}
				
			}

		}
	}

	// Insert para popular a base de dados.
	public String getIsert(Object o, List<String> list) {

		Class<?> clazz = o.getClass();
		StringBuilder sb = new StringBuilder();
		String tableName = "";

		if (clazz.isAnnotationPresent(Table.class)) {

			tableName = clazz.getAnnotation(Table.class).name();

		} else {

			tableName = clazz.getName().toLowerCase();
		}

		sb.append("INSERT INTO ").append(tableName);
		sb.append("(");

		Field[] f = clazz.getDeclaredFields();

		for (int i = 0; i < f.length; i++) {

			Field fields = f[i];

			String nomeAtributo;

			if (fields.isAnnotationPresent(Column.class)) {

				nomeAtributo = fields.getAnnotation(Column.class).name();
			
			} else {

				nomeAtributo = fields.getName().toLowerCase();
			}
			if (i > 0) {

				sb.append(",");
			}
			sb.append(nomeAtributo);
		}
		sb.append(")").append(" VALUES( ");

		for (int i = 0; i < f.length; i++) {

			Field fields = f[i];
			if (i > 0)
				sb.append(",");
			if (fields.getType().equals(String.class)) {

				sb.append("'");
				sb.append(list.get(i));
				sb.append("'");
			} else {

				sb.append(list.get(i));
			}
		}

		sb.append(")");

		return sb.toString();
	}

	// Select.
	public String getSearch(Object o) {

		Class<?> clazz = o.getClass();
		StringBuilder sb = new StringBuilder();

		String nomeBusca = "";

		if (clazz.isAnnotationPresent(Table.class)) {

			nomeBusca = clazz.getAnnotation(Table.class).name();
		} else {

			nomeBusca = clazz.getName().toLowerCase();
		}

		sb.append("SELECT * FROM ").append(nomeBusca);

		return sb.toString();
	}

	// Drop na tabela desejada.
	public String getDrop(Object o) {

		Class<?> clazz = o.getClass();
		StringBuilder sb = new StringBuilder();

		String nomeBusca = "";

		if (clazz.isAnnotationPresent(Table.class)) {

			nomeBusca = clazz.getAnnotation(Table.class).name();
		} else {

			nomeBusca = clazz.getName().toLowerCase();
		}

		sb.append("DROP TABLE ").append(nomeBusca);

		return sb.toString();
	}

	// Query para deletar algo na base de dados pelas chaves primárias.
	public String deleteFrom(Object o , int opcao) {
		StringBuilder sb = new StringBuilder();

		
		
		Class<?> clazz = o.getClass();
		Field[] f = clazz.getDeclaredFields();
		sb.append("DELETE FROM ");
		
		String nomeTabela;
		
		if(clazz.isAnnotationPresent(Table.class)){
			
			nomeTabela = clazz.getAnnotation(Table.class).name();
		}
		else{
			
			nomeTabela = clazz.getSimpleName().toLowerCase();
			
		}
		sb.append(nomeTabela);
		sb.append(" WHERE ");
		getPkinFields(sb, f);
		sb.append(" = ");
		sb.append(opcao);
		return sb.toString();

	}

	// ------------------------------------------------------
	

	


}
