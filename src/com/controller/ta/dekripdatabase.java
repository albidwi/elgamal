/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.controller.ta;

import Model.koneksi;
import com.algo.ta.Elgamal;
import com.algo.ta.EnkripDekrip;
import com.algo.ta.PecahChiperText;
import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
/**
 *
 * @author Karjono,Bhatara,Guntur
 */
public class dekripdatabase{
    private String filename;
    private String directory;
    private String publicFile;
    private String p;
    private String g;
    private String x;
    private String fileNameDecrypt;
    String UkuranFile= null;
    String UkuranEnkripsi = null;
    private final Model.koneksi con = new koneksi();

    /**
     *
     */
    public static final double SPACE_KB = 1024;

    /**
     *
     */
    public static final double SPACE_MB = 1024 * SPACE_KB;
    private String extension;
    private int progressCount;
    private int maxProgress;

    JProgressBar dpb;
    private long[] data4;

    /**
     *
     */
    public dekripdatabase() {
   
    }

    /**
     *
     * @param directory
     * @param filename
     * @param extens
     */
    public void setFile(String directory, String filename, String extens) {

        this.fileNameDecrypt = directory + "/" + "de." + filename + "." + extens;

    }

    /**
     *
     * @param filePrivate
     */
    public void setPrivateKey(String filePrivate) {
         BufferedReader br = null;
        try {
            int batas = 0;
            String current;
            br = new BufferedReader(new FileReader(filePrivate));//mebaca file PublicKey
           
            while ((current = br.readLine()) != null) {
                if (batas == 1) {
                    // set nilai modulo dari file kunci publik
                    this.p = current;
               
                } else if (batas == 2) {
              
                    this.g = current;
                 
                }
                  else if (batas == 3) {
                    // set nilai eksponen e dari file kunci publik
                    this.x = current;
         
                }
                batas++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {br.close();}
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        }

     public String dekrip_database(String database, String table) throws SQLException{
        con.setDatabase("jdbc:mysql://localhost/"+database);
        Connection connect = con.getConnection();
        
        ArrayList<String> Data = new ArrayList<>() ;
        String Dataget = null;
        int j = 0;
        Elgamal ed = new Elgamal();
        if(connect != null){
            Statement sta = connect.createStatement();
            String sql = "SELECT * FROM "+table+";";
            ResultSet res =sta.executeQuery(sql);
            ResultSetMetaData meta = res.getMetaData();
            int collumn = meta.getColumnCount();
            while(res.next()){
                for(int i=1;i<=collumn;i++){
                    Dataget = res.getString(i);
                   Data.add(Dataget);
                 System.out.println(Data.get(j));
                    j++;
                } 
            }
            System.out.println(Dataget);
            String[][] arrData = new String[Data.size()/collumn][collumn];
            int k = 0;
            String plain = null;
//            System.out.println(Data.size()/collumn);
            System.out.println("PLAIN TEXT");
            for(int i=0;i<Data.size()/collumn;i++){
                Connection connect2 = con.getConnection();
                Connection connect3 = con.getConnection();
                for(int a=0;a<collumn;a++){
                    arrData[i][a] = Data.get(k);
                    arrData[i][a] = ed.decrypt(arrData[i][a], p, g, x);
                    k++;
               
  
                    //plain += arrData[i][a]+",";
                }
                
                for(int a=0;a<collumn;a++){
                    String[] data1 = arrData[i][a].split(" ");  
                    String[] data2 = new String[data1.length];
                    for (int l=0; l < data1.length; l++){
                         data2[l] = data1[l];
                   
                      
                    }
                    
                    
                    for (int l=0; l < data2.length; l++){
                       String dek = data2[l];
               
               
//                       System.out.println(c);
                        if(plain==null){
                            plain = "('"+dek;
                        }else{
                            plain += dek;
                        }
                       
                   }
                    if((a+1)==collumn){
                        plain += "');";
                    }else{
                        plain += "','";
                    }
                    
                    
                   
                    
                }
//                System.out.println(plain);
                String query = "insert into "+table+" values";
                String delete = "delete from "+table+";";
                
                query += plain;
                
                System.out.println(query);
                
                Statement sta2 = connect2.createStatement();
                if(i==0){
                    Statement sta3 = connect3.createStatement();
                    int res1 = sta3.executeUpdate(delete);
                    connect3.close();
                }
                int res2 = sta2.executeUpdate(query);
                connect2.close();
                plain = null;
                query = null;
                System.out.println("");
            }
            //System.out.println(plain);
            String chiper = null;
        }
        String a;
        return a ="berhasil";
    }
}
