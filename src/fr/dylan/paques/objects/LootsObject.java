package fr.dylan.paques.objects;

import org.bukkit.inventory.ItemStack;

import fr.dylan.paques.data.EggRarity;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LootsObject 
{
	public int value;
	public String command;
	public ItemStack itemStack;
	public int pourcent;
	public EggRarity rarity;
	
	public LootsObject(int value,String command, int pourcent, EggRarity rarity)
	{
		this.value = value;
		this.command = command;
		this.pourcent = pourcent;
		this.rarity = rarity;
	}
	
	public LootsObject(int value,ItemStack itemStack, int pourcent, EggRarity rarity)
	{
		this.value = value;
		this.itemStack = itemStack;
		this.pourcent = pourcent;
		this.rarity = rarity;
	}
}
