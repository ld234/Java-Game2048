/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2048;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;
import java.awt.event.*;
/**
 *
 * @author ASUS
 */
public class Main {

    public static void main(String[] args) {
        Matrix m = new Matrix(4);
        JPanel panel = new JPanel(null);
        panel.setBorder(null);
        panel.setPreferredSize(new Dimension(710, 700));
        panel.setBackground(new Color(203, 200, 189, 180));
        panel.setLayout(null);
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setLayout(new FlowLayout());
        window.getContentPane().add(panel, BorderLayout.NORTH);
        window.getContentPane().add(m,BorderLayout.SOUTH);
        window.pack();
        window.setPreferredSize(new Dimension(510,900));
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        
        Font main = new Font ("Century Gothic", Font.PLAIN, 48);
        JLabel score = new JLabel(String.format("%1$-12s", "SCORE"));
        //score.setVerticalAlignment(1);
        score.setFont(main);
        score.setOpaque(false);
        panel.add(score);
        score.setBounds(200, 100, 340, 70);
        JButton restart = new JButton("Restart");
        restart.setFont(new Font("Century Gothic", Font.PLAIN, 24));
        restart.setFocusPainted(false);
        restart.setContentAreaFilled(false);
        panel.add(restart);
        panel.setBackground(new Color(203, 200, 189));
        panel.setBorder(null);
        restart.setBounds(360, 205, 140, 60);
        restart.addMouseListener(new MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                m.clear();
                m.randomize(2);
                m.repaint2();
                restart.setFocusable(false);                
            }
        });
        JButton undo = new JButton("Undo");
        undo.setFont(new Font("Century Gothic", Font.PLAIN, 24));
        panel.add(undo);
        undo.setFocusPainted(false);
        undo.setContentAreaFilled(false);
        undo.setBounds(200, 205,140, 60);
        undo.addMouseListener(new MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (m.undoable())
                    m.undo();
            }
        });
    }
    
}
