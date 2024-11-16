import java.sql.*;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import io.github.cdimascio.dotenv.Dotenv;

public class App {
    static Connection con;
    static int numOfAffectedRow;

    public static void main(String[] args) throws Exception {
        App app = new App();
        app.createConnection(); // this creates the connection with the data base

        // this code below is just an example!!!!!


        Statement stmt = con.createStatement();// this creates a statement so we can send it to the database
        ResultSet rs = stmt.executeQuery( // we save the results that are made by the query is rs
                " SELECT * FROM EMPLOYEE WHERE Bcode='Riyadh-01' ");
        while (rs.next()) {// this reads the results and prints it
            System.out.println(rs.getString(1));// emp id
            System.out.println(rs.getString(2));// emp branch
            System.out.println(rs.getString(3));// emp name
        }
        
        // GUI IS HERE

    }

    void createConnection() {
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

    @SuppressWarnings("unchecked")
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
            Statement ps2 = con.createStatement();
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
            Statement ps2 = con.createStatement();
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

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static void removeCashier() {
        JComboBox jComboBox1 = new JComboBox<>();
        String sql2 = "SELECT `Order_Number` from `ORDER`";
        try {
            Statement ps2 = con.createStatement();
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
