// 
// Decompiled by Procyon v0.5.36
// 

package de.wiese_christoph.tnx.utils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import de.wiese_christoph.tnx.Main;
import de.wiese_christoph.tnx.objects.PlayerStats;
import java.sql.DriverManager;
import java.sql.Connection;

public class MysqlCon
{
    private final String username;
    private final String passwd;
    private final String dbname;
    private Connection conn;
    
    public MysqlCon(final String username, final String passwd, final String dbname) {
        this.conn = null;
        this.username = username;
        this.passwd = passwd;
        this.dbname = dbname;
    }
    
    private void Connect() {
        try {
            Class.forName("org.postgresql.Driver");
            this.conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + this.dbname, this.username, this.passwd);
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public void updateData(final PlayerStats ps) {
        this.Connect();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = this.conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM stats WHERE uuid='" + ps.uuid + "';");
            if (!rs.isBeforeFirst()) {
                final PreparedStatement pr = this.conn.prepareStatement("INSERT INTO stats (name, uuid, deaths, brokenBlocks, placedBlocks, pveKills, pvpKills) VALUES (?,?,?,?,?,?,?)");
                pr.setString(1, ps.name);
                pr.setString(2, ps.uuid);
                pr.setInt(3, ps.deaths);
                pr.setInt(4, ps.brokenBlocks);
                pr.setInt(5, ps.placedBlocks);
                pr.setInt(6, ps.pveKills);
                pr.setInt(7, ps.pvpKills);
                if (pr.executeUpdate() > 0) {
                    System.out.println(String.valueOf(Main.Name) + "§6Successfully added " + ps.name + " §6to the database!");
                }
                else {
                    System.out.println(String.valueOf(Main.Name) + "Error while adding " + ps.name + " to the database!");
                }
            }
            else {
                while (rs.next()) {
                    final PreparedStatement pr = this.conn.prepareStatement("UPDATE stats SET name=?, deaths=?, brokenBlocks=?, placedBlocks=?, pveKills=?, pvpKills=? WHERE uuid=?");
                    pr.setString(1, ps.name);
                    pr.setInt(2, ps.deaths + rs.getInt("deaths"));
                    pr.setInt(3, ps.brokenBlocks + rs.getInt("brokenBlocks"));
                    pr.setInt(4, ps.placedBlocks + rs.getInt("placedBlocks"));
                    pr.setInt(5, ps.pveKills + rs.getInt("pveKills"));
                    pr.setInt(6, ps.pvpKills + rs.getInt("pvpKills"));
                    pr.setString(7, ps.uuid);
                    if (pr.executeUpdate() > 0) {
                        System.out.println(String.valueOf(Main.Name) + "§6Successfully updated " + ps.name + "§6 in the database!");
                    }
                    else {
                        System.out.println(String.valueOf(Main.Name) + "Error while updating " + ps.name + " in the database!");
                    }
                }
            }
        }
        catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        finally {
            if (rs != null) {
                try {
                    rs.close();
                }
                catch (SQLException ex2) {}
                rs = null;
            }
            if (stmt != null) {
                try {
                    stmt.close();
                }
                catch (SQLException ex3) {}
                stmt = null;
            }
            if (this.conn != null) {
                try {
                    this.conn.close();
                }
                catch (SQLException ex4) {}
            }
        }
        if (rs != null) {
            try {
                rs.close();
            }
            catch (SQLException ex5) {}
            rs = null;
        }
        if (stmt != null) {
            try {
                stmt.close();
            }
            catch (SQLException ex6) {}
            stmt = null;
        }
        if (this.conn != null) {
            try {
                this.conn.close();
            }
            catch (SQLException ex7) {}
        }
    }
}
