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

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.mysql.jdbc.ResultSetMetaData;

public class InsertPage extends JFrame implements ActionListener{
	
	public Connection connection;
	public Statement statement;
	public ResultSet resultSet;
	public ResultSetMetaData resultMeta;
	public PreparedStatement preparedStatement;
	
	private JPanel north, center, south;
	private JLabel insert, codeL, nameL, priceL, stockL;
	private JTextField codeF, nameF, priceF, stockF;
	private JButton submitB, cancelB;
	
	String code, name;
	int price, stock;
	
	void init() {
		insert = new JLabel("Please fill the menu data");
		
		codeL = new JLabel("Menu Code");
		nameL = new JLabel("Menu Name");
		priceL = new JLabel("Menu Price");
		stockL = new JLabel("Menu Stock");
		
		codeF = new JTextField();
		nameF = new JTextField();
		priceF = new JTextField();
		stockF = new JTextField();
		
		submitB = new JButton("Submit");
		cancelB = new JButton("Cancel");
		
		north = new JPanel();
		center = new JPanel(new GridLayout(4,2,0,10));
		center.setBorder(new EmptyBorder(0,10,0,10));
		south = new JPanel();
		
		north.add(insert);
		center.add(codeL);
		center.add(codeF);
		center.add(nameL);
		center.add(nameF);
		center.add(priceL);
		center.add(priceF);
		center.add(stockL);
		center.add(stockF);
		south.add(submitB);
		south.add(cancelB);
		
		submitB.addActionListener(this);
		cancelB.addActionListener(this);
		
	}
	
	public InsertPage() {
		setTitle("Insert Menu");
		setEnabled(true);
		setVisible(true);
		setSize(500, 275);
		setLocationRelativeTo(null);
		setResizable(true);
		setLayout(new BorderLayout(0, 10));
		
		init();
		
		add(north, BorderLayout.NORTH);
		add(center, BorderLayout.CENTER);
		add(south, BorderLayout.SOUTH);
		
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
		
		
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new InsertPage();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == submitB) {
			if (codeF.getText().isEmpty() || nameF.getText().isEmpty() || priceF.getText().isEmpty() || stockF.getText().isEmpty()) {
				JOptionPane.showMessageDialog(this, "Please input all the field first", "WARNING", JOptionPane.WARNING_MESSAGE);
			}
			else {
				code = codeF.getText();
				name = nameF.getText();
				price = Integer.parseInt(priceF.getText());
				stock = Integer.parseInt(stockF.getText());
				
				try {
					preparedStatement = connection.prepareStatement("INSERT INTO `menu` VALUES (?, ?, ?, ?);");
					preparedStatement.setString(1, code);
					preparedStatement.setString(2, name);
					preparedStatement.setInt(3, price);
					preparedStatement.setInt(4, stock);
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
				
				JOptionPane.showMessageDialog(this, "Success Insert New Menu", "Information", JOptionPane.INFORMATION_MESSAGE);
				
				codeF.setText("");
				nameF.setText("");
				priceF.setText("");
				stockF.setText("");
				
			}
		}
		else if(e.getSource() == cancelB) {
			this.dispose();
		}
	}

}
