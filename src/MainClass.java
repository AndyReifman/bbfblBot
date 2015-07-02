/**
	* Created by Andrew Reifman-Packett
	* March 2014
	* Copyright: 2014-2015
	* Thanks to Misha Gale for helping guide me in the write direction and for writing the code that connects to a webpage

public class MainClass {
	public static void main(String[] args)
	{
		BotClass bot = new BotClass("BBFBL_Mod"); //create new bot and name it

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
