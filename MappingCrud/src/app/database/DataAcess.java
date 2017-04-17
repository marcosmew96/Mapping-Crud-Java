package app.database;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JOptionPane;

public class DataAcess {

	public Connection getConnection() {

		Connection con = null;

		try {
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "1234560mar");

		} catch (SQLException e) {

			JOptionPane.showMessageDialog(null, "Não foi possivel se conectar a base de dados !");
		}

		return con;

	}

	public void disconnect() {

		Connection con = getConnection();

		if (con != null) {

			try {

				con.close();
			} catch (Exception e) {

				JOptionPane.showMessageDialog(null, "Não foi possivel desconectar !");
			}
		}

	}

	public List returnAll(String query, Object o) {

		Connection con = getConnection();

		List lista = new ArrayList<>();

		PreparedStatement ps;

		Class<?> clazz = o.getClass();

		Field[] f = clazz.getDeclaredFields();

		int i = 0;

		try {

			ps = con.prepareStatement(query);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {

				i = 1;

				while (i != f.length + 1) {

					lista.add(rs.getObject(i));
					i++;
				}

			}

		} catch (SQLException e) {

			JOptionPane.showMessageDialog(null, "A lista pode estar vazia, ou algum problema de conexao !");
		}

		return lista;

	}

	public void insert(String query) {

		Connection con = getConnection();

		PreparedStatement ps;

		try {

			ps = con.prepareStatement(query);
			ps.executeUpdate();
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	public void delete(String query) {

		Connection con = getConnection();

		PreparedStatement ps;

		try {

			ps = con.prepareStatement(query);
			ps.executeUpdate();
		} catch (SQLException e) {

			JOptionPane.showMessageDialog(null, " ID invalido, ou digitou errado !");
		}
	}

	public void createTable(String query) {

		Connection con = getConnection();

		PreparedStatement ps;

		try {

			ps = con.prepareStatement(query);
			ps.executeUpdate();

		} catch (SQLException e) {

			JOptionPane.showMessageDialog(null, " Podem haver tabelas repetidas , ou , erro na conexao !");
		}
	}

}
