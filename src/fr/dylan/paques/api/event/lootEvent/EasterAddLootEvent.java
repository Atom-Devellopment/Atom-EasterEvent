package fr.dylan.paques.api.event.lootEvent;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import fr.dylan.paques.data.EggRarity;
import fr.dylan.paques.utils.event.CancelledPaquesEvent;
import lombok.Getter;

@Getter
public class EasterAddLootEvent extends CancelledPaquesEvent
{
	public Player player;
	public EggRarity eggRarity;
	public int pourcent;
	public String lootCommand;
	public ItemStack lootItem;
	
	public EasterAddLootEvent(Player player, EggRarity rarity, int pourcent, String lootCommand)
	{
		this.player = player;
		this.eggRarity = rarity;
		this.pourcent = pourcent;
		this.lootCommand = lootCommand;
		this.lootItem = player.getItemInHand();
	}
	
}
