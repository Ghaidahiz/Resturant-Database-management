
import java.sql.*;
import io.github.cdimascio.dotenv.Dotenv;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import java.awt.Image;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class Wister extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	static Connection con;
	static int numOfAffectedRow;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Wister frame = new Wister();
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

						managerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

						JButton managerSearch = new JButton("Search"); // TODO: EMAN
						managerSearch.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
							}
						});

						JButton managerUpdate = new JButton("Update");// TODO: EMAN
						managerUpdate.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
							}
						});

						JButton bmanagerRemove = new JButton("Remove");// TODO: EMAN
						bmanagerRemove.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								JFrame managerRemove = new JFrame();
								managerRemove.setTitle("REMOVE");
								managerRemove.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
								managerRemove.setBounds(100, 100, 550, 400);
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
								labal_2.setBounds(10, 26, 181, 32);
								Employee.add(labal_2);

								// set up tab Employee (remove) until line 93
								JComboBox<String> comboBox = new JComboBox<String>();
								comboBox.setModel(
										new DefaultComboBoxModel<String >(new String[] { " Employee_ID", "Emp_Name", "Gender",
												"Role", "Salary" }));
								comboBox.setBounds(53, 69, 96, 20);
								Employee.add(comboBox);

								JTextField textField = new JTextField();
								textField.setBounds(229, 69, 96, 20);
								Employee.add(textField);
								textField.setColumns(10);

								JLabel lblNewLabel = new JLabel("enter a value to remove all");
								lblNewLabel.setBounds(213, 23, 171, 20);
								Employee.add(lblNewLabel);

								JLabel lblNewLabel_1 = new JLabel("employees that match:");
								lblNewLabel_1.setBounds(229, 45, 138, 20);
								Employee.add(lblNewLabel_1);

								JButton button = new JButton("Remove");
								button.setBounds(314, 173, 89, 23);
								Employee.add(button);

								button.addActionListener(new ActionListener() {
									@Override
									public void actionPerformed(ActionEvent e) {
										try {
											Statement stmt = con.createStatement();
											// ResultSet sr;
											if (comboBox.getSelectedItem().equals("Emp_Name")
													|| comboBox.getSelectedItem().equals("Gender")
													|| comboBox.getSelectedItem().equals("Role"))
												numOfAffectedRow = stmt.executeUpdate(
														"DELETE FROM EMPLOYEE " +
																"WHERE " + comboBox.getSelectedItem() + "=" + "\'"
																+ textField.getText()
																+ "\'");

											else {
												int num = Integer.parseInt(textField.getText());
												numOfAffectedRow = stmt.executeUpdate(
														"DELETE FROM EMPLOYEE " +
																"WHERE " + comboBox.getSelectedItem() + "=" + num);
											}

											if (numOfAffectedRow == 0)
												JOptionPane.showMessageDialog(null,
														"There is no Empolyee with the received value");

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
								lblNewLabel_2.setBounds(10, 36, 257, 31);
								Branch.add(lblNewLabel_2);

								JTextField textField_1 = new JTextField();
								textField_1.setBounds(277, 41, 96, 20);
								Branch.add(textField_1);
								textField_1.setColumns(10);

								JButton Button_1 = new JButton("Remove");
								Button_1.setBounds(277, 72, 96, 23);
								Branch.add(Button_1);

								Button_1.addActionListener(new ActionListener() {

									public void actionPerformed(ActionEvent e) {
										try {
											int code = Integer.parseInt(textField_1.getText());

											Statement stmt = con.createStatement();
											numOfAffectedRow = stmt.executeUpdate(
													"DELETE FROM BRANCH " +
															"WHERE Branch_code=" + code

											);

											if (numOfAffectedRow == 0)
												JOptionPane.showMessageDialog(null,
														"There is no Branch with the recived Branch_code");

										} catch (NumberFormatException ex) {
											JOptionPane.showMessageDialog(null, "please enter a number", "Input Error",
													JOptionPane.WARNING_MESSAGE);

										}

										catch (SQLException ex) {
											System.out.println(ex.getMessage());

										}

									}

								});

								JTextArea txtrEman = new JTextArea();
								txtrEman.setText("eman\r\nameen\r\nf\r\nf\r\nf\r\nf\r\nd\r\nd\r\nd\r\nd\r\n");
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
											Statement stmt = con.createStatement();
											numOfAffectedRow = stmt.executeUpdate(
													"DELETE FROM ITEM " +
															"WHERE Item_Name= \'" + textField_2.getText() + "\'");
											if (numOfAffectedRow == 0)
												JOptionPane.showMessageDialog(null,
														"There is no item with the received name!");
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

						JButton managerAdd = new JButton("Add"); // TODO: Ghaida
						managerAdd.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
							}
						});

						JLabel lblNewLabel = new JLabel(
								"Select the operation you want to perform on Wister's Database");
						GroupLayout groupLayout = new GroupLayout(managerFrame.getContentPane());
						groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
										.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
												.addGroup(groupLayout.createSequentialGroup().addGap(151)
														.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
																.addComponent(managerAdd, GroupLayout.PREFERRED_SIZE,
																		149, GroupLayout.PREFERRED_SIZE)
																.addComponent(bmanagerRemove,
																		GroupLayout.PREFERRED_SIZE,
																		149, GroupLayout.PREFERRED_SIZE)
																.addComponent(managerUpdate, GroupLayout.PREFERRED_SIZE,
																		149, GroupLayout.PREFERRED_SIZE)
																.addComponent(managerSearch, GroupLayout.PREFERRED_SIZE,
																		149, GroupLayout.PREFERRED_SIZE)))
												.addGroup(groupLayout.createSequentialGroup().addGap(29)
														.addComponent(lblNewLabel)))
										.addContainerGap(29, Short.MAX_VALUE)));
						groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup().addGap(27).addComponent(lblNewLabel)
										.addGap(18)
										.addComponent(managerAdd, GroupLayout.PREFERRED_SIZE, 53,
												GroupLayout.PREFERRED_SIZE)
										.addGap(6)
										.addComponent(bmanagerRemove, GroupLayout.PREFERRED_SIZE, 53,
												GroupLayout.PREFERRED_SIZE)
										.addGap(6)
										.addComponent(managerUpdate, GroupLayout.PREFERRED_SIZE, 53,
												GroupLayout.PREFERRED_SIZE)
										.addGap(6).addComponent(managerSearch, GroupLayout.PREFERRED_SIZE, 53,
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
		ImageIcon imageIcon = new ImageIcon(new ImageIcon("/Users/ghaida/Downloads/Subject.png").getImage()
				.getScaledInstance(50, 50, Image.SCALE_DEFAULT));

		blank.setIcon(imageIcon);
		getContentPane().add(blank);
		setBackground(new Color(45, 45, 45));
		setBounds(400, 200, 556, 367);
		setVisible(true);

	}
}
