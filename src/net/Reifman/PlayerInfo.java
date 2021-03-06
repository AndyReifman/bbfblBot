package net.Reifman;
/**
 * Class to get player information.
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import org.jibble.pircbot.PircBot;



public class PlayerInfo extends PircBot
{
	
	public ArrayList<String> getPlayers(String lastName) throws SQLException
	{
		Connection con;
		ArrayList<String> names = new ArrayList<String>(2); 
		try
		{
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			con = DriverManager.getConnection("jdbc:ucanaccess://C:/Russell/BBFBL/BBFBLMasterVersion3.accdb"); //name of ODBC driver
			Statement stmt = con.createStatement();
			stmt.executeQuery("SELECT * FROM DraftNightQuery");
			ResultSet rSet = stmt.getResultSet();
			
			while(rSet.next())
			{	
				String name = rSet.getString("Last"); //get the item from column named Team Name
				String price = rSet.getString("Salary"); //get the item from column named Salary
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
		Connection con;
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
		Connection con;
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

