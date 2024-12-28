package fr.dylan.paques.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.file.FileConfiguration;

import fr.dylan.paques.Easter;

public class ConfigLoader 
{
	
	//GENERAL SETTINGS
	public static String COMMAND_PERMISSION;
	public static String NO_PERMISSION;
	public static String MESSAGE_PREFIX;
	public static String PLAYER_RECOVER_EGG_MESSAGE;
	public static ArrayList<String> EGG_EVENT_START;
	public static ArrayList<String> EGG_EVENT_STOP;
	
	public static String EVENT_HAS_ALREADY_LAUNCH;
	public static String CONFIG_RELOADED;
	public static String EVENT_IS_ALREADY_STOP;
	public static String EGG_HAS_ALREADY_PLACED_AT_LOCATION;
	public static String EGG_PLACED;
	
	public static int TASK_TIMER_BEFORE_START;
	public static Map<Integer, String> TASK_MESSAGE_BEFORE_START = new HashMap<>();

	//SCOREBOARD SETTINGS

	public static String SCOREBOARD_TITLE;
	public static ArrayList<String> SCOREBOARD_LINES;
	
	//EGG LOOTS SETTINGS
	public static String COMMON_RARITY_TEXURE;
	public static String EPIC_RARITY_TEXURE;
	public static String LEGENDARY_RARITY_TEXURE;

	public static void loadConfig()
	{
		FileConfiguration config = Easter.getINSTANCE().getConfig();
		
		//GENERAL SETTINGS
		COMMAND_PERMISSION = config.getString("COMMAND_PERMISSION");
		NO_PERMISSION = config.getString("NO_PERMISSION").replace("&", "§");
		MESSAGE_PREFIX = config.getString("MESSAGE_PREFIX").replace("&", "§");
		PLAYER_RECOVER_EGG_MESSAGE = config.getString("PLAYER_RECOVER_EGG_MESSAGE").replace("&", "§");
		EGG_EVENT_START = (ArrayList<String>) config.getStringList("EGG_EVENT_START");
		EGG_EVENT_STOP = (ArrayList<String>) config.getStringList("EGG_EVENT_STOP");
		EVENT_HAS_ALREADY_LAUNCH = config.getString("EVENT_HAS_ALREADY_LAUNCH").replace("&", "§");
		CONFIG_RELOADED = config.getString("CONFIG_RELOADED").replace("&", "§");
		EVENT_IS_ALREADY_STOP = config.getString("EVENT_IS_ALREADY_STOP").replace("&", "§");
		EGG_HAS_ALREADY_PLACED_AT_LOCATION = config.getString("EGG_HAS_ALREADY_PLACED_AT_LOCATION").replace("&", "§");
		EGG_PLACED = config.getString("EGG_PLACED").replace("&", "§");
		TASK_TIMER_BEFORE_START = config.getInt("TASK_TIMER_BEFORE_START");
		
		for (String key : config.getConfigurationSection("TASK_MESSAGE_BEFORE_START").getKeys(false)) {
            int time = Integer.parseInt(key);
            String message = config.getString("TASK_MESSAGE_BEFORE_START." + key);
            TASK_MESSAGE_BEFORE_START.put(time, message);
        }
		
		
		//SCOREBOARD SETTINGS
		SCOREBOARD_TITLE = config.getString("SCOREBOARD_TITLE").replace("&", "§");
		SCOREBOARD_LINES = (ArrayList<String>) config.getStringList("SCOREBOARD_LINES");

		//EGG LOOTS SETTINGS
		COMMON_RARITY_TEXURE = config.getString("EGG_RARITY_TEXTURE.COMMON");
		EPIC_RARITY_TEXURE = config.getString("EGG_RARITY_TEXTURE.EPIC");
		LEGENDARY_RARITY_TEXURE = config.getString("EGG_RARITY_TEXTURE.LEGENDARY");

	}

}
