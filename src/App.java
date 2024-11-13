import java.sql.*;
import io.github.cdimascio.dotenv.Dotenv;
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class App {
    static Connection con;
    static int numOfAffectedRow;

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void main(String[] args) throws Exception {
        App app = new App();
        app.createConnection(); // this creates the connection with the data base
        JFrame managerRemove= new JFrame(),
        managerUpdare= new JFrame();

        {   //  ************ MANAGER REMOVE VEIW *************
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
        comboBox.setModel(new DefaultComboBoxModel(new String[] {" Employee_ID", "Emp_Name", "Gender", "Role", "Salary"}));
        comboBox.setBounds(53, 69, 96, 20);
        Employee.add(comboBox);
        
       JTextField  textField = new JTextField();
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
        try{      
        Statement stmt = con.createStatement();
        //ResultSet sr;
        if(comboBox.getSelectedItem().equals("Emp_Name") || comboBox.getSelectedItem().equals("Gender") || comboBox.getSelectedItem().equals("Role") )
        numOfAffectedRow=stmt.executeUpdate(
        "DELETE FROM EMPLOYEE "+
        "WHERE "+ comboBox.getSelectedItem()+"="+"\'"+textField.getText()+"\'");
        
        else{
            int num=Integer.parseInt(textField.getText());
            numOfAffectedRow = stmt.executeUpdate(
                             "DELETE FROM EMPLOYEE "+
                             "WHERE "+ comboBox.getSelectedItem()+"="+num);}
        
         if(numOfAffectedRow==0)
         JOptionPane.showMessageDialog(null, "There is no Empolyee with the received value");


        }
        catch(NumberFormatException E){
            JOptionPane.showMessageDialog(null,"please enter a number", "Input Error", JOptionPane.WARNING_MESSAGE);  
        }
        catch(SQLException E){
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
        
        Button_1.addActionListener(new ActionListener(){
        	
        	public void actionPerformed(ActionEvent e) {
        		try {
        		    int code= Integer.parseInt(textField_1.getText());
                    
                    Statement stmt = con.createStatement();
                    numOfAffectedRow =stmt.executeUpdate(
                    "DELETE FROM BRANCH "+
                    "WHERE Branch_code="+code

                    );

                    if(numOfAffectedRow==0)
                    JOptionPane.showMessageDialog(null, "There is no Branch with the recived Branch_code");

        		}
        		catch(NumberFormatException ex){
        		 JOptionPane.showMessageDialog(null,"please enter a number", "Input Error", JOptionPane.WARNING_MESSAGE);
        		
        		}
        		
        		
        		catch(SQLException ex){
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
        	         numOfAffectedRow=stmt.executeUpdate(
        	        "DELETE FROM ITEM "+
        	        "WHERE Item_Name= \'"+textField_2.getText()+"\'"
        	        );
        	      if (numOfAffectedRow ==0)
        	      JOptionPane.showMessageDialog(null,"There is no item with the received name!");
        		}
        		catch(SQLException e) {
                    System.out.println(e.getMessage());
                }	 
        	}	
        });




        

        tabbedPane.addTab("Employee",Employee);
        tabbedPane.addTab("Branch",Branch);
        tabbedPane.addTab("Item",Item);


      managerRemove.getContentPane().add(tabbedPane);

      managerRemove.setVisible(true); }  // END OF MANAGER REMOVE VEIW



        
      {  //  ************ MANAGER UPDATE VEIW *************
        managerUpdare.setTitle("UPDATE");
      managerUpdare.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      managerUpdare.setBounds(100, 100, 550, 400);
      managerUpdare.getContentPane().setLayout(new BorderLayout());

		JTabbedPane tabbedPane = new JTabbedPane();

		JPanel Item = new JPanel();
		Item.setLayout(null);
		 
		 		JPanel Employee = new JPanel();
		 		Employee.setLayout(null);
		 		tabbedPane.addTab("Employee",Employee);
		 		
		 		JLabel lblNewLabel = new JLabel("Enter ID of the employee you want to update his info:");
		 		lblNewLabel.setBounds(10, 51, 312, 31);
		 		Employee.add(lblNewLabel);
		 		
		 		JComboBox comboBox_1 = new JComboBox();
		 		comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"Salary", "Emp_Phone", "Role"}));
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
		 		
		 		JButton btnNewButton = new JButton("UPDATE");
		 		btnNewButton.setBounds(332, 201, 96, 23);
		 		Employee.add(btnNewButton);
		 		
		 		JTextField textField_6 = new JTextField();
		 		textField_6.setBounds(332, 56, 96, 20);
		 		Employee.add(textField_6);
		 		textField_6.setColumns(10);
		 
		 		JPanel Branch = new JPanel();
		 		Branch.setLayout(null);
		 		tabbedPane.addTab("Branch",Branch);
		 		
		 		JLabel lblNewLabel_3 = new JLabel("enter branch code of the branch you want to update  its working time:\r\n");
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
		 		
		 		JButton btnNewButton_1 = new JButton("UPDATE");
		 		btnNewButton_1.setBounds(399, 287, 96, 23);
		 		Branch.add(btnNewButton_1);
		 		
		 		JComboBox<String> comboBox = new JComboBox<String>();
		 		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"00", "01", "02", "03", "04", "05", "06", "07", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"}));
		 		comboBox.setBounds(135, 103, 42, 22);
		 		Branch.add(comboBox);
		 		
		 		JComboBox<String> comboBox_2 = new JComboBox<String>();
		 		comboBox_2.setModel(new DefaultComboBoxModel<String>(new String[] {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59"}));
		 		comboBox_2.setBounds(135, 136, 42, 22);
		 		Branch.add(comboBox_2);
		 		
		 		JLabel lblNewLabel_4 = new JLabel("Hour");
		 		lblNewLabel_4.setBounds(86, 107, 49, 14);
		 		Branch.add(lblNewLabel_4);
		 		
		 		JLabel lblNewLabel_6 = new JLabel("Minutes ");
		 		lblNewLabel_6.setBounds(86, 140, 49, 14);
		 		Branch.add(lblNewLabel_6);
		 tabbedPane.addTab("Item",Item);
		 
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

         managerUpdare.getContentPane().add(tabbedPane);
         managerUpdare.setVisible(true);

      } //END OF MANAGER UPDATE VEIW








        Statement stmt = con.createStatement();// this creates a statement so we can send it to the database
        ResultSet rs = stmt.executeQuery( // we save the results that are made by the query is rs
                " SELECT * "+
               " FROM EMPLOYEE "+
               "WHERE Emp_Name = 'Ali' ");
       
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
}