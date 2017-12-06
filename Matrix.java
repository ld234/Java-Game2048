/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2048;

/**
 *
 * @author ASUS
 */

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import javax.swing.undo.*;

public class Matrix extends JPanel implements Runnable, KeyListener{
    
    private Tile[][] matrix;
    private int size;
    private boolean moved;
    private boolean init;
    public Stack<int[][]> st;
    public static final int WIDTH = 700;
    public static final int HEIGHT = 700;
    
    public Matrix(int n)
    {
        st = new Stack<int[][]>();
        System.out.println("Intialized");
        moved = false;
        size = n;
        matrix = new Tile[n][n];
        this.setFocusable(true);
        this.setPreferredSize(new Dimension (WIDTH, HEIGHT));
        this.addKeyListener(this);
        this.setLayout(new GridLayout(n,n,3,3));
        for (int i = 0; i < n; i++){
            for(int j=0; j < n; j++){
                matrix[i][j] = new Tile();
                matrix[i][j].setEditable(false);
                this.add(matrix[i][j]);
            }
        }
        init = true;
        randomize(2);
        repaint2();
        init = false;
    }   
    
    void randomize (int noOfNewTiles){
        if (isFull () == size*size) return;
        int i = -1, j = -1, c=0;
        Random r = new Random();
        while(c<noOfNewTiles){
            int tmp1 = r.nextInt(size);
            int tmp2 = r.nextInt(size);
            if ((tmp1 != i || tmp2 != j) && matrix[tmp1][tmp2].getValue()==0){
                i = tmp1; 
                j= tmp2;
                matrix[i][j].setText("2");
                matrix[i][j].setValue(2);
       
                /*if (matrix[i][j].getValue() == 2){
                    matrix[i][j].setColor(Color.ORANGE);
                }*/
                c++;
            }
        }
    }
    
    public void keyPressed(KeyEvent e){
        Random r = new Random ();
        int number = r.nextInt(3);
        moved = false;
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            moveLeft();
        }
        else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            moveRight();  
        }
        else if (e.getKeyCode() == KeyEvent.VK_UP) {
            moveUp();
        }
        else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            moveDown();
        }
        if(size*size - isFull() < number)
            number = r.nextInt(size*size - isFull() + 1);
        if (moved) 
            randomize(number);
        repaint2();
        this.requestFocus();
        if (isLost()){
            int n = JOptionPane.showConfirmDialog(this,"You lost.", "Lost!", JOptionPane.PLAIN_MESSAGE);
            if (n == JOptionPane.OK_OPTION){
                clear();
                randomize(2);
            }
        }     
    }
    
    private void moveLeft(){
        System.out.println("left pressed");
        for (int i = 0; i < size; i++){
            int mark = 0;
            for (int j = 1; j < size; j++){
                if (matrix[i][j].getValue() != 0){
                    int k = mark;
                    System.out.println(mark);
                    while(k < j){
                        System.out.println(i + " " + k + ": " +matrix[i][k].getValue());
                        if (matrix[i][k].getValue() == matrix[i][j].getValue() && matrix[i][k].getValue() != 0){
                            merge(i,j,i,k);
                            mark = k+1;
                        }
                        else if (matrix[i][k].getValue() ==0){
                            merge (i,j,i,k);
                        }
                        else{
                            mark =k+1;
                        }
                        k++;
                    }
                }
            }
        }
        print();
    }
    
    private void moveRight(){
    System.out.println("right pressed");
        for (int i = 0; i < size; i++){
            int mark = size - 1;
            for (int j = size - 2; j >= 0; j--){
                if (matrix[i][j].getValue() != 0){
                    System.out.println (i + " "+ j);
                    int k = mark;
                    while(k > j){
                        System.out.println(i + " " + k + ": " +matrix[i][k].getValue());
                        if (matrix[i][k].getValue() == matrix[i][j].getValue() && matrix[i][k].getValue() != 0){
                            merge(i,j,i,k);
                            mark = k - 1;
                        }
                        else if (matrix[i][k].getValue() == 0){
                            merge (i,j,i,k);
                        }
                        else {
                            mark = k - 1;
                        }
                        k--;
                    }
                }

            }
        }
        print();
    }
    
    private void moveUp(){
        System.out.println("up pressed");
        for (int i = 0; i < size; i++){
            int mark = 0; 
            for (int j = 1; j < size; j++){
                if (matrix[j][i].getValue() != 0){
                    int k = mark;
                    while(k < j){
                        System.out.println(k + " " + i + ": " +matrix[k][i].getValue());
                        if (matrix[k][i].getValue() == matrix[j][i].getValue() && matrix[k][i].getValue() != 0){
                            merge(j,i,k,i);
                            mark  = k+1;
                        }
                        else if (matrix[k][i].getValue() == 0 && matrix[j][i].getValue() != 0){
                            merge (j,i,k,i);
                        }
                        else {
                            mark = k +1;
                        }
                        k++;
                    }
                }
            }
        }
        print();
    }
    
    private void moveDown(){
        System.out.println("down pressed");
        for (int i = 0; i < size; i++){
            int mark = size -1;
            for (int j = size-2; j >= 0; j--){
                if (matrix[j][i].getValue() != 0){
                    int k = mark;
                    while(k > j){
                        System.out.println(k + " " + i + ": " +matrix[k][i].getValue());
                        if (matrix[k][i].getValue() == matrix[j][i].getValue() && matrix[k][i].getValue() != 0){
                            System.out.println("1a");
                            merge(j,i,k,i);
                            mark = k-1;
                        }
                        else if (matrix[k][i].getValue() == 0){
                            System.out.println("2a");
                            merge (j,i,k,i);
                        }
                        else {
                            System.out.println("3a");
                            mark = k -1;
                        }
                        k--;
                    }
                }
            }
        } 
        print();
    }
    
    int isFull(){
        int cnt = 0;
        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j ++){
                if (matrix[i][j].getValue() != 0){
                    cnt ++;
                }
            }
        }
        return cnt;
    }
    
    private void merge(int row1, int col1, int row2, int col2){
        matrix[row2][col2].setValue(matrix[row1][col1].getValue() + matrix[row2][col2].getValue());
        matrix[row1][col1].setValue(0);
        moved = true;
    }
    
    public void keyReleased(KeyEvent e){
        print();
    }
    
    
    public void repaint2(){
        int [][] mt = new int[size][size];
        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j ++){
                matrix[i][j].setText("");
                int val = matrix[i][j].getValue();
                
                if(val != 0) {
                    matrix[i][j].setText(val + "");
                    int x = 2;
                    int y = 255;
                    while (true){
                        if (val == x){
                            matrix[i][j].setColor(new Color (255,y,102));
                            break;
                        }
                        else {
                            x+=x;
                            y-=10;
                        }
                    }
                }
                else{
                    matrix[i][j].setColor(new Color(203, 200, 189));
                }
                mt[i][j] = matrix[i][j].getValue();
            }
        }
        
        if (st != null && (moved || init)){
            System.out.println ("stack is not null");
            st.push(mt);
        }
        //printStack();
    }
    
    private void repaint3(){
        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j ++){
                matrix[i][j].setText("");
                int val = matrix[i][j].getValue();
                
                if(val != 0) {
                    matrix[i][j].setText(val + "");
                    int x = 2;
                    int y = 255;
                    while (true){
                        if (val == x){
                            matrix[i][j].setColor(new Color (255,y,102));
                            break;
                        }
                        else {
                            x+=x;
                            y-=10;
                        }
                    }
                }
                else{
                    matrix[i][j].setColor(new Color(203, 200, 189));
                }
            }
        }
    }
    
    private void printStack(){
        System.out.println("Print stack");
        int z = 0;
        for (int[][] mt : st){
            System.out.println(z++);
            for (int i = 0; i < size; i++){
                for (int j = 0; j < size; j++){
                    System.out.print(mt[i][j] + " ");
                }
                System.out.println();
            }
        }
    }
    
    private boolean isLost(){
        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){
                if (matrix[i][j].getValue() == 0)
                    return false;
                if (j> 0){
                    int left = j-1;
                    if (matrix[i][j].getValue() == matrix[i][left].getValue())
                        return false;
                }
                if (i > 0 ){
                    int up = i - 1;
                    if (matrix[i][j].getValue() == matrix[up][j].getValue())
                        return false;
                }
                if (j < size-1){
                    int right = j+1;
                    if (matrix[i][j].getValue() == matrix[i][right].getValue())
                        return false;
                }
                if(i < size-1){
                    int down = i+1;
                    if (matrix[i][j].getValue() == matrix[down][j].getValue())
                        return false;
                }                
            }
        }
        System.out.println("LOST");
        return true;
    }
    
   public void clear(){
        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){
                matrix[i][j].setValue(0);
            }
        }
        st.clear();
    }
   
    void undo(){
        System.out.println("Undo pressed" + st.size());
        
        st.pop();
        int[][] m = st.peek();
        for (int i= 0; i < size; i++){
            for (int j = 0; j < size; j++ ){
                System.out.print (m[i][j] + " ");
                matrix[i][j].setValue(m[i][j]);
            }
            System.out.println();
        }
        repaint3();
        this.requestFocus();
    }
   
    void print (){
        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j ++){
                System.out.print(matrix[i][j].getValue() + " ");
            }
            System.out.println();
        }
    }
    
    public void keyTyped(KeyEvent e){
        if (e.getKeyChar() == 'r'){
            reset();
        }
    }
    
    private void reset(){
        clear();
        randomize(2);
        repaint2();
    }
    
    public void run(){
        
    }
    
    public boolean undoable(){
        return st.size() > 1;
    }
}

