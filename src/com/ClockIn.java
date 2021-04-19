package com;

import javax.swing.*;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.border.Border;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class ClockIn implements ActionListener{
    private static JButton HomeButton;
    private static JFrame Frame2;
    private static JButton RegInButton;
    private static JLabel GetInLabel;
    private static JButton RegOutButton;
    private static JLabel GetOutLabel;
    private static JButton Submit1;
    private static JButton Submit2;
    private static JComboBox UserID;
    static String[] IDs = {"","1","2","3","4"};
    private static JLabel UserIDCapture;
    private static JLabel NameCapture;
    SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss");
    Date date = new Date(System.currentTimeMillis());

    Connection conn;
    PreparedStatement pst;

    public static void Clock() {
        JPanel Panel2 = new JPanel();
        Frame2 = new JFrame();
        Frame2.setSize(700,400);
        Frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Frame2.setTitle("Clock In");
        Frame2.setResizable(false);
        Frame2.add(Panel2);
        Frame2.setLocationRelativeTo(null);
        Panel2.setLayout(null);
        Font font = new Font("Comic Sans MS", Font.BOLD, 18);
        Font font1 = new Font("Comic Sans MS", Font.BOLD, 13);
        JLabel Name1 = new JLabel("Staff Clock-In Management System");
        Name1.setBounds(50,5,600,100);
        Name1.setSize(500,90);
        Name1.setFont(font);
        Panel2.add(Name1);
        UserIDCapture = new JLabel("");
        UserIDCapture.setBounds(10,100,200,40);
        Panel2.add(UserIDCapture);
        UserIDCapture.setVisible(false);
        JLabel Name3 = new JLabel("Select User ID");
        Name3.setBounds(10,100,200,40);
        Name3.setFont(font1);
        Panel2.add(Name3);
        UserID = new JComboBox(IDs);
        UserID.setBounds(230,100,50,40);
        UserID.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {

            }

            @Override
            public void focusLost(FocusEvent e) {

                UserIDCapture.setText(String.valueOf(UserID.getSelectedItem()));
                switch (UserIDCapture.getText()) {
                    case "1" -> NameCapture.setText("George");
                    case "2" -> NameCapture.setText("Joseph");
                    case "3" -> NameCapture.setText("Nancy");
                    case "4" -> NameCapture.setText("Peter");
                    default -> NameCapture.setText("");
                }
            }
        });
        Panel2.add(UserID);
        NameCapture = new JLabel("");
        NameCapture.setBounds(290,100,250,40);
        Border bluelines = BorderFactory.createLineBorder(Color.BLUE);
        NameCapture.setBorder(bluelines);
        NameCapture.setFont(font1);
        Panel2.add(NameCapture);
        RegInButton = new JButton("Clock-In Time");
        RegInButton.setBounds(10,150,200,40);
        RegInButton.addActionListener(new ClockIn());
        RegInButton.setFont(font1);
        Panel2.add(RegInButton);
        String space = "   ";
        GetInLabel = new JLabel(space+"");
        GetInLabel.setBounds(230,150,300,40);
        Border blueline = BorderFactory.createLineBorder(Color.BLUE);
        GetInLabel.setBorder(blueline);
        Panel2.add(GetInLabel);
        Submit1 = new JButton("Submit");
        Submit1.setBounds(550,150,100,40);
        Submit1.addActionListener(new ClockIn());
        Submit1.setFont(font1);
        Panel2.add(Submit1);
        RegOutButton = new JButton("Clock-Out Time");
        RegOutButton.setBounds(10,200,200,40);
        RegOutButton.addActionListener(new ClockIn());
        RegOutButton.setFont(font1);
        Panel2.add(RegOutButton);
        GetOutLabel = new JLabel(space+"");
        GetOutLabel.setBounds(230,200,300,40);
        Border Redline = BorderFactory.createLineBorder(Color.RED);
        GetOutLabel.setBorder(Redline);
        Panel2.add(GetOutLabel);
        Submit2 = new JButton("Submit");
        Submit2.setBounds(550,200,100,40);
        Submit2.addActionListener(new ClockIn());
        Submit2.setFont(font1);
        Panel2.add(Submit2);
        HomeButton = new JButton("Home");
        HomeButton.setBounds(10,320,100,30);
        HomeButton.addActionListener(new ClockIn());
        HomeButton.setFont(font1);
        Panel2.add(HomeButton);
        Frame2.setVisible(true);


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==HomeButton){
            try {
                Class.forName(Connect.Driver_class);
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hrmis", "George", "M@t@2021");
                Statement st = conn.createStatement();
                String selectSQL="SELECT *FROM `control` WHERE `UserID`= 1";
                ResultSet res = st.executeQuery(selectSQL);
                while (res.next()){
                    String CheckID = res.getString("Reget");
                    if (CheckID.equals("1")){
                        Main.Home();
                        Frame2.setVisible(false);
                    }else {UserMain.Home(); Frame2.setVisible(false);}
                }
            }catch (ClassNotFoundException | SQLException d) {
                d.printStackTrace();
            }
        }
        if (e.getSource()==RegInButton){
            try {
                Class.forName(Connect.Driver_class);
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hrmis", "George", "M@t@2021");
                Statement st = conn.createStatement();
                String selectSQL="SELECT *FROM `control` WHERE `UserID`= 1";
                ResultSet res = st.executeQuery(selectSQL);
                while (res.next()){
                    String Username = res.getString("Reget");

                    if (!UserIDCapture.getText().equals(Username)){
                        JOptionPane.showMessageDialog(null, "You cannot sign-in for someone else", "Error", JOptionPane.ERROR_MESSAGE);
                    }else {
                        String space = "    ";
                        GetInLabel.setText(space+formatter.format(date));
                    }
                }
            }catch (ClassNotFoundException | SQLException d) {
                d.printStackTrace();
            }
        }
        if (e.getSource()==RegOutButton){
            try {
                Class.forName(Connect.Driver_class);
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hrmis", "George", "M@t@2021");
                Statement st = conn.createStatement();
                String selectSQL="SELECT *FROM `control` WHERE `UserID`= 1";
                ResultSet res = st.executeQuery(selectSQL);
                while (res.next()){
                    String Username = res.getString("Reget");

                    if (!UserIDCapture.getText().equals(Username)){
                        JOptionPane.showMessageDialog(null, "You cannot sign-out for someone else", "Error", JOptionPane.ERROR_MESSAGE);
                    }else {
                        String space = "    ";
                        GetOutLabel.setText(space+formatter.format(date));
                    }
                }
            }catch (ClassNotFoundException | SQLException d) {
                d.printStackTrace();
            }
        }
        if (e.getSource()==Submit1) {
            try {
                String User = NameCapture.getText();
                String Details = GetInLabel.getText();
                String Type = "Time In";
                String ID = UserIDCapture.getText();

                Class.forName(Connect.Driver_class);
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hrmis", "George", "M@t@2021");
                pst = conn.prepareStatement("Insert into clocker(Username,UserID,Details,Type)values(?,?,?,?)");
                pst.setString(1,User);
                pst.setString(2,ID);
                pst.setString(3,Details);
                pst.setString(4,Type);
                pst.executeUpdate();
                JOptionPane.showMessageDialog(null,"Sign-In Details Updated.....");
                GetInLabel.setText("");
                UserID.setSelectedIndex(0);
                NameCapture.setText("");
            } catch (ClassNotFoundException e1) {
                Logger.getLogger(ClockIn.class.getName()).log(Level.SEVERE, "Class problem", e);
            } catch (SQLException e1) {
                Logger.getLogger(ClockIn.class.getName()).log(Level.SEVERE,"SQL Problem",e);
            }
        }
        if (e.getSource()==Submit2) {
            try {
                String User = NameCapture.getText();
                String Details = GetOutLabel.getText();
                String Type = "Time Out";
                String ID2 = UserIDCapture.getText();

                Class.forName(Connect.Driver_class);
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hrmis", "George", "M@t@2021");
                pst = conn.prepareStatement("Insert into clocker(Username,UserID,Details,Type)values(?,?,?,?)");
                pst.setString(1,User);
                pst.setString(2,ID2);
                pst.setString(3,Details);
                pst.setString(4,Type);
                pst.executeUpdate();
                JOptionPane.showMessageDialog(null,"Sign-out Details Updated.....");
                GetOutLabel.setText("");
                UserID.setSelectedIndex(0);
                NameCapture.setText("");
            } catch (ClassNotFoundException e1) {
                Logger.getLogger(ClockIn.class.getName()).log(Level.SEVERE, "Class problem", e);
            } catch (SQLException e1) {
                Logger.getLogger(ClockIn.class.getName()).log(Level.SEVERE,"SQL Problem",e);
            }
        }
    }
}
//url=jdbc:mysql://127.0.0.1:3306/hrmis
//driver=com.mysql.cj.jdbc.Driver

//jdbc:mysql://localhost:3306/hrmis
//com.mysql.cj.jdbc.Driver
//George@localhost