import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");
        App app = new App();
        app.createConnection();

    }
    void createConnection(){
        try {
            // Use the correct driver class name for MySQL Connector/J 8.x
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Establish connection
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Database381", "root", "gH123456");
            System.out.println("db con good");
        } 
        catch (ClassNotFoundException ex) {
            System.out.println("ERRORRRR! Driver class not found.");
        }
        catch (SQLException exception) {
            System.out.println("ERRORRRR SQLLL! " + exception.getMessage());
        }
        
    }}
