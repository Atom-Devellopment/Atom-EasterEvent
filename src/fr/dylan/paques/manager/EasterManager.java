package fr.dylan.paques.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitScheduler;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;

import fr.dylan.paques.Easter;
import fr.dylan.paques.api.event.eggEvent.EasterAddEggEvent;
import fr.dylan.paques.api.event.lootEvent.EasterAddLootEvent;
import fr.dylan.paques.api.event.reloadEvent.EasterReloadConfigEvent;
import fr.dylan.paques.api.event.startEvent.EasterStartEvent;
import fr.dylan.paques.api.event.startEvent.PaquesStartTimerEvent;
import fr.dylan.paques.api.event.stopEvent.EasterStopEvent;
import fr.dylan.paques.commands.EasterCommand;
import fr.dylan.paques.config.ConfigLoader;
import fr.dylan.paques.data.EggRarity;
import fr.dylan.paques.events.EasterListener;
import fr.dylan.paques.inventory.InfosInventory;
import fr.dylan.paques.objects.EggObject;
import fr.dylan.paques.objects.LootsObject;
import fr.dylan.paques.timer.EasterTimer;
import fr.dylan.paques.utils.Utils;
import fr.dylan.paques.utils.scoreboard.FastBoard;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class EasterManager 
{
	@Getter public static EasterManager INSTANCE;
	public Gson gson;

	public ArrayList<EggObject> eggsList;
	public HashMap<Location, EggRarity> eggsMap;
	public boolean isActive;
	
	public ConfigLoader configLoader = new ConfigLoader();
	public final Map<UUID, FastBoard> scoreBoards;
	public ArrayList<LootsObject> loots;
	
	public EasterManager()
	{
		INSTANCE = this;
		
		new SaverManager();
		this.gson = SaverManager.getINSTANCE().getGsonBuilder().create();

		configLoader.loadConfig();
		
		eggsList = Lists.newArrayList();
		eggsMap = Maps.newHashMap();
		scoreBoards = Maps.newHashMap();
		loots = Lists.newArrayList();
		
		Utils.registerCommand(new EasterCommand());
		Utils.registerListener(new EasterListener());
	}
	
	public void openInformationsInventory(Player player)
	{
		new InfosInventory(player).open(player);
	}
	
	public void startEvent()
	{
		PaquesStartTimerEvent startTimerEvent = new PaquesStartTimerEvent();
		startTimerEvent.call();
		if(startTimerEvent.isCancelled()) {return;}
		
		EasterTimer paquesTimer = new EasterTimer();
		paquesTimer.runTaskTimer(Easter.getINSTANCE(), 0, 20);
		setActive(true);
	}
	
	public void startNowEvent()
	{
		EasterStartEvent startEvent = new EasterStartEvent();
		startEvent.call();
		if(startEvent.isCancelled()) {return;}
		
		Utils.transformList(eggsList);
		eggsMap.forEach((location, rarity) ->{
			
			Utils.getBlockEgg(location, rarity);
		});
		
		configLoader.EGG_EVENT_START.forEach(message ->{
			Bukkit.broadcastMessage(message.replace("&", "§"));
		});
		
		if(isActive == false)
		{
			setActive(true);
		}

		Bukkit.getOnlinePlayers().forEach(player ->{
			createScoreBoard(player);
		});
	}
	
	public void stopEggEvent()
	{		
		EasterStopEvent stopEvent = new EasterStopEvent();
		stopEvent.call();
		if(stopEvent.isCancelled()) {return;}
		
		eggsMap.forEach((loc, rarity) ->{
			loc.getWorld().getBlockAt(loc).setType(Material.AIR);
		});
		
		configLoader.EGG_EVENT_STOP.forEach(message ->{
			Bukkit.broadcastMessage(message.replace("&", "§"));
		});	
		
		Bukkit.getOnlinePlayers().forEach(player ->{
			deleteScoreBoard(player);
		});
		setActive(false);
	}
	
	public void setEgg(Player player, Location location, EggRarity eggRarity)
	{
		EasterAddEggEvent addEggEvent = new EasterAddEggEvent(player, location, eggRarity);
		addEggEvent.call();
		if(addEggEvent.isCancelled()) {return;}
		
		if(eggsList.contains(new EggObject(location,eggRarity)))
		{
			player.sendMessage(configLoader.MESSAGE_PREFIX+" "+configLoader.EGG_HAS_ALREADY_PLACED_AT_LOCATION);
			return;
		}
		
		eggsList.add(new EggObject(location,eggRarity));
		player.sendMessage(configLoader.MESSAGE_PREFIX+" "+configLoader.EGG_PLACED.replace("%rarity%", eggRarity.rarityName).replace("%posX%", String.valueOf(location.getBlockX())).replace("%posY%", String.valueOf(location.getBlockY())).replace("%posZ%", String.valueOf(location.getBlockZ())));
	}
	
	public void reloadConfig()
	{
		EasterReloadConfigEvent reloadEvent = new EasterReloadConfigEvent();
		reloadEvent.call();
		if(reloadEvent.isCancelled()) {return;}
		
		Easter.getINSTANCE().reloadConfig();
		configLoader.loadConfig();
	}
	
	public void createScoreBoard(Player player) {
	    BukkitScheduler scheduler = Bukkit.getServer().getScheduler();

	    scheduler.runTaskLater(Easter.getINSTANCE(), () -> {
	        FastBoard board = new FastBoard(player);
	        updateScoreBoard(board); 
	        scoreBoards.put(player.getUniqueId(), board);
	    }, 10);
	}

	public void deleteScoreBoard(Player player) {
	    FastBoard board = scoreBoards.remove(player.getUniqueId());
	    if (board != null) {
	        board.delete();
	    }
	}

	public void updateScoreBoard(FastBoard board) {
	    board.updateTitle(configLoader.SCOREBOARD_TITLE);
	    ArrayList<String> lines = Lists.newArrayList();
	    configLoader.SCOREBOARD_LINES.forEach(line -> {
	        lines.add(line.replace("&", "§").replace("%current-egg%", String.valueOf(eggsMap.size())).replace("%max-egg%", String.valueOf(eggsList.size())));
	    });
	    board.updateLines(lines);
	}
	
	public void addLoot(Player player, EggRarity rarity, int pourcent, String command, int value)
	{
		EasterAddLootEvent addLootEvent = new EasterAddLootEvent(player, rarity, pourcent, command);
		addLootEvent.call();
		if(addLootEvent.isCancelled()) {return;}
		
		
		ItemStack item = player.getItemInHand();
		
		if(value == 0)
			getLoots().add(new LootsObject(value, command, pourcent, rarity));
		else
			getLoots().add(new LootsObject(value, item, pourcent, rarity));
		
		player.sendMessage("§aYou just added loot with a rarity "+ rarity.getRarityName());

	}
	
}
