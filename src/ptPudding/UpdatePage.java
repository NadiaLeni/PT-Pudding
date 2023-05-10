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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.mysql.jdbc.ResultSetMetaData;

public class UpdatePage extends JFrame implements ActionListener{
	
	public Connection connection;
	public Statement statement;
	public ResultSet resultSet;
	public ResultSetMetaData resultMeta;
	public PreparedStatement preparedStatement;
	
	private JPanel north, center, south;
	private JLabel updateID, codeL;
	private JTextField codeF;
	private JButton updateB, cancelB;
	private DefaultTableModel dtm;
	private JTable table;
	
	private static String code;
	
	void init() {
		updateID = new JLabel("Please insert Menu Code that you want to update");
		
		codeL = new JLabel("Menu Code");
		
		codeF =  new JTextField();
		
		updateB = new JButton("Update");
		
		cancelB = new JButton("Cancel");
		
		table = new JTable(dtm);
		
//		scroll = new JScrollPane();
		
//		sp = new JScrollPane();
		
		north = new JPanel(new GridLayout(2,1,0,10));
		north.setBorder(new EmptyBorder(0,10,0,10));
		center = new JPanel(new GridLayout(1,2,0,10));
		center.setBorder(new EmptyBorder(0,10,0,10));
		south = new JPanel();
		
//		add(sp);
//		north.add(new JScrollPane(table));
		north.add(table);
		north.add(updateID);
		center.add(codeL);
		center.add(codeF);
		south.add(updateB);
		south.add(cancelB);
		
		updateB.addActionListener(this);
		cancelB.addActionListener(this);
		
	}
	
	public UpdatePage() {
		setTitle("Update Page");
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
		
		Vector<Vector<String>> rows = new Vector<>();
		
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
		
		Vector<String> columns = new Vector<String>();
		
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
		new UpdatePage();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == updateB) {
			if (codeF.getText().isEmpty()) {
				JOptionPane.showMessageDialog(this, "Please input all the field first", "WARNING", JOptionPane.WARNING_MESSAGE);
			}
			else {
				code = codeF.getText();
				new Update(code);
				
				codeF.setText("");
				
			}
		}
		else if(e.getSource() == cancelB) {
			this.dispose();
		}
	}

}
