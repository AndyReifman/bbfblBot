package net.Reifman;
/**
 * Class to get player information.
 */

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;



import org.jibble.pircbot.PircBot;
import org.pircbotx.PircBotX;



public class PlayerInfo extends PircBot
{
	/*public ArrayList<String> getPlayers(String lastName) throws IOException
	{
		Document doc = Jsoup.connect("http://www.bbfbl.com/players-available/").timeout(0).get(); //The website it is getting the information from
		Elements rows = doc.getElementsByTag("tr"); //searches by row
		ArrayList<String> names = new ArrayList<String>(2); 
		for(Element row : rows)
		{
			Elements columns = row.getElementsByTag("td"); //Columns
			String name = columns.get(1).text(); //Gets the item in column 2 (1 in the array)
			String price = columns.get(3).text(); //gets the item in column 4 (2 in the array)
			if(lastName.equalsIgnoreCase(name))
			{
				name = name + ", "+ columns.get(2).text() +" "+ price;
				names.add(name);
			}
		}
		if(names.size() > 0)
		{
			return names;
		}
		else
			return null;
	} */
	
	public ArrayList<String> getPlayers(String lastName) throws SQLException
	{
		String connectionString = "jdbc:odbc:Driver={Microsoft Access Driver (*.accdb)}; DBQ = C:\\Users\\Andrew\\Dropbox\\Public\\Schoolwork\\IRC\\BBFBL";
		Connection con;
		Properties connectionProps = new Properties();
		ArrayList<String> names = new ArrayList<String>(2); 
		try
		{
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			con = DriverManager.getConnection("jdbc:ucanaccess://C:\\Users\\Andrew\\Dropbox\\Public\\Schoolwork\\IRC\\BBFBL"); //name of ODBC driver
			Statement stmt = con.createStatement();
			stmt.executeQuery("SELECT * FROM DraftNightQuery");
			ResultSet rSet = stmt.getResultSet();
			
			while(rSet.next())
			{	
				String name = rSet.getString("Last"); //get the item from column named Team Name
				String price = rSet.getString("Salary"); //get the item from column named Salary
				//int x = Integer.parseInt(salary);
				if(name.toLowerCase().startsWith(lastName))
				{
					name = name + ", " + rSet.getString("First") + " $" + price;
					names.add(name);
				}
			}
			if(names.size() > 0)
				return names;
			else
				return null;
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	public ArrayList<String> getPrice(String money) throws SQLException
	{
		String connectionString = "jdbc:odbc:Driver={Microsoft Access Driver (*.accdb)}; DBQ = C:\\Users\\Andrew\\Dropbox\\Public\\Schoolwork\\IRC\\BBFBL";
		Connection con;
		Properties connectionProps = new Properties();
		ArrayList<String> names = new ArrayList<String>(2); 
		try
		{
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			con = DriverManager.getConnection("jdbc:odbc:BBFBL"); //name of ODBC driver
			Statement stmt = con.createStatement();
			stmt.executeQuery("SELECT * FROM DraftNightQuery");
			ResultSet rSet = stmt.getResultSet();
			
			while(rSet.next())
			{	
				String name = rSet.getString("Last"); //get the item from column named Team Name
				String price = rSet.getString("Salary"); //get the item from column named Salary
				if(price.toLowerCase().startsWith(money))
				{
					name = name + ", " + rSet.getString("First") + " $" + price;
					names.add(name);
				}
			}
			if(names.size() > 0)
				return names;
			else
				return null;
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	public ArrayList<String> getPosition(String spot) throws SQLException
	{
		String connectionString = "jdbc:odbc:Driver={Microsoft Access Driver (*.accdb)}; DBQ = C:\\Users\\Andrew\\Dropbox\\Public\\Schoolwork\\IRC\\BBFBL";
		Connection con;
		Properties connectionProps = new Properties();
		ArrayList<String> names = new ArrayList<String>(2); 
		try
		{
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			con = DriverManager.getConnection("jdbc:odbc:test"); //name of ODBC driver
			Statement stmt = con.createStatement();
			stmt.executeQuery("SELECT * FROM DraftNightQuery");
			ResultSet rSet = stmt.getResultSet();
			
			while(rSet.next())
			{	
				String name = rSet.getString("Last"); //get the item from column named Team Name
				String price = rSet.getString("Salary"); //get the item from column named Salary
				String position = rSet.getString("pos");
				if(position.toLowerCase().startsWith(spot))
				{
					name = name + ", " + rSet.getString("First") + " $" + price;
					names.add(name);
				}
			}
			if(names.size() > 0)
				return names;
			else
				return null;
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
}

