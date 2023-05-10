package ptPudding;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame implements ActionListener{
	
	private JPanel north, center;
	private JLabel menu;
	private JButton insert, view, update, delete;
	
	void init() {
		menu = new JLabel("Select one of the buttons below");
		
		insert = new JButton("Insert New Menu");
		view = new JButton("View Menu");
		update = new JButton("Update Menu");
		delete = new JButton("Delete Menu");
		
		north = new JPanel();
		center = new JPanel(new GridLayout(4,1,0,15));
		center.setBorder(new EmptyBorder(0,49,50,50));
		
		north.add(menu);
		center.add(insert);
		center.add(view);
		center.add(update);
		center.add(delete);
		
		insert.addActionListener(this);
		view.addActionListener(this);
		update.addActionListener(this);
		delete.addActionListener(this);
	}
	
	public Main() {
		setTitle("Database PT Pudding");
		setEnabled(true);
		setVisible(true);
		setSize(500,250);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(true);
		setLayout(new BorderLayout(0, 10));
		
		init();
		
		add(north, BorderLayout.NORTH);
		add(center, BorderLayout.CENTER);

	}
	
	public void Main2() {
		setTitle("windows 2");
		setSize(500,500);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(true);
		setLocation(500,200);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == insert) {
			new InsertPage();
		}
		else if(e.getSource() == view) {
			new ViewPage();
		}
		else if(e.getSource() == update) {
			new UpdatePage();
		}
		else if(e.getSource() == delete) {
			new DeletePage();
		}
		
	}
	
	public static void main(String[] args) {
		new Main();
	}
	
}