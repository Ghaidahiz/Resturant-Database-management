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
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

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

						JButton managerSearchBtn = new JButton("Search"); //TODO: EMAN
						managerSearchBtn.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
							}
						});

						JButton managerUpdateBtn = new JButton("Update");//TODO: EMAN
						managerUpdateBtn.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
							}
						});

						JButton managerRemoveBtn = new JButton("Remove");//TODO: EMAN
						managerRemoveBtn.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
                                 JFrame managerRemove= new JFrame();
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
                    new DefaultComboBoxModel<String>(new String[] { " Employee_ID", "Emp_Name", "Gender", "Role", "Salary" }));
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
                        java.sql.Statement stmt = con.createStatement();
                        // ResultSet sr;
                        if (comboBox.getSelectedItem().equals("Emp_Name") || comboBox.getSelectedItem().equals("Gender")
                                || comboBox.getSelectedItem().equals("Role"))
                            numOfAffectedRow = stmt.executeUpdate(
                                    "DELETE FROM EMPLOYEE " +
                                            "WHERE " + comboBox.getSelectedItem() + "=" + "\'" + textField.getText()
                                            + "\'");

                        else {
                            int num = Integer.parseInt(textField.getText());
                            numOfAffectedRow = stmt.executeUpdate(
                                    "DELETE FROM EMPLOYEE " +
                                            "WHERE " + comboBox.getSelectedItem() + "=" + num);
                        }

                        if (numOfAffectedRow == 0)
                            JOptionPane.showMessageDialog(null, "There is no Empolyee with the received value");

                    } catch (NumberFormatException E) {
                        JOptionPane.showMessageDialog(null, "please enter a number", "Input Error",
                                JOptionPane.WARNING_MESSAGE);
                    } catch (SQLException E) {
                        System.out.println(E.getMessage());
                    }
                }
            });

            JLabel lblNewLabel_2 = new JLabel("enter the code of the Branch \r\nyou want to remove:");
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

                        java.sql.Statement stmt = con.createStatement();
                        numOfAffectedRow = stmt.executeUpdate(
                                "DELETE FROM BRANCH " +
                                        "WHERE Branch_code=" + code

                        );

                        if (numOfAffectedRow == 0)
                            JOptionPane.showMessageDialog(null, "There is no Branch with the recived Branch_code");

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
                        java.sql.Statement stmt = con.createStatement();
                        numOfAffectedRow = stmt.executeUpdate(
                                "DELETE FROM ITEM " +
                                        "WHERE Item_Name= \'" + textField_2.getText() + "\'");
                        if (numOfAffectedRow == 0)
                            JOptionPane.showMessageDialog(null, "There is no item with the received name!");
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

						JButton managerAddBtn = new JButton("Add"); //TODO: Ghaida
						managerAddBtn.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								JFrame managerAdd= new JFrame();
								managerAdd.setTitle("Add");
								managerAdd.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
								managerAdd.setBounds(100, 100, 550, 400);
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
								ButtonGroup group = new ButtonGroup();
								GridBagLayout gbl_Employee = new GridBagLayout();
								gbl_Employee.columnWidths = new int[]{122, 3, 41, 86, 25, 40, 6, 67, 94, 0};
								gbl_Employee.rowHeights = new int[]{31, 16, 26, 32, 29, 32, 26, 5, 5, 4, 21, 40, 0};
								gbl_Employee.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
								gbl_Employee.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
								Employee.setLayout(gbl_Employee);
								
								JLabel lblNewLabel_1 = new JLabel("Enter the new employee's info:");
								GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
								gbc_lblNewLabel_1.anchor = GridBagConstraints.NORTHWEST;
								gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
								gbc_lblNewLabel_1.gridwidth = 5;
								gbc_lblNewLabel_1.gridx = 3;
								gbc_lblNewLabel_1.gridy = 1;
								Employee.add(lblNewLabel_1, gbc_lblNewLabel_1);
								
								JLabel lblNewLabel = new JLabel("Employee ID:");
								GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
								gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
								gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
								gbc_lblNewLabel.gridx = 0;
								gbc_lblNewLabel.gridy = 2;
								Employee.add(lblNewLabel, gbc_lblNewLabel);
								
								JTextField empId = new JTextField();
								empId.setColumns(10);
								GridBagConstraints gbc_empId = new GridBagConstraints();
								gbc_empId.anchor = GridBagConstraints.NORTHWEST;
								gbc_empId.insets = new Insets(0, 0, 5, 5);
								gbc_empId.gridwidth = 3;
								gbc_empId.gridx = 1;
								gbc_empId.gridy = 2;
								Employee.add(empId, gbc_empId);
								
								JLabel emp_name = new JLabel("Name:");
								GridBagConstraints gbc_emp_name = new GridBagConstraints();
								gbc_emp_name.anchor = GridBagConstraints.EAST;
								gbc_emp_name.insets = new Insets(0, 0, 5, 5);
								gbc_emp_name.gridx = 0;
								gbc_emp_name.gridy = 3;
								Employee.add(emp_name, gbc_emp_name);
								
								JTextField empName = new JTextField();
								empName.setColumns(10);
								GridBagConstraints gbc_empName = new GridBagConstraints();
								gbc_empName.anchor = GridBagConstraints.SOUTHWEST;
								gbc_empName.insets = new Insets(0, 0, 5, 5);
								gbc_empName.gridwidth = 3;
								gbc_empName.gridx = 1;
								gbc_empName.gridy = 3;
								Employee.add(empName, gbc_empName);
								
								JLabel lblNewLabel_3_1_1_1 = new JLabel("Branch:");
								GridBagConstraints gbc_lblNewLabel_3_1_1_1 = new GridBagConstraints();
								gbc_lblNewLabel_3_1_1_1.anchor = GridBagConstraints.NORTHEAST;
								gbc_lblNewLabel_3_1_1_1.insets = new Insets(0, 0, 5, 5);
								gbc_lblNewLabel_3_1_1_1.gridwidth = 3;
								gbc_lblNewLabel_3_1_1_1.gridx = 4;
								gbc_lblNewLabel_3_1_1_1.gridy = 3;
								Employee.add(lblNewLabel_3_1_1_1, gbc_lblNewLabel_3_1_1_1);
								
								JComboBox comboBox_1_1 = new JComboBox(); //TODO: FIXXXX
								comboBox_1_1.setModel(new DefaultComboBoxModel(new String[] {"Hi", "Hello", "Test"}));
								GridBagConstraints gbc_comboBox_1_1 = new GridBagConstraints();
								gbc_comboBox_1_1.anchor = GridBagConstraints.NORTHWEST;
								gbc_comboBox_1_1.insets = new Insets(0, 0, 5, 0);
								gbc_comboBox_1_1.gridwidth = 2;
								gbc_comboBox_1_1.gridx = 7;
								gbc_comboBox_1_1.gridy = 3;
								Employee.add(comboBox_1_1, gbc_comboBox_1_1);
								
								JLabel lblNewLabel_3_1_1_2 = new JLabel("Salary:");
								GridBagConstraints gbc_lblNewLabel_3_1_1_2 = new GridBagConstraints();
								gbc_lblNewLabel_3_1_1_2.anchor = GridBagConstraints.EAST;
								gbc_lblNewLabel_3_1_1_2.insets = new Insets(0, 0, 5, 5);
								gbc_lblNewLabel_3_1_1_2.gridwidth = 2;
								gbc_lblNewLabel_3_1_1_2.gridx = 0;
								gbc_lblNewLabel_3_1_1_2.gridy = 4;
								Employee.add(lblNewLabel_3_1_1_2, gbc_lblNewLabel_3_1_1_2);
								
								JTextField textField_5 = new JTextField();
								textField_5.setColumns(10);
								GridBagConstraints gbc_textField_5 = new GridBagConstraints();
								gbc_textField_5.anchor = GridBagConstraints.SOUTHWEST;
								gbc_textField_5.insets = new Insets(0, 0, 5, 5);
								gbc_textField_5.gridwidth = 3;
								gbc_textField_5.gridx = 1;
								gbc_textField_5.gridy = 4;
								Employee.add(textField_5, gbc_textField_5);
								
								JLabel lblNewLabel_3_1_1_1_1 = new JLabel("Gender:");
								GridBagConstraints gbc_lblNewLabel_3_1_1_1_1 = new GridBagConstraints();
								gbc_lblNewLabel_3_1_1_1_1.anchor = GridBagConstraints.NORTHEAST;
								gbc_lblNewLabel_3_1_1_1_1.insets = new Insets(0, 0, 5, 5);
								gbc_lblNewLabel_3_1_1_1_1.gridwidth = 3;
								gbc_lblNewLabel_3_1_1_1_1.gridx = 4;
								gbc_lblNewLabel_3_1_1_1_1.gridy = 4;
								Employee.add(lblNewLabel_3_1_1_1_1, gbc_lblNewLabel_3_1_1_1_1);
								
								JRadioButton rdbtnMale = new JRadioButton("Male");
								group.add(rdbtnMale);
								GridBagConstraints gbc_rdbtnMale = new GridBagConstraints();
								gbc_rdbtnMale.anchor = GridBagConstraints.NORTH;
								gbc_rdbtnMale.fill = GridBagConstraints.HORIZONTAL;
								gbc_rdbtnMale.insets = new Insets(0, 0, 5, 5);
								gbc_rdbtnMale.gridx = 7;
								gbc_rdbtnMale.gridy = 4;
								Employee.add(rdbtnMale, gbc_rdbtnMale);
								
								JRadioButton rdbtnFemale = new JRadioButton("Female");
								group.add(rdbtnFemale);
								GridBagConstraints gbc_rdbtnFemale = new GridBagConstraints();
								gbc_rdbtnFemale.anchor = GridBagConstraints.NORTH;
								gbc_rdbtnFemale.fill = GridBagConstraints.HORIZONTAL;
								gbc_rdbtnFemale.insets = new Insets(0, 0, 5, 0);
								gbc_rdbtnFemale.gridx = 8;
								gbc_rdbtnFemale.gridy = 4;
								Employee.add(rdbtnFemale, gbc_rdbtnFemale);
										
										JLabel lblResidenceNumber = new JLabel("Residence Number:");
										GridBagConstraints gbc_lblResidenceNumber = new GridBagConstraints();
										gbc_lblResidenceNumber.fill = GridBagConstraints.HORIZONTAL;
										gbc_lblResidenceNumber.insets = new Insets(0, 0, 5, 5);
										gbc_lblResidenceNumber.gridwidth = 2;
										gbc_lblResidenceNumber.gridx = 0;
										gbc_lblResidenceNumber.gridy = 5;
										Employee.add(lblResidenceNumber, gbc_lblResidenceNumber);
										
										JTextField textField = new JTextField();
										textField.setColumns(10);
										GridBagConstraints gbc_textField = new GridBagConstraints();
										gbc_textField.anchor = GridBagConstraints.NORTHWEST;
										gbc_textField.insets = new Insets(0, 0, 5, 5);
										gbc_textField.gridwidth = 3;
										gbc_textField.gridx = 1;
										gbc_textField.gridy = 5;
										Employee.add(textField, gbc_textField);
								
										
										JLabel lblNewLabel_3_1_1_1_1_1 = new JLabel("Role:");
										GridBagConstraints gbc_lblNewLabel_3_1_1_1_1_1 = new GridBagConstraints();
										gbc_lblNewLabel_3_1_1_1_1_1.anchor = GridBagConstraints.WEST;
										gbc_lblNewLabel_3_1_1_1_1_1.insets = new Insets(0, 0, 5, 5);
										gbc_lblNewLabel_3_1_1_1_1_1.gridx = 5;
										gbc_lblNewLabel_3_1_1_1_1_1.gridy = 5;
										Employee.add(lblNewLabel_3_1_1_1_1_1, gbc_lblNewLabel_3_1_1_1_1_1);
								
								JComboBox comboBox_1 = new JComboBox();
								comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"Chef", "Cashier", "Server"}));
								GridBagConstraints gbc_comboBox_1 = new GridBagConstraints();
								gbc_comboBox_1.anchor = GridBagConstraints.SOUTHWEST;
								gbc_comboBox_1.insets = new Insets(0, 0, 5, 0);
								gbc_comboBox_1.gridwidth = 2;
								gbc_comboBox_1.gridx = 7;
								gbc_comboBox_1.gridy = 5;
								Employee.add(comboBox_1, gbc_comboBox_1);
								
								JLabel lblPhoneNumber = new JLabel("Phone Number:");
								GridBagConstraints gbc_lblPhoneNumber = new GridBagConstraints();
								gbc_lblPhoneNumber.anchor = GridBagConstraints.EAST;
								gbc_lblPhoneNumber.insets = new Insets(0, 0, 5, 5);
								gbc_lblPhoneNumber.gridx = 0;
								gbc_lblPhoneNumber.gridy = 6;
								Employee.add(lblPhoneNumber, gbc_lblPhoneNumber);
								
								JTextField textField_1 = new JTextField();
								textField_1.setColumns(10);
								GridBagConstraints gbc_textField_1 = new GridBagConstraints();
								gbc_textField_1.anchor = GridBagConstraints.NORTHWEST;
								gbc_textField_1.insets = new Insets(0, 0, 5, 5);
								gbc_textField_1.gridwidth = 3;
								gbc_textField_1.gridx = 1;
								gbc_textField_1.gridy = 6;
								Employee.add(textField_1, gbc_textField_1);
								
								JButton btnNewButton = new JButton("Add");
								btnNewButton.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
									}
								});
								
								JLabel lblNewLabel_3_1 = new JLabel("Street:");
								GridBagConstraints gbc_lblNewLabel_3_1 = new GridBagConstraints();
								gbc_lblNewLabel_3_1.anchor = GridBagConstraints.SOUTH;
								gbc_lblNewLabel_3_1.fill = GridBagConstraints.HORIZONTAL;
								gbc_lblNewLabel_3_1.insets = new Insets(0, 0, 5, 5);
								gbc_lblNewLabel_3_1.gridheight = 2;
								gbc_lblNewLabel_3_1.gridwidth = 2;
								gbc_lblNewLabel_3_1.gridx = 5;
								gbc_lblNewLabel_3_1.gridy = 6;
								Employee.add(lblNewLabel_3_1, gbc_lblNewLabel_3_1);
								
								JTextField textField_3 = new JTextField();
								textField_3.setColumns(10);
								GridBagConstraints gbc_textField_3 = new GridBagConstraints();
								gbc_textField_3.anchor = GridBagConstraints.SOUTHWEST;
								gbc_textField_3.insets = new Insets(0, 0, 5, 0);
								gbc_textField_3.gridheight = 3;
								gbc_textField_3.gridwidth = 3;
								gbc_textField_3.gridx = 6;
								gbc_textField_3.gridy = 6;
								Employee.add(textField_3, gbc_textField_3);
								
								JTextField textField_2 = new JTextField();
								textField_2.setColumns(10);
								GridBagConstraints gbc_textField_2 = new GridBagConstraints();
								gbc_textField_2.anchor = GridBagConstraints.NORTHWEST;
								gbc_textField_2.insets = new Insets(0, 0, 5, 5);
								gbc_textField_2.gridheight = 4;
								gbc_textField_2.gridwidth = 3;
								gbc_textField_2.gridx = 1;
								gbc_textField_2.gridy = 7;
								Employee.add(textField_2, gbc_textField_2);
								
								JLabel lblNewLabel_3 = new JLabel("Neighborhood:");
								GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
								gbc_lblNewLabel_3.anchor = GridBagConstraints.NORTHEAST;
								gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
								gbc_lblNewLabel_3.gridheight = 2;
								gbc_lblNewLabel_3.gridx = 0;
								gbc_lblNewLabel_3.gridy = 8;
								Employee.add(lblNewLabel_3, gbc_lblNewLabel_3);
								
								JTextField textField_4 = new JTextField();
								textField_4.setColumns(10);
								GridBagConstraints gbc_textField_4 = new GridBagConstraints();
								gbc_textField_4.anchor = GridBagConstraints.NORTHWEST;
								gbc_textField_4.insets = new Insets(0, 0, 5, 0);
								gbc_textField_4.gridheight = 2;
								gbc_textField_4.gridwidth = 3;
								gbc_textField_4.gridx = 6;
								gbc_textField_4.gridy = 9;
								Employee.add(textField_4, gbc_textField_4);
								
								JLabel lblNewLabel_3_1_1 = new JLabel("Post Code:");
								GridBagConstraints gbc_lblNewLabel_3_1_1 = new GridBagConstraints();
								gbc_lblNewLabel_3_1_1.anchor = GridBagConstraints.NORTHWEST;
								gbc_lblNewLabel_3_1_1.insets = new Insets(0, 0, 5, 5);
								gbc_lblNewLabel_3_1_1.gridwidth = 2;
								gbc_lblNewLabel_3_1_1.gridx = 4;
								gbc_lblNewLabel_3_1_1.gridy = 10;
								Employee.add(lblNewLabel_3_1_1, gbc_lblNewLabel_3_1_1);
								GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
								gbc_btnNewButton.anchor = GridBagConstraints.EAST;
								gbc_btnNewButton.fill = GridBagConstraints.VERTICAL;
								gbc_btnNewButton.insets = new Insets(0, 0, 0, 5);
								gbc_btnNewButton.gridwidth = 2;
								gbc_btnNewButton.gridx = 3;
								gbc_btnNewButton.gridy = 11;
								Employee.add(btnNewButton, gbc_btnNewButton);

								tabbedPane.addTab("Employee",Employee);

								tabbedPane.addTab("Branch",Branch);
								tabbedPane.addTab("Item",Item);
						
								
							  managerAdd.getContentPane().add(tabbedPane);
						
							  managerAdd.setVisible(true);
								

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
																.addComponent(managerAddBtn, GroupLayout.PREFERRED_SIZE,
																		149, GroupLayout.PREFERRED_SIZE)
																.addComponent(managerRemoveBtn, GroupLayout.PREFERRED_SIZE,
																		149, GroupLayout.PREFERRED_SIZE)
																.addComponent(managerUpdateBtn, GroupLayout.PREFERRED_SIZE,
																		149, GroupLayout.PREFERRED_SIZE)
																.addComponent(managerSearchBtn, GroupLayout.PREFERRED_SIZE,
																		149, GroupLayout.PREFERRED_SIZE)))
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
		ImageIcon imageIcon = new ImageIcon(new ImageIcon("img/Subject.png").getImage()
				.getScaledInstance(50, 50, Image.SCALE_DEFAULT));

		blank.setIcon(imageIcon);
		getContentPane().add(blank);
		setBackground(new Color(45, 45, 45));
		setBounds(400, 200, 556, 367);
		setVisible(true);

	}
}
