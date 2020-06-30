// 
// Decompiled by Procyon v0.5.36
// 

package de.wiese_christoph.tnx.objects;

public class PlayerStats
{
    public String name;
    public final String uuid;
    public int deaths;
    public int brokenBlocks;
    public int placedBlocks;
    public int pveKills;
    public int pvpKills;
    
    public PlayerStats(final String name, final String uuid) {
        this.deaths = 0;
        this.brokenBlocks = 0;
        this.placedBlocks = 0;
        this.pveKills = 0;
        this.pvpKills = 0;
        this.name = name;
        this.uuid = uuid;
    }
    
    public void addDeath() {
        ++this.deaths;
    }
    
    public void addBrokenBlocks() {
        ++this.brokenBlocks;
    }
    
    public void addPlacedBlocks() {
        ++this.placedBlocks;
    }
    
    public void addPveKills() {
        ++this.pveKills;
    }
    
    public void addPvpKills() {
        ++this.pvpKills;
    }
}
