package fr.dylan.paques.inventory;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import fr.dylan.paques.data.EggRarity;
import fr.dylan.paques.inventory.type.InventoryType;
import fr.dylan.paques.utils.Utils;
import fr.dylan.paques.utils.inventory.FastInv;
import fr.dylan.paques.utils.inventory.ItemBuilder;

public class RarityChoiceInventory extends FastInv {

	public RarityChoiceInventory(Player player, InventoryType type) {
		super((9*3), "§dRarity Choice");
		
		setItems(getBorders(), new ItemBuilder(Material.STAINED_GLASS_PANE).name(" ").build());
		
		if(type == InventoryType.EGGS)
		{
			setItem(11, new ItemBuilder(Material.IRON_INGOT)
					.name(EggRarity.COMMON.rarityName)
					.addLore("","§eIl y à acctuellement §6"+ Utils.getRarityEgg(EggRarity.COMMON).size() +"§e Oeuf de rareté "+ EggRarity.COMMON.rarityName,
							"§cClique droit pour voir tous les Oeufs avec une rareté " + EggRarity.COMMON.rarityName).build(),
					e ->{
							new DisplayInfosInventory(player, InventoryType.EGGS, EggRarity.COMMON).open(player);
					});
			setItem(13, new ItemBuilder(Material.GOLD_INGOT)
					.name(EggRarity.EPIC.rarityName)
					.addLore("","§eIl y à acctuellement §6"+ Utils.getRarityEgg(EggRarity.EPIC).size() +"§e Oeuf de rareté "+ EggRarity.EPIC.rarityName,
							"§cClique droit pour voir tous les Oeufs avec une rareté " + EggRarity.EPIC.rarityName).build(),
					e ->{
						new DisplayInfosInventory(player, InventoryType.EGGS, EggRarity.EPIC).open(player);
					});
			setItem(15, new ItemBuilder(Material.DIAMOND)
					.name(EggRarity.LEGENDARY.rarityName)
					.addLore("","§eIl y à acctuellement §6"+ Utils.getRarityEgg(EggRarity.LEGENDARY).size() +"§e Oeuf de rareté "+ EggRarity.LEGENDARY.rarityName,
							"§cClique droit pour voir tous les Oeufs avec une rareté " + EggRarity.LEGENDARY.rarityName).build(),
					e ->{
						new DisplayInfosInventory(player, InventoryType.EGGS, EggRarity.LEGENDARY).open(player);
					});
		}
		else if(type == InventoryType.LOOTS)
		{
			setItem(11, new ItemBuilder(Material.IRON_INGOT)
					.name(EggRarity.COMMON.rarityName)
					.addLore("","§eIl y à acctuellement §6"+ Utils.getRarityLoots(EggRarity.COMMON).size() +"§e Loots de rareté "+ EggRarity.COMMON.rarityName,
							"§cClique droit pour voir tous les Loots avec une rareté " + EggRarity.COMMON.rarityName).build(),
					e ->{
						new DisplayInfosInventory(player, InventoryType.LOOTS, EggRarity.COMMON).open(player);
					});
			setItem(13, new ItemBuilder(Material.GOLD_INGOT)
					.name(EggRarity.EPIC.rarityName)
					.addLore("","§eIl y à acctuellement §6"+ Utils.getRarityLoots(EggRarity.EPIC).size() +"§e Loots de rareté "+ EggRarity.EPIC.rarityName,
							"§cClique droit pour voir tous les Loots avec une rareté " + EggRarity.EPIC.rarityName).build(),
					e ->{
						new DisplayInfosInventory(player, InventoryType.LOOTS, EggRarity.EPIC).open(player);
					});
			setItem(15, new ItemBuilder(Material.DIAMOND)
					.name(EggRarity.LEGENDARY.rarityName)
					.addLore("","§eIl y à acctuellement §6"+ Utils.getRarityLoots(EggRarity.LEGENDARY).size() +"§e Loots de rareté "+ EggRarity.LEGENDARY.rarityName,
							"§cClique droit pour voir tous les Loots avec une rareté " + EggRarity.LEGENDARY.rarityName).build(),
					e ->{
						new DisplayInfosInventory(player, InventoryType.LOOTS, EggRarity.LEGENDARY).open(player);
					});
		}
	}
}
