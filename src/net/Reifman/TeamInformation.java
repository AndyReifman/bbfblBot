package net.Reifman;
/**
	* Connects to the database and does the work to add/drop players from teams.
	* Will also return a managers current salary (Not including players dropped during current draft)
	* @author Andrew Reifman-Packett
	* @version 6 July 2015
	* Copyright 2014-2015
	*
	*/


import java.sql.*;



public class TeamInformation {

	public String salary(String name) throws SQLException //finds a persons salary
	{
		Connection con;
		int money = 0;
		try
		{
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			con = DriverManager.getConnection("jdbc:ucanaccess://C:/Russell/BBFBL/BBFBLMasterVersion3.accdb"); //name of ODBC driver
			Statement stmt = con.createStatement();
			//stmt.executeQuery("SELECT * FROM TeamPayrollQuery");
			ResultSet rSet = stmt.executeQuery("SELECT * FROM TeamPayrollQuery");
			while(rSet.next())
			{
				String lastName = rSet.getString("TeamName"); //get the item from column named Team Name
				String salary = rSet.getString("Salary"); //get the item from column named Salary
				int x = Integer.parseInt(salary);
				if(lastName.equalsIgnoreCase(name))
					money += x;
			}
			String salary = "$" + money;
			return salary;
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	public int addPlayer(String name, int x) throws SQLException //drafts a person to team x (ownerID)
, ClassNotFoundException
	{
		Connection con;
		try
		{
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			con = DriverManager.getConnection("jdbc:ucanaccess://C:/Russell/BBFBL/BBFBLMasterVersion3.accdb"); //name of ODBC driver
			Statement stmt = con.createStatement();
			ResultSet rSet = stmt.executeQuery("Select * FROM Players");
			String[] split = name.split(" ");
			String salary = "1";
			while(rSet.next())
			{
				String lastName = rSet.getString("Last");
				if(split[0].toLowerCase().equalsIgnoreCase(lastName))
				{
					String firstName = rSet.getString("First"); //get the item from column named Team Name
					if(split[1].toLowerCase().equalsIgnoreCase(firstName))
					{
						int drafted = rSet.getInt("OwnerID");
						if(drafted == 0){
							Statement connec = con.createStatement();
							Statement idMatch = con.createStatement();
							String id = rSet.getString("ID");
							connec.executeUpdate("UPDATE Players SET OwnerID = "+x+" WHERE Last ='"+split[0]+"' AND First='"+split[1]+"' ");
							//stmt.executeUpdate(whoToAdd);
							ResultSet temp =idMatch.executeQuery("SELECT * FROM Salaries WHERE ID ='"+id+"'");
							while(temp.next()){
								String tempID = rSet.getString("ID");
								if(id.toLowerCase().equalsIgnoreCase(tempID)){
									salary = temp.getString("Salary");
								}
							}
							con.close();
							connec.close();
							stmt.close();
							idMatch.close();
							return Integer.parseInt(salary);
						}
						
					}
				}
			}
			return 1;
		}
		finally{}
	}

	public int dropPlayer(String name, int x) throws SQLException
	{
		Connection con;
		try
		{
			String[] split = name.split("\\s");
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			con = DriverManager.getConnection("jdbc:ucanaccess://C:/Russell/BBFBL/BBFBLMasterVersion3.accdb"); //name of ODBC driver
			Statement getPrice = con.createStatement();
			Statement stmt = con.createStatement();
			ResultSet rSet = getPrice.executeQuery("Select * FROM Players");
			while(rSet.next())
			{
				String lastName = rSet.getString("Last");
				if(split[0].toLowerCase().equalsIgnoreCase(lastName))
				{
					String firstName = rSet.getString("First"); //get the item from column named Team Name
					if(split[1].toLowerCase().equalsIgnoreCase(firstName))
					{
						String temp = rSet.getString("OwnerID");
						System.out.println(temp);
						if(temp.equals(""))
							return 0;
						else if(x == Integer.parseInt(rSet.getString("OwnerID")))
						{
							String whoToDrop = "INSERT INTO WeeklyPlayersDropped (Last, First, OwnerID) VALUES ('"+split[0]+"','"+split[1]+"','"+x+"')";
							stmt.executeUpdate(whoToDrop);
							return 1;
						}
						else{
							return 0;
						}
					}
				}
			}

		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		return 0;
	}

}
