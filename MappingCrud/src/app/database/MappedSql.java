package app.database;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

import javax.swing.JOptionPane;

import app.annotations.Column;
import app.annotations.Table;
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

				nameColumn = field.getAnnotation(Column.class).name().toLowerCase();
			} else {

				nameColumn = field.getName().toLowerCase();
			}

			// type Column ( integer , varchar , numeric , etc ) !

			if (field.getType().equals(int.class)) {

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
		{
			sb.append("\n\tPRIMARY KEY(");

			for (int i = 0, searchs = 0; i < f.length; i++) {

				Field field = f[i];

				if (field.isAnnotationPresent(Column.class)) {

					Column annId = field.getAnnotation(Column.class);

					if (annId.pk()) {

						if (searchs > 0) {

							sb.append(",");
						}

						if (annId.name().isEmpty()) {

							sb.append(field.getName().toLowerCase());
						} else {

							sb.append(annId.name().toLowerCase());
						}
						searchs++;
					}

				}

			}
			sb.append(")");

		}
		sb.append("\n);");

		return sb.toString();
	}

	/**
	 * 
	 * 
	 * @param o
	 * @return
	 */
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

			if (fields.isAnnotationPresent(Table.class)) {

				nomeAtributo = fields.getAnnotation(Table.class).name();
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

	public String deleteFrom(Object o) {
		StringBuilder sb = new StringBuilder();

		Class<?> clazz = o.getClass();
		Field[] f = clazz.getDeclaredFields();
		sb.append("DELETE FROM ");
		sb.append(clazz.getSimpleName());
		sb.append(" WHERE ");

		for (int i = 0, searchs = 0; i < f.length; i++) {

			Field field = f[i];

			if (field.isAnnotationPresent(Column.class)) {

				Column annId = field.getAnnotation(Column.class);

				if (annId.pk()) {

					if (searchs > 0) {

						sb.append(",");
					}

					if (annId.name().isEmpty()) {

						sb.append(field.getName().toLowerCase());
					} else {

						sb.append(annId.name().toLowerCase());
					}
					searchs++;
				}

			}

		}

		return sb.toString();

	}

	// ------------------------------------------------------
	
	public static void main(String[] args) {
		
		
		MappedSql sql = new MappedSql();
		
		System.out.println(sql.deleteFrom(new User()));
		
		
	}
}
