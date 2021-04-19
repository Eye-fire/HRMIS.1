package com;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Approvers implements ActionListener {
    private static JFrame Frame2;
    private static JLabel User;
    private static JLabel AppDays;
    private static JLabel Balance1;
    private static JLabel StartDate;
    private static JLabel EndDate;
    private static JLabel Type1;
    private static JTextField RemDays;
    private static JButton CheckApp;
    private static JButton Approve;
    private static JButton Home;
    private static JButton Assign;
    private static JComboBox UserID;
    static String[] IDs = {"","1","2","3","4"};
    private static JLabel UserIDCapture;
    static Border blueline = BorderFactory.createLineBorder(Color.BLUE);
    private static Connection conn;
    PreparedStatement pst;

    public static void main() {
        JPanel Panel2 = new JPanel();
        Frame2 = new JFrame();
        Frame2.setSize(700,400);
        Frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Frame2.setTitle("Leave");
        Frame2.setResizable(false);
        Frame2.add(Panel2);
        Frame2.setLocationRelativeTo(null);
        Panel2.setLayout(null);
        Font font = new Font("Comic Sans MS", Font.BOLD, 18);
        Font font1 = new Font("Comic Sans MS", Font.BOLD, 13);
        JLabel Name1 = new JLabel("Staff Leave Approvals");
        Name1.setBounds(10,2,600,30);
        Name1.setSize(500,30);
        Name1.setFont(font);
        Panel2.add(Name1);
        JLabel Name9 = new JLabel("Select User ID");
        Name9.setBounds(10,50,100,25);
        Name9.setFont(font1);
        Panel2.add(Name9);
        UserID = new JComboBox(IDs);
        UserID.setBounds(120,50,50,25);
        UserID.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {

            }

            @Override
            public void focusLost(FocusEvent e) {
                UserIDCapture.setText(String.valueOf(UserID.getSelectedItem()));
            }
        });
        Panel2.add(UserID);
        UserIDCapture = new JLabel("");
        UserIDCapture.setBounds(340,200,100,30);
        Panel2.add(UserIDCapture);
        UserIDCapture.setVisible(false);
        JLabel Name2 = new JLabel("Username");
        Name2.setBounds(10,100,100,20);
        Name2.setFont(font1);
        Panel2.add(Name2);
        User = new JLabel("");
        User.setBounds(111,100,100,20);
        User.setBorder(blueline);
        Panel2.add(User);
        JLabel Name3 = new JLabel("Applied Days");
        Name3.setBounds(212,100,100,20);
        Name3.setFont(font1);
        Panel2.add(Name3);
        AppDays = new JLabel("");
        AppDays.setBounds(313,100,100,20);
        AppDays.setBorder(blueline);
        Panel2.add(AppDays);
        JLabel Name4 = new JLabel("Leave Balance");
        Name4.setBounds(414,100,100,20);
        Name4.setFont(font1);
        Panel2.add(Name4);
        Balance1 = new JLabel("");
        Balance1.setBounds(515,100,100,20);
        Balance1.setBorder(blueline);
        Panel2.add(Balance1);
        JLabel Name6 = new JLabel("Start Date");
        Name6.setBounds(10,150,100,20);
        Name6.setFont(font1);
        Panel2.add(Name6);
        StartDate = new JLabel("");
        StartDate.setBounds(111,150,100,20);
        StartDate.setBorder(blueline);
        Panel2.add(StartDate);
        JLabel Name7 = new JLabel("End Date");
        Name7.setBounds(212,150,100,20);
        Name7.setFont(font1);
        Panel2.add(Name7);
        EndDate = new JLabel("");
        EndDate.setBounds(313,150,100,20);
        EndDate.setBorder(blueline);
        Panel2.add(EndDate);
        JLabel Name8 = new JLabel("Leave Type");
        Name8.setBounds(414,150,100,20);
        Name8.setFont(font1);
        Panel2.add(Name8);
        Type1 = new JLabel("");
        Type1.setBounds(515,150,100,20);
        Type1.setBorder(blueline);
        Panel2.add(Type1);
        JLabel Name5 = new JLabel("Remaining Days");
        Name5.setBounds(10,200,100,20);
        Name5.setFont(font1);
        Panel2.add(Name5);
        RemDays = new JTextField("");
        RemDays.setBounds(111,200,100,20);
        RemDays.setBorder(blueline);
        RemDays.setEditable(false);
        Panel2.add(RemDays);
        CheckApp = new JButton("Get Record");
        CheckApp.setBounds(130,300,100,30);
        CheckApp.addActionListener(new Approvers());
        CheckApp.setFont(font1);
        Panel2.add(CheckApp);
        Approve = new JButton("Approve");
        Approve.setBounds(250,300,100,30);
        Approve.addActionListener(new Approvers());
        Approve.setFont(font1);
        Panel2.add(Approve);
        Home = new JButton("Home");
        Home.setBounds(10,300,100,30);
        Home.addActionListener(new Approvers());
        Home.setFont(font1);
        Panel2.add(Home);
        Assign = new JButton("Assign Leave Days");
        Assign.setBounds(370,300,150,30);
        Assign.addActionListener(new Approvers());
        Assign.setFont(font1);
        Panel2.add(Assign);
        Frame2.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==CheckApp){
            int UID = Integer.parseInt(UserIDCapture.getText());
            try {
                Class.forName(Connect.Driver_class);
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hrmis", "George", "M@t@2021");
                Statement st = conn.createStatement();
                String selectSQL="SELECT *FROM `rec` WHERE `UserID`= ";
                ResultSet res = st.executeQuery(selectSQL+UID);

                while (res.next()){
                    String Username = res.getString("Username");
                    String FromDate = res.getString("FromDate");
                    String ToDate = res.getString("ToDate");
                    Integer DaysApplied = res.getInt("DaysApplied");
                    String AppDate = res.getString("ApplicationDate");
                    String Type = res.getString("Comments");
                    Integer Balance = res.getInt("Lbalance");

                    if (Type.equals("Approved Leave") || Type.equals("Assigned Days")){
                        JOptionPane.showMessageDialog(null,"User has not applied Leave");
                    }else {
                        User.setText(Username);
                        AppDays.setText(String.valueOf(DaysApplied));
                        Balance1.setText(String.valueOf(Balance));
                        StartDate.setText(FromDate);
                        EndDate.setText(ToDate);
                        Type1.setText(Type);
                        Integer Remainder = Balance - DaysApplied;
                        RemDays.setText(String.valueOf(Remainder));
                    }
                }
            }catch (ClassNotFoundException d) {
                d.printStackTrace();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if (e.getSource()==Approve){
            int UID = Integer.parseInt(UserIDCapture.getText());
            try {
                String Username = User.getText();
                String FromDate = StartDate.getText();
                String ToDate = EndDate.getText();
                int DaysApplied = Integer.parseInt(AppDays.getText());
                String ApplicationDate = EndDate.getText();
                String Comments = "Approved Leave";
                int Lbalance = Integer.parseInt(RemDays.getText());

                Class.forName(Connect.Driver_class);
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hrmis", "George", "M@t@2021");
                pst = conn.prepareStatement("UPDATE `rec` SET `Comments`= ?,`Lbalance`= ? WHERE `UserID` = ?");
                pst.setString(1, Comments);
                pst.setInt(2, Lbalance);
                pst.setInt(3,UID);
                pst.executeUpdate();
                JOptionPane.showMessageDialog(null, "Leave approved successfully.....");
                User.setText("");
                AppDays.setText("");
                Balance1.setText("");
                StartDate.setText("");
                EndDate.setText("");
                Type1.setText("");
                RemDays.setText("");
                UserID.setSelectedIndex(0);
            } catch (ClassNotFoundException e1) {
                Logger.getLogger(Leave.class.getName()).log(Level.SEVERE, "Class problem", e);
            } catch (SQLException e1) {
                Logger.getLogger(Leave.class.getName()).log(Level.SEVERE, "SQL Problem", e);
            }
            try {
                Class.forName(Connect.Driver_class);
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hrmis", "George", "M@t@2021");
                pst = conn.prepareStatement("DELETE FROM `alert` WHERE UserID = "+UID);
                pst.executeUpdate();

            } catch (ClassNotFoundException e1) {
                Logger.getLogger(Leave.class.getName()).log(Level.SEVERE, "Class problem", e);
            } catch (SQLException e1) {
                Logger.getLogger(Leave.class.getName()).log(Level.SEVERE, "SQL Problem", e);
            }
        }
        if (e.getSource()==Home){
            Main.Home();
            Frame2.setVisible(false);
        }
        if (e.getSource()==Assign){
            int UID = Integer.parseInt(UserIDCapture.getText());
            try {
                String Username = User.getText();
                String FromDate = "04/04/2021";
                String ToDate = "04/04/2021";
                int DaysApplied = 0;
                String ApplicationDate = "04/04/2021";
                String Comments = "Assigned Days";
                int Lbalance = 30;

                Class.forName(Connect.Driver_class);
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hrmis", "George", "M@t@2021");
                pst = conn.prepareStatement("UPDATE `rec` SET `FromDate`= ?,`ToDate`= ?,`DaysApplied`= ?,`ApplicationDate`= ?,`Comments`= ?,`Lbalance`= ? WHERE `UserID` = ?");
                pst.setString(1, FromDate);
                pst.setString(2, ToDate);
                pst.setInt(3, DaysApplied);
                pst.setString(4, ApplicationDate);
                pst.setString(5, Comments);
                pst.setInt(6, Lbalance);
                pst.setInt(7,UID);
                pst.executeUpdate();
                JOptionPane.showMessageDialog(null, "Leave Assigned successfully.....");
            } catch (ClassNotFoundException e1) {
                Logger.getLogger(Leave.class.getName()).log(Level.SEVERE, "Class problem", e);
            } catch (SQLException e1) {
                Logger.getLogger(Leave.class.getName()).log(Level.SEVERE, "SQL Problem", e);
            }
        }

    }
}
