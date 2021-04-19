package com;

import javax.swing.*;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.*;

public class UserMain implements ActionListener{
    private static JButton LeaveButton;
    private static JButton Report;
    private static JFrame Frame2;
    private static JButton ViewClock;
    private static JButton Approve;
    private static JButton Exit;
    private static JButton LeaveViewer;
    private static JTextArea LCD;
    private static JLabel UID;
    static Connection conn;
    static PreparedStatement pst;

    public static void Home() {
        JPanel Panel2 = new JPanel();
        Frame2 = new JFrame();
        Frame2.setSize(900,500);
        Frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Frame2.setTitle("Main");
        Frame2.setResizable(false);
        Frame2.add(Panel2);
        Frame2.setLocationRelativeTo(null);
        Panel2.setLayout(null);
        UID = new JLabel("");
        JLabel Name1 = new JLabel("Welcome to Human Resource Management Software");
        Font font = new Font("Comic Sans MS", Font.BOLD, 18);
        Name1.setFont(font);
        Name1.setBounds(10,1,1500,200);
        Name1.setSize(500,90);
        Panel2.add(Name1);
        JLabel Name = new JLabel("Select an action to perform");
        Name.setBounds(100,30,600,100);
        Panel2.add(Name);
        Font font1 = new Font("Comic Sans MS", Font.BOLD, 13);
        JLabel Alert = new JLabel("Current Alerts");
        Alert.setBounds(50,100,600,100);
        Alert.setFont(font1);
        Panel2.add(Alert);
        LCD = new JTextArea("");
        LCD.setBounds(50,160,600,50);
        LCD.setEditable(false);
        LCD.setBackground(Color.CYAN);
        LCD.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                try {
                    Class.forName(Connect.Driver_class);
                    conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hrmis", "George", "M@t@2021");
                    Statement st = conn.createStatement();
                    String selectSQL="SELECT *FROM `control` WHERE `UserID`= 1";
                    ResultSet res = st.executeQuery(selectSQL);
                    while (res.next()){
                        String Username = res.getString("Reget");
                        UID.setText(Username);
                    }
                }catch (ClassNotFoundException | SQLException d) {
                    d.printStackTrace();
                }
                try {
                    String UIDs = UID.getText();
                    Class.forName(Connect.Driver_class);
                    conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hrmis", "George", "M@t@2021");
                    Statement st = conn.createStatement();
                    String selectSQL="SELECT *FROM `rec` WHERE `UserID`= ";
                    ResultSet res = st.executeQuery(selectSQL+UIDs);
                    while (res.next()){
                        String Data = res.getString("Comments");

                        if (Data.equals("Approved Leave")){
                            LCD.setText("Your leave application has been approved");
                        }else if (Data.equals("01/01/2000")){
                            LCD.setText("You have no pending notification");
                        }else {
                            LCD.setText("Your leave application is pending approval");
                        }
                        if (Data.equals("Assigned Days")){
                            LCD.setText("You have no pending notification");
                        }

                    }
                }catch (ClassNotFoundException | SQLException d) {
                    d.printStackTrace();
                }
                Font font2 = new Font("Courier New", Font.BOLD, 14);
                LCD.setFont(font2);

            }

            @Override
            public void focusLost(FocusEvent e) {

            }
        });
        Panel2.add(LCD);
        LeaveButton = new JButton("Apply Leave");
        LeaveButton.setBounds(10,300,300,35);
        LeaveButton.addActionListener(new UserMain());
        LeaveButton.setFont(font1);
        Panel2.add(LeaveButton);
        Approve = new JButton("Approve Leave");
        Approve.setBounds(340,300,300,35);
        Approve.addActionListener(new Main());
        Approve.setFont(font1);
        Approve.disable();
        Panel2.add(Approve);
        Report = new JButton("Clock In");
        Report.setBounds(10,350,300,35);
        Report.addActionListener(new UserMain());
        Report.setFont(font1);
        Panel2.add(Report);
        ViewClock = new JButton("View Clock-In Details");
        ViewClock.setBounds(340,350,300,35);
        //ViewClock.addActionListener(new Main());
        ViewClock.setFont(font1);
        ViewClock.disable();
        Panel2.add(ViewClock);
        Exit = new JButton("Exit");
        Exit.setBounds(10,400,300,35);
        Exit.addActionListener(new UserMain());
        Exit.setFont(font1);
        Panel2.add(Exit);
        LeaveViewer = new JButton("View Leave Balances");
        LeaveViewer.setBounds(340,400,300,35);
        //LeaveViewer.addActionListener(new UserMain());
        LeaveViewer.disable();
        LeaveViewer.setFont(font1);
        Panel2.add(LeaveViewer);
        Frame2.setVisible(true);


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==Report){
            ClockIn.Clock();
            Frame2.setVisible(false);
        }
        if (e.getSource()==LeaveButton){
            Leave.main();
            Frame2.setVisible(false);
        }
        if (e.getSource()==ViewClock){
            TableList.main();
            Frame2.setVisible(false);
        }
        if (e.getSource()==Approve){
            Approvers.main();
            Frame2.setVisible(false);
        }
        if (e.getSource()==Exit){
            System.exit(0);
        }
        if (e.getSource()==LeaveViewer){
            LeaveList.main();
            Frame2.setVisible(false);
        }

    }
}
