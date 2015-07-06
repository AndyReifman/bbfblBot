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
		 launch.setVisible(true);
		 rounds = temp.get(0);
		 temp.remove(0);
		 order = temp;
		 connect();
	}

	private static void connect(){
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
