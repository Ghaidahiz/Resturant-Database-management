import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import io.github.cdimascio.dotenv.Dotenv;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
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
//import java.awt.Taskbar.State;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import  java.sql.*;

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
								comboBox.setModel(new DefaultComboBoxModel<>(new String[] { "Show All", "Employee_ID",
										"Emp_Name", "Bcode", "Role", "Gender", "Salary" }));
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
								DefaultTableModel emodel = new DefaultTableModel(data,
										new String[] { "ID", "Branch_Code", "Name", "Residence_Number", "Phone",
												"Gender", "Role", "Neighborhood", "Street", "Post_Code", "Salary" });
								DefaultTableModel bmodel = new DefaultTableModel(data,
										new String[] { "Branch_code", "Work_time", "City", "Neighborhood", "Street" });
								DefaultTableModel imodel = new DefaultTableModel(data,
										new String[] { "Item_Name", "Price", "Item_Type", "Calories" });
								JTable etable = new JTable(emodel);
								JTable btable = new JTable(bmodel);
								JTable itable = new JTable(imodel);

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
									public void actionPerformed(ActionEvent E) {
										try {
											java.sql.Statement stm = con.createStatement();
											ResultSet resultSet;
											if (comboBox.getSelectedItem().equals("Show All"))
												resultSet = stm.executeQuery("SELECT * " + "FROM EMPLOYEE ");
											else if (comboBox.getSelectedItem().equals("Employee_ID")
													|| comboBox.getSelectedItem().equals("Branch_code")
													|| comboBox.getSelectedItem().equals("Salary")) {
												int num = Integer.parseInt(textField.getText());
												resultSet = stm.executeQuery("SELECT * " + "FROM EMPLOYEE " + "WHERE "
														+ comboBox.getSelectedItem() + " = " + num);
											} else
												resultSet = stm.executeQuery("SELECT * " + "FROM EMPLOYEE " + "WHERE "
														+ comboBox.getSelectedItem() + "= \'" + textField.getText()
														+ "\'"

												);
											emodel.setRowCount(0);
											fillETable(emodel, resultSet);

										} catch (NumberFormatException ex) {
											JOptionPane.showMessageDialog(null, "Please Enter a number!",
													"invalid input", JOptionPane.ERROR_MESSAGE);
										} catch (SQLException ex) {
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
								comboBox_1.setModel(
										new DefaultComboBoxModel<>(new String[] { "Show All", "Branch_code", "City" }));
								comboBox_1.setBounds(221, 43, 88, 22);
								Branch.add(comboBox_1);

								JLabel lblNewLabel_3 = new JLabel("value:");
								lblNewLabel_3.setBounds(367, 45, 49, 19);
								Branch.add(lblNewLabel_3);

								JButton btnNewButton_1 = new JButton("SEARCH");
								btnNewButton_1.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										try {
											ResultSet resultSet;
											java.sql.Statement stm = con.createStatement();
											if (comboBox_1.getSelectedItem().equals("Show All"))
												resultSet = stm.executeQuery("SELECT *" + " FROM BRANCH ");
											else if (comboBox_1.getSelectedItem().equals("City"))
												resultSet = stm.executeQuery("SELECT *" + " FROM BRANCH "
														+ "WHERE City=\'" + textField_1.getText() + "\'");
											else {
												int num = Integer.parseInt(textField_1.getText());
												resultSet = stm.executeQuery(
														"SELECT *" + " FROM BRANCH " + "WHERE Branch_code=" + num);
											}
											bmodel.setRowCount(0);
											fillBTable(bmodel, resultSet);

										} catch (NumberFormatException ex) {
											JOptionPane.showMessageDialog(null, "Please Enter a number!",
													"invalid input", JOptionPane.ERROR_MESSAGE);
										} catch (SQLException ex) {
											System.out.println(ex.getMessage());
										}
									}
								});
								btnNewButton_1.setBounds(552, 43, 88, 23);
								Branch.add(btnNewButton_1);

								JLabel lblNewLabel_4 = new JLabel("search by:");
								lblNewLabel_4.setBounds(140, 47, 69, 22);
								Item.add(lblNewLabel_4);

								JComboBox<String> comboBox_2 = new JComboBox<>();
								comboBox_2.setModel(new DefaultComboBoxModel<>(
										new String[] { "Show All", "Item_Name", "Item_Type", "Price" }));
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

								btnNewButton_2.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent E) {
										try {
											java.sql.Statement stm = con.createStatement();
											ResultSet resultSet;
											if (comboBox_2.getSelectedItem().equals("Show All"))
												resultSet = stm.executeQuery("SELECT *" + " FROM ITEM ");
											else if (comboBox_2.getSelectedItem().equals("Price")) {
												double num = Double.parseDouble(textField_2.getText());
												resultSet = stm.executeQuery(
														"SELECT * " + "FROM ITEM " + "WHERE Price=" + num);
											} else
												resultSet = stm.executeQuery("SELECT * " + "FROM ITEM " + " WHERE "
														+ comboBox_2.getSelectedItem() + "= \'" + textField_2.getText()
														+ "\'");

											imodel.setRowCount(0);
											fillITable(imodel, resultSet);

										} catch (NumberFormatException e) {
											JOptionPane.showMessageDialog(null, "Please Enter a number!",
													"invalid input", JOptionPane.ERROR_MESSAGE);

										} catch (SQLException e) {
											System.out.println(e.getMessage());
										}
									}
								});
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
								managerUpdate.setBounds(100, 100, 830, 400);
								managerUpdate.getContentPane().setLayout(new BorderLayout());
								managerUpdate.setLocationRelativeTo(null);

								JTabbedPane tabbedPane = new JTabbedPane();

								JPanel Item = new JPanel();
								Item.setLayout(null);

								JPanel Employee = new JPanel();
								Employee.setLayout(null);
								tabbedPane.addTab("Employee", Employee);

								JPanel Branch = new JPanel();
								Branch.setLayout(null);
								tabbedPane.addTab("Branch", Branch);

								Object[][] data = {};
								DefaultTableModel emodel = new DefaultTableModel(data,
										new String[] { "ID", "Branch_Code", "Name", "Residence_Number", "Phone",
												"Gender", "Role", "Neighborhood", "Street", "Post_Code", "Salary" });
								DefaultTableModel bmodel = new DefaultTableModel(data,
										new String[] { "Branch_code", "Work_time", "City", "Neighborhood", "Street" });
								DefaultTableModel imodel = new DefaultTableModel(data,
										new String[] { "Item_Name", "Price", "Item_Type", "Calories" });
								JTable etable = new JTable(emodel);
								JTable btable = new JTable(bmodel);
								JTable itable = new JTable(imodel);

								etable.setEnabled(false);
								btable.setEnabled(false);
								itable.setEnabled(false);

								JScrollPane escrollPane = new JScrollPane(etable);
								JScrollPane bscrollPane = new JScrollPane(btable);
								JScrollPane iscrollPane = new JScrollPane(itable);

								escrollPane.setBounds(5, 120, 800, 200);
								bscrollPane.setBounds(169, 124, 500, 200);
								iscrollPane.setBounds(140, 124, 500, 200);

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

								try {
									java.sql.Statement stm = con.createStatement();
									ResultSet resultSet = stm.executeQuery("SELECT * " + "FROM EMPLOYEE ");
									fillETable(emodel, resultSet);
								} catch (SQLException ex) {
									System.out.println(ex.getMessage());
								}

								try {
									java.sql.Statement stm = con.createStatement();
									ResultSet resultSet = stm.executeQuery("SELECT * " + "FROM BRANCH ");
									fillBTable(bmodel, resultSet);
								} catch (SQLException ex) {
									System.out.println(ex.getMessage());
								}

								try {
									java.sql.Statement stm = con.createStatement();
									ResultSet resultSet = stm.executeQuery("SELECT * " + "FROM ITEM ");
									fillITable(imodel, resultSet);
								} catch (SQLException ex) {
									System.out.println(ex.getMessage());
								}

								JLabel lblNewLabel = new JLabel(
										"Enter ID of the employee you want to update his info:");
								lblNewLabel.setBounds(10, 21, 312, 31);
								Employee.add(lblNewLabel);

								JComboBox comboBox_1 = new JComboBox();
								comboBox_1.setModel(new DefaultComboBoxModel(
										new String[] { "Salary", "Emp_Phone", "Role", "Bcode" }));
								comboBox_1.setBounds(332, 55, 96, 22);
								Employee.add(comboBox_1);

								JLabel lblNewLabel_1 = new JLabel("select which info you want to update:");
								lblNewLabel_1.setBounds(111, 50, 223, 33);
								Employee.add(lblNewLabel_1);

								JTextField textField = new JTextField();
								textField.setBounds(570, 55, 96, 22);
								Employee.add(textField);
								textField.setColumns(10);

								JLabel lblNewLabel_2 = new JLabel("new value:");
								lblNewLabel_2.setBounds(498, 51, 75, 31);
								Employee.add(lblNewLabel_2);

								JTextField textField_6 = new JTextField();
								textField_6.setBounds(332, 26, 96, 20);
								Employee.add(textField_6);
								textField_6.setColumns(10);

								JButton btnNewButton = new JButton("UPDATE");
								btnNewButton.setBounds(705, 55, 96, 23);
								Employee.add(btnNewButton);

								btnNewButton.addActionListener(new ActionListener() { // update empolyee info(manager
																						// veiw)
									public void actionPerformed(ActionEvent E) {
										try {
											java.sql.Statement stm = con.createStatement();
											if (comboBox_1.getSelectedItem().equals("Emp_Phone")
													|| comboBox_1.getSelectedItem().equals("Role"))
												numOfAffectedRow = stm.executeUpdate("UPDATE EMPLOYEE " + "SET "
														+ comboBox_1.getSelectedItem() + " = \'" + textField.getText()
														+ "\' " + " WHERE Employee_ID=" + textField_6.getText());
											else {
												int num = Integer.parseInt(textField_6.getText());
												numOfAffectedRow = stm.executeUpdate("UPDATE EMPLOYEE " + "SET "
														+ comboBox_1.getSelectedItem() + " = " + textField.getText()
														+ " WHERE Employee_ID=" + num);
											}

											if (numOfAffectedRow == 0)
												JOptionPane.showMessageDialog(null,
														"There is no Empolyee with the received ID", "not found :(",
														JOptionPane.ERROR_MESSAGE);
											/*
											 * else JOptionPane.showMessageDialog(null, "The " +
											 * comboBox_1.getSelectedItem() + " of the Empolyee with the received ID ("
											 * + textField_6.getText() + ") was updated to be: " + textField.getText(),
											 * "Updated seccessfully :)", JOptionPane.INFORMATION_MESSAGE);
											 */
											else {
												java.sql.Statement stm2 = con.createStatement();
												ResultSet resultSet = stm2.executeQuery("SELECT * " + "FROM EMPLOYEE ");
												emodel.setRowCount(0);
												fillETable(emodel, resultSet);
											}
										} catch (NumberFormatException e) {
											JOptionPane.showMessageDialog(null, "please enter a number!", "Input Error",
													JOptionPane.WARNING_MESSAGE);
										} catch (SQLIntegrityConstraintViolationException e) {
											JOptionPane.showMessageDialog(null,
													"NO Branch with the folowng branch code!", "invalid input",
													JOptionPane.WARNING_MESSAGE);

										} catch (SQLException e) {
											if (e.getMessage().contains("EMPLOYEE_chk_3"))
											JOptionPane.showMessageDialog(null,"The only allowed Roles for employees are:\n    {Cashier, Chef, Server, Manager}", "invalid input",JOptionPane.WARNING_MESSAGE);
											System.out.println(e.getMessage());
										}

									}
								});

								JLabel lblNewLabel_3 = new JLabel(
										"enter branch code of the branch you want to update  its working time:\r\n");
								lblNewLabel_3.setHorizontalAlignment(SwingConstants.LEFT);
								lblNewLabel_3.setBounds(23, 24, 405, 23);
								Branch.add(lblNewLabel_3);

								JTextField textField_1 = new JTextField();
								textField_1.setBounds(452, 25, 96, 20);
								Branch.add(textField_1);
								textField_1.setColumns(10);

								JLabel lblNewLabel_5 = new JLabel("the new time:");
								lblNewLabel_5.setBounds(33, 59, 96, 20);
								Branch.add(lblNewLabel_5);

								JButton btnNewButton_1 = new JButton("UPDATE");
								btnNewButton_1.setBounds(585, 24, 96, 23);
								Branch.add(btnNewButton_1);

								JComboBox<String> comboBox = new JComboBox<String>();
								comboBox.setModel(new DefaultComboBoxModel<String>(new String[] { "00", "01", "02",
										"03", "04", "05", "06", "07", "07", "08", "09", "10", "11", "12", "13", "14",
										"15", "16", "17", "18", "19", "20", "21", "22", "23" }));
								comboBox.setBounds(227, 58, 42, 22);
								Branch.add(comboBox);

								JComboBox<String> comboBox_2 = new JComboBox<String>();
								comboBox_2.setModel(new DefaultComboBoxModel<String>(new String[] { "00", "01", "02",
										"03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15",
										"16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28",
										"29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41",
										"42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54",
										"55", "56", "57", "58", "59" }));
								comboBox_2.setBounds(395, 58, 42, 22);
								Branch.add(comboBox_2);

								JLabel lblNewLabel_4 = new JLabel("Hour");
								lblNewLabel_4.setBounds(159, 62, 49, 14);
								Branch.add(lblNewLabel_4);

								JLabel lblNewLabel_6 = new JLabel("Minutes ");
								lblNewLabel_6.setBounds(336, 62, 49, 14);
								Branch.add(lblNewLabel_6);

								btnNewButton_1.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent E) {

										try {
											java.sql.Statement stm = con.createStatement();
											numOfAffectedRow = stm.executeUpdate("UPDATE BRANCH " + "SET Work_time= \'"
													+ comboBox.getSelectedItem() + ":" + comboBox_2.getSelectedItem()
													+ ":00\' " + "WHERE Branch_code = " + textField_1.getText());
											/*
											 * if (numOfAffectedRow == 1) JOptionPane.showMessageDialog(null,
											 * "The working time of the branch(" + textField_1.getText() +
											 * ") was updated to be: " + comboBox.getSelectedItem() + ":" +
											 * comboBox_2.getSelectedItem() + ":00", "Updated seccessfully :)",
											 * JOptionPane.INFORMATION_MESSAGE); else
											 */ if (numOfAffectedRow == 0) {
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

										try {
											java.sql.Statement stm = con.createStatement();
											ResultSet resultSet = stm.executeQuery("SELECT * " + "FROM BRANCH ");
											bmodel.setRowCount(0);
											fillBTable(bmodel, resultSet);
										} catch (SQLException ex) {
											System.out.println(ex.getMessage());
										}
									}
								});

								tabbedPane.addTab("Item", Item);

								JLabel lblNewLabel_9 = new JLabel("enter item name yuo want to update its price:");
								lblNewLabel_9.setBounds(10, 35, 303, 20);
								Item.add(lblNewLabel_9);

								JTextField textField_4 = new JTextField();
								textField_4.setBounds(312, 35, 96, 20);
								Item.add(textField_4);
								textField_4.setColumns(10);

								JLabel lblNewLabel_10 = new JLabel("new price:");
								lblNewLabel_10.setBounds(214, 69, 62, 14);
								Item.add(lblNewLabel_10);

								JTextField textField_5 = new JTextField();
								textField_5.setBounds(312, 66, 96, 20);
								Item.add(textField_5);
								textField_5.setColumns(10);

								JButton btnNewButton_2 = new JButton("UPDATE");
								btnNewButton_2.setBounds(533, 34, 96, 23);
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
											/*
											 * else JOptionPane.showMessageDialog(null, "The price of " +
											 * textField_4.getText() + " was updated to be: " + textField_5.getText() +
											 * ".00SR", "Updated seccessfully :)", JOptionPane.INFORMATION_MESSAGE);
											 */

										} catch (SQLException e) {
											System.err.println(e.getMessage());
										} catch (NumberFormatException e) {
											JOptionPane.showMessageDialog(null,
													"please enter a number to set the new price", "invalid input",
													JOptionPane.ERROR_MESSAGE);
										}

										try {
											java.sql.Statement stm = con.createStatement();
											ResultSet resultSet = stm.executeQuery("SELECT * " + "FROM ITEM ");
											imodel.setRowCount(0);
											fillITable(imodel, resultSet);
										} catch (SQLException ex) {
											System.out.println(ex.getMessage());
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
								managerRemove.setBounds(100, 100, 830, 400);
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

								Object[][] data = {}; // ************** LOOK HERE RENAD
								DefaultTableModel emodel = new DefaultTableModel(data,
										new String[] { "ID", "Branch_Code", "Name", "Residence_Number", "Phone",
												"Gender", "Role", "Neighborhood", "Street", "Post_Code", "Salary" });
								DefaultTableModel bmodel = new DefaultTableModel(data,
										new String[] { "Branch_code", "Work_time", "City", "Neighborhood", "Street" });
								DefaultTableModel imodel = new DefaultTableModel(data,
										new String[] { "Item_Name", "Price", "Item_Type", "Calories" });
								JTable etable = new JTable(emodel);
								JTable btable = new JTable(bmodel);
								JTable itable = new JTable(imodel);

								etable.setEnabled(false);
								btable.setEnabled(false);
								itable.setEnabled(false);

								JScrollPane escrollPane = new JScrollPane(etable);
								JScrollPane bscrollPane = new JScrollPane(btable);
								JScrollPane iscrollPane = new JScrollPane(itable);

								escrollPane.setBounds(5, 120, 800, 200);
								bscrollPane.setBounds(169, 124, 500, 200);
								iscrollPane.setBounds(140, 124, 500, 200);

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

								tabbedPane.addTab("Employee", Employee);
								tabbedPane.addTab("Branch", Branch);
								tabbedPane.addTab("Item", Item);

								try {
									java.sql.Statement stm = con.createStatement();
									ResultSet resultSet = stm.executeQuery("SELECT * " + "FROM EMPLOYEE ");
									fillETable(emodel, resultSet);
								} catch (SQLException ex) {
									System.out.println(ex.getMessage());
								}

								try {
									java.sql.Statement stm = con.createStatement();
									ResultSet resultSet = stm.executeQuery("SELECT * " + "FROM BRANCH ");
									fillBTable(bmodel, resultSet);
								} catch (SQLException ex) {
									System.out.println(ex.getMessage());
								}

								try {
									java.sql.Statement stm = con.createStatement();
									ResultSet resultSet = stm.executeQuery("SELECT * " + "FROM ITEM ");
									fillITable(imodel, resultSet);
								} catch (SQLException ex) {
									System.out.println(ex.getMessage());
								}

								JLabel lblEnterNameOf = new JLabel("Enter name of the item you want to remove:   ");
								lblEnterNameOf.setBounds(96, 59, 273, 20);
								Item.add(lblEnterNameOf);

								// Add panels as tabs to the JTabbedPane
								JLabel labal_2 = new JLabel("Remove employees by thier:");
								labal_2.setHorizontalAlignment(SwingConstants.LEFT);
								labal_2.setBounds(134, 32, 181, 32);
								Employee.add(labal_2);

								// set up tab Employee (remove) until line 93
								JComboBox<String> comboBox = new JComboBox<String>();
								comboBox.setModel(new DefaultComboBoxModel(
										new String[] { " Employee_ID", "Emp_Name", "Gender", "Role", "Salary" ,"Bcode"}));
								comboBox.setBounds(325, 38, 96, 20);
								Employee.add(comboBox);

								JTextField textField = new JTextField();
								textField.setBounds(325, 75, 96, 20);
								Employee.add(textField);
								textField.setColumns(10);

								JLabel lblNewLabel = new JLabel("Enter a value to remove all employees that match:");
								lblNewLabel.setBounds(10, 75, 305, 20);
								Employee.add(lblNewLabel);

								JButton button = new JButton("Remove");
								button.setBounds(496, 37, 89, 23);
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
											else {
												java.sql.Statement stm2 = con.createStatement();
												ResultSet resultSet = stm2.executeQuery("SELECT * " + "FROM EMPLOYEE ");
												emodel.setRowCount(0);
												fillETable(emodel, resultSet);
											}
											/*
											 * JOptionPane.showMessageDialog(null, "All Empolyees with \'" +
											 * comboBox.getSelectedItem() + " = " + textField.getText() +
											 * "\' are Removed seccessfully ", "Removed seccessfully :)",
											 * JOptionPane.INFORMATION_MESSAGE);
											 */

										} catch (NumberFormatException E) {
											JOptionPane.showMessageDialog(null, "please enter a number", "Input Error",
													JOptionPane.WARNING_MESSAGE);
										} catch (SQLException E) {
											System.out.println(E.getMessage());
										}
									}
								});

								JLabel lblNewLabel_2 = new JLabel(
										"Enter the code of the Branch \r\nyou want to remove:");
								lblNewLabel_2.setBounds(98, 36, 305, 31);
								Branch.add(lblNewLabel_2);

								JTextField textField_1 = new JTextField();
								textField_1.setBounds(401, 41, 96, 20);
								Branch.add(textField_1);
								textField_1.setColumns(10);

								JButton Button_1 = new JButton("Remove");
								Button_1.setBounds(530, 40, 96, 23);
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
											else {
												java.sql.Statement stm2 = con.createStatement();
												ResultSet resultSet = stm2.executeQuery("SELECT * " + "FROM BRANCH ");
												bmodel.setRowCount(0);
												fillBTable(bmodel, resultSet);
											}
											/*
											 * JOptionPane.showMessageDialog(null, "The Branch (with Branch_code = " +
											 * textField_1.getText() + ") is removed seccessfully.",
											 * "Removed seccessfully :)", JOptionPane.INFORMATION_MESSAGE);
											 */
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
								JTextField textField_2 = new JTextField();
								textField_2.setBounds(361, 59, 96, 20);
								Item.add(textField_2);
								textField_2.setColumns(10);

								JButton btnNewButton = new JButton("Remove");
								btnNewButton.setBounds(518, 58, 99, 23);
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
											else {
												java.sql.Statement stm2 = con.createStatement();
												ResultSet resultSet = stm2.executeQuery("SELECT * " + "FROM ITEM ");
												imodel.setRowCount(0);
												fillITable(imodel, resultSet);
											}
											/*
											 * JOptionPane.showMessageDialog(null, "The Item ( " + textField_2.getText()
											 * + " ) is removed seccessfully.", "Removed Seccessfully :)",
											 * JOptionPane.INFORMATION_MESSAGE);
											 */

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
								cashierSearch();
							}
						});

						JButton cashierUpdate = new JButton("Update");// TODO: RENAD
						cashierUpdate.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								cashierUpdate();
							}
						});

						JButton cashierRemove = new JButton("Remove");// TODO: RENAD
						cashierRemove.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								cashierRemove();
							}
						});

						JButton cashierAdd = new JButton("Add"); // TODO: Ghaida
						cashierAdd.addActionListener(new ActionListener() {
							@SuppressWarnings("unchecked")
							public void actionPerformed(ActionEvent e) {
								JFrame cashierAdd = new JFrame();
								cashierAdd.setTitle("Add");
								cashierAdd.setBounds(100, 100, 550, 400);
								cashierAdd.setLocationRelativeTo(null);
								cashierAdd.getContentPane().setLayout(new BorderLayout());

								JTabbedPane tabbedPane = new JTabbedPane();

								// Create panels to add to each tab
								JPanel Order = new JPanel();

								JPanel Customer = new JPanel();

								// Add panels as tabs to the JTabbedPane
								tabbedPane.addTab("Order", Order);
								ButtonGroup group = new ButtonGroup();
								Order.setLayout(null);

								JLabel lblNewLabel_1 = new JLabel("Enter the new Order's info:");
								lblNewLabel_1.setBounds(168, 14, 192, 16);
								Order.add(lblNewLabel_1);

								JLabel lblNewLabel = new JLabel("Employee ID:");
								lblNewLabel.setBounds(22, 114, 82, 16);
								Order.add(lblNewLabel);

								JLabel ord_name = new JLabel("Order number:");
								ord_name.setBounds(6, 142, 94, 16);
								Order.add(ord_name);

								JLabel lblNewLabel_3_1_1_1 = new JLabel("Payment method:");
								lblNewLabel_3_1_1_1.setBounds(267, 111, 116, 16);
								Order.add(lblNewLabel_3_1_1_1);

								JComboBox comboBox_1_1 = new JComboBox();
								comboBox_1_1.setBounds(380, 107, 121, 27);
								comboBox_1_1.setModel(new DefaultComboBoxModel(new String[] { "CASH", "CREDIT CARD" })); // TODO:
																															// fix
								Order.add(comboBox_1_1);

								JLabel lblNewLabel_3_1_1_2 = new JLabel("Total price:");
								lblNewLabel_3_1_1_2.setBounds(25, 170, 79, 16);
								Order.add(lblNewLabel_3_1_1_2);

								JLabel lblResidenceNumber = new JLabel("Preperation time:");
								lblResidenceNumber.setBounds(267, 141, 107, 16);
								Order.add(lblResidenceNumber);

								JLabel lblPhoneNumber = new JLabel("Customer Phone:");
								lblPhoneNumber.setBounds(267, 165, 108, 16);
								Order.add(lblPhoneNumber);

								/*
								 * JButton btnNewButton = new JButton("Add"); btnNewButton.setBounds(227, 257,
								 * 75, 40); btnNewButton.addActionListener(new ActionListener() { public void
								 * actionPerformed(ActionEvent e) {// add Order } }); Order.add(btnNewButton);
								 */

								JTextField ordNumField = new JTextField();// ord id
								ordNumField.setBounds(107, 137, 130, 26);
								ordNumField.setColumns(10);
								Order.add(ordNumField);

								JTextField pricField = new JTextField();// total price
								pricField.setBounds(107, 163, 130, 26);
								pricField.setColumns(10);
								Order.add(pricField);

								JTextField prepField = new JTextField();// prep time
								prepField.setBounds(382, 134, 130, 26);
								prepField.setColumns(10);
								Order.add(prepField);

								/*
								 * JTextField cPhoneField = new JTextField();//customer phone
								 * cPhoneField.setBounds(382, 160, 130, 26); cPhoneField.setColumns(10);
								 * Order.add(cPhoneField);
								 */
								JComboBox comboBox_1_3 = new JComboBox();
								comboBox_1_3.removeAllItems();
								ArrayList<String> items3 = new ArrayList<>();
								try {// this block gets customer numbers from DB
									java.sql.Statement custStatement = con.createStatement();
									ResultSet resultSet = custStatement
											.executeQuery("SELECT `Phone_Number` FROM CUSTOMER;");
									while (resultSet.next()) {
										items3.add(resultSet.getString("Phone_Number"));
									}
								} catch (SQLException e1) {
									e1.getMessage();
								}

								for (String custID : items3) {
									comboBox_1_3.addItem(custID);

								}
								comboBox_1_3.setBounds(382, 160, 130, 26);
								Order.add(comboBox_1_3);

								JComboBox comboBox_1_2 = new JComboBox();
								comboBox_1_2.removeAllItems();
								ArrayList<Integer> items = new ArrayList<>();
								try {// this block gets employee ids from DB
									java.sql.Statement empStatement = con.createStatement();
									ResultSet resultSet = empStatement
											.executeQuery("SELECT `Employee_ID` FROM EMPLOYEE;");
									while (resultSet.next()) {
										items.add(resultSet.getInt("Employee_ID"));
									}
								} catch (SQLException e1) {
									e1.getMessage();
								}

								for (Integer empID : items) {
									comboBox_1_2.addItem(empID);

								}
								comboBox_1_2.setBounds(107, 110, 130, 27);
								Order.add(comboBox_1_2);

								// Add Items to Order Button
								JButton addItemsButton = new JButton("Add Items to the Order");
								addItemsButton.setBounds(167, 200, 200, 40);
								addItemsButton.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										JFrame order2 = new JFrame();
										order2.setDefaultCloseOperation(HIDE_ON_CLOSE);
										order2.setTitle("Add items");
										order2.setBounds(100, 100, 550, 400);
										order2.setLocationRelativeTo(null);

										JPanel itemSelectionPanel = new JPanel(new GridLayout(0, 3, 10, 10));
										itemSelectionPanel.setPreferredSize(new java.awt.Dimension(500, 300));

										JLabel itemLabel = new JLabel("Item");
										JLabel quantityLabel = new JLabel("Quantity");
										itemSelectionPanel.add(itemLabel);
										itemSelectionPanel.add(quantityLabel);
										itemSelectionPanel.add(new JLabel());

										ArrayList<String> items2 = new ArrayList<String>();
										try {// this block gets item names from DB
											java.sql.Statement itmStatement = con.createStatement();
											ResultSet resultSet = itmStatement
													.executeQuery("SELECT `Item_Name` FROM ITEM;");
											while (resultSet.next()) {
												items2.add(resultSet.getString("Item_Name"));
											}
										} catch (SQLException e1) {
											e1.getMessage();
										}

										ArrayList<JCheckBox> checkBoxes = new ArrayList<>();
										ArrayList<JTextField> quantityFields = new ArrayList<>();

										for (String item : items2) {
											JCheckBox itemCheckBox = new JCheckBox(item);
											JTextField quantityField = new JTextField();
											quantityField.setEnabled(false);
											checkBoxes.add(itemCheckBox);
											quantityFields.add(quantityField);

											// listener for enabling/disabling the quantity text field based on checkbox
											// state
											itemCheckBox.addItemListener(new ItemListener() {
												@Override
												public void itemStateChanged(ItemEvent e) {
													if (e.getStateChange() == ItemEvent.SELECTED) {
														quantityField.setEnabled(true);
													} else {
														quantityField.setEnabled(false);
														quantityField.setText("");
													}
												}
											});

											itemSelectionPanel.add(itemCheckBox);
											itemSelectionPanel.add(quantityField);
											itemSelectionPanel.add(new JLabel());
										}
										JButton additemsButton2 = new JButton("Add Order");// adds in
																							// CONSISTS OF and ORDER
										additemsButton2.addActionListener(new ActionListener() {
											public void actionPerformed(ActionEvent e) {
												boolean exists = false, ordNumValid = false;
												String itmQuery = new String();
												int ordNum = 0;
												try {
													ordNum = Integer.parseInt(ordNumField.getText().trim());
													ordNumValid = true; // If parsing is successful, the order number is
																		// valid
													itmQuery = "SELECT * FROM `ORDER` WHERE Order_Number = " + ordNum;
												} catch (NumberFormatException e3) {
													System.out.println("parsing: " + e3.getMessage());

												}

												if (ordNumValid) {
													// If the order number is valid, check if it already exists in the
													// database
													try (java.sql.Statement stmt = con.createStatement();
															ResultSet rs = stmt.executeQuery(itmQuery)) {
														if (rs.next()) {
															exists = true;
														}
													} catch (SQLException e4) {
														System.out.println("Error executing SQL: " + e4.getMessage());
													}
												}

												if (exists || !ordNumValid) {
													JOptionPane.showMessageDialog(null,
															"Incorrect/duplicate Order number", "Oops! ..",
															JOptionPane.ERROR_MESSAGE);
												} else {

													boolean validOrder = true;
													double price = 0;
													int prepTime = 0;

													// Validate price and preparation time
													try {
														price = Double.parseDouble(pricField.getText());
														prepTime = Integer.parseInt(prepField.getText());
														if (price < 0 || prepTime < 0) {
															validOrder = false;
															throw new NumberFormatException("negative");
														}
													} catch (NumberFormatException e6) {
														JOptionPane.showMessageDialog(null,
																"Incorrect input in price/preparation time. Please enter valid numbers.",
																"Oops! ..", JOptionPane.ERROR_MESSAGE);
														validOrder = false;
													}

													// Insert the order into the ORDER table only once
													if (validOrder) {
														try {
															String orderInsertQuery = "INSERT INTO `ORDER` (E_ID, Order_Number, Total_Price, Preperation_time, Cus_phone, Payment_Method) "
																	+ "VALUES (" + comboBox_1_2.getSelectedItem() + ", "
																	+ ordNum + ", " + price + ", " + prepTime + ", '"
																	+ comboBox_1_3.getSelectedItem() + "', '"
																	+ comboBox_1_1.getSelectedItem() + "')";
															java.sql.Statement orderStatement = con.createStatement();
															orderStatement.executeUpdate(orderInsertQuery);
														} catch (SQLException sq1) {
															validOrder = false;
															JOptionPane.showMessageDialog(null,
																	"Error inserting order: " + sq1.getMessage(),
																	"Order Error", JOptionPane.ERROR_MESSAGE);
														}
													}

													// If the order was successfully inserted, insert the items
													if (validOrder) {
														for (int i = 0; i < checkBoxes.size(); i++) {
															if (checkBoxes.get(i).isSelected()) {
																String currentItem = items2.get(i);
																int currentQty = 0;

																// Validate the quantity for the item
																try {
																	currentQty = Integer
																			.parseInt(quantityFields.get(i).getText());
																	if (currentQty <= 0) {
																		JOptionPane.showMessageDialog(null,
																				"Quantity must be greater than 0 for item: "
																						+ currentItem,
																				"Invalid Quantity",
																				JOptionPane.ERROR_MESSAGE);
																		continue; // Skip this item
																	}
																} catch (NumberFormatException ex) {
																	JOptionPane.showMessageDialog(null,
																			"Invalid quantity for item: " + currentItem,
																			"Oops! ..", JOptionPane.ERROR_MESSAGE);
																	continue; // Skip this item
																}

																// Insert item into CONSIST_OF table
																try {
																	String insertQuery = "INSERT INTO `CONSIST_OF` (It_Name, Ord_Number, Quantity) VALUES ('"
																			+ currentItem + "', " + ordNum + ", "
																			+ currentQty + ")";
																	java.sql.Statement consistsStatement = con
																			.createStatement();
																	consistsStatement.executeUpdate(insertQuery);
																} catch (SQLException sq2) {
																	JOptionPane.showMessageDialog(null,
																			"Error inserting item: " + currentItem
																					+ " - " + sq2.getMessage(),
																			"Item Error", JOptionPane.ERROR_MESSAGE);
																}
															}
														}

														// Notify the user that the order was processed
														JOptionPane.showMessageDialog(null,
																"Order has been successfully processed!", "Success",
																JOptionPane.INFORMATION_MESSAGE);
													}
												}
											}

										});
										itemSelectionPanel.add(new JLabel());
										itemSelectionPanel.add(additemsButton2);

										JScrollPane scrollPane = new JScrollPane(itemSelectionPanel);
										scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
										scrollPane.setHorizontalScrollBarPolicy(
												JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

										order2.getContentPane().add(scrollPane, BorderLayout.CENTER);

										order2.setVisible(true);
									}
								});
								Order.add(addItemsButton);

								tabbedPane.addTab("Customer", Customer);
								Customer.setLayout(null);

								JLabel lblNewLabel_1_1 = new JLabel("Enter the new Customer info:");
								lblNewLabel_1_1.setBounds(169, 38, 190, 16);
								Customer.add(lblNewLabel_1_1);

								JLabel lblNewLabel_2 = new JLabel("Customer Phone number:");
								lblNewLabel_2.setBounds(87, 158, 159, 16);
								Customer.add(lblNewLabel_2);

								JTextField custNameField = new JTextField();
								custNameField.setBounds(248, 120, 130, 26);
								custNameField.setColumns(10);
								Customer.add(custNameField);

								JLabel emp_name_1 = new JLabel("Customer Name:");
								emp_name_1.setBounds(141, 125, 105, 16);
								Customer.add(emp_name_1);

								JTextField custPhoneField = new JTextField();
								custPhoneField.setBounds(248, 153, 130, 26);
								custPhoneField.setColumns(10);
								Customer.add(custPhoneField);

								JButton btnNewButton_2 = new JButton("Add");
								btnNewButton_2.setBounds(227, 253, 75, 40);
								btnNewButton_2.addActionListener(new ActionListener() {// add Customer
									public void actionPerformed(ActionEvent e) {
										String cPhone = custPhoneField.getText();
										String cName = custNameField.getText();
										String numQuery = "SELECT * FROM CUSTOMER WHERE Phone_Number= '" + cPhone + "'";
										boolean numExists = false;
										// we need to verify uniqness and 10-char length for the number
										try (java.sql.Statement stmt4 = con.createStatement();
												ResultSet rs = stmt4.executeQuery(numQuery)) {
											if (rs.next()) {
												numExists = true;
												JOptionPane.showMessageDialog(null, "The customer already exists",
														"oops..", JOptionPane.ERROR_MESSAGE);
											}
										} catch (SQLException e4) {
											System.out.println("Error executing SQL: " + e4.getMessage());
										}
										if(cPhone.length()!=10){
											JOptionPane.showMessageDialog(null, "The number is invalid try again",
											"oops..", JOptionPane.ERROR_MESSAGE);

										}
										if (!numExists&&cPhone.length()==10) {
											try {
												String insertQuery1 = "INSERT INTO `CUSTOMER` (Cust_Name, Table_Number, Phone_Number) VALUES ('"
														+ cName + "', " + 0 + ", " + cPhone + ")";
												java.sql.Statement custStatement = con.createStatement();
												custStatement.executeUpdate(insertQuery1);
												JOptionPane.showMessageDialog(null,
														"Success! customer " + cName + " has been added"
														,"yay", JOptionPane.INFORMATION_MESSAGE);
											} catch (SQLException sq2) {
												JOptionPane.showMessageDialog(null,
														"Error inserting customer: " + cName + " - "
																+ sq2.getMessage(),
														"customer Error", JOptionPane.ERROR_MESSAGE);
											}
										}
									}
								});
								Customer.add(btnNewButton_2);

								cashierAdd.getContentPane().add(tabbedPane);

								cashierAdd.setVisible(true);

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

				} else

				{
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
		springLayout.putConstraint(SpringLayout.EAST, blank, -433, SpringLayout.EAST,

				getContentPane());
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

	static void fillETable(DefaultTableModel model, ResultSet resultSet) throws SQLException {

		while (resultSet.next()) {
			model.addRow(new Object[] { resultSet.getInt("Employee_ID"), resultSet.getInt("Bcode"),
					resultSet.getString("Emp_Name"), resultSet.getString("Residence_Number"),
					resultSet.getString("Emp_Phone"), resultSet.getString("Gender"), resultSet.getString("Role"),
					resultSet.getString("Neighborhood"), resultSet.getString("Street"),
					resultSet.getString("Post_Code"), resultSet.getDouble("Salary") });

		}

	}

	static void fillBTable(DefaultTableModel model, ResultSet resultSet) throws SQLException {

		while (resultSet.next()) {
			model.addRow(new Object[] { resultSet.getInt("Branch_code"),
					new SimpleDateFormat("HH:mm:ss").format(resultSet.getTime("Work_time")),
					resultSet.getString("City"), resultSet.getString("Neighborhood"), resultSet.getString("Street") });
		}
	}

	static void fillITable(DefaultTableModel model, ResultSet resultSet) throws SQLException {

		while (resultSet.next()) {
			model.addRow(new Object[] { resultSet.getString("Item_Name"), resultSet.getDouble("Price"),
					resultSet.getString("Item_Type"), resultSet.getInt("Calories") });
		}
	}

	public static void cashierSearch() {
        // Frame
        JFrame frame = new JFrame("Cashier search");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(438, 345);

        frame.setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);

        // ------------------- Tab 1----------------
        JPanel panel1 = new JPanel();

        panel1.setLayout(null);
        JLabel label = new JLabel("search by ");
        label.setBounds(50, 5, 230, 30);
        panel1.add(label);

        String list[] = { "Order_Number", "E_ID", "It_Name", "Cus_phone" };
        @SuppressWarnings({ "rawtypes", "unchecked" })
        JComboBox jComboBox1 = new JComboBox(list);

        jComboBox1.setBounds(50, 30, 100, 50);
        panel1.add(jComboBox1);

        JLabel label2 = new JLabel("enter value ");
        label2.setBounds(250, 5, 230, 30);
        panel1.add(label2);

        JTextField value = new JTextField();
        value.setBounds(250, 40, 100, 30);
        value.setPreferredSize(null);

        panel1.add(value);

        // result
        JTextArea textArea = new JTextArea();
        textArea.setColumns(15);
        textArea.setRows(8);
        textArea.setBorder(BorderFactory.createLineBorder(Color.blue));
        textArea.setEditable(false);
        textArea.setBounds(50, 90, 300, 150);
        panel1.add(textArea);

        JButton searchButton = new JButton("Search");
        searchButton.setBounds(120, 240, 150, 30);

        panel1.add(searchButton);
        panel1.revalidate();
        panel1.repaint();

        searchButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String sql = " SELECT * FROM `ORDER` INNER JOIN `CONSIST_OF` ON `ORDER`.`Order_Number` = `CONSIST_OF`.`Ord_Number` WHERE `"
                            + jComboBox1.getSelectedItem().toString() + "` = ? ";

                    PreparedStatement pst = con.prepareStatement(sql);

                    if (jComboBox1.getSelectedItem().toString().equals("It_Name")
                            || jComboBox1.getSelectedItem().toString().equals("Cus_phone"))
                        pst.setString(1, value.getText());
                    else
                        pst.setInt(1, Integer.parseInt(value.getText()));

                    ResultSet rs = pst.executeQuery();
                    if (rs.next() == false) {
                        textArea.setText("");
                        JOptionPane.showMessageDialog(null, "Not found");
                    } else {
                        // String res = "";
                        textArea.setText("");

                        int ordID = rs.getInt(2);
                        boolean firstItem = true;
                        do {
                            int ord2;
                            if (firstItem) {
                                System.out.println();
                                textArea.append("Employee ID: " + rs.getString(1) + "\n");
                                textArea.append("Order number: " + ordID + "\n");
                                textArea.append("Total price: " + rs.getString(3) + "\n");
                                textArea.append("Preperation time: " + rs.getString(4) + "\n");
                                textArea.append("Customer phone number: " + rs.getString(5) + "\n");
                                textArea.append("payment method: " + rs.getString(6) + "\n");
                                textArea.append("Items:\n");
                                firstItem = false;
                            }

                            textArea.append(rs.getString(7) + " Quantity: " + rs.getString(9) + "\n");

                            if (rs.next()) {
                                ord2 = rs.getInt(2);
                                if (ord2 == ordID)
                                    continue;
                                else {
                                    firstItem = true;
                                    ordID = ord2;
                                    continue;
                                }
                            }
                        } while (rs.next());

                    }
                } catch (SQLException ex) {
                    // ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());

                } finally {
                    value.setText("");
                }

            }

        });

        tabbedPane.addTab("Order", panel1);

        // ------------------- Tab 2----------------
        JPanel panel2 = new JPanel();
        panel2.setLayout(null);
        JLabel label3 = new JLabel("search by ");
        label3.setBounds(50, 5, 230, 30);
        panel2.add(label3);

        String list2[] = { "Cust_Name", "Phone_Number" };
        @SuppressWarnings({ "rawtypes", "unchecked" })
        JComboBox jComboBox2 = new JComboBox(list2);
        jComboBox2.setBounds(50, 30, 100, 50);

        panel2.add(jComboBox2);

        JLabel label4 = new JLabel("enter value ");
        label4.setBounds(250, 5, 230, 30);
        panel2.add(label4);

        JTextField value2 = new JTextField();
        value2.setColumns(10);
        value2.setBounds(250, 40, 100, 30);
        panel2.add(value2);

        // result
        JTextArea textArea2 = new JTextArea();
        textArea.setBounds(50, 90, 300, 150);

        textArea2.setColumns(15);
        textArea2.setRows(8);
        textArea2.setBorder(BorderFactory.createLineBorder(Color.blue));
        textArea2.setEditable(false);
        textArea2.setBounds(50, 90, 300, 150);
        panel2.add(textArea2);

        JButton searchButton2 = new JButton("Search");
        searchButton2.setBounds(120, 240, 150, 30);
        panel2.add(searchButton2);

        searchButton2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String sql = " SELECT * FROM `CUSTOMER` INNER JOIN `ORDER` ON `CUSTOMER`.`Phone_Number`=`ORDER`.`Cus_phone` WHERE `"
                            + jComboBox2.getSelectedItem().toString() + "` = ? ";
                    PreparedStatement pst = con.prepareStatement(sql);

                    pst.setString(1, value2.getText());
                    System.out.println("Searching for phone number: " + value2.getText());

                    ResultSet rs = pst.executeQuery();
                    if (rs.next() == false) {
                        textArea2.setText("");
                        JOptionPane.showMessageDialog(null, "Not found");
                    } else {
                        int ordID = rs.getInt(2);
                        textArea2.setText("");
                        do {
                            textArea2.append("Customer Name: " + rs.getString(1) + "\n");
                            textArea2.append("phone number: " + ordID + "\n");
                            textArea2.append("orders numbers: \n" + rs.getString(3) + "\n");

                            textArea2.append(rs.getString(7) + " Quantity: " + rs.getString(9) + "\n");

                            int ordID2 = rs.getInt(2);
                            while (rs.next() && ordID2 == ordID) {
                                textArea2.append(rs.getString(7) + " Quantity: " + rs.getString(9) + "\n");
                                ordID2 = rs.getInt(2);
                            }
                        } while (rs.next());
                    }

                } catch (SQLException ex) {
                    // ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());

                } finally {
                    value2.setText("");
                }

            }
        });

        tabbedPane.addTab("Customer", panel2);

        frame.add(tabbedPane);
        frame.setVisible(true);

    }

    public static void cashierUpdate() {

        // Frame
        JFrame frame = new JFrame("Cashier Update");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(438, 345);
        frame.setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);

        // ------------------- Tab 1----------------
        JPanel panel1 = new JPanel();
        panel1.setLayout(null);
        JLabel label = new JLabel("choose order id: ");
        label.setBounds(50, 5, 230, 30);
        panel1.add(label);

        // create and update comboBox

        @SuppressWarnings("rawtypes")
        JComboBox jComboBox1 = new JComboBox();
        String sql2 = "SELECT `Order_Number` from `ORDER`";
        try {
            java.sql.Statement ps2 = con.createStatement();
            ResultSet rs2 = ps2.executeQuery(sql2);
            while (rs2.next()) {
                jComboBox1.addItem(rs2.getInt("Order_Number"));
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR!");
        }

        jComboBox1.setBounds(50, 30, 100, 50);
        panel1.add(jComboBox1);

        JLabel label2 = new JLabel("select what to edit ");
        label2.setBounds(250, 5, 230, 30);
        panel1.add(label2);

        String list[] = { "E_ID", "Preperation_time", "Payment_method" };
        JComboBox<String> jComboBox2 = new JComboBox<String>(list);
        panel1.add(jComboBox2);
        jComboBox2.setBounds(250, 40, 100, 30);

        JLabel label3 = new JLabel("new value");
        panel1.add(label3);
        label3.setBounds(170, 90, 230, 30);

        JTextField value = new JTextField();
        value.setColumns(10);
        value.setBounds(150, 120, 100, 30);
        panel1.add(value);

        JButton updateButton = new JButton("update");
        updateButton.setBounds(150, 150, 100, 30);
        panel1.add(updateButton);

        updateButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    System.out.println(jComboBox2.getSelectedItem().toString());
                    System.out.println(value.getText());

                    String sql = "UPDATE `ORDER` SET `" + jComboBox2.getSelectedItem().toString()
                            + "` = ? WHERE `Order_Number` = ? ";

                    PreparedStatement pst = con.prepareStatement(sql);

                    if (jComboBox2.getSelectedItem().toString().equals("Payment_method"))
                        pst.setString(1, value.getText());
                    else
                        pst.setInt(1, Integer.parseInt(value.getText()));
                    pst.setInt(2, Integer.parseInt(jComboBox1.getSelectedItem().toString()));

                    int count = pst.executeUpdate();
                    if (count > 0)
                        JOptionPane.showMessageDialog(null, "Updated succesfully!");
                    else
                        JOptionPane.showMessageDialog(null, "ERROR!");

                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
                } finally {
                    value.setText("");
                }

            }
        });

        tabbedPane.addTab("Order", panel1);

        // ------------------- Tab 2----------------
        JPanel panel2 = new JPanel();

        JLabel label4 = new JLabel("choose customer phone number: ");
        label4.setBounds(10, 100, 230, 30);
        panel2.add(label4);

        // create and update comboBox

        @SuppressWarnings("rawtypes")
        JComboBox jComboBox3 = new JComboBox();
        String sql3 = "SELECT `Phone_Number` from `CUSTOMER`";
        try {
            java.sql.Statement ps2 = con.createStatement();
            ResultSet rs2 = ps2.executeQuery(sql3);
            // Vector vector = new vector();
            while (rs2.next()) {
                jComboBox3.addItem(rs2.getString("Phone_Number"));
            }

        } catch (Exception e) {

        }
        panel2.add(jComboBox3);

        JLabel label5 = new JLabel("new name");
        panel2.add(label5);

        JTextField value2 = new JTextField();
        value2.setColumns(10);
        value2.setBounds(250, 100, 190, 30);
        panel2.add(value2);

        JButton updateButton2 = new JButton("update");
        updateButton2.setBounds(450, 100, 150, 30);
        panel2.add(updateButton2);

        updateButton2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    String sql = "UPDATE `CUSTOMER` SET `Cust_Name` = ? WHERE `Phone_Number` = ? ";

                    PreparedStatement pst = con.prepareStatement(sql);

                    pst.setString(1, value2.getText());

                    pst.setString(2, jComboBox3.getSelectedItem().toString());

                    int count = pst.executeUpdate();
                    if (count > 0)
                        JOptionPane.showMessageDialog(null, "Updated succesfully!");
                    else
                        JOptionPane.showMessageDialog(null, "ERROR!");

                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
                } catch (Exception ex) {
                    // ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
                }

                finally {
                    value2.setText("");
                }

            }
        });

        tabbedPane.addTab("Customer", panel2);

        frame.add(tabbedPane);
        frame.setVisible(true);

    }

    public static void cashierRemove() {
        JComboBox jComboBox1 = new JComboBox<>();
        String sql2 = "SELECT `Order_Number` from `ORDER`";
        try {
            java.sql.Statement ps2 = con.createStatement();
            ResultSet rs2 = ps2.executeQuery(sql2);
            while (rs2.next()) {
                jComboBox1.addItem(rs2.getInt("Order_Number"));
            }

        } catch (Exception e) {

        }

        int id = Integer.parseInt(JOptionPane.showInputDialog(null, "order id"));

        try {
            String sql = "DELETE FROM `ORDER` WHERE `Order_Number` = ? ";

            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, id);

            int count = pst.executeUpdate();
            if (count > 0)
                JOptionPane.showMessageDialog(null, "Deleted succesfully!");
            else
                JOptionPane.showMessageDialog(null, "ERROR!");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
        }

    }
}

