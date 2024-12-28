package fr.dylan.paques.utils.event;

import org.bukkit.event.Cancellable;

public class CancelledPaquesEvent extends PaquesEvent implements Cancellable
{
	private boolean cancelled;

	public boolean isCancelled() {return cancelled;}

	public void setCancelled(boolean cancelled) 
	{
		this.cancelled = cancelled;
	}

}
