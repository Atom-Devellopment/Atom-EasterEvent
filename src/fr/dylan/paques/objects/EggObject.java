package fr.dylan.paques.objects;

import org.bukkit.Location;

import fr.dylan.paques.data.EggRarity;
import lombok.Getter;

@Getter
public class EggObject 
{
	private Location eggLocation;
	private EggRarity eggRarity;
	
	public EggObject(Location eggLocation, EggRarity eggRarity)
	{
		this.eggLocation = eggLocation;
		this.eggRarity = eggRarity;
	}
}
