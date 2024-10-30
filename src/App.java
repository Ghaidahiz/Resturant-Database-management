import java.sql.*;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");
        App app = new App();
        app.createConnection();

    }
    void createConnection() {
        try {
            // Use the correct driver class name for MySQL Connector/J 8.x
            Class.forName("com.mysql.cj.jdbc.Driver");
    
            // Get the database password from the environment variable (to protect the database)
            String dbPassword = System.getenv("DB_PASSWORD");
            
            // Establish connection using the environment variable
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Database381", "root", dbPassword);
            System.out.println("db connection successful");
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            Statement stmt = con.createStatement();//this creates a statement so we can send it to the database
            ResultSet rs = stmt.executeQuery( //we save the results that are made by the query is rs
                """
                SELECT * 
                FROM EMPLOYEE 
                WHERE Bcode='Riyadh-01'
                """);
            while(rs.next()){//this reads the results and prints it
                System.out.println(rs.getString(1));//emp id 
                System.out.println(rs.getString(2));//emp branch
                System.out.println(rs.getString(3));// emp name
            }
    
        } 
        catch (ClassNotFoundException ex) {
            System.out.println("ERRORRRR! Driver class not found.");
        }
        catch (SQLException exception) {
            System.out.println("ERRORRRR SQLLL! " + exception.getMessage());
        }
    }
}    