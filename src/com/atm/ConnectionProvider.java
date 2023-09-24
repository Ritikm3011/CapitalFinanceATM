//provide a connection to the MySQL database used by the ATM application
package com.atm;
import java.sql.*;


/**
 *
 * @author Ritik Mondal
 */
public class ConnectionProvider {
    private static Connection con;
    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/atm";
            String user = "root";
            String password = "Asdfghjkl12.";
            con = DriverManager.getConnection(url,user,password);
            
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("problem in ConnecionProvider");
        }
        return con;
    }
    
    
}
