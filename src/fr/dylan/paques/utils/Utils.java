package fr.dylan.paques.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;

import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import fr.dylan.paques.Easter;
import fr.dylan.paques.data.EggRarity;
import fr.dylan.paques.manager.EasterManager;
import fr.dylan.paques.objects.EggObject;
import fr.dylan.paques.objects.LootsObject;
import io.netty.util.internal.ThreadLocalRandom;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.TileEntitySkull;

public class Utils 
{
	private static EasterManager paqueManager = EasterManager.getINSTANCE();
	
	public static void registerCommand(Object obj) 
	{
		Easter.getINSTANCE().commandFramework.registerCommands(obj);
	}
	
	public static void registerListener(Listener listener) 
	{
		PluginManager pluginManager = Bukkit.getPluginManager();
		pluginManager.registerEvents(listener, Easter.getINSTANCE());
	}
	
	public static String getTextureByRarity(EggRarity rarity)
	{
		switch (rarity) 
		{
		case COMMON:
			return paqueManager.getConfigLoader().COMMON_RARITY_TEXURE;
		
		case EPIC:
			return paqueManager.getConfigLoader().EPIC_RARITY_TEXURE;
		
		case LEGENDARY:
			return paqueManager.getConfigLoader().LEGENDARY_RARITY_TEXURE;
		}
		
		return null;
	}
	
	public static ArrayList<LootsObject> getRarityLoots(EggRarity rarity)
	{
		ArrayList<LootsObject> loot = Lists.newArrayList();
		
		paqueManager.getLoots().forEach(loots ->{
			if(loots.getRarity() == rarity)
			{
				loot.add(loots);
			}
		});
		return loot;
	}
	
	public static ArrayList<EggObject> getRarityEgg(EggRarity rarity)
	{
		ArrayList<EggObject> eggs = Lists.newArrayList();
		
		paqueManager.getEggsList().forEach(egg ->{
			if(egg.getEggRarity() == rarity)
			{
				eggs.add(egg);
			}
		});
		return eggs;
	}
	
	public static LootsObject getRandomLoot(List<LootsObject> loots) {
	    if (loots.isEmpty()) {
	        return null;
	    }
	    
	    double totalProbability = loots.stream().mapToDouble(LootsObject::getPourcent).sum();
	    double randomValue = ThreadLocalRandom.current().nextDouble() * totalProbability;
	    double cumulativeProbability = 0.0D;
	    
	    for (LootsObject loot : loots) {
	        cumulativeProbability += loot.getPourcent();
	        if (randomValue <= cumulativeProbability) {
	            return loot;
	        }
	    }
	    
	    return null;
	}


	
	public static boolean isInventoryFull(Player player) {
	    Inventory inventory = player.getInventory();
	    int emptySlots = 0;

	    for (ItemStack itemStack : inventory.getContents()) {
	        if (itemStack == null) {
	            emptySlots++;
	        }
	    }

	    return emptySlots == 0;
	}
	
	public static String arrayToString(String[] array) {
	    StringBuilder sb = new StringBuilder();
	    for (int i = 0; i < array.length; i++) {
	        sb.append(array[i]);
	        if (i < array.length - 1) {
	            sb.append(" "); 
	        }
	    }
	    return sb.toString();
	}
	
	public static void transformList(ArrayList<EggObject> loots)
	{		
		loots.forEach(loot ->{
			paqueManager.getEggsMap().put(loot.getEggLocation(), loot.getEggRarity());
		});
		
	}
	
	public static Block getBlockEgg(Location location, EggRarity rarity)
	{
		String url = Utils.getTextureByRarity(rarity);
		Block block = location.getWorld().getBlockAt(location);
		block.setType(Material.SKULL);
		
		GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        byte[] encodedData = Base64.encodeBase64(String.format("{textures:{SKIN:{url:\"%s\"}}}", "http://textures.minecraft.net/texture/" + url).getBytes());
        profile.getProperties().put("textures", new Property("textures", new String(encodedData)));
       
        TileEntitySkull skullTile = (TileEntitySkull)((CraftWorld)block.getWorld()).getHandle().getTileEntity(new BlockPosition(block.getX(), block.getY(), block.getZ()));
        skullTile.setGameProfile(profile);
		
        block.getState().update(true);
        
        return block;
	}
	
	public static String removeArguments(String command, String... argsToRemove) {
	    String result = command;
	    for (String arg : argsToRemove) {
	        result = result.replaceAll("\\b" + arg + "\\b", "").replaceAll("\\s{2,}", " ").trim();
	    }
	    return result;
	}
	
}
