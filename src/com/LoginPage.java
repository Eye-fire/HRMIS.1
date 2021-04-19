package com;

import javax.swing.*;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JDialog;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginPage implements ActionListener {
    private javax.swing.JPanel JPanel;
    private static JTextField Username;
    private static JPasswordField Password;
    private static JButton Logins;
    private static JButton Create;
    private static JLabel success;
    private static JTextField UserIDs;
    private static int Count = 0;
    private static JFrame frame;
    private static Connection conn;
    PreparedStatement pst;

    public static void main(String[] args) {
        JPanel JPanel = new JPanel();
        frame = new JFrame();
        frame.setSize(400,300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Login");
        frame.setResizable(false);
        frame.add(JPanel);
        frame.setLocationRelativeTo(null);
        JPanel.setLayout(null);
        Font font1 = new Font("Comic Sans MS", Font.BOLD, 13);
        JLabel Name = new JLabel("Username");
        Name.setBounds(10,20,80,25);
        Name.setFont(font1);
        JPanel.add(Name);
        Username = new JTextField();
        Username.setBounds(100,20,165,25);
        JPanel.add(Username);
        JLabel Pass = new JLabel("Password");
        Pass.setBounds(10,70,80,25);
        Pass.setFont(font1);
        JPanel.add(Pass);
        Password = new JPasswordField();
        Password.setBounds(100,70,165,25);
        JPanel.add(Password);
        JLabel Gets = new JLabel("User ID");
        Gets.setBounds(10,120,80,25);
        Gets.setFont(font1);
        JPanel.add(Gets);
        UserIDs = new JTextField();
        UserIDs.setBounds(100,120,50,20);
        JPanel.add(UserIDs);
        Logins = new JButton("Login");
        Logins.setBounds(100,200,80,25);
        Logins.addActionListener(new LoginPage());
        Logins.setFont(font1);
        JPanel.add(Logins);
        Create = new JButton("Create User");
        Create.setBounds(200,200,110,25);
        Create.addActionListener(new LoginPage());
        Create.setFont(font1);
        JPanel.add(Create);
        success = new JLabel("");
        success.setBounds(200,120,120,30);
        JPanel.add(success);
        frame.setVisible(true);


        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        /**UIManager.LookAndFeelInfo[] looks = UIManager.getInstalledLookAndFeels();
        for (UIManager.LookAndFeelInfo look : looks) {
            System.out.println(look.getClassName());
        }**/
        //com.sun.java.swing.plaf.motif.MotifLookAndFeel
        //UIManager.getSystemLookAndFeelClassName()


    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource()==Logins){
            try {
                String set = UserIDs.getText();
                Class.forName(Connect.Driver_class);
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hrmis", "George", "M@t@2021");
                pst = conn.prepareStatement("UPDATE `control` SET `Reget`= ? WHERE `UserID` = 1");
                pst.setString(1, set);
                pst.executeUpdate();

            } catch (ClassNotFoundException e1) {
                Logger.getLogger(Leave.class.getName()).log(Level.SEVERE, "Class problem", e);
            } catch (SQLException e1) {
                Logger.getLogger(Leave.class.getName()).log(Level.SEVERE, "SQL Problem", e);
            }
            try {
                if (UserIDs.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Key in the User ID", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    String UID = UserIDs.getText();
                    Class.forName(Connect.Driver_class);
                    conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hrmis", "George", "M@t@2021");
                    Statement st = conn.createStatement();
                    String selectSQL = "SELECT * FROM  user WHERE UserID = ";
                    ResultSet res = st.executeQuery(selectSQL + UID);

                    while (res.next()) {
                        String User = res.getString("Username");
                        String Pass = res.getString("Password");

                        String Name = Username.getText();
                        String Code = Password.getText();

                        if (User.equals("") || Pass.equals("")) {
                            JOptionPane.showMessageDialog(null, "No user found", "Error", JOptionPane.ERROR_MESSAGE);
                        }

                        if (Name.equals(User) && Code.equals(Pass)) {
                            JOptionPane.showMessageDialog(null, "Welcome to HRMIS "+Name);
                            if (UserIDs.getText().equals("1")){
                                Main.Home();
                                frame.setVisible(false);
                            }else {
                                UserMain.Home();
                                frame.setVisible(false);
                            }

                        } else {
                            int Max = 3;
                            Count += 1;
                            JOptionPane.showMessageDialog(null, "Wrong password " + (Max - Count) + " trials remaining");
                            if (Count == 3) {
                                JOptionPane.showMessageDialog(null, "System Blocked...contact admin", "Error", JOptionPane.ERROR_MESSAGE);
                                System.exit(0);
                            }
                        }
                    }
                }
                }catch(ClassNotFoundException | SQLException d){
                    d.printStackTrace();
                }

        }
        if (e.getSource()==Create){
            SetUser.main();
            frame.setVisible(false);
        }

    }
}
