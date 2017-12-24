/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author Gonzzz
 */
public class koneksi {
    public static final String driver = "com.mysql.jdbc.Driver";
    public static String database = "jdbc:mysql://localhost/ptkmr";
    public static final String user = "root";
    public static final String password = "";

    public static com.mysql.jdbc.Connection getKoneksi() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private Connection connection;        
    private String pesanKesalahan;
    

    public String getPesanKesalahan() {
        return pesanKesalahan;
    }

    public static String getDatabase() {
        return database;
    }

    public static void setDatabase(String database) {
        koneksi.database = database;
    }

    
   
    
    public Connection getConnection(){
        connection = null;        
        pesanKesalahan = "";
        
        try{ 
            Class.forName(driver); 
        } catch (ClassNotFoundException ex){ 
            pesanKesalahan = "JDBC Driver tidak ditemukan atau rusak\n"+ex;
        } 
        
        if (pesanKesalahan.equals("")){ 
            try { 
                connection = DriverManager.getConnection(database+"?user="+user+"&password="+password+""); 
            } catch (SQLException ex) { 
                pesanKesalahan = "Koneksi ke "+database+" gagal\n"+ex;
            }
        }
        
        return connection;
    }
    
}
