import java.sql.*;
import io.github.cdimascio.dotenv.Dotenv;

public class App {
    static Connection con;

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
}