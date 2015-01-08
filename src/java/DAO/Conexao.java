package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 *
 * @author Rovena Lima
 */
public class Conexao {
 
    public static Connection getConnection() throws ClassNotFoundException {
        
        Connection con = null;

      Class.forName("org.postgresql.Driver");
      String url = "jdbc:postgresql://localhost:5432/acervobd?charSet=utf8";
      String user = "postgres";
      String password = "25deabrilde1996";
      try {
        con = DriverManager.getConnection(url, user, password);
         System.out.println("Connection completed.");
      } catch (SQLException ex) {
         System.out.println(ex.getMessage());
      }
      finally{
      }
      return con;
    }
    
           
 
    public static void close(Connection con) {
        try {
            con.close();
        } catch (Exception ex) {
        }
    }
}


