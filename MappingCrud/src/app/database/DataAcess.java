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
			con = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/test2", "sa", "");

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

		Statement ps;

		Class<?> clazz = o.getClass();

		Field[] f = clazz.getDeclaredFields();

		int i = 0;

		try {

			ps = con.createStatement();
			ResultSet rs = ps.executeQuery(query);

			while (rs.next()) {

				i = 1;

				while (i != f.length + 1) {

					lista.add(rs.getObject(i));
					i++;
				}

			}

		} catch (SQLException e) {

			JOptionPane.showMessageDialog(null, "Problema na busca !");
		}

		return lista;

	}

	public void insert(String query) {

		Connection con = getConnection();

		Statement ps;

		try {

			ps = con.createStatement();
			ps.execute(query);
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	public void delete(String query) {

		Connection con = getConnection();

		PreparedStatement ps;

		try {

			ps = con.prepareStatement(query);
			ps.executeQuery();
		} catch (SQLException e) {

			JOptionPane.showMessageDialog(null, "Algo invalido !");
		}
	}

	public void createTable(String query) {

		Connection con = getConnection();

		Statement ps;

		try {

			ps = con.createStatement();
			ps.execute(query);

		} catch (SQLException e) {

			JOptionPane.showMessageDialog(null, "Algo invalido !");
		}
	}

}
