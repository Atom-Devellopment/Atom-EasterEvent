package fr.dylan.paques.manager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

import fr.dylan.paques.Easter;
import fr.dylan.paques.objects.EggObject;
import fr.dylan.paques.objects.LootsObject;
import fr.dylan.paques.utils.json.DiscUtil;
import fr.dylan.paques.utils.json.EnumTypeAdapter;
import fr.dylan.paques.utils.json.adaptater.ItemStackAdapter;
import fr.dylan.paques.utils.json.adaptater.LocationAdapter;
import fr.dylan.paques.utils.json.adaptater.PotionEffectAdapter;
import lombok.Getter;

@Getter
public class SaverManager 
{
	@Getter public static SaverManager INSTANCE;
	private EasterManager manager = EasterManager.getINSTANCE();
	
	public SaverManager()
	{
		INSTANCE = this;
		
		if(!Easter.getINSTANCE().getDataFolder().exists())
			Easter.getINSTANCE().getDataFolder().mkdir();
			
		File directory = getFolder();
		if (!directory.exists()) directory.mkdir(); 
	}
	
	public File getFolder() {
		return new File(Easter.getINSTANCE().getDataFolder(), "/easterData");
	}
	
	public File getEggFile()
	{
		return new File(Easter.getINSTANCE().getDataFolder(), "/easterData/eggsData.json");
	}
	
	public File getLootFile()
	{
		return new File(Easter.getINSTANCE().getDataFolder(), "/easterData/lootsData.json");
	}
	
	public void loadEggData() {
		String content = DiscUtil.readCatch(this.getEggFile());
		if (content != null) {
			try {
				ArrayList<EggObject> eggData = manager.getGson().fromJson(content, new TypeToken<List<EggObject>>() {}.getType());
				manager.eggsList = eggData;
			} catch (Exception exception) {
				exception.printStackTrace();
			}
		}
	}

	public void saveLootData() {
		DiscUtil.writeCatch(Easter.getINSTANCE(),this.getEggFile(), manager.getGson().toJson(manager.eggsList), false);
	}
	
	public void loadLootData() {
		String content = DiscUtil.readCatch(this.getLootFile());
		if (content != null) {
			try {
				ArrayList<LootsObject> lootData = manager.getGson().fromJson(content, new TypeToken<List<LootsObject>>() {}.getType());
				manager.loots = lootData;
			} catch (Exception exception) {
				exception.printStackTrace();
			}
		}
	}

	public void saveEggData() {
		DiscUtil.writeCatch(Easter.getINSTANCE(),this.getLootFile(), manager.getGson().toJson(manager.loots), false);
	}
	
	public static GsonBuilder getGsonBuilder() {
	    return (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping()
	      .excludeFieldsWithModifiers(new int[] { 128, 64 }).registerTypeAdapterFactory((TypeAdapterFactory) EnumTypeAdapter.ENUM_FACTORY)
	      .registerTypeHierarchyAdapter(ItemStack.class, new ItemStackAdapter(EasterManager.getINSTANCE()))
	      
	      .registerTypeAdapter(PotionEffect.class, new PotionEffectAdapter(EasterManager.getINSTANCE()))
	      .registerTypeAdapter(Location.class, new LocationAdapter(EasterManager.getINSTANCE()));
	  }
}
