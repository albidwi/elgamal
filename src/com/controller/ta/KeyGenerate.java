/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.controller.ta;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Random;
/**
 *
 * @author Karjono,Bhatara,Guntur
 */
public class KeyGenerate {
    private String p;
    private String g;
    private String x;
    private String direc;
    private String directory;
    private String id;
    private String[] log = new String[6];
    private Random r;

    /**
     *
     */

    public KeyGenerate() {

        getLog();   

        }

    /**
     *
     * @param directorypublik
     */
 public void setDir(String directorypublik) {
        this.directory = directorypublik; //dir untuk save publik key
    }

    /**
     *
     * @param directoryprivate
     */
    public void setDirec(String directoryprivate) {
        this.direc = directoryprivate; //set directory untuk save private key
    }
      public void setkunci(String p1,String g1,String x1) {
        this.p = p1;
        this.g = g1;
        this.x = x1;
    }
      
    /**
     *
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @throws IOException
     */
    public void createpublic() throws IOException {
        try {
            String fileNamePublik = this.id + ".PublicKey";
            File filePublic = new File(this.directory + fileNamePublik);
            if (!filePublic.exists()) {
                filePublic.createNewFile();
            }
            FileWriter fwPublic = new FileWriter(filePublic.getAbsoluteFile());
            BufferedWriter bwPublic = new BufferedWriter(fwPublic);

            //tulis kunci
            bwPublic.write(id);
            bwPublic.write("\n");
            bwPublic.write(String.valueOf(this.p));
            bwPublic.write("\n");
            bwPublic.write(String.valueOf(this.g));
             bwPublic.write("\n");
            bwPublic.write(String.valueOf(this.x));
            
            bwPublic.close();
            System.out.println("Sukses membuat kunci public!, cek di : " + this.directory + fileNamePublik);
        }catch (IOException e) {
        }
    }

    /**
     *
     */
    public void createprivite() {
        try {
            String fileNamePrivate = this.id + ".PrivateKey";
            File filePrivate = new File(this.direc + fileNamePrivate);
            if (!filePrivate.exists()) {
                filePrivate.createNewFile();
            }
            FileWriter fwPrivate = new FileWriter(filePrivate.getAbsoluteFile());
            BufferedWriter bwPrivate = new BufferedWriter(fwPrivate);

            // menulis kunci private
            bwPrivate.write(id);
            bwPrivate.write("\n");
            bwPrivate.write(String.valueOf(this.p));// ini yang akan di enkrip
            bwPrivate.write("\n");
            bwPrivate.write(String.valueOf(this.g));// ini yang akan di enkrip
            bwPrivate.write("\n");
            bwPrivate.write(String.valueOf(this.x));// ini yang akan di enkrip
            bwPrivate.close();
            System.out.println("Sukses membuat kunci private!, cek di : " + this.direc + fileNamePrivate);
        } catch (IOException e) {
        }

    }

    /**
     *
     * @throws Exception
     */
    public void createKeys() throws Exception {
        this.createpublic();
        this.createprivite();
    }

    /**
     *
     */
    public void getLog() {
        System.out.println(Arrays.toString(log));
    }

    class Array {

        public Array() {
        }
    }
    
    
}