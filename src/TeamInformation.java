import java.sql.*;
import java.util.Properties;



public class TeamInformation {
	
	public String salary(String name) throws SQLException //finds a persons salary
	{
		String connectionString = "jdbc:odbc:Driver={Microsoft Access Driver (*.accdb)}; DBQ = C:\\RUSSELL\\BBFBL";
		Connection con;
		Properties connectionProps = new Properties();
		int money = 0;
		try
		{
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			con = DriverManager.getConnection("jdbc:odbc:test"); //name of ODBC driver
			Statement stmt = con.createStatement();
			stmt.executeQuery("SELECT * FROM TeamPayrollQuery");
			ResultSet rSet = stmt.getResultSet();
			
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
	{
		Connection con;
		try
		{
			con = DriverManager.getConnection("jdbc:ucanaccess://C:\\RUSSELL\\BBFBL\\BBFBLMasterVersion3.accdb"); //name of ODBC driver
			Statement stmt = con.createStatement();
			stmt.executeQuery("SELECT * FROM DraftNightQuery");
			ResultSet rSet = stmt.getResultSet();
			String[] split = name.split(" "); 
			while(rSet.next())
			{	
				String lastName = rSet.getString("Last");
				//int x = Integer.parseInt(salary);
				if(split[0].toLowerCase().equalsIgnoreCase(lastName))
				{
					String firstName = rSet.getString("First"); //get the item from column named Team Name
					if(split[1].toLowerCase().equalsIgnoreCase(firstName))
					{
						stmt = con.createStatement();
						String whoToAdd = "UPDATE DraftNightQuery SET [OwnerID]="+x+" WHERE Last='"+split[0]+"' AND First='"+split[1]+ "' "; 
						stmt.executeUpdate(whoToAdd);
						String salary = rSet.getString("Salary");
						con.close();
						stmt.close();
						return Integer.parseInt(salary);
					}
				}
			}
			return 1;
		}
		finally{}
	}
	
	public void dropPlayer(String name, int x) throws SQLException
	{
		String connectionString = "jdbc:odbc:Driver={Microsoft Access Driver (*.accdb)}; DBQ = C:\\RUSSELL\\BBFBL";
		Connection con;
		Properties connectionProps = new Properties();
		int money = 0;
		try
		{
			String[] split = name.split("\\s");
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			con = DriverManager.getConnection("jdbc:odbc:test"); //name of ODBC driver
			Statement getPrice = con.createStatement();
			Statement stmt = con.createStatement();
			String whoToDrop = "INSERT INTO WeeklyPlayersDropped (Last, First, OwnerID) VALUES ('"+split[0]+"','"+split[1]+"','"+x+"')"; 
			stmt.executeUpdate(whoToDrop);
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
	}
	
}
