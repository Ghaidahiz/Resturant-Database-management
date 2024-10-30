import java.sql.*;

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
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(
                """
                SELECT * 
                FROM EMPLOYEE 
                WHERE Bcode='Riyadh-01'
                """);
            while(rs.next()){
                System.out.println(rs.getString(1));
                System.out.println(rs.getString(2));
                System.out.println(rs.getString(3));
            }
            System.out.println("YAAAAAAAAYYYY");
        } 
        catch (ClassNotFoundException ex) {
            System.out.println("ERRORRRR! Driver class not found: " + ex.getMessage());
        }
        catch (SQLException exception) {
            System.out.println("ERRORRRR SQLLL! " + exception.getMessage());
        }
        
    }}
