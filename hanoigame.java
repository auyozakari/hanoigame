/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hanoigame;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


class StackPoles extends Stack<Integer> {

    public void display(int x, int y, Graphics g) {
        for(int i = 0; i < size(); i++) {
            int width = 20*((Integer)get(i)).intValue();
            g.setColor(Color.green);
            g.fillRoundRect(x-width/2,y-10*(i+1),width+1,10,10,10);
            g.setColor(Color.black);
            g.drawRoundRect(x-width/2,y-10*(i+1),width+1,10,10,10);
        }
    }
}
public class hanoigame {
    JFrame myFrame;
    JLabel myLabel;
    Container myContainer;
    JLabel fromLabel;
    JLabel toLabel;
    int count;
    int countMoves;
    JButton GameStart;
    JButton moveDisk;
    JTextField from;
    JTextField to;
    JPanel myPanel;
    StackPoles peg = new StackPoles();
    StackPoles hpoleA = new StackPoles();
    StackPoles hpoleB = new StackPoles();
    StackPoles hpoleC = new StackPoles();
    final int WIDTH = 650;
    final int HEIGHT = 500;
    final int XLEFT = 110;
    final int XMIDDLE = 320;
    final int XRIGHT = 520;
    final int YDOWN = 280;
    final int YUP = 170;
    final int WIDTHINC = 110;
    final int HEIGHTINC = 220;
    Graphics g;
    
    public hanoigame() {
        myFrame = new JFrame("Hanoi Towers Game");
        Toolkit myToolKit = Toolkit.getDefaultToolkit();
        myFrame.setSize(WIDTH,HEIGHT);
        myFrame.setLocation(myToolKit.getScreenSize().width/4,myToolKit.getScreenSize().height/4);
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myContainer = myFrame.getContentPane();
        myPanel = new JPanel();
        myLabel= new JLabel("Click TO Start The Game");
        fromLabel= new JLabel("From");
        toLabel= new JLabel(" TO");
        myPanel.add(myLabel);
        GameStart= new JButton("Start");
        GameStart.addActionListener(new inputAction());
        myPanel.add(GameStart);
        myPanel.setBackground(Color.magenta);
        moveDisk = new JButton("Move Disk");
        moveDisk.setEnabled(false);
        moveDisk.addActionListener(new moveAction());
        from = new JTextField("",4);
        to = new JTextField("",4);
        myContainer.setBackground(Color.white);
        myContainer.setLayout(new FlowLayout(FlowLayout.CENTER));
        myContainer.add(fromLabel);
        myContainer.add(from);
        myContainer.add(toLabel);
        myContainer.add(to);
        myContainer.add(moveDisk);
        myContainer.add(myPanel,0);
        myFrame.setVisible(true);
    }
    public static void main(String[] args) {
        new hanoigame();
    }
    public void drawLines(Graphics g) {
        g.drawLine(XLEFT,YDOWN,XLEFT,YUP);
        g.drawLine(XMIDDLE,YDOWN,XMIDDLE,YUP);
        g.drawLine(XRIGHT,YDOWN,XRIGHT,YUP);
        g.drawLine(10,YDOWN,HEIGHTINC,YDOWN);
        g.drawLine(10+WIDTHINC,YDOWN,2*HEIGHTINC,YDOWN);
        g.drawLine(10+2*WIDTHINC,YDOWN,3*HEIGHTINC,YDOWN);
    }
    //generate the d
    public void displayPoles(Graphics g) {
        g.setColor(Color.white);
        g.fillRect(0,75,WIDTH,HEIGHT-75);
        g.setColor(Color.black);
        drawLines(g);
        peg.display(XLEFT,YDOWN,g);
        hpoleB.display(XMIDDLE,YDOWN,g);
        hpoleC.display(XRIGHT,YDOWN,g);
        try{
            Thread.sleep(500);
        }catch(Exception e){
        JOptionPane.showMessageDialog(null, e.getMessage());
        }
    } 

//GAME INITIALIZATION button action
    class inputAction implements ActionListener {
        public void actionPerformed(ActionEvent A){
            count = 3;
            if(!moveDisk.isEnabled()){
                moveDisk.setEnabled(true);
            }
            GameStart.setText("Again");
            from.setText("");
            to.setText("");
            countMoves = 0;
            peg.setSize(0);
            hpoleA.setSize(0);
            hpoleB.setSize(0);
            hpoleC.setSize(0);
            for(int i = count; i > 0; i--) {
                peg.push(i);
            }
            hpoleA = peg;

            g = myFrame.getGraphics();
            displayPoles(g);
         }
    }
    //button when click to activate move action
    class moveAction implements ActionListener{
        public void actionPerformed(ActionEvent e){

            StackPoles gameOver = new StackPoles();
            gameOver.setSize(0);
            for(int i = count; i > 0; i--) {
                gameOver.push(i);
            }
            String act1,act2;
               act1=from.getText().toString();
               act2=to.getText().toString();
               if(act1.equals("A") && act2.equals("B")){
                    moveDisks(3,hpoleA,hpoleB);
               }else if (act1.equals("A") && act2.equals("C")){
                   moveDisks(3,hpoleA,hpoleC);
               }else if(act1.equals("B") && act2.equals("A")){
                   moveDisks(3,hpoleB,hpoleA);
               }else if(act1.equals("B") && act2.equals("C")){
                   moveDisks(3,hpoleB,hpoleC);
               }else if(act1.equals("C") && act2.equals("A")){
                   moveDisks(3,hpoleC,hpoleA);
               }else if(act1.equals("C")&& act2.equals("B")){
                   moveDisks(3,hpoleC,hpoleB);
               }
                countMoves++;
               if(hpoleB.equals(gameOver)){
                   JOptionPane.showMessageDialog(null, "Game Over with "+countMoves+" moves");
                   moveDisk.setEnabled(false);
               }

        }
    }
    //function to move the stack
    public void moveDisks(int n, StackPoles poleA, StackPoles poleB){
                poleB.push(poleA.pop());
                 if(check(poleB) == true){
                     poleA.push(poleB.pop());
                     JOptionPane.showMessageDialog(null,"you can only put small on large");
                 }
                displayPoles(g);
   }
    
   //funtion to check wether a larger stack is on top of a smaller stack
    boolean check(StackPoles checker){
        StackPoles er12,er13,er23,er123,er213;
        er12 = new StackPoles();
        er13 = new StackPoles();
        er23 = new StackPoles();
        er123 = new StackPoles();
        er213 = new StackPoles();
        er12.setSize(0);er13.setSize(0);er23.setSize(0);er123.setSize(0);er213.setSize(0);
        for(int i = 1; i < 2; i++){
            er12.push(i);
            er13.push(i);
            er123.push(i);
            for(int j = 2; j < 3; j++){
                er12.push(j);
                er23.push(j);
                er123.push(j);
                er213.push(j);
                er213.push(1);
                for(int k = 3; k < 4; k++){
                    er13.push(k);
                    er23.push(k);
                    er123.push(k);
                    er213.push(k);
                }
            }
        }
        if(checker.equals(er12)|| checker.equals(er13)|| checker.equals(er23)|| checker.equals(er123) || checker.equals(er213)){
            return true;
        }
        return false;
    }
}