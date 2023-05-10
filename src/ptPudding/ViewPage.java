package ptPudding;

import java.awt.BorderLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.mysql.jdbc.ResultSetMetaData;

public class ViewPage extends JFrame{
	
	public Connection connection;
	public Statement statement;
	public ResultSet resultSet;
	public ResultSetMetaData resultMeta;
	public PreparedStatement preparedStatement;
	
	public ViewPage() {
		setTitle("View Menu");
		setEnabled(true);
		setVisible(true);
		setSize(500, 275);
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
		
		columns.add("MenuID");
		columns.add("Menu Name");
		columns.add("Menu Price");
		columns.add("Menu Stock");
		
		DefaultTableModel dtm = new DefaultTableModel(rows, columns);
		
		JTable table = new JTable(dtm);
		
		add(new JScrollPane(table));
		
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new ViewPage();
	}

}
