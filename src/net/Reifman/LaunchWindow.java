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
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class LaunchWindow extends JFrame {
  
  
  private PropertyChangeSupport propSupport = new PropertyChangeSupport(this);
  
  JPanel buttons = new JPanel();
  ArrayList<Integer> temp; 
  public LaunchWindow(){
    
  }
  
  public void addPropertyChangeListener(PropertyChangeListener listener){
    propSupport.addPropertyChangeListener(listener);
  }
  
  public void removePropertChangeListener(PropertyChangeListener listener){
    propSupport.removePropertyChangeListener(listener);
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
        boolean pass = false;
        temp = pullInfo(txtOrder, txtRounds);
        if(!temp.isEmpty()){
          setVisible(false);
          int rounds = temp.get(0);
          temp.remove(0);
          ArrayList<Integer> order = temp;
          connect(order, rounds);
        }
      }
    });
    
    return temp;
  }
  
  public ArrayList<Integer> pullInfo(JTextField txtOrder, JTextField txtRounds){
    ArrayList<Integer> returns = new ArrayList<Integer>();
      String tempOrder = txtOrder.getText();
      String rounds = txtRounds.getText();
      String[] temp = tempOrder.split("\\s+");

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
    
    return returns;
  }
  
  private static void connect(ArrayList<Integer> order, int rounds){
    BotClass bot = new BotClass("BBFBL_Mod", order, rounds); //create new bot and name it

    try
    {
        bot.setVerbose(true);
        bot.connect("irc.freenode.org"); //connect to freenode. Can be set to other IRC chat
        //bot.sendMessage("nickserv", "IDENTIFY <password>"); //Once username is registered, this will be the password
        bot.joinChannel("#bbfbl"); //what channel to join
    }
    catch (Exception e)
    {
        e.printStackTrace();
    }
}


}
