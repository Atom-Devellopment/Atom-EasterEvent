package fr.dylan.paques.api;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import fr.dylan.paques.data.EggRarity;
import fr.dylan.paques.manager.EasterManager;
import fr.dylan.paques.objects.LootsObject;
import lombok.Getter;

@Getter
public class EasterApi 
{
	private EasterManager manager = EasterManager.getINSTANCE();
	
	@Getter public static EasterApi API; //Get l'instance de la class Api
	
	public boolean eventIsActivated = manager.isActive; //Get si l'event est lancer
	
	public HashMap<Location, EggRarity> eggs = manager.getEggsMap(); //Get les oeufs
	
	public ArrayList<LootsObject> loots = manager.getLoots(); //Get les loots
	
	public EasterApi() {API = this;}
	
	//Lance l'event sans Timer
	public void startEvent()
	{
		manager.startNowEvent();
	}
	
	//Lance l'event avec Timer
	public void startEventWithTimer()
	{
		manager.startEvent();
	}
	
	//Arrête l'event
	public void stopEvent()
	{
		manager.stopEggEvent();
	}
	
	//Permet d'ajouter un oeuf dans la map
	public void setEgg(Player player, Location location, EggRarity rarity)
	{
		manager.setEgg(player, location, rarity);
	}
	
	//Permet d'ajouter un loot
	public void setLoot(Player player, EggRarity rarity, int pourcent, String command, int value)
	{
		manager.addLoot(player, rarity, pourcent, command, value);
	}
}
