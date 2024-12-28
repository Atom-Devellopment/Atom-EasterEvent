package fr.dylan.paques;

import org.bukkit.plugin.java.JavaPlugin;

import fr.dylan.paques.config.ConfigLoader;
import fr.dylan.paques.manager.EasterManager;
import fr.dylan.paques.manager.SaverManager;
import fr.dylan.paques.utils.commands.CommandFramework;
import fr.dylan.paques.utils.inventory.FastInvManager;
import lombok.Getter;

@Getter
public class Easter extends JavaPlugin
{
	@Getter public static Easter INSTANCE;
	public CommandFramework commandFramework;
		
	
	@Override
	public void onEnable() 
	{
		INSTANCE = this;
		
		this.saveDefaultConfig();
		
		commandFramework = new CommandFramework(this);
		FastInvManager.register(this);
		
		new EasterManager();
		SaverManager.getINSTANCE().loadEggData();
		SaverManager.getINSTANCE().loadLootData();

		getLogger().info("---------------------------------------");
		getLogger().info("EasterEvent: ON");
		getLogger().info("---------------------------------------");		
	}
	
	@Override
	public void onDisable()
	{
		SaverManager.getINSTANCE().saveEggData();
		SaverManager.getINSTANCE().saveLootData();
		
		getLogger().info("---------------------------------------");
		getLogger().info("EasterEvent: OFF");
		getLogger().info("---------------------------------------");		
	}
}
