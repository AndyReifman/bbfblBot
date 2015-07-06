package net.Reifman;
/**
 * This file creates and fills all labels and boxes for the LaunchWindow GUI
 * @author Andrew Reifman-Packett
 */
import java.awt.*;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class WindowInfo extends JPanel {

  public void paintComponent(Graphics g){
    super.paintComponent(g);
    Font f = new Font("SansSerif", Font.BOLD, 14);
    Font fi = new Font("SansSerif", Font.BOLD + Font.ITALIC, 14);
    FontMetrics fm = g.getFontMetrics(f);
    FontMetrics fim = g.getFontMetrics(fi);
    int cx = 75; int cy = 100;
    g.setFont(f);
    g.drawString("Hello, ", cx, cy);
    cx += fm.stringWidth("Hello, ");
    g.setFont(fi);
    g.drawString("World!", cx, cy);
  }
  
  public static void main(String[] args){
    JFrame window = new LaunchWindow();
    window.show();
  }
}