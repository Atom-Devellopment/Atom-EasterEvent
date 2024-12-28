package fr.dylan.paques.commands;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import fr.dylan.paques.data.EggRarity;
import fr.dylan.paques.manager.EasterManager;
import fr.dylan.paques.objects.LootsObject;
import fr.dylan.paques.utils.Utils;
import fr.dylan.paques.utils.commands.Command;
import fr.dylan.paques.utils.commands.CommandArgs;

public class EasterCommand 
{
	private EasterManager paquesManager = EasterManager.getINSTANCE();
	
	@Command(name = "easter")
	public void onCommand(CommandArgs args) 
	{
		if(args.isPlayer())
		{
			Player player = args.getPlayer();
			
			if(!player.hasPermission(paquesManager.getConfigLoader().COMMAND_PERMISSION))
			{
				player.sendMessage(paquesManager.getConfigLoader().NO_PERMISSION);
				return;
			}
		}
		
		args.getSender().sendMessage("§6§m---------------------------------§d§lEasterEvent§6§m---------------------------------");
		args.getSender().sendMessage("§6/easter start§7: §eAllows you to §alaunch §ethe §dEaster Event");
		args.getSender().sendMessage("§6/easter now§7: §eAllows you to spawn §dEggs §einstantly");
		args.getSender().sendMessage("§6/easter reload§7: §eAllows ou to §7reload §ethe plugin configuration");
		args.getSender().sendMessage("§6/easter stop§7: §cStop §ethe event and delete any remaining §dEggs");
		args.getSender().sendMessage("");
		args.getSender().sendMessage("§6/easter set <§7§lCOMMON§6/§5§lEPIC§6/§c§lLEGENDARY§6>§7: §eAllows you to place the location of an §dEgg §ewith a chosen §6rarity");
		args.getSender().sendMessage("§6/easter addloot <rarity> <pourcentage> <itemHand/command>");
		args.getSender().sendMessage("§6/easter infos§7: §eOpen an inventory with all the all §5loots §eand §dEgg§e information");
		args.getSender().sendMessage("§6§m------------------------------------------------------------------------");
	}
	
	
	@Command(name = "easter.start")
	public void onStartEvent(CommandArgs args)
	{
		if(args.isPlayer())
		{
			Player player = args.getPlayer();
			if(!player.hasPermission(paquesManager.getConfigLoader().COMMAND_PERMISSION))
			{
				player.sendMessage(paquesManager.getConfigLoader().NO_PERMISSION);
				return;
			}
		}
		
		if(paquesManager.isActive)
		{
			args.getSender().sendMessage(paquesManager.getConfigLoader().MESSAGE_PREFIX+" "+paquesManager.getConfigLoader().EVENT_HAS_ALREADY_LAUNCH);
			return;
		}
		
		paquesManager.startEvent();
	}
	
	@Command(name = "easter.now")
	public void onStartNowEvent(CommandArgs args)
	{
		if(args.isPlayer())
		{
			Player player = args.getPlayer();
			
			if(!player.hasPermission(paquesManager.getConfigLoader().COMMAND_PERMISSION))
			{
				player.sendMessage(paquesManager.getConfigLoader().NO_PERMISSION);
				return;
			}
		}
		
		if(paquesManager.isActive)
		{
			args.getSender().sendMessage(paquesManager.getConfigLoader().MESSAGE_PREFIX+" "+paquesManager.getConfigLoader().EVENT_HAS_ALREADY_LAUNCH);
			return;
		}
		
		paquesManager.startNowEvent();
	}
	
	@Command(name = "easter.reload")
	public void onReloadConfig(CommandArgs args)
	{
		if(args.isPlayer())
		{
			Player player = args.getPlayer();
			
			if(!player.hasPermission(paquesManager.getConfigLoader().COMMAND_PERMISSION))
			{
				player.sendMessage(paquesManager.getConfigLoader().NO_PERMISSION);
				return;
			}
		}
		
		paquesManager.reloadConfig();
		args.getSender().sendMessage(paquesManager.getConfigLoader().MESSAGE_PREFIX+" "+paquesManager.getConfigLoader().CONFIG_RELOADED);
	}
	
	@Command(name = "easter.stop")
	public void onStopEvent(CommandArgs args)
	{
		if(args.isPlayer())
		{
			Player player = args.getPlayer();
			
			if(!player.hasPermission(paquesManager.getConfigLoader().COMMAND_PERMISSION))
			{
				player.sendMessage(paquesManager.getConfigLoader().NO_PERMISSION);
				return;
			}
		}
		
		if(!paquesManager.isActive())
		{
			args.getSender().sendMessage(paquesManager.getConfigLoader().MESSAGE_PREFIX+" "+paquesManager.getConfigLoader().EVENT_IS_ALREADY_STOP);
			return;
		}
		
		paquesManager.stopEggEvent();
	}
	
	@Command(name = "easter.set")
	public void onSetEgg(CommandArgs args)
	{
		if(!args.isPlayer())
		{
			args.getSender().sendMessage("§cOnly one player can execute this command !");
			return;
		}
		
		Player player = args.getPlayer();
		
		if(!player.hasPermission(paquesManager.getConfigLoader().COMMAND_PERMISSION))
		{
			player.sendMessage(paquesManager.getConfigLoader().NO_PERMISSION);
			return;
		}
		
		String rarity = args.getArgs(0);
		System.out.println(rarity);
		
		if(EggRarity.of(rarity) == EggRarity.NONE)
		{
			player.sendMessage("§6/easter set <§7§lCOMMON§6/§5§lEPIC§6/§c§lLEGENDARY§6>");
			return;
		}
		Location loc = player.getLocation();
		paquesManager.setEgg(player, loc.getBlock().getLocation(), EggRarity.of(rarity));
	}
	
	@Command(name = "easter.infos")
	public void onViewInfo(CommandArgs args)
	{
		if(!args.isPlayer())
		{
			args.getSender().sendMessage("§cOnly one player can execute this command !");
			return;
		}
		
		Player player = args.getPlayer();
		
		if(!player.hasPermission(paquesManager.getConfigLoader().COMMAND_PERMISSION))
		{
			player.sendMessage(paquesManager.getConfigLoader().NO_PERMISSION);
			return;
		}
		
		paquesManager.openInformationsInventory(player);
	}
	
	@Command(name = "easter.addloot")
	public void onAddLoot(CommandArgs args)
	{
		if(!args.isPlayer())
		{
			args.getSender().sendMessage("§cOnly one player can execute this command !");
			return;
		}
		
		Player player = args.getPlayer();
		
		
		if(!player.hasPermission(paquesManager.getConfigLoader().COMMAND_PERMISSION))
		{
			player.sendMessage(paquesManager.getConfigLoader().NO_PERMISSION);
			return;
		}
		
		String rarityString = args.getArgs(0);
		
		if(EggRarity.of(rarityString) == EggRarity.NONE)
		{
			player.sendMessage("§6/easter addloot <rarity> <pourcentage> <itemHand/command>");
			return;
		}
		
		EggRarity rarity = EggRarity.of(rarityString);
		int pourcent = Integer.valueOf(args.getArgs(1));
		String command = Utils.removeArguments(Utils.arrayToString(args.getArgs()), args.getArgs(0), args.getArgs(1));

		int value;
		if(args.length() >2)
			value = 0;
		else
			value = 1;
		
		paquesManager.addLoot(player, rarity, pourcent, command, value);
	}
}
