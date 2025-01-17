package by.dragonsurvivalteam.dragonsurvival.magic.abilities.ForestDragon.innate;

import by.dragonsurvivalteam.dragonsurvival.DragonSurvivalMod;
import by.dragonsurvivalteam.dragonsurvival.magic.common.RegisterDragonAbility;
import by.dragonsurvivalteam.dragonsurvival.magic.common.innate.DragonWingAbility;
import by.dragonsurvivalteam.dragonsurvival.util.DragonType;
import net.minecraft.resources.ResourceLocation;

@RegisterDragonAbility
public class ForestWingsAbility extends DragonWingAbility{
	@Override
	public String getName(){
		return "forest_wings";
	}

	@Override
	public DragonType getDragonType(){
		return DragonType.FOREST;
	}

	@Override
	public ResourceLocation[] getSkillTextures(){
		return new ResourceLocation[]{new ResourceLocation(DragonSurvivalMod.MODID, "textures/skills/forest/forest_wings_0.png"),
		                              new ResourceLocation(DragonSurvivalMod.MODID, "textures/skills/forest/forest_wings_1.png")};
	}


	@Override
	public int getSortOrder(){
		return 2;
	}
}