package de.wiese_christoph.tnx.objects;

public class PlayerStats {
	
	public String name;
	final public String uuid;
	
	public int deaths = 0;
	public int brokenBlocks = 0;
	public int placedBlocks = 0;
	public int pveKills = 0;
	public int pvpKills = 0;
	
	
	public PlayerStats(String name, String uuid) {
		this.name = name;
		this.uuid = uuid;
	}
	
	
	public void addDeath() {
		this.deaths++;
	}
	
	
	public void addBrokenBlocks() {
		this.brokenBlocks++;
	}
	
	
	public void addPlacedBlocks() {
		this.placedBlocks++;
	}
	
	
	public void addPveKills() {
		this.pveKills++;
	}
	
	
	public void addPvpKills() {
		this.pvpKills++;
	}
}
