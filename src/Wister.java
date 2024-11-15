import java.sql.*;
import io.github.cdimascio.dotenv.Dotenv;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.mysql.cj.xdevapi.Statement;
import java.awt.Image;
import java.awt.Insets;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.sql.SQLIntegrityConstraintViolationException;

public class Wister extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	static Connection con;
	static int numOfAffectedRow;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		createConnection();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Wister frame = new Wister();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception e) {
					System.out.println(e.getMessage());
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Wister() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(400, 200, 556, 367);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		setResizable(false);
		getContentPane().setBackground(UIManager.getColor("window"));
		SpringLayout springLayout = new SpringLayout();
		getContentPane().setLayout(springLayout);

		JLabel msg = new JLabel("Hello Wister database user, please enter your information");
		msg.setForeground(UIManager.getColor("textText"));
		getContentPane().add(msg);

		JLabel adminLabel = new JLabel("username:");
		springLayout.putConstraint(SpringLayout.EAST, adminLabel, -344, SpringLayout.EAST, getContentPane());
		adminLabel.setForeground(UIManager.getColor("textText"));
		getContentPane().add(adminLabel);
		JTextField userfield = new JTextField();

		adminLabel.setLabelFor(userfield);

		springLayout.putConstraint(SpringLayout.SOUTH, msg, -37, SpringLayout.NORTH, userfield);
		springLayout.putConstraint(SpringLayout.SOUTH, userfield, -170, SpringLayout.SOUTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, userfield, 6, SpringLayout.EAST, adminLabel);
		getContentPane().add(userfield);
		userfield.setColumns(10);

		JLabel adminlabel2 = new JLabel("password:");
		springLayout.putConstraint(SpringLayout.NORTH, adminlabel2, 163, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, adminLabel, -15, SpringLayout.NORTH, adminlabel2);
		springLayout.putConstraint(SpringLayout.EAST, adminlabel2, 0, SpringLayout.EAST, adminLabel);
		adminlabel2.setForeground(UIManager.getColor("textText"));
		getContentPane().add(adminlabel2);

		JPasswordField passwordField = new JPasswordField();
		springLayout.putConstraint(SpringLayout.NORTH, passwordField, 5, SpringLayout.SOUTH, userfield);
		springLayout.putConstraint(SpringLayout.WEST, passwordField, 6, SpringLayout.EAST, adminlabel2);
		passwordField.setColumns(10);
		getContentPane().add(passwordField);
		adminlabel2.setLabelFor(passwordField);

		JButton loginButton = new JButton("log in");
		springLayout.putConstraint(SpringLayout.NORTH, loginButton, 25, SpringLayout.SOUTH, passwordField);
		springLayout.putConstraint(SpringLayout.WEST, loginButton, 233, SpringLayout.WEST, getContentPane());
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = userfield.getText();
				String password = String.valueOf(passwordField.getPassword());
				if (login(name, password)) {
					JOptionPane.showMessageDialog(null, "Welcome! " + name, "Success!", JOptionPane.PLAIN_MESSAGE);
					dispose();
					if (name.equalsIgnoreCase("managerUser")) { // if the user is a manager open manager view
						JFrame managerFrame = new JFrame();
						managerFrame.setBounds(450, 220, 450, 334);
						managerFrame.setLocationRelativeTo(null);

						managerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

						JButton managerSearchBtn = new JButton("Search"); // TODO: EMAN
						managerSearchBtn.addActionListener(new ActionListener() { // 'SEARCH' manager veiw
							public void actionPerformed(ActionEvent e) {

								JFrame managerSearch = new JFrame();
								managerSearch.setTitle("SEARCH");
								managerSearch.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
								managerSearch.setBounds(100, 100, 830, 400);
								managerSearch.setLocationRelativeTo(null);
								managerSearch.getContentPane().setLayout(new BorderLayout());

								JTabbedPane tabbedPane = new JTabbedPane();

								getContentPane().add(tabbedPane);

								JPanel Employee = new JPanel();
								Employee.setLayout(null);
								tabbedPane.addTab("Employee", Employee);

								JPanel Branch = new JPanel();
								Branch.setLayout(null);
								tabbedPane.addTab("Branch", Branch);

								JPanel Item = new JPanel();
								Item.setLayout(null);
								tabbedPane.addTab("Item", Item);

								JComboBox<String> comboBox = new JComboBox<>();
								comboBox.setModel(new DefaultComboBoxModel<>(new String[] {"Show All", "Emp_ID", "Emp_Name",
										"Branch_code", "Role", "Gender", "Salary" }));
								comboBox.setBounds(75, 43, 88, 22);
								Employee.add(comboBox);

								JLabel lblNewLabel = new JLabel("search by:");
								lblNewLabel.setBounds(10, 45, 69, 18);
								Employee.add(lblNewLabel);

								JTextField textField = new JTextField();
								textField.setBounds(270, 44, 96, 20);
								Employee.add(textField);
								textField.setColumns(10);

								JLabel lblNewLabel_1 = new JLabel("value:");
								lblNewLabel_1.setBounds(228, 45, 49, 19);
								Employee.add(lblNewLabel_1);

								JButton btnNewButton = new JButton("SEARCH");
								btnNewButton.setBounds(607, 43, 102, 23);
								Employee.add(btnNewButton);

								Object[][] data = {};
								JTable etable = new JTable(new DefaultTableModel(data,
										new String[] { "ID", "Branch_Code", "Name", "Residence_Number", "Phone",
												"Gender", "Role", "Neighborhood", "Street", "Post_Code", "Salary" }));
								JTable btable = new JTable(new DefaultTableModel(data,
										new String[] { "Branch_code", "Work_time", "City", "Neighborhood", "Street" }));
								JTable itable = new JTable(new DefaultTableModel(data,
										new String[] { "Item_Name", "Price", "Item_Type", "Calories" }));

								etable.setEnabled(false);
								btable.setEnabled(false);
								itable.setEnabled(false);

								JScrollPane escrollPane = new JScrollPane(etable);
								JScrollPane bscrollPane = new JScrollPane(btable);
								JScrollPane iscrollPane = new JScrollPane(itable);

								escrollPane.setBounds(5, 120, 800, 200);
								bscrollPane.setBounds(140, 120, 500, 200);
								iscrollPane.setBounds(140, 120, 500, 200);

								Employee.add(escrollPane);
								Branch.add(bscrollPane);
								Item.add(iscrollPane);

								etable.getColumnModel().getColumn(0).setPreferredWidth(30);
								etable.getColumnModel().getColumn(1).setPreferredWidth(80);
								etable.getColumnModel().getColumn(2).setPreferredWidth(50);
								etable.getColumnModel().getColumn(5).setPreferredWidth(40);
								etable.getColumnModel().getColumn(6).setPreferredWidth(50);
								etable.getColumnModel().getColumn(10).setPreferredWidth(40);
								etable.getColumnModel().getColumn(3).setPreferredWidth(90);
								etable.getColumnModel().getColumn(4).setPreferredWidth(65);
								etable.getColumnModel().getColumn(8).setPreferredWidth(50);

								btnNewButton.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent E){
										try{
											java.sql.Statement stm = con.createStatement();
											ResultSet resultSet;
											if(comboBox.getSelectedItem().equals("Show All"))
											   resultSet=stm.executeQuery(
												"SELECT * "+
												"FROM EMPLOYEE ");
										    else if(comboBox.getSelectedItem().equals("Emp_ID") || comboBox.getSelectedItem().equals("Branch_code") || comboBox.getSelectedItem().equals("Salary") ){
											    int num = Integer.parseInt(textField.getText());
												resultSet = stm.executeQuery(
												"SELECT * "+
												"FROM EMPLOYEE "+
												"WHERE "+comboBox.getSelectedItem()+" = "+num
												);}
											else 
											    resultSet= stm.executeQuery(
													"SELECT * "+
													"FROM EMPLOYEE "+
													"WHERE "+comboBox.getSelectedItem()+"= \'"+textField.getText()+"\'"

												);
												

										}
										catch(NumberFormatException ex){
											JOptionPane.showMessageDialog(null, "Please Enter a umber!", "invalid input", JOptionPane.ERROR_MESSAGE);
										}
										catch(SQLException ex){
											System.out.println(ex.getMessage());
										}
									}
								});

								JLabel lblNewLabel_2 = new JLabel("search by:");
								lblNewLabel_2.setBounds(155, 45, 69, 18);
								Branch.add(lblNewLabel_2);

								JTextField textField_1 = new JTextField();
								textField_1.setBounds(414, 44, 75, 20);
								Branch.add(textField_1);
								textField_1.setColumns(10);

								JComboBox<String> comboBox_1 = new JComboBox<>();
								comboBox_1.setModel(new DefaultComboBoxModel<>(new String[] { "Branch_code", "City" }));
								comboBox_1.setBounds(221, 43, 88, 22);
								Branch.add(comboBox_1);

								JLabel lblNewLabel_3 = new JLabel("value:");
								lblNewLabel_3.setBounds(367, 45, 49, 19);
								Branch.add(lblNewLabel_3);

								JButton btnNewButton_1 = new JButton("SEARCH");
								btnNewButton_1.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										try{
											java.sql.Statement stm = con.createStatement();
											ResultSet resultSet = stm.executeQuery(
												"SELECT "
												);

										}
										catch(SQLException ex){

										}
									}
								});
								btnNewButton_1.setBounds(552, 43, 88, 23);
								Branch.add(btnNewButton_1);

								JLabel lblNewLabel_4 = new JLabel("search by:");
								lblNewLabel_4.setBounds(140, 47, 69, 22);
								Item.add(lblNewLabel_4);

								JComboBox<String> comboBox_2 = new JComboBox<>();
								comboBox_2.setModel(
										new DefaultComboBoxModel<>(new String[] { "Item_Name", "Item_Type", "Price" }));
								comboBox_2.setBounds(203, 47, 81, 22);
								Item.add(comboBox_2);

								JLabel lblNewLabel_5 = new JLabel("value");
								lblNewLabel_5.setBounds(339, 49, 37, 18);
								Item.add(lblNewLabel_5);

								JTextField textField_2 = new JTextField();
								textField_2.setBounds(386, 46, 81, 20);
								Item.add(textField_2);
								textField_2.setColumns(10);

								JButton btnNewButton_2 = new JButton("SEARCH");
								btnNewButton_2.setBounds(525, 45, 115, 23);
								Item.add(btnNewButton_2);

								managerSearch.getContentPane().add(tabbedPane);
								managerSearch.setVisible(true);

							}
						});

						JButton managerUpdateBtn = new JButton("Update");// TODO: EMAN
						managerUpdateBtn.addActionListener(new ActionListener() { // 'UPDATE' manager veiw
							public void actionPerformed(ActionEvent e) {

								JFrame managerUpdate = new JFrame();
								managerUpdate.setTitle("UPDATE");
								managerUpdate.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
								managerUpdate.setBounds(100, 100, 550, 400);
								managerUpdate.getContentPane().setLayout(new BorderLayout());
								managerUpdate.setLocationRelativeTo(null);

								JTabbedPane tabbedPane = new JTabbedPane();

								JPanel Item = new JPanel();
								Item.setLayout(null);

								JPanel Employee = new JPanel();
								Employee.setLayout(null);
								tabbedPane.addTab("Employee", Employee);

								JLabel lblNewLabel = new JLabel(
										"Enter ID of the employee you want to update his info:");
								lblNewLabel.setBounds(10, 51, 312, 31);
								Employee.add(lblNewLabel);

								JComboBox<String> comboBox_1 = new JComboBox<String>();
								comboBox_1.setModel(
										new DefaultComboBoxModel<String>(new String[] { "Salary", "Emp_Phone" }));
								comboBox_1.setBounds(332, 98, 96, 22);
								Employee.add(comboBox_1);

								JLabel lblNewLabel_1 = new JLabel("select which info you want to update:");
								lblNewLabel_1.setBounds(87, 93, 235, 33);
								Employee.add(lblNewLabel_1);

								JTextField textField = new JTextField();
								textField.setBounds(332, 141, 96, 22);
								Employee.add(textField);
								textField.setColumns(10);

								JLabel lblNewLabel_2 = new JLabel("new value:");
								lblNewLabel_2.setBounds(225, 137, 75, 31);
								Employee.add(lblNewLabel_2);

								JTextField textField_6 = new JTextField();
								textField_6.setBounds(332, 56, 96, 20);
								Employee.add(textField_6);
								textField_6.setColumns(10);

								JButton btnNewButton = new JButton("UPDATE");
								btnNewButton.setBounds(332, 201, 96, 23);
								Employee.add(btnNewButton);

								btnNewButton.addActionListener(new ActionListener() { // update empolyee info(manager
																						// veiw)
									public void actionPerformed(ActionEvent E) {
										try {
											java.sql.Statement stm = con.createStatement();
											if (comboBox_1.getSelectedItem().equals("Emp_Phone"))
												numOfAffectedRow = stm.executeUpdate("UPDATE EMPLOYEE "
														+ "SET Emp_Phone = \'" + textField.getText() + "\' "
														+ " WHERE Employee_ID=" + textField_6.getText());
											else {
												int num = Integer.parseInt(textField_6.getText());
												numOfAffectedRow = stm
														.executeUpdate("UPDATE EMPLOYEE " + "SET Salary = "
																+ textField.getText() + " WHERE Employee_ID=" + num);
											}

											if (numOfAffectedRow == 0)
												JOptionPane.showMessageDialog(null,
														"There is no Empolyee with the received ID", "not found :(",
														JOptionPane.ERROR_MESSAGE);
											else
												JOptionPane.showMessageDialog(null,
														"The " + comboBox_1.getSelectedItem()
																+ " of the Empolyee with the received ID ("
																+ textField_6.getText() + ") was updated to be: "
																+ textField.getText(),
														"Updated seccessfully :)", JOptionPane.INFORMATION_MESSAGE);

										} catch (NumberFormatException e) {
											JOptionPane.showMessageDialog(null, "please enter a number!", "Input Error",
													JOptionPane.WARNING_MESSAGE);
										}

										catch (SQLException e) {
											System.out.println(e.getMessage());
										}
									}
								});

								JPanel Branch = new JPanel();
								Branch.setLayout(null);
								tabbedPane.addTab("Branch", Branch);

								JLabel lblNewLabel_3 = new JLabel(
										"enter branch code of the branch you want to update  its working time:\r\n");
								lblNewLabel_3.setHorizontalAlignment(SwingConstants.LEFT);
								lblNewLabel_3.setBounds(10, 57, 405, 23);
								Branch.add(lblNewLabel_3);

								JTextField textField_1 = new JTextField();
								textField_1.setBounds(425, 58, 96, 20);
								Branch.add(textField_1);
								textField_1.setColumns(10);

								JLabel lblNewLabel_5 = new JLabel("the new time:");
								lblNewLabel_5.setBounds(10, 80, 96, 20);
								Branch.add(lblNewLabel_5);

								JComboBox<String> comboBox = new JComboBox<String>();
								comboBox.setModel(new DefaultComboBoxModel<String>(new String[] { "00", "01", "02",
										"03", "04", "05", "06", "07", "07", "08", "09", "10", "11", "12", "13", "14",
										"15", "16", "17", "18", "19", "20", "21", "22", "23" }));
								comboBox.setBounds(135, 103, 42, 22);
								Branch.add(comboBox);

								JComboBox<String> comboBox_2 = new JComboBox<String>();
								comboBox_2.setModel(new DefaultComboBoxModel<String>(new String[] { "00", "01", "02",
										"03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15",
										"16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28",
										"29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41",
										"42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54",
										"55", "56", "57", "58", "59" }));
								comboBox_2.setBounds(135, 136, 42, 22);
								Branch.add(comboBox_2);

								JButton btnNewButton_1 = new JButton("UPDATE");
								btnNewButton_1.setBounds(399, 287, 96, 23);
								Branch.add(btnNewButton_1);

								btnNewButton_1.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent E) {

										try {
											java.sql.Statement stm = con.createStatement();
											numOfAffectedRow = stm.executeUpdate("UPDATE BRANCH " + "SET Work_time= \'"
													+ comboBox.getSelectedItem() + ":" + comboBox_2.getSelectedItem()
													+ ":00\' " + "WHERE Branch_code = " + textField_1.getText());
											if (numOfAffectedRow == 1)
												JOptionPane.showMessageDialog(null,
														"The working time of the branch(" + textField_1.getText()
																+ ") was updated to be: " + comboBox.getSelectedItem()
																+ ":" + comboBox_2.getSelectedItem() + ":00",
														"Updated seccessfully :)", JOptionPane.INFORMATION_MESSAGE);
											else if (numOfAffectedRow == 0) {
												JOptionPane.showMessageDialog(null,
														"There is no Branch with the received Branch code!",
														"not found :(", JOptionPane.ERROR_MESSAGE);
											}

										}

										catch (SQLException e) {
											System.out.println(e.getMessage());
										} catch (Exception e) {
											System.err.println(e.getMessage());
										}
									}
								});

								JLabel lblNewLabel_4 = new JLabel("Hour");
								lblNewLabel_4.setBounds(86, 107, 49, 14);
								Branch.add(lblNewLabel_4);

								JLabel lblNewLabel_6 = new JLabel("Minutes ");
								lblNewLabel_6.setBounds(86, 140, 49, 14);
								Branch.add(lblNewLabel_6);
								tabbedPane.addTab("Item", Item);

								JLabel lblNewLabel_9 = new JLabel("enter item name that yuo want to update its price:");
								lblNewLabel_9.setBounds(10, 53, 303, 20);
								Item.add(lblNewLabel_9);

								JTextField textField_4 = new JTextField();
								textField_4.setBounds(312, 53, 96, 20);
								Item.add(textField_4);
								textField_4.setColumns(10);

								JLabel lblNewLabel_10 = new JLabel("new price:");
								lblNewLabel_10.setBounds(227, 96, 62, 14);
								Item.add(lblNewLabel_10);

								JTextField textField_5 = new JTextField();
								textField_5.setBounds(312, 93, 96, 20);
								Item.add(textField_5);
								textField_5.setColumns(10);

								JButton btnNewButton_2 = new JButton("UPDATE");
								btnNewButton_2.setBounds(312, 140, 96, 23);
								Item.add(btnNewButton_2);

								btnNewButton_2.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent E) {
										try {
											java.sql.Statement stm = con.createStatement();
											int price = Integer.parseInt(textField_5.getText());
											numOfAffectedRow = stm.executeUpdate("UPDATE ITEM " + "SET Price=" + price
													+ " WHERE Item_Name= \'" + textField_4.getText() + "\'");
											if (numOfAffectedRow == 0)
												JOptionPane.showMessageDialog(null,
														"There is no Item with the received Name!", "not found :(",
														JOptionPane.ERROR_MESSAGE);
											else
												JOptionPane.showMessageDialog(null,
														"The price of " + textField_4.getText() + " was updated to be: "
																+ textField_5.getText() + ".00SR",
														"Updated seccessfully :)", JOptionPane.INFORMATION_MESSAGE);

										} catch (SQLException e) {
											System.err.println(e.getMessage());
										} catch (NumberFormatException e) {
											JOptionPane.showMessageDialog(null,
													"please enter a number to set the new price", "invalid input",
													JOptionPane.ERROR_MESSAGE);
										}
									}
								});

								managerUpdate.getContentPane().add(tabbedPane);
								managerUpdate.setVisible(true);

							}
						});

						JButton managerRemoveBtn = new JButton("Remove");// TODO: EMAN
						managerRemoveBtn.addActionListener(new ActionListener() { // 'REMOVE' manager veiw
							public void actionPerformed(ActionEvent e) {
								JFrame managerRemove = new JFrame();
								managerRemove.setTitle("REMOVE");
								managerRemove.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
								managerRemove.setBounds(100, 100, 550, 400);
								managerRemove.setLocationRelativeTo(null);
								managerRemove.getContentPane().setLayout(new BorderLayout());

								JTabbedPane tabbedPane = new JTabbedPane();

								// Create panels to add to each tab
								JPanel Employee = new JPanel();
								Employee.setLayout(null);

								JPanel Branch = new JPanel();
								Branch.setLayout(null);

								JPanel Item = new JPanel();
								Item.setLayout(null);
								JLabel label_1 = new JLabel("enter name of the item you want to remove:   ");
								label_1.setBounds(10, 59, 273, 20);
								Item.add(label_1);

								// Add panels as tabs to the JTabbedPane
								JLabel labal_2 = new JLabel("remove employees by thier:");
								labal_2.setHorizontalAlignment(SwingConstants.CENTER);
								labal_2.setBounds(134, 32, 181, 32);
								Employee.add(labal_2);

								// set up tab Employee (remove) until line 93
								JComboBox<String> comboBox = new JComboBox<String>();
								comboBox.setModel(new DefaultComboBoxModel<String>(
										new String[] { " Employee_ID", "Emp_Name", "Gender", "Role", "Salary" }));
								comboBox.setBounds(325, 38, 96, 20);
								Employee.add(comboBox);

								JTextField textField = new JTextField();
								textField.setBounds(325, 79, 96, 20);
								Employee.add(textField);
								textField.setColumns(10);

								JLabel lblNewLabel = new JLabel("enter a value to remove all employees that match:");
								lblNewLabel.setBounds(10, 79, 305, 20);
								Employee.add(lblNewLabel);

								JButton button = new JButton("Remove");
								button.setBounds(332, 139, 89, 23);
								Employee.add(button);

								button.addActionListener(new ActionListener() {
									@Override
									public void actionPerformed(ActionEvent e) {
										try {
											java.sql.Statement stmt = con.createStatement();
											// ResultSet sr;
											if (comboBox.getSelectedItem().equals("Emp_Name")
													|| comboBox.getSelectedItem().equals("Gender")
													|| comboBox.getSelectedItem().equals("Role"))
												numOfAffectedRow = stmt.executeUpdate(
														"DELETE FROM EMPLOYEE " + "WHERE " + comboBox.getSelectedItem()
																+ "=" + "\'" + textField.getText() + "\'");

											else {
												int num = Integer.parseInt(textField.getText());
												numOfAffectedRow = stmt.executeUpdate("DELETE FROM EMPLOYEE " + "WHERE "
														+ comboBox.getSelectedItem() + "=" + num);
											}

											if (numOfAffectedRow == 0)
												JOptionPane.showMessageDialog(null,
														"There is no Empolyee with the received value", "not found :(",
														JOptionPane.ERROR_MESSAGE);
											else
												JOptionPane.showMessageDialog(null,
														"All Empolyees with \'" + comboBox.getSelectedItem() + " = "
																+ textField.getText() + "\' are Removed seccessfully ",
														"Removed seccessfully :)", JOptionPane.INFORMATION_MESSAGE);

										} catch (NumberFormatException E) {
											JOptionPane.showMessageDialog(null, "please enter a number", "Input Error",
													JOptionPane.WARNING_MESSAGE);
										} catch (SQLException E) {
											System.out.println(E.getMessage());
										}
									}
								});

								JLabel lblNewLabel_2 = new JLabel(
										"enter the code of the Branch \r\nyou want to remove:");
								lblNewLabel_2.setBounds(10, 36, 305, 31);
								Branch.add(lblNewLabel_2);

								JTextField textField_1 = new JTextField();
								textField_1.setBounds(325, 41, 96, 20);
								Branch.add(textField_1);
								textField_1.setColumns(10);

								JButton Button_1 = new JButton("Remove");
								Button_1.setBounds(325, 72, 96, 23);
								Branch.add(Button_1);

								Button_1.addActionListener(new ActionListener() {

									public void actionPerformed(ActionEvent e) {
										try {
											int code = Integer.parseInt(textField_1.getText());

											java.sql.Statement stmt = con.createStatement();
											numOfAffectedRow = stmt
													.executeUpdate("DELETE FROM BRANCH " + "WHERE Branch_code=" + code

													);

											if (numOfAffectedRow == 0)
												JOptionPane.showMessageDialog(null,
														"There is no Branch with the recived Branch_code",
														"not found :(", JOptionPane.ERROR_MESSAGE);
											else
												JOptionPane.showMessageDialog(null,
														"The Branch (with Branch_code = " + textField_1.getText()
																+ ") is removed seccessfully.",
														"Removed seccessfully :)", JOptionPane.INFORMATION_MESSAGE);
										} catch (NumberFormatException ex) {
											JOptionPane.showMessageDialog(null, "please enter a number", "Input Error",
													JOptionPane.WARNING_MESSAGE);

										}

										catch (SQLIntegrityConstraintViolationException eX) {
											JOptionPane.showMessageDialog(null,
													"CAN'T COMPLETE REMOVAL!!!\n A number of employees are working in Branch("
															+ textField_1.getText() + ").",
													"Remove fails :(", JOptionPane.ERROR_MESSAGE);

										} catch (SQLException ex) {
											System.out.println(ex.getMessage());

										}

									}

								});

								JTextArea txtrEman = new JTextArea();
								txtrEman.setText("eman\r\nameen\r\nf\r\nf\r\nf\r\nf\r\nd\r\nd\r\nd\r\nd\r\n");// TODO:
																												// for
																												// testing
								txtrEman.setBounds(10, 130, 411, 94);
								Branch.add(txtrEman);

								JTextField textField_2 = new JTextField();
								textField_2.setBounds(293, 59, 96, 20);
								Item.add(textField_2);
								textField_2.setColumns(10);

								JButton btnNewButton = new JButton("Remove");
								btnNewButton.setBounds(290, 96, 99, 23);
								Item.add(btnNewButton);

								btnNewButton.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent ev) {
										try {
											java.sql.Statement stmt = con.createStatement();
											numOfAffectedRow = stmt.executeUpdate("DELETE FROM ITEM "
													+ "WHERE Item_Name= \'" + textField_2.getText() + "\'");
											if (numOfAffectedRow == 0)
												JOptionPane.showMessageDialog(null,
														"There is no item with the received name!", "not found :(",
														JOptionPane.ERROR_MESSAGE);
											else
												JOptionPane.showMessageDialog(null,
														"The Item ( " + textField_2.getText()
																+ " ) is removed seccessfully.",
														"Removed Seccessfully :)", JOptionPane.INFORMATION_MESSAGE);

										} catch (SQLException e) {
											System.out.println(e.getMessage());
										}
									}
								});

								tabbedPane.addTab("Employee", Employee);
								tabbedPane.addTab("Branch", Branch);
								tabbedPane.addTab("Item", Item);

								managerRemove.getContentPane().add(tabbedPane);

								managerRemove.setVisible(true);
							}
						});

						JButton managerAddBtn = new JButton("Add"); // TODO: Ghaida
						managerAddBtn.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								JFrame managerAdd = new JFrame();
								managerAdd.setTitle("Add");
								managerAdd.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
								managerAdd.setBounds(100, 100, 550, 400);
								managerAdd.setLocationRelativeTo(null);
								managerAdd.getContentPane().setLayout(new BorderLayout());

								JTabbedPane tabbedPane = new JTabbedPane();

								// Create panels to add to each tab
								JPanel Employee = new JPanel();
								Employee.setLayout(null);

								JPanel Branch = new JPanel();
								Branch.setLayout(null);

								JPanel Item = new JPanel();
								Item.setLayout(null);

								// Add panels as tabs to the JTabbedPane
								tabbedPane.addTab("Employee", Employee);
								ButtonGroup group = new ButtonGroup();
								Employee.setLayout(null);

								JLabel lblNewLabel_1 = new JLabel("Enter the new employee's info:");
								lblNewLabel_1.setBounds(168, 14, 192, 16);
								Employee.add(lblNewLabel_1);

								JLabel lblNewLabel = new JLabel("Employee ID:");
								lblNewLabel.setBounds(54, 47, 82, 16);
								Employee.add(lblNewLabel);

								JLabel emp_name = new JLabel("Name:");
								emp_name.setBounds(96, 75, 40, 16);
								Employee.add(emp_name);

								JLabel lblNewLabel_3_1_1_1 = new JLabel("Branch:");
								lblNewLabel_3_1_1_1.setBounds(317, 78, 46, 16);
								Employee.add(lblNewLabel_3_1_1_1);

								JComboBox comboBox_1_1 = new JComboBox();
								comboBox_1_1.setBounds(368, 71, 89, 27);
								comboBox_1_1.setModel(new DefaultComboBoxModel(new String[] { "Hi", "Hello", "Test" })); // TODO:
																															// fix
								Employee.add(comboBox_1_1);

								JLabel lblNewLabel_3_1_1_2 = new JLabel("Salary:");
								lblNewLabel_3_1_1_2.setBounds(96, 103, 41, 16);
								Employee.add(lblNewLabel_3_1_1_2);

								JLabel lblNewLabel_3_1_1_1_1 = new JLabel("Gender:");
								lblNewLabel_3_1_1_1_1.setBounds(312, 106, 48, 16);
								Employee.add(lblNewLabel_3_1_1_1_1);

								JRadioButton rdbtnMale = new JRadioButton("Male");
								rdbtnMale.setBounds(368, 103, 62, 23);
								group.add(rdbtnMale);
								Employee.add(rdbtnMale);

								JRadioButton rdbtnFemale = new JRadioButton("Female");
								rdbtnFemale.setBounds(435, 103, 94, 23);
								group.add(rdbtnFemale);
								Employee.add(rdbtnFemale);

								JLabel lblResidenceNumber = new JLabel("Residence Number:");
								lblResidenceNumber.setBounds(15, 129, 121, 16);
								Employee.add(lblResidenceNumber);

								JLabel lblNewLabel_3_1_1_1_1_1 = new JLabel("Role:");
								lblNewLabel_3_1_1_1_1_1.setBounds(322, 140, 31, 16);
								Employee.add(lblNewLabel_3_1_1_1_1_1);

								JComboBox comboBox_1 = new JComboBox();
								comboBox_1.setBounds(368, 134, 103, 27);
								comboBox_1.setModel(
										new DefaultComboBoxModel(new String[] { "Chef", "Cashier", "Server" }));
								Employee.add(comboBox_1);

								JLabel lblPhoneNumber = new JLabel("Phone Number:");
								lblPhoneNumber.setBounds(40, 155, 96, 16);
								Employee.add(lblPhoneNumber);

								JLabel lblNewLabel_3_1 = new JLabel("Street:");
								lblNewLabel_3_1.setBounds(317, 173, 40, 16);
								Employee.add(lblNewLabel_3_1);

								JLabel lblNewLabel_3 = new JLabel("Neighborhood:");
								lblNewLabel_3.setBounds(42, 183, 94, 16);
								Employee.add(lblNewLabel_3);

								JLabel lblNewLabel_3_1_1 = new JLabel("Post Code:");
								lblNewLabel_3_1_1.setBounds(290, 201, 67, 16);
								Employee.add(lblNewLabel_3_1_1);

								JButton btnNewButton = new JButton("Add");
								btnNewButton.setBounds(227, 257, 75, 40);
								btnNewButton.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {// add employee
									}
								});
								Employee.add(btnNewButton);

								JTextField textField_14 = new JTextField();
								textField_14.setBounds(368, 196, 130, 26);
								textField_14.setColumns(10);
								Employee.add(textField_14);

								JTextField textField_15 = new JTextField();
								textField_15.setBounds(368, 168, 130, 26);
								textField_15.setColumns(10);
								Employee.add(textField_15);

								JTextField textField = new JTextField();
								textField.setBounds(143, 42, 130, 26);
								textField.setColumns(10);
								Employee.add(textField);

								JTextField textField_1 = new JTextField();
								textField_1.setBounds(143, 70, 130, 26);
								textField_1.setColumns(10);
								Employee.add(textField_1);

								JTextField textField_2 = new JTextField();
								textField_2.setBounds(143, 96, 130, 26);
								textField_2.setColumns(10);
								Employee.add(textField_2);

								JTextField textField_3 = new JTextField();
								textField_3.setBounds(143, 124, 130, 26);
								textField_3.setColumns(10);
								Employee.add(textField_3);

								JTextField textField_16 = new JTextField();
								textField_16.setBounds(143, 178, 130, 26);
								textField_16.setColumns(10);
								Employee.add(textField_16);

								JTextField textField_17 = new JTextField();
								textField_17.setBounds(143, 150, 130, 26);
								textField_17.setColumns(10);
								Employee.add(textField_17);
								tabbedPane.addTab("Branch", Branch);
								Branch.setLayout(null);

								JLabel lblNewLabel_1_1 = new JLabel("Enter the new branch info:");
								lblNewLabel_1_1.setBounds(178, 38, 173, 16);
								Branch.add(lblNewLabel_1_1);

								JLabel lblNewLabel_2 = new JLabel("Work time:");
								lblNewLabel_2.setBounds(31, 133, 75, 16);
								Branch.add(lblNewLabel_2);

								JTextField textField_6 = new JTextField();
								textField_6.setBounds(111, 128, 130, 26);
								textField_6.setColumns(10);
								Branch.add(textField_6);

								JLabel lblNewLabel_3_2 = new JLabel("Neighborhood:");
								lblNewLabel_3_2.setBounds(262, 105, 94, 16);
								Branch.add(lblNewLabel_3_2);

								JLabel emp_name_1 = new JLabel("City:");
								emp_name_1.setBounds(72, 164, 34, 16);
								Branch.add(emp_name_1);

								JTextField textField_7 = new JTextField();
								textField_7.setBounds(111, 159, 130, 26);
								textField_7.setColumns(10);
								Branch.add(textField_7);

								JLabel lblNewLabel_3_1_2 = new JLabel("Street:");
								lblNewLabel_3_1_2.setBounds(316, 136, 40, 16);
								Branch.add(lblNewLabel_3_1_2);

								JTextField textField_9 = new JTextField();
								textField_9.setBounds(361, 131, 130, 26);
								textField_9.setColumns(10);
								Branch.add(textField_9);

								JButton btnNewButton_2 = new JButton("Add");
								btnNewButton_2.setBounds(227, 253, 75, 40);
								btnNewButton_2.addActionListener(new ActionListener() {// add branch
									public void actionPerformed(ActionEvent e) {
									}
								});
								Branch.add(btnNewButton_2);

								JLabel lblNewLabel_2_2 = new JLabel("Branch code:");
								lblNewLabel_2_2.setBounds(18, 100, 88, 16);
								Branch.add(lblNewLabel_2_2);

								JTextField textField_12 = new JTextField();
								textField_12.setBounds(111, 95, 130, 26);
								textField_12.setColumns(10);
								Branch.add(textField_12);

								JTextField textField_13 = new JTextField();
								textField_13.setBounds(361, 100, 130, 26);
								textField_13.setColumns(10);
								Branch.add(textField_13);
								tabbedPane.addTab("Item", Item);

								JLabel lblNewLabel_1_1_1 = new JLabel("Enter the new item info:");
								lblNewLabel_1_1_1.setBounds(189, 45, 150, 16);

								JLabel lblNewLabel_2_1 = new JLabel("Item name:");
								lblNewLabel_2_1.setBounds(42, 117, 75, 16);

								JTextField textField_8 = new JTextField();
								textField_8.setBounds(122, 112, 130, 26);
								textField_8.setColumns(10);

								JLabel emp_name_1_1 = new JLabel("Price:");
								emp_name_1_1.setBounds(83, 148, 34, 16);

								JTextField textField_10 = new JTextField();
								textField_10.setBounds(122, 143, 130, 26);
								textField_10.setColumns(10);

								JLabel lblNewLabel_3_2_1 = new JLabel("Item type:");
								lblNewLabel_3_2_1.setBounds(287, 117, 63, 16);

								JComboBox comboBox_1_2_1 = new JComboBox();
								comboBox_1_2_1.setModel(
										new DefaultComboBoxModel(new String[] { "Appetizer", "Dessert", "Main" }));
								comboBox_1_2_1.setBounds(355, 113, 130, 27);

								JLabel lblNewLabel_3_1_2_1 = new JLabel("Calories:");
								lblNewLabel_3_1_2_1.setBounds(295, 148, 55, 16);

								JTextField textField_11 = new JTextField();
								textField_11.setBounds(355, 143, 130, 26);
								textField_11.setColumns(10);

								JButton btnNewButton_2_1 = new JButton("Add");
								btnNewButton_2_1.setBounds(227, 260, 75, 40);
								btnNewButton_2_1.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {// add item
									}
								});
								Item.setLayout(null);
								Item.add(lblNewLabel_1_1_1);
								Item.add(lblNewLabel_2_1);
								Item.add(textField_8);
								Item.add(lblNewLabel_3_2_1);
								Item.add(comboBox_1_2_1);
								Item.add(emp_name_1_1);
								Item.add(textField_10);
								Item.add(lblNewLabel_3_1_2_1);
								Item.add(textField_11);
								Item.add(btnNewButton_2_1);

								managerAdd.getContentPane().add(tabbedPane);

								managerAdd.setVisible(true);

							}
						});

						JLabel lblNewLabel = new JLabel(
								"Select the operation you want to perform on Wister's Database");
						GroupLayout groupLayout = new GroupLayout(managerFrame.getContentPane());
						groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup().addGroup(groupLayout
										.createParallelGroup(Alignment.LEADING)
										.addGroup(groupLayout.createSequentialGroup().addGap(151)
												.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
														.addComponent(managerAddBtn, GroupLayout.PREFERRED_SIZE, 149,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(managerRemoveBtn, GroupLayout.PREFERRED_SIZE, 149,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(managerUpdateBtn, GroupLayout.PREFERRED_SIZE, 149,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(managerSearchBtn, GroupLayout.PREFERRED_SIZE, 149,
																GroupLayout.PREFERRED_SIZE)))
										.addGroup(groupLayout.createSequentialGroup().addGap(29)
												.addComponent(lblNewLabel)))
										.addContainerGap(29, Short.MAX_VALUE)));
						groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup().addGap(27).addComponent(lblNewLabel)
										.addGap(18)
										.addComponent(managerAddBtn, GroupLayout.PREFERRED_SIZE, 53,
												GroupLayout.PREFERRED_SIZE)
										.addGap(6)
										.addComponent(managerRemoveBtn, GroupLayout.PREFERRED_SIZE, 53,
												GroupLayout.PREFERRED_SIZE)
										.addGap(6)
										.addComponent(managerUpdateBtn, GroupLayout.PREFERRED_SIZE, 53,
												GroupLayout.PREFERRED_SIZE)
										.addGap(6).addComponent(managerSearchBtn, GroupLayout.PREFERRED_SIZE, 53,
												GroupLayout.PREFERRED_SIZE)
										.addGap(15)));
						managerFrame.getContentPane().setLayout(groupLayout);
						managerFrame.setVisible(true);
					} else if (name.equalsIgnoreCase("cashierUser")) { // if the user is a cashier open cashier view
						JFrame cashierFrame = new JFrame();
						cashierFrame.setBounds(450, 220, 450, 334);

						cashierFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

						JButton cashierSearch = new JButton("Search"); // TODO: RENAD
						cashierSearch.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
							}
						});

						JButton cashierUpdate = new JButton("Update");// TODO: RENAD
						cashierUpdate.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
							}
						});

						JButton cashierRemove = new JButton("Remove");// TODO: RENAD
						cashierRemove.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
							}
						});

						JButton cashierAdd = new JButton("Add"); // TODO: Ghaida
						cashierAdd.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
							}
						});

						JLabel lblNewLabel = new JLabel(
								"Select the operation you want to perform on Wister's Database");
						GroupLayout groupLayout = new GroupLayout(cashierFrame.getContentPane());
						groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
										.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
												.addGroup(groupLayout.createSequentialGroup().addGap(151)
														.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
																.addComponent(cashierAdd, GroupLayout.PREFERRED_SIZE,
																		149, GroupLayout.PREFERRED_SIZE)
																.addComponent(cashierRemove, GroupLayout.PREFERRED_SIZE,
																		149, GroupLayout.PREFERRED_SIZE)
																.addComponent(cashierUpdate, GroupLayout.PREFERRED_SIZE,
																		149, GroupLayout.PREFERRED_SIZE)
																.addComponent(cashierSearch, GroupLayout.PREFERRED_SIZE,
																		149, GroupLayout.PREFERRED_SIZE)))
												.addGroup(groupLayout.createSequentialGroup().addGap(29)
														.addComponent(lblNewLabel)))
										.addContainerGap(29, Short.MAX_VALUE)));
						groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup().addGap(27).addComponent(lblNewLabel)
										.addGap(18)
										.addComponent(cashierAdd, GroupLayout.PREFERRED_SIZE, 53,
												GroupLayout.PREFERRED_SIZE)
										.addGap(6)
										.addComponent(cashierRemove, GroupLayout.PREFERRED_SIZE, 53,
												GroupLayout.PREFERRED_SIZE)
										.addGap(6)
										.addComponent(cashierUpdate, GroupLayout.PREFERRED_SIZE, 53,
												GroupLayout.PREFERRED_SIZE)
										.addGap(6).addComponent(cashierSearch, GroupLayout.PREFERRED_SIZE, 53,
												GroupLayout.PREFERRED_SIZE)
										.addGap(15)));
						cashierFrame.getContentPane().setLayout(groupLayout);
						cashierFrame.setVisible(true);
					}

				} else {
					JOptionPane.showMessageDialog(null, "Incorrect username/password", "Oops! ..",
							JOptionPane.ERROR_MESSAGE);
				}

			}

			private boolean login(String username, String password) {
				Dotenv dotenv = Dotenv.load(); // Load the .env file
				String dbEndPoint = "jdbc:mysql://" + dotenv.get("DB_ENDPOINT") + ":3306/WISTERDATABASE";
				boolean isAuthenticated = false;

				try (Connection con = DriverManager.getConnection(dbEndPoint, username, password)) {
					if (con != null) {
						System.out.println("Login successful!");
						isAuthenticated = true;
					}
				} catch (SQLException e) {
					System.out.println("Invalid username or password.");
					e.getMessage();
				}

				return isAuthenticated;
			}

		});

		getContentPane().add(loginButton);

		JLabel blank = new JLabel("");
		springLayout.putConstraint(SpringLayout.EAST, blank, -433, SpringLayout.EAST, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, msg, 25, SpringLayout.EAST, blank);
		springLayout.putConstraint(SpringLayout.NORTH, blank, 48, SpringLayout.NORTH, getContentPane());
		ImageIcon imageIcon = new ImageIcon(
				new ImageIcon("img/Subject.png").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));

		blank.setIcon(imageIcon);
		getContentPane().add(blank);
		setBackground(new Color(45, 45, 45));
		setBounds(400, 200, 556, 367);
		setVisible(true);

	}

	static void createConnection() {
		try {
			// Use the driver class for MySQL Connector
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Get the database info from the environment variable (to protect the database)
			Dotenv dotenv = Dotenv.load(); // Load the .env file
			String dbPassword = dotenv.get("DB_PASSWORD"); // Retrieve the password
			String dbEndPoint = "jdbc:mysql://" + dotenv.get("DB_ENDPOINT") + ":3306/WISTERDATABASE";
			String dbAdmin = dotenv.get("DB_ADMIN");

			// Establish connection using the environment variable
			con = DriverManager.getConnection(dbEndPoint, dbAdmin, dbPassword);// all -> variables in env
			System.out.println("db connection successful");

		} catch (ClassNotFoundException ex) {
			System.out.println("ERRORRRR! Driver class not found." + ex.getMessage());
		} catch (SQLException exception) {
			System.out.println("ERRORRRR SQLLL! " + exception.getMessage());
		}
	}
}
