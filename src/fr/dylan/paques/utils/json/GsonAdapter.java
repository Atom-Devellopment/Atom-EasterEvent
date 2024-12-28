package fr.dylan.paques.utils.json;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import fr.dylan.paques.manager.EasterManager;

public abstract class GsonAdapter<T> extends TypeAdapter<T> {
	
  private EasterManager plugin;
  
  public GsonAdapter(EasterManager plugin) {
    this.plugin = plugin;
  }
  
  public Gson getGson() {
    return this.plugin.getGson();
  }
}
 