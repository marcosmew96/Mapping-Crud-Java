package app.frame;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import app.annotations.Column;
import app.database.DataAcess;
import app.database.MappedSql;
import app.pojo.Animal;
import app.pojo.Carro;
import app.pojo.User;

import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.Icon;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.border.LineBorder;
import java.awt.Font;
import javax.swing.SwingConstants;

public class TelaPrincipal extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JButton btnAdd;
	private DataAcess db;
	private MappedSql sql;
	private String inserir;
	private String listar;
	private String deletar;
	private List resultadoLista;
	private JButton btnListar;
	private JScrollPane buttons;
	private JButton btnExcluir;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaPrincipal frame = new TelaPrincipal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TelaPrincipal() {

		contentPane = new JPanel();
		contentPane.setBackground(new Color(189, 183, 107));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		Carro carro  = new Carro(); // Importante !!! testar outros pojos aqui !
//		User user = new User();
		Animal animal = new Animal();
		
		// GridBagLayout e propriedades !
		getGridBagLayout();
		
		geraTela(animal);
		db.createTable(sql.createTable(animal)); //Importante !! Gera a tabela no banco, trocar aqui caso teste outros pojos !

	}

	public void getGridBagLayout() {
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_contentPane.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_contentPane.columnWeights = new double[] { 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, 1.0, Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);
	}

	public void geraTela(Object o) {

		db = new DataAcess();
		sql = new MappedSql();

		// Cria a tabela com o objeto passado !
		Class<?> clazz = o.getClass();

		int linha = 0;
		int coluna = 1;

		List<JTextField> lista = new ArrayList<>();
		for (Field fields : clazz.getDeclaredFields()) {

			JLabel lbl = new JLabel(fields.getAnnotation(Column.class).name());

			contentPane.add(lbl, gridBagLabel(0, linha));

			JTextField txtFields = new JTextField(10);

			contentPane.add(txtFields, gridBagButton(coluna, linha));

			// Lista de texto para as querys !

			lista.add(txtFields);

			linha++;
		}

		// Parte de acoes e botoes !
		// Inserir

		buttons = devolveBotoesEAcoes();
		btnAdd.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				List<String> conteudo = new ArrayList<>();

				for (JTextField jTextField : lista) {

					conteudo.add(jTextField.getText());
				}

				// Inseri no banco os dados passados pelas listas.
				if (!conteudo.get(1).equals("")) {

					inserir = sql.getIsert(o, conteudo);
					db.insert(inserir);

					renewInfos(o);

				}

				for (JTextField jTextField : lista) {
					jTextField.setText("");
				}

			}

		});

		// Botao de listar

		btnListar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				renewInfos(o);

			}

		});

		btnExcluir.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				// Considerar que o identificador unico nos casos é um inteiro.
				int opcao = Integer.parseInt(JOptionPane.showInputDialog(null, "Digite o id que quer deletar !"));

				deletar = sql.deleteFrom(o, opcao);

				db.delete(deletar);

				renewInfos(o);
			}
		});

		buttons.setViewportView(table);
	}

	// Organiza meus buttons.
	private GridBagConstraints gridBagButton(int x, int y) {

		GridBagConstraints gbBtn = new GridBagConstraints();
		gbBtn.gridwidth = 3;
		gbBtn.fill = GridBagConstraints.HORIZONTAL;
		gbBtn.insets = new Insets(0, 0, 5, 5);
		gbBtn.gridx = x;
		gbBtn.gridy = y;

		return gbBtn;
	}

	// Labels !
	public GridBagConstraints gridBagLabel(int x, int y) {

		GridBagConstraints gbLbl = new GridBagConstraints();
		gbLbl.insets = new Insets(0, 0, 5, 5);
		gbLbl.anchor = GridBagConstraints.EAST;
		gbLbl.gridx = x;
		gbLbl.gridy = y;

		return gbLbl;
	}

	// Scroll pane e personalizacao de botoes !
	public JScrollPane devolveBotoesEAcoes() {

		btnAdd = new JButton("Adicionar");
		btnAdd.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		btnAdd.setForeground(new Color(255, 255, 255));
		btnAdd.setBackground(new Color(85, 107, 47));
		GridBagConstraints gbcAdd = new GridBagConstraints();
		gbcAdd.anchor = GridBagConstraints.WEST;
		gbcAdd.fill = GridBagConstraints.VERTICAL;
		gbcAdd.insets = new Insets(0, 0, 5, 5);
		gbcAdd.gridx = 1;
		gbcAdd.gridy = 9;
		contentPane.add(btnAdd, gbcAdd);

		btnListar = new JButton("Listar");
		btnListar.setBackground(new Color(85, 107, 47));
		btnListar.setForeground(new Color(255, 255, 255));
		btnListar.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		GridBagConstraints gbc_List = new GridBagConstraints();
		gbc_List.insets = new Insets(0, 0, 5, 5);
		gbc_List.gridx = 2;
		gbc_List.gridy = 9;
		contentPane.add(btnListar, gbc_List);
		//
		btnExcluir = new JButton("Excluir");
		btnExcluir.setForeground(new Color(255, 255, 255));
		btnExcluir.setBackground(new Color(85, 107, 47));
		btnExcluir.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		btnExcluir.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_exclude = new GridBagConstraints();
		gbc_exclude.insets = new Insets(0, 0, 5, 5);
		gbc_exclude.gridx = 3;
		gbc_exclude.gridy = 9;
		contentPane.add(btnExcluir, gbc_exclude);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 19;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 15;
		contentPane.add(scrollPane, gbc_scrollPane);

		table = new JTable();

		scrollPane.setViewportView(table);

		return scrollPane;

	}

	// Utilizado para listar e atualizar os dados do table model !
	public void renewInfos(Object o) {
		
		listar = sql.getSearch(o);
		resultadoLista = db.returnAll(listar, o);

		TableModel tm = new TableModel(o, resultadoLista);
		table.setModel(tm);
	}

}
