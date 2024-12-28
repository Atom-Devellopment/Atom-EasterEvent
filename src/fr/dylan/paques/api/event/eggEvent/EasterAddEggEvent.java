package fr.dylan.paques.api.event.eggEvent;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import fr.dylan.paques.data.EggRarity;
import fr.dylan.paques.utils.event.CancelledPaquesEvent;
import lombok.Getter;

@Getter
public class EasterAddEggEvent extends CancelledPaquesEvent
{
	public Player player;
	public Location eggLocation;
	public EggRarity eggRarity;
	
	public EasterAddEggEvent(Player player, Location location, EggRarity rarity)
	{
		this.player = player;
		this.eggLocation = location;
		this.eggRarity = rarity;
	}
}
