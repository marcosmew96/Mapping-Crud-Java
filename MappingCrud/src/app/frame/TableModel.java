package app.frame;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import app.annotations.Column;
import app.annotations.Label;

public class TableModel extends AbstractTableModel {

	private int tableRows;// Linhas
	private int columns;// Colunas
	private String[][] tableContent; // Conteúdo em matriz para ser exibido !
	private List<String> atts;
	private List list;

	public TableModel(Object o, List lista) { // construtor da minha table
												// recebendo o objeto para obter
												// informacoes.

		Class<?> clazz = o.getClass();

		Field[] f = clazz.getDeclaredFields();

		this.list = lista;
		this.tableRows = lista.size() / f.length;
		this.columns =  f.length ;
		this.tableContent = new String[tableRows][columns];
		pegaAtributo(o);
		carregaMeuTable();
	}

	public void pegaAtributo(Object o) {

		atts = new ArrayList<String>();
		Class<?> clazz = o.getClass();

		for (Field f : clazz.getDeclaredFields()) {

			atts.add(f.getAnnotation(Label.class).nameLbl().toString());

		}

	}

	public void carregaMeuTable() {
		
		int get = 0;
		
		for(int i = 0 ; i < this.tableRows; i++){
			
			for(int j = 0 ; j < this.columns ; j++){
				
				tableContent[i][j] = list.get(get).toString();
				
				get++;
			}
		}

	}

	@Override
	public String getColumnName(int column) {

		return atts.get(column);
	}

	@Override
	public int getColumnCount() {

		return this.columns;

	}

	@Override
	public int getRowCount() {

		return this.tableRows;
	}

	@Override
	public Object getValueAt(int tableRows, int columns) {

		return tableContent[tableRows][columns];
	}

}
