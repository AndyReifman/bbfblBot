package net.Reifman;
import java.util.ArrayList;

import javax.swing.JFrame;

/**
	* March 2014
	* Copyright: 2014-2015
	* Thanks to Misha Gale for helping guide me in the write direction and for writing the code that connects to a webpage
	* @author Andrew Reifman-Packett
*/

public class MainClass {
    public static ArrayList<Integer> order;
    public static int rounds;
   
	public static void main(String[] args)
	{
		 JFrame launch = new LaunchWindow(); 
		 ArrayList<Integer> temp = ((LaunchWindow) launch).createGUI();
		 
	}



}
