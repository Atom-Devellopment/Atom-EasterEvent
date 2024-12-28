package fr.dylan.paques.events;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import fr.dylan.paques.api.event.PlayerTakeEggEvent;
import fr.dylan.paques.data.EggRarity;
import fr.dylan.paques.manager.EasterManager;
import fr.dylan.paques.objects.LootsObject;
import fr.dylan.paques.utils.Utils;
import fr.dylan.paques.utils.scoreboard.FastBoard;

public class EasterListener implements Listener 
{
	private EasterManager paquesManager = EasterManager.getINSTANCE();
	
	 @EventHandler
	 public void onJoin(PlayerJoinEvent event) 
	 {
		if(paquesManager.isActive == false) {return;}
		
		Player player = event.getPlayer();
	    paquesManager.createScoreBoard(player);
	 }
	 
	 

	 @EventHandler
	 public void onQuit(PlayerQuitEvent event) 
	 {
		 if(paquesManager.isActive == false) {return;}

		 Player player = event.getPlayer();
		 paquesManager.deleteScoreBoard(player);
	 }
	 
	 @EventHandler
	 public void onInteract(PlayerInteractEvent event)
	 {
		 if(paquesManager.isActive == false) {return;}
		 
		 Action action = event.getAction();
		 
		 if(action == Action.RIGHT_CLICK_AIR || action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK) {return;}
		 
		 
		 if(event.getClickedBlock().getType() != Material.SKULL) {return;}
		 
		 if(!paquesManager.getEggsMap().containsKey(event.getClickedBlock().getLocation().getBlock().getLocation())) 
		 {
			 return;
		 }
		 
		 Location location = event.getClickedBlock().getLocation();
		 EggRarity rarity = paquesManager.getEggsMap().get(location);
		 
		 int lootRarityCount = 0;
		 for(LootsObject lootObject : paquesManager.getLoots())
		 {
			 if(lootObject.getRarity() == rarity) 
			 {
				 lootRarityCount += 1;
			 }
		 }
		 
		 if(lootRarityCount == 0)
		 {
			 event.getPlayer().sendMessage("§cNo loot has been assigned to this rarity !");
			 return;
		 }
		 
		 //DROP ITEM
		 LootsObject loot = Utils.getRandomLoot(Utils.getRarityLoots(rarity));
		 
		 PlayerTakeEggEvent takeEggEvent = new PlayerTakeEggEvent(event.getPlayer(), location, rarity, loot);
		 takeEggEvent.call();
		 
		 if(takeEggEvent.isCancelled()) {return;}
		 
		 if(loot.getValue() == 0)
		 {
			 Bukkit.dispatchCommand(Bukkit.getConsoleSender(), loot.getCommand().replace("%player%", event.getPlayer().getName()));
			 System.out.println(loot.getCommand().replace("%player%", event.getPlayer().getName()));
		 }
		 else if(loot.getValue() == 1)
		 {
			event.getClickedBlock().getWorld().dropItemNaturally(event.getClickedBlock().getLocation(), loot.getItemStack());
		 }
		 
		 event.getPlayer().sendMessage(paquesManager.getConfigLoader().PLAYER_RECOVER_EGG_MESSAGE.replace("%rarity%", rarity.getRarityName()));
		 event.getClickedBlock().setType(Material.AIR);
		 event.getClickedBlock().getWorld().getBlockAt(event.getClickedBlock().getLocation()).setType(Material.AIR);
		 paquesManager.getEggsMap().remove(location);
		 
		 if(paquesManager.getEggsMap().size() == 0)
		 {
			 paquesManager.stopEggEvent();
		 }
		 
		 for(FastBoard board : paquesManager.getScoreBoards().values())
		 {
			 paquesManager.updateScoreBoard(board);
		 }
	 }		
}
