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

public class Update extends JFrame implements ActionListener{
	
	public Connection connection;
	public Statement statement;
	public ResultSet resultSet;
	public ResultSetMetaData resultMeta;
	public PreparedStatement preparedStatement;
	
	private JPanel north, center, south;
	private JLabel update, codeL, nameL, priceL, stockL;
	private JTextField codeF, nameF, priceF, stockF;
	private JButton submitB, cancelB;
	
	private static String code;
	private String name;
	private int price, stock;

//	public Update(String code) {
//		// TODO Auto-generated constructor stub
//		this.code = code;
//	}
	
	void init() {
		update = new JLabel("Please fill the menu data");
		
		codeL = new JLabel("Menu Code");
		nameL = new JLabel("Menu Name");
		priceL = new JLabel("Menu Price");
		stockL = new JLabel("Menu Stock");
		
		codeF = new JTextField();
		codeF.setText(code);
		nameF = new JTextField();
		nameF.setText(name);
		priceF = new JTextField();
		priceF.setText(String.valueOf(price));
		stockF = new JTextField();
		stockF.setText(String.valueOf(stock));
		
		submitB = new JButton("Submit");
		cancelB = new JButton("Cancel");
		
		north = new JPanel();
		center = new JPanel(new GridLayout(4,2,0,10));
		center.setBorder(new EmptyBorder(0,10,0,10));
		south = new JPanel();
		
		north.add(update);
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
	
	public Update(String code) {
		this.code = code;
		
		setTitle("Update Menu");
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
			preparedStatement = connection.prepareStatement("SELECT * FROM `menu` WHERE `MenuID` = ?");
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
		
		try {
			resultSet = preparedStatement.executeQuery();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			while(resultSet.next()) {
				name = resultSet.getString("MenuName");
				price = resultSet.getInt("MenuPrice");
				stock = resultSet.getInt("MenuStock");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		init();
		
		add(north, BorderLayout.NORTH);
		add(center, BorderLayout.CENTER);
		add(south, BorderLayout.SOUTH);
		
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Update(code);
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
					preparedStatement = connection.prepareStatement("UPDATE `menu` SET `MenuPrice` = ?, `MenuStock` = ? WHERE `MenuID` = ?");
					preparedStatement.setInt(1, price);
					preparedStatement.setInt(2, stock);
					preparedStatement.setString(3, code);
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
				
				JOptionPane.showMessageDialog(this, "Success Update Menu", "Information", JOptionPane.INFORMATION_MESSAGE);
				
				this.dispose();
			}
		}
		else if(e.getSource() == cancelB) {
			this.dispose();
		}
	}

}
