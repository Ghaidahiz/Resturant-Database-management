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


        {   //  ************ MANAGER REMOVE VEIW *************
             JFrame managerRemove= new JFrame(); 
       managerRemove.setTitle("REMOVE");
		managerRemove.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		managerRemove.setBounds(100, 100, 450, 300);
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