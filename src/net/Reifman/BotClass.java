package net.Reifman;

import java.sql.SQLException;
import java.util.ArrayList;

import org.jibble.pircbot.PircBot;


public class BotClass extends PircBot{

	PlayerInfo player = new PlayerInfo(); //creates instance of PlayerInfo
	TeamInformation team = new TeamInformation(); //creates instance of TeamInformation
	public int x = 0; //Always set to zero. First person in the draft
	public int numPeople = 10; //Number of people in the round
	public ArrayList<Integer> order = new ArrayList<Integer>();
	public int numRounds; //number of rounds for the night
	public int roundNum = 1; //round to start on
	//Constructor
	public BotClass(String name, ArrayList<Integer> t, int rounds)
	{
		setName(name);
		numRounds = rounds;
		order = t;
		System.out.println("Number of Rounds: "+numRounds);
		System.out.println("Order: "+order);
	}

	/* (non-Javadoc)
	 * @see org.jibble.pircbot.PircBot#onMessage(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public void onMessage(String channel, String sender, String login, String hostname, String message) //when a message is said in chat box
	{
		if(message.equalsIgnoreCase("!hello")) //checks to see if message is !hello
		{
			sendMessage(channel, "Hello "+sender); //responds to user with hello
			return; //required to send to channel. Kind of like pressing enter.
		}
		if(message.equalsIgnoreCase("pick_bot"))
		{
			sendMessage(channel, "Yes "+sender+"?");
			return;
		}


		if(message.toLowerCase().startsWith("!salary")) //Salary check command
		{
			String lastName = message.toLowerCase().substring(8);
			String salary = null;
			try {
				salary = team.salary(lastName);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			sendMessage(channel, salary);
		}
		if(message.toLowerCase().startsWith("!draft")) //draft
		{
			String turn = teamName(order.get(x));
			int ownerID = order.get(x);
			int response = 0;
			String name = message.toLowerCase().substring(7);
			if(sender.equalsIgnoreCase(turn) || sender.equalsIgnoreCase("Junior_Commish") || sender.equalsIgnoreCase("TheCommish") || sender.equalsIgnoreCase("The_Commish"))
			{

				try {
					response = team.addPlayer(name, ownerID);
					if(response != 1)
						sendMessage(channel, turn + " drafts " + name + " $" + response);
					else
						sendMessage(channel, "That player's name is either spelled wrong or not available.");

				}
				catch (SQLException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else
			{
				sendMessage(channel, "Sorry " + sender + " it's not your turn yet.");
			}
		}
		if(message.toLowerCase().startsWith("!pass"))
		{
			String turn = teamName(order.get(x));
			if(sender.equalsIgnoreCase(turn) || sender.equalsIgnoreCase("Junior_Commish") || sender.equalsIgnoreCase("TheCommish") || sender.equalsIgnoreCase("The_Commish"))
			{
				sendMessage(channel, turn + " passes");
			}
			else
			{
				sendMessage(channel, "Sorry " + sender + " it's not your turn yet.");
			}
		}
		if(message.toLowerCase().startsWith("!drop")) //draft
		{
			String turn = teamName(order.get(x));
			int ownerID = order.get(x);
			int response;
			String name = message.toLowerCase().substring(6);
			if(sender.equalsIgnoreCase(turn) || sender.equalsIgnoreCase("Junior_Commish") || sender.equalsIgnoreCase("Eabryt") || sender.equalsIgnoreCase("TheCommish") || sender.equalsIgnoreCase("The_Commish"))
			{

				try {
					response = team.dropPlayer(name, ownerID);
					if(response == 1)
						sendMessage(channel, turn + " dropped "+name);
					else
						sendMessage(channel, turn + " doesn't own "+name);
				}
				catch (SQLException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else
			{
				sendMessage(channel, "Sorry " + sender + " it's not your turn yet.");
			}
		}
	}
	public void onPrivateMessage(String sender, String login, String hostname, String message) //When a private message is recieved
	{
		if(message.equalsIgnoreCase("!next") && (sender.equalsIgnoreCase("Junior_Commish") || sender.equalsIgnoreCase("The_Commish") || sender.equalsIgnoreCase("Eabryt")) || sender.equalsIgnoreCase("TheCommish")) //goes to the next person to pick
		{
			if(x == numPeople)
			{
				if(roundNum == numRounds)
					sendMessage("#bbfbl", "End of tonight's draft");
				else
				{
					sendMessage("#bbfbl", "End of round "+roundNum);
					roundNum++;
					sendMessage("#bbfbl", "Start of round "+roundNum);
				}
				x = 0;
			}
			else
			{
				String nextPick = pick(order.get(x));
				sendMessage("#bbfbl", nextPick);
				x++;
				return;
			}
		}

		if(message.toLowerCase().startsWith("!player")) //search by last name
		{
			String lastName = message.toLowerCase().substring(8);
			ArrayList<String> players = new ArrayList<String>(2);
			try {
					players = player.getPlayers(lastName);
					while(players.size() > 0)
					{
						sendMessage(sender, players.get(0));
						players.remove(0);
					}
					return;
				}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
		if(message.toLowerCase().startsWith("!price")) //search for a specific price
		{
			String price = message.toLowerCase().substring(7);
			ArrayList<String> players = new ArrayList<String>(2);
			try {
					players = player.getPrice(price);
					while(players.size() > 0)
					{
						sendMessage(sender, players.get(0));
						players.remove(0);
					}
					return;
				}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(message.toLowerCase().startsWith("!position")) //search by positition
		{
			String position = message.toLowerCase().substring(10);
			ArrayList<String> players = new ArrayList<String>(2);
			try {
					players = player.getPosition(position);
					while(players.size() > 0)
					{
						sendMessage(sender, players.get(0));
						players.remove(0);
					}
					return;
				}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public void onAction(String sender, String login, String hostname, String target, String action) //On a /me action
	{
		String[] channel = getChannels();
		if(action.toLowerCase().contains("poke") && action.toLowerCase().contains("pick_bot"))
		{
			sendAction(channel[0], "pokes " + sender + " back!");
		}
		else if(action.toLowerCase().contains("pick_bot"))
		{
			sendAction(channel[0], "Is awake and listening.");
		}
	}

	/**
	 * Case 1 represents the first person in the round. It then goes in order from there.
	 * To change the order simply change the names in the quotes ("Russell") after the return statement
	 * To add more people simple increase the number of cases. The variable numPeople at the top of the program
	 * MUST match the number of cases.
	 * @param x
	 * @return
	 */
	public String pick(int x)
	{
		switch(x)
		{
		case 1:
			return "Jim";
		case 2:
			return "Russell";
		case 3:
			return "Dutra";
		case 4:
			return "Jeff";
		case 5:
			return "John C";
		case 6:
			return "Scotty";
		case 7:
			return "Tom";
		case 8:
			return "Mike";
		case 9:
			return "AJ";
		case 10:
			return "Glenn";
		case 11:
			return "Darren";

		}
		return "";
	}
	/**
	 * The return statements can be based off peoples usernames.
	 * Order is again determined by specific draft
	 * Usernames MUST be team names as one word OR a specific (preferably registered) name
	 * and can be changed to that in the statement
	 * @param x
	 * @return
	 */
	public String teamName(int x)
	{
		switch(x)
		{
		case 1:
			return "JimLightning";
		case 2:
			return "RussellHurlers";
		case 3:
			return "DavidPontiffs";
		case 4:
			return "JeffChiefs";
		case 5:
			return "JohnHuskies";
		case 6:
			return "ScottyDead";
		case 7:
			return "TomBums";
		case 8:
			return "MikeWindbag";
		case 9:
			return "AJWildThings";
		case 10:
			return "GlennTippers";
		case 11:
			return "DarrenExpress";

		}
		return null;
	}

	/**
	 * Same cases as the pick method. Instead of returning the string name, return the persons OwnerID
	 * @param x
	 * @return
	 */
	/*public int ID(int x)
	{
		switch(x)
		{
		case 1:
			return 4;
		case 2:
			return 10;
		case 3:
			return 1;
		case 4:
			return 12;
		case 5:
			return 7;
		case 6:
			return 5;
		case 7:
			return 8;
		case 8:
			return 9;
		case 9:
			return 2;
		case 10:
			return 6;
		case 11:
			return 3;
		case 12:
			return 11;
		}
		return 0;
	}*/
}
