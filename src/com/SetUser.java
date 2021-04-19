package com;

import javax.swing.*;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Reader;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class SetUser implements ActionListener {
    private static JTextField Username;
    private static JPasswordField Password;
    private static JPasswordField Password2;
    private static JButton Logins;
    private static JButton Proceed;
    private static JLabel success;
    private static JLabel PassRE;
    private static int Count = 0;
    private static JFrame frame;
    private static JTextField UserIDs;
    Connection conn;
    PreparedStatement pst;

    public static void main() {
        JPanel JPanel = new JPanel();
        frame = new JFrame();
        frame.setSize(400,300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Login");
        frame.setResizable(false);
        frame.add(JPanel);
        frame.setLocationRelativeTo(null);
        JPanel.setLayout(null);
        JLabel Name = new JLabel("Username");
        Name.setBounds(10,70,80,25);//10 20 100 20
        JPanel.add(Name);
        Username = new JTextField();
        Username.setBounds(100,70,165,25);
        JPanel.add(Username);
        JLabel Pass = new JLabel("Password");
        Pass.setBounds(10,105,80,25);//10 70 100 70
        JPanel.add(Pass);
        Password = new JPasswordField();
        Password.setBounds(100,105,165,25);
        JPanel.add(Password);
        PassRE = new JLabel("Re-type Pass");
        PassRE.setBounds(10,150,100,25);//10 105 100 105
        JPanel.add(PassRE);
        Password2 = new JPasswordField();
        Password2.setBounds(100,150,165,25);
        JPanel.add(Password2);
        JLabel US = new JLabel("User ID");
        US.setBounds(10,20,80,25);// 10 150 100 150
        JPanel.add(US);
        UserIDs = new JTextField();
        UserIDs.setBounds(100,20,50,25);
        JPanel.add(UserIDs);
        Logins = new JButton("Create User");
        Logins.setBounds(100,200,120,25);
        Logins.addActionListener(new SetUser());
        JPanel.add(Logins);
        Proceed = new JButton("Proceed");
        Proceed.setBounds(240,200,120,25);
        Proceed.addActionListener(new SetUser());
        JPanel.add(Proceed);
        success = new JLabel("");
        success.setBounds(200,120,120,30);
        JPanel.add(success);
        frame.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==Logins){
            String Pass1 = Password.getText();
            String Pass2 = Password2.getText();
            if (!Pass1.equals(Pass2)){JOptionPane.showMessageDialog(null,"Passwords do not match","error",JOptionPane.ERROR_MESSAGE);}
            else {
                try {
                    String Usernames = Username.getText();
                    String Passwords = Password2.getText();
                    String IDnumber = UserIDs.getText();


                    Class.forName(Connect.Driver_class);
                    conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hrmis", "George", "M@t@2021");
                    pst = conn.prepareStatement("Insert into user(UserID,Username,Password)values(?,?,?)");
                    pst.setString(1, IDnumber);
                    pst.setString(2, Usernames);
                    pst.setString(3, Passwords);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "User Created successfully.....");
                } catch (ClassNotFoundException e1) {
                    Logger.getLogger(Leave.class.getName()).log(Level.SEVERE, "Class problem", e);
                } catch (SQLException e1) {
                    Logger.getLogger(Leave.class.getName()).log(Level.SEVERE, "SQL Problem", e);
                }

                //*****************************Create User in REC table**************************
                try {
                    String Usernames = Username.getText();
                    String Passwords = Password2.getText();
                    String IDnumber = UserIDs.getText();
                    String Date = "01/01/2000";
                    int Days = 0;


                    Class.forName(Connect.Driver_class);
                    conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hrmis", "George", "M@t@2021");
                    pst = conn.prepareStatement("Insert into rec(UserID,Username,FromDate,ToDate,DaysApplied,ApplicationDate,Comments,Lbalance)values(?,?,?,?,?,?,?,?)");
                    pst.setString(1, IDnumber);
                    pst.setString(2, Usernames);
                    pst.setString(3, Date);
                    pst.setString(4, Date);
                    pst.setInt(5, Days);
                    pst.setString(6, Date);
                    pst.setString(7, Date);
                    pst.setInt(8, Days);
                    pst.executeUpdate();

                    Username.setText("");
                    Password.setText("");
                    Password2.setVisible(false);
                    PassRE.setVisible(false);
                    Logins.setVisible(false);
                } catch (ClassNotFoundException e1) {
                    Logger.getLogger(Leave.class.getName()).log(Level.SEVERE, "Class problem", e);
                } catch (SQLException e1) {
                    Logger.getLogger(Leave.class.getName()).log(Level.SEVERE, "SQL Problem", e);
                }
            }
        }
        if (e.getSource()==Proceed){
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
                if (Username.getText().equals("") || Password.getText().equals("") || UserIDs.getText().equals("")){
                    JOptionPane.showMessageDialog(null, "Fill all the required fields","Error",JOptionPane.ERROR_MESSAGE);
                }else {
                    String UID = UserIDs.getText();
                    Class.forName(Connect.Driver_class);
                    conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hrmis", "George", "M@t@2021");
                    Statement st = conn.createStatement();
                    String selectSQL = "SELECT * FROM  user WHERE UserID = ";
                    ResultSet res = st.executeQuery(selectSQL + UID);

                    while (res.next()) {
                        String User = res.getString("Username");
                        String Pass = res.getString("Password");
                        String ID = res.getString("UserID");

                        String Name = Username.getText();
                        String Code = Password.getText();
                        String IDnum = UserIDs.getText();

                        if (Name.equals(User) && Code.equals(Pass) && IDnum.equals(ID)) {
                            JOptionPane.showMessageDialog(null, "Login successful");
                            if (IDnum.equals("1")) {
                                Main.Home();
                                frame.setVisible(false);
                            } else {
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
            }catch (ClassNotFoundException | SQLException d) {
                d.printStackTrace();
            }

        }

    }
}
