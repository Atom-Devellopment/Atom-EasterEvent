package fr.dylan.paques.utils.event;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import lombok.Getter;

public class PaquesEvent extends Event
{
	private final static HandlerList handlers = new HandlerList();

	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
	
	public void call(){
		Bukkit.getPluginManager().callEvent(this);
	}
}
