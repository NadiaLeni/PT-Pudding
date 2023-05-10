package ptPudding;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.mysql.jdbc.ResultSetMetaData;

public class DeletePage extends JFrame implements ActionListener{
	
	public Connection connection;
	public Statement statement;
	public ResultSet resultSet;
	public ResultSetMetaData resultMeta;
	public PreparedStatement preparedStatement;
	
	private JPanel north, center, south;
	private JLabel delete = new JLabel("Please insert Menu Code that you want to delete");
	private JLabel codeL;
	private JTextField codeF;
	private JButton deleteB, cancelB;
	private DefaultTableModel dtm;
	private JTable table;
	
	private static String code;
	
	Vector<Vector<String>> rows = new Vector<>();
	Vector<String> columns = new Vector<String>();
	
	public void refreshTable() {
		try {
			statement = connection.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			resultSet = statement.executeQuery("SELECT * FROM `menu`;");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			while (resultSet.next()) {
				Vector<String> row = new Vector<String>();
				row.add(resultSet.getString("MenuID"));
				row.add(resultSet.getString("MenuName"));
				row.add(resultSet.getString("MenuPrice"));
				row.add(resultSet.getString("MenuStock"));
				rows.add(row);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		dtm = new DefaultTableModel(rows, columns);
		dtm.setDataVector(rows, columns);
		table.
		
//		init();
		
		remove(north);
		
		north.add(table);
		north.add(delete);
		
		add(north, BorderLayout.NORTH);
//		add(center, BorderLayout.CENTER);
//		add(south, BorderLayout.SOUTH);
	}
	
	void init() {
		
		codeL = new JLabel("Menu Code");
		
		codeF =  new JTextField();
		
		deleteB = new JButton("Delete");
		cancelB = new JButton("Cancel");
		
		table = new JTable(dtm);
		
		north = new JPanel(new GridLayout(2,1,0,10));
		north.setBorder(new EmptyBorder(0,10,0,10));
		center = new JPanel(new GridLayout(1,2,0,10));
		center.setBorder(new EmptyBorder(0,10,0,10));
		south = new JPanel();
	
		north.add(table);
		north.add(delete);
		center.add(codeL);
		center.add(codeF);
		south.add(deleteB);
		south.add(cancelB);
		
		deleteB.addActionListener(this);
		cancelB.addActionListener(this);
		
	}
	
	public DeletePage() {
		setTitle("Delete Page");
		setEnabled(true);
		setVisible(true);
		setSize(500, 400);
		setLocationRelativeTo(null);
		setResizable(true);
		setLayout(new BorderLayout(0, 10));

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost/ptpuding", "root", "");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			System.out.println("Database Connection Error");
		}
		
		try {
			statement = connection.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			resultSet = statement.executeQuery("SELECT * FROM `menu`;");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			while (resultSet.next()) {
				Vector<String> row = new Vector<String>();
				row.add(resultSet.getString("MenuID"));
				row.add(resultSet.getString("MenuName"));
				row.add(resultSet.getString("MenuPrice"));
				row.add(resultSet.getString("MenuStock"));
				rows.add(row);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		columns.add("Menu Code");
		columns.add("Menu Name");
		columns.add("Menu Price");
		columns.add("Menu Stock");
		
		dtm = new DefaultTableModel(rows, columns);
		
		init();
		
		add(north, BorderLayout.NORTH);
		add(center, BorderLayout.CENTER);
		add(south, BorderLayout.SOUTH);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new DeletePage();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == deleteB) {
			if(codeF.getText().isEmpty()) {
				JOptionPane.showMessageDialog(this, "Please input all the field first", "WARNING", JOptionPane.WARNING_MESSAGE);
			}
			else {
				code = codeF.getText();
				
				try {
					preparedStatement = connection.prepareStatement("DELETE FROM `menu` WHERE `MenuID` = ?");
					preparedStatement.setString(1, code);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				try {
					preparedStatement.execute();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				JOptionPane.showMessageDialog(this, "Success Delete Menu", "Information", JOptionPane.INFORMATION_MESSAGE);
				
				codeF.setText("");
				
				refreshTable();
			}
		}
		else if(e.getSource() == cancelB) {
			this.dispose();
		}
	}

}
