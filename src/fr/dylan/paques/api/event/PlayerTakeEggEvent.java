package fr.dylan.paques.api.event;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import fr.dylan.paques.data.EggRarity;
import fr.dylan.paques.objects.LootsObject;
import fr.dylan.paques.utils.event.CancelledPaquesEvent;
import lombok.Getter;

@Getter
public class PlayerTakeEggEvent extends CancelledPaquesEvent
{
	public Player player;
	public Location eggLocation;
	public EggRarity eggRarity;
	public LootsObject loot;

	public PlayerTakeEggEvent(Player player, Location location, EggRarity rarity, LootsObject loot)
	{
		this.player = player;
		this.eggLocation = location;
		this.eggRarity = rarity;
		this.loot = loot;
	}

}
