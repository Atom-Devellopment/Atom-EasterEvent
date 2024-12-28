package fr.dylan.paques.data;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.Getter;

public enum EggRarity 
{
	COMMON("§7§lCOMMON"),
	EPIC("§5§lEPIC"),
	LEGENDARY("§c§lLEGENDARY"),
	NONE("NONE");
	
	@Getter public String rarityName;
	
	EggRarity(String rarityName)
	{
		this.rarityName = rarityName;
	}
	
	private static final Map<String, EggRarity> ENUM_MAP = Stream.of(EggRarity.values())
            .collect(Collectors.toMap(Enum::name, Function.identity()));

    public static EggRarity of(final String name) {
        return ENUM_MAP.getOrDefault(name.toUpperCase(), NONE);
    }
}
