package user;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class ConvertCtoF extends JFrame implements ActionListener {
	private JPanel panel;
	private JTextField field1, field2;

	public ConvertCtoF() {
		setSize(500, 300);
		setTitle("Convert C to F");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(new FlowLayout());
		setResizable(false);
		panel = new JPanel();
		JLabel label1 = new JLabel();
		label1.setText("Nhap do C: ");
		field1 = new JTextField(10);
		field1.setSize(50, 50);
		field1.addActionListener(this);
		JLabel label2 = new JLabel();
		label2.setText("Do F: ");
		field2 = new JTextField(10);
		field2.setSize(50, 50);
		field2.addActionListener(this);
		panel.add(label1);
		panel.add(field1);
		panel.add(label2);
		panel.add(field2);
		getContentPane().add(panel);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		try {
			if(e.getSource() == field1) {
			Double convert = Double.parseDouble(field1.getText()) * 1.8 + 32;
			field2.setText(convert.toString());}
			else if(e.getSource() == field2) {
				Double convert = (Double.parseDouble((field2.getText()))-32) / 1.8;
				field1.setText(convert.toString());
			}
		} catch (NumberFormatException error) {
			JOptionPane.showMessageDialog(this, "Khong phai la so. Nhap Lai!");
		}
	}

	public static void main(String[] args) {
		new ConvertCtoF();
	}

}
