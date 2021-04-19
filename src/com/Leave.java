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
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Leave implements ActionListener{
    private static JButton HomeButton;
    private static JFrame Frame2;
    private static JTextField StartDate;
    private static JTextField EndDate;
    private static JComboBox LeaveType;
    private static JComboBox UserID;
    static String[] Leaves = {"","Annual Leave","Maternity Leave","Paternity Leave","Study Leave","Sick Leave"};
    static String[] IDs = {"","1","2","3","4"};
    private static JTextField Days;
    private static JButton Apply;
    private static JLabel DateCapture;
    private static JLabel LeaveCapture;
    private static JLabel UserIDCapture;
    private static JLabel User;
    private static JLabel LeaveBalance;
    private static JButton NewApp;
    static Border blueline = BorderFactory.createLineBorder(Color.BLUE);
    static Border Redline = BorderFactory.createLineBorder(Color.RED);
    static SimpleDateFormat formatter= new SimpleDateFormat("dd/MM/yyyy");
    static Date date = new Date(System.currentTimeMillis());
    static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    Connection conn;
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
        Font font1 = new Font("Comic Sans MS", Font.BOLD, 13);
        JLabel Name1 = new JLabel("Staff Leave Management System");
        Name1.setBounds(5,2,600,100);
        Name1.setSize(500,30);
        Name1.setFont(font1);
        Panel2.add(Name1);
        JLabel Name9 = new JLabel("Select User ID");
        Name9.setBounds(230,20,100,25);
        Name9.setFont(font1);
        Panel2.add(Name9);
        UserID = new JComboBox(IDs);
        UserID.setBounds(350,20,50,25);
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
        JLabel Name6 = new JLabel("Username");
        Name6.setBounds(10,50,100,30);
        Name6.setFont(font1);
        Panel2.add(Name6);
        User = new JLabel("");
        User.setBounds(120,50,100,30);
        User.setBorder(blueline);
        Panel2.add(User);
        JLabel Name7 = new JLabel("Leave Balance");
        Name7.setBounds(230,50,100,30);
        Name7.setFont(font1);
        Panel2.add(Name7);
        LeaveBalance = new JLabel("");
        LeaveBalance.setBounds(350,50,100,30);
        LeaveBalance.setBorder(blueline);
        Panel2.add(LeaveBalance);
        NewApp = new JButton("New Application");
        NewApp.setBounds(250,320,150,30);
        NewApp.addActionListener(new Leave());
        NewApp.setFont(font1);
        Panel2.add(NewApp);
        JLabel Name2 = new JLabel("Starting Date");
        Name2.setBounds(10,100,100,30);
        Name2.setFont(font1);
        Panel2.add(Name2);
        StartDate = new JTextField();
        StartDate.setBounds(120,100,100,30);
        StartDate.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                StartDate.setText(formatter.format(date));
            }

            @Override
            public void focusLost(FocusEvent e) {
                EndDate.setText(formatter.format(date));
            }
        });
        StartDate.setEditable(false);
        Panel2.add(StartDate);
        JLabel Name3 = new JLabel("End Date");
        Name3.setBounds(240,100,100,30);
        Name3.setFont(font1);
        Panel2.add(Name3);
        EndDate = new JTextField();
        EndDate.setBounds(350,100,100,30);
        EndDate.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {

            }

            @Override
            public void focusLost(FocusEvent e) {
                DateCapture.setText(formatter.format(date));
                LocalDate Start = LocalDate.parse(StartDate.getText(),dateTimeFormatter);
                LocalDate End = LocalDate.parse(EndDate.getText(),dateTimeFormatter);
                int numDays = Period.between(Start, End).getDays();
                Days.setText(String.valueOf(numDays));
            }
        });
        EndDate.setEditable(false);
        Panel2.add(EndDate);
        JLabel Name4 = new JLabel("Select Leave Type");
        Name4.setBounds(10,150,150,30);
        Name4.setFont(font1);
        Panel2.add(Name4);
        LeaveType = new JComboBox(Leaves);
        LeaveType.setBounds(170,150,200,30);
        LeaveType.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {

            }

            @Override
            public void focusLost(FocusEvent e) {
                LeaveCapture.setText(String.valueOf(LeaveType.getSelectedItem()));
            }
        });
        Panel2.add(LeaveType);
        JLabel Name5 = new JLabel("Number of Days");
        Name5.setBounds(10,200,150,30);
        Name5.setFont(font1);
        Panel2.add(Name5);
        Days = new JTextField();
        Days.setBounds(170,200,50,30);
        Days.setEditable(false);
        Panel2.add(Days);
        DateCapture = new JLabel("");
        DateCapture.setBounds(230,200,100,30);
        Panel2.add(DateCapture);
        DateCapture.setVisible(false);
        LeaveCapture = new JLabel("");
        LeaveCapture.setBounds(340,200,100,30);
        Panel2.add(LeaveCapture);
        LeaveCapture.setVisible(false);
        UserIDCapture = new JLabel("");
        UserIDCapture.setBounds(340,200,100,30);
        Panel2.add(UserIDCapture);
        UserIDCapture.setVisible(false);
        Apply = new JButton("Apply");
        Apply.setBounds(130,320,100,30);
        Apply.addActionListener(new Leave());
        Apply.setFont(font1);
        Panel2.add(Apply);
        HomeButton = new JButton("Home");
        HomeButton.setBounds(10,320,100,30);
        HomeButton.addActionListener(new Leave());
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
        if (e.getSource()==Apply) {
            int UID = Integer.parseInt(UserIDCapture.getText());
            int Applied = Integer.parseInt(Days.getText());
            int balance = Integer.parseInt(LeaveBalance.getText());
            if (Applied>balance){JOptionPane.showMessageDialog(null,"Applied days is more than Leave balance","error",JOptionPane.ERROR_MESSAGE);}
            else {
                try {
                    String FromDate = StartDate.getText();
                    String ToDate = EndDate.getText();
                    int DaysApplied = Integer.parseInt(Days.getText());
                    String ApplicationDate = DateCapture.getText();
                    String Comments = LeaveCapture.getText();
                    int Lbalance = Integer.parseInt(LeaveBalance.getText());

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
                    JOptionPane.showMessageDialog(null, "Leave applied successfully.....");
                } catch (ClassNotFoundException e1) {
                    Logger.getLogger(Leave.class.getName()).log(Level.SEVERE, "Class problem", e);
                } catch (SQLException e1) {
                    Logger.getLogger(Leave.class.getName()).log(Level.SEVERE, "SQL Problem", e);
                }

                //************************************For History update*******************************
                try {
                    Random objGenerator = new Random();
                    for (int iCount = 100; iCount< 101; iCount++) {
                        int randomNumber = objGenerator.nextInt(500);

                        String FromDate = StartDate.getText();
                        String ToDate = EndDate.getText();
                        int DaysApplied = Integer.parseInt(Days.getText());
                        String Comments = LeaveCapture.getText();
                        int Lbalance = Integer.parseInt(LeaveBalance.getText());
                        String UserName = User.getText();

                        Class.forName(Connect.Driver_class);
                        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hrmis", "George", "M@t@2021");
                        pst = conn.prepareStatement("Insert into archive(LeaveNo,UserID,Username,LeaveType,DaysApplied,FromDate,ToDate,Lbalance)values(?,?,?,?,?,?,?,?)");
                        pst.setInt(1, randomNumber);
                        pst.setInt(2, UID);
                        pst.setString(3, UserName);
                        pst.setString(4, Comments);
                        pst.setInt(5, DaysApplied);
                        pst.setString(6, FromDate);
                        pst.setString(7, ToDate);
                        pst.setInt(8,Lbalance);
                        pst.executeUpdate();
                    }
                } catch (ClassNotFoundException e1) {
                    Logger.getLogger(Leave.class.getName()).log(Level.SEVERE, "Class problem", e);
                } catch (SQLException e1) {
                    Logger.getLogger(Leave.class.getName()).log(Level.SEVERE, "SQL Problem", e);
                }

                //******************************For Alerts update***************************************************
                try {
                    Random objGenerator = new Random();
                    for (int iCount = 0; iCount< 1; iCount++) {
                        int randomNumber = objGenerator.nextInt(100);

                        String UserName = User.getText();

                        Class.forName(Connect.Driver_class);
                        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hrmis", "George", "M@t@2021");
                        pst = conn.prepareStatement("Insert into alert(AlertNo,UserID,Username)values(?,?,?)");
                        pst.setInt(1, randomNumber);
                        pst.setInt(2, UID);
                        pst.setString(3, UserName);
                        pst.executeUpdate();
                        StartDate.setText("");
                        EndDate.setText("");
                        Days.setText("");
                        LeaveBalance.setText("");
                        User.setText("");
                        UserID.setSelectedIndex(0);
                    }
                } catch (ClassNotFoundException e1) {
                    Logger.getLogger(Leave.class.getName()).log(Level.SEVERE, "Class problem", e);
                } catch (SQLException e1) {
                    Logger.getLogger(Leave.class.getName()).log(Level.SEVERE, "SQL Problem", e);
                }
            }
        }
        if (e.getSource()==NewApp){
            int UID = Integer.parseInt(UserIDCapture.getText());
            try {
                Class.forName(Connect.Driver_class);
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hrmis", "George", "M@t@2021");
                Statement st = conn.createStatement();
                String selectSQL="SELECT *FROM `rec` WHERE `UserID`= ";
                ResultSet res = st.executeQuery(selectSQL+UID);
                while (res.next()){
                    String Username = res.getString("Username");
                    Integer Balance = res.getInt("Lbalance");
                    //String Type = res.getString("Type");

                    User.setText(Username);
                    LeaveBalance.setText(String.valueOf(Balance));
                    EndDate.setEditable(true);
                    StartDate.setEditable(true);


                }
            }catch (ClassNotFoundException | SQLException d) {
                d.printStackTrace();
            }
            try {
                Class.forName(Connect.Driver_class);
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hrmis", "George", "M@t@2021");
                Statement st = conn.createStatement();
                String selectSQL="SELECT *FROM `control` WHERE `UserID`= 1";
                ResultSet res = st.executeQuery(selectSQL);
                while (res.next()){
                    String Username = res.getString("Reget");

                    if (!UserIDCapture.getText().equals(Username)){
                        JOptionPane.showMessageDialog(null, "Wrong User ID", "Error", JOptionPane.ERROR_MESSAGE);
                        User.setText("");
                        LeaveBalance.setText("");
                    }
                }
            }catch (ClassNotFoundException | SQLException d) {
                d.printStackTrace();
            }
        }
    }
}
