/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2048;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;
/**
 *
 * @author ASUS
 */
public class Tile extends JTextField{
    public static final Font main = new Font ("Century Gothic", Font.PLAIN, 36);
    public static final Color ORI = new Color(255, 255, 102, 255);
    private int value;

    public Tile(){
        this.setFont(main);
        this.value = 0;
        this.setHorizontalAlignment(SwingConstants.CENTER);
        this.setOpaque(true);
        this.setForeground(Color.black);
        //Border l = BorderFactory.createRaisedBevelBorder();
        this.setBorder(null);
    }
    
    public void setColor (Color c){
        this.setBackground(c);
    }
    
    public int getValue(){
        return value;
    }
    
    public void setValue(int value){
        this.value = value;
    }

    public boolean isEmpty (){
        if (value == 0)
            return true;
        else
            return false;
    }
}
