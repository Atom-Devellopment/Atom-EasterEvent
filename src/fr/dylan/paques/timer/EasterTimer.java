package fr.dylan.paques.timer;

import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import fr.dylan.paques.manager.EasterManager;


public class EasterTimer extends BukkitRunnable{
	
	private EasterManager paquesManager = EasterManager.getINSTANCE();
	
	private int taskTimer = paquesManager.getConfigLoader().TASK_TIMER_BEFORE_START;

	@Override
	 public void run() {
				
		if(taskTimer <= 0)
		{
			paquesManager.startNowEvent();
			this.cancel();
			return;
		}
		
		Map<Integer, String> message = paquesManager.configLoader.TASK_MESSAGE_BEFORE_START;
		
		if(message.containsKey(taskTimer)) 
		{
			Bukkit.broadcastMessage(paquesManager.configLoader.MESSAGE_PREFIX+" "+message.get(taskTimer).replace("&", "§"));
		}
		
		taskTimer--;
      }
}