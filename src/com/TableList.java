package com;

import javax.swing.*;
import javax.swing.JTable;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TableList implements ActionListener {
    private static JFrame Frame;
    private static JTable Tablix;
    private static JMenuItem m11;
    private static Connection conn;
    PreparedStatement pst;

    public static void main() {
        try {
            Class.forName(Connect.Driver_class);
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hrmis", "George", "M@t@2021");
            Statement st = conn.createStatement();
            String selectSQL="SELECT * FROM  clocker";
            ResultSet res = st.executeQuery(selectSQL);
            String[] columns = new String[]{"Username","Date & Time","Action Type"};
            List<Object[]> sets = new ArrayList<Object[]>();
            while (res.next()){
                String Username = res.getString("Username");
                String Details = res.getString("Details");
                String Type = res.getString("Type");
                sets.add(new String[]{Username, Details, Type});

                Tablix = new JTable(sets.toArray(new Object[sets.size()][]), columns);
            }
        }catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        Frame = new JFrame();
        Frame.setSize(700,400);
        Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Frame.setTitle("Attendance Register");
        Frame.setResizable(false);
        /**GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
         Frame.setMaximizedBounds(env.getMaximumWindowBounds());
         Frame.setExtendedState(Frame.getExtendedState() | Frame.MAXIMIZED_BOTH);**/
        Frame.setLocationRelativeTo(null);
        Font font = new Font("cambria", Font.PLAIN, 16);
        Tablix.setFont(font);
        Tablix.setRowHeight(30);
        JMenuBar mb = new JMenuBar();
        JMenu m1 = new JMenu("FILE");
        JMenu m2 = new JMenu("Help");
        mb.add(m1);
        mb.add(m2);
        m11 = new JMenuItem("Home page");
        m11.addActionListener(new TableList());
        m1.add(m11);
        Frame.getContentPane().add(BorderLayout.CENTER, new JScrollPane(Tablix));
        Frame.getContentPane().add(BorderLayout.NORTH,mb);
        Frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==m11){
            Main.Home();
            Frame.setVisible(false);
        }
    }
}
