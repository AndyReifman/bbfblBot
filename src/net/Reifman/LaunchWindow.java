package net.Reifman;
/**
 * This file will create the GUI that will retrieve information for the bot.
 * Should collect information on draft order and # of rounds in the nights draft.
 * @author Andrew Reifman-Packett
 *
 */

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class LaunchWindow extends JFrame {
  
  JPanel buttons = new JPanel();
  ArrayList<Integer> temp; 
  public LaunchWindow(){
    
  }
  
  public ArrayList<Integer> createGUI(){
    JLabel lblOrder = new JLabel("Enter Draft Order");
    JLabel lblRounds = new JLabel("Enter the number of rounds in tonights draft");
    
    final JTextField txtOrder = new JTextField(10);
    final JTextField txtRounds = new JTextField(10);
    
    setTitle("Bbfbl Information");
    setSize(300,200);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setVisible(true);
    
    JButton close = new JButton("Exit");
    JButton start = new JButton("Start");
    buttons.setLayout(new FlowLayout());
    buttons.add(lblOrder);
    buttons.add(txtOrder);
    buttons.add(lblRounds);
    buttons.add(txtRounds);
    buttons.add(start);
    buttons.add(close);
    getContentPane().add(buttons);
    
    close.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
        System.exit(0);
      }
    });
    
    start.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
        temp = pullInfo(txtOrder, txtRounds);
      }
    });
    
    return temp;
  }
  
  public ArrayList<Integer> pullInfo(JTextField txtOrder, JTextField txtRounds){
    String tempOrder = txtOrder.getText();
    String rounds = txtRounds.getText();
    String[] temp = tempOrder.split("\\s+");
    ArrayList<Integer> returns = new ArrayList<Integer>();
    try{
      int numRounds  = Integer.parseInt(rounds);
      returns.add(numRounds);
      for(String t: temp){
        returns.add(Integer.parseInt(t));
      }
    }catch(Exception e){
      JOptionPane.showMessageDialog(null,"Please ensure you're only entering numbers and spaces");
      txtOrder.setText("");
      txtRounds.setText("");
    }
    setVisible(false);
    return returns;
  }


}
