package fr.dylan.paques.api.event.lootEvent;

import org.bukkit.entity.Player;

import fr.dylan.paques.objects.LootsObject;
import fr.dylan.paques.utils.event.CancelledPaquesEvent;
import lombok.Getter;

@Getter
public class EasterRemoveLootEvent extends CancelledPaquesEvent
{
	public Player player;
	public LootsObject loot;
	
	public EasterRemoveLootEvent(Player player, LootsObject loot)
	{
		this.player = player;
		this.loot = loot;
	}
}
