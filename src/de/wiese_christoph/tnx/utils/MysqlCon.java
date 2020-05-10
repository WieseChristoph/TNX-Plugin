package de.wiese_christoph.tnx.utils;
import java.sql.*;
import de.wiese_christoph.tnx.Main;
import de.wiese_christoph.tnx.objects.PlayerStats;

public class MysqlCon {
	
	final private String username;
	final private String passwd;
	final private String dbname;
	private Connection conn = null;
	
	// get database login on class instantiation
	public MysqlCon(String username, String passwd, String dbname) {
		this.username = username;
		this.passwd = passwd;
		this.dbname = dbname;
	}
	
	
	// create a connection to the database
	private void Connect() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + this.dbname, this.username, this.passwd);
		}catch(Exception e){ System.out.println(e);}
	}
	
	
	// update the given data to the database
	public void updateData(PlayerStats ps) {
		// connect to database
		this.Connect();
		
        // create vars
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
		    stmt = conn.createStatement();
		    // get data from player
		    rs = stmt.executeQuery("SELECT * FROM stats WHERE uuid='" + ps.uuid + "';");
		    // check if no entry with this uuid was found
		    if(!rs.isBeforeFirst()) {
		    	// prepare statement
		    	PreparedStatement pr=conn.prepareStatement("INSERT INTO stats (name, uuid, deaths, brokenBlocks, placedBlocks, pveKills, pvpKills) VALUES (?,?,?,?,?,?,?)");
		    	pr.setString(1, ps.name);
		    	pr.setString(2, ps.uuid);
		    	pr.setInt(3, ps.deaths);
		    	pr.setInt(4, ps.brokenBlocks);
		    	pr.setInt(5, ps.placedBlocks);
		    	pr.setInt(6, ps.pveKills);
		    	pr.setInt(7, ps.pvpKills);
		    	
		    	// execute statement
		    	if(pr.executeUpdate() > 0) System.out.println(Main.Name + "Successfully added "+ ps.name + " to the database!");
		    	else System.out.println(Main.Name + "Error while adding "+ ps.name + " to the database!");
		    }
		    // if the player is already in the database, update the data
		    else {
		    	while (rs.next()) {
		    		// prepare statement
		    		PreparedStatement pr=conn.prepareStatement("UPDATE stats SET name=?, deaths=?, brokenBlocks=?, placedBlocks=?, pveKills=?, pvpKills=? WHERE uuid=?");
		    		pr.setString(1, ps.name);
		    		pr.setInt(2, ps.deaths + rs.getInt("deaths"));
			    	pr.setInt(3, ps.brokenBlocks + rs.getInt("brokenBlocks"));
			    	pr.setInt(4, ps.placedBlocks + rs.getInt("placedBlocks"));
			    	pr.setInt(5, ps.pveKills + rs.getInt("pveKills"));
			    	pr.setInt(6, ps.pvpKills + rs.getInt("pvpKills"));
			    	pr.setString(7, ps.uuid);
			    	
			    	// execute statement
			    	if(pr.executeUpdate() > 0) System.out.println(Main.Name + "Successfully updated "+ ps.name + " in the database!");
			    	else System.out.println(Main.Name + "Error while updating "+ ps.name + " in the database!");
			    }
		    } 
		    
		}
		catch (SQLException ex){
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		}
		finally {
		    // it is a good idea to release
		    // resources in a finally{} block
		    // in reverse-order of their creation
		    // if they are no-longer needed

		    if (rs != null) {
		        try {
		            rs.close();
		        } catch (SQLException sqlEx) { } // ignore

		        rs = null;
		    }

		    if (stmt != null) {
		        try {
		            stmt.close();
		        } catch (SQLException sqlEx) { } // ignore

		        stmt = null;
		    }
		    
		    if(conn != null) {
		    	try {
					conn.close();
				} catch (SQLException e) {}
		    }
		}
	}
	
}
