package by.dragonsurvivalteam.dragonsurvival.magic.abilities.CaveDragon.passive;

import by.dragonsurvivalteam.dragonsurvival.DragonSurvivalMod;
import by.dragonsurvivalteam.dragonsurvival.magic.common.passive.AthleticsAbility;
import by.dragonsurvivalteam.dragonsurvival.magic.common.RegisterDragonAbility;
import by.dragonsurvivalteam.dragonsurvival.config.obj.ConfigOption;
import by.dragonsurvivalteam.dragonsurvival.config.obj.ConfigSide;
import by.dragonsurvivalteam.dragonsurvival.util.DragonType;
import net.minecraft.resources.ResourceLocation;

@RegisterDragonAbility
public class CaveAthleticsAbility extends AthleticsAbility{
	@ConfigOption( side = ConfigSide.SERVER, category = {"magic", "abilities", "cave_dragon", "passives"}, key = "caveAthletics", comment = "Whether the cave athletics ability should be enabled" )
	public static Boolean caveAthletics = true;

	@Override
	public int getSortOrder(){
		return 2;
	}

	@Override
	public String getName(){
		return "cave_athletics";
	}

	@Override
	public DragonType getDragonType(){
		return DragonType.CAVE;
	}

	@Override
	public ResourceLocation[] getSkillTextures(){
		return new ResourceLocation[]{new ResourceLocation(DragonSurvivalMod.MODID, "textures/skills/cave/cave_athletics_0.png"),
		                              new ResourceLocation(DragonSurvivalMod.MODID, "textures/skills/cave/cave_athletics_1.png"),
		                              new ResourceLocation(DragonSurvivalMod.MODID, "textures/skills/cave/cave_athletics_2.png"),
		                              new ResourceLocation(DragonSurvivalMod.MODID, "textures/skills/cave/cave_athletics_3.png"),
		                              new ResourceLocation(DragonSurvivalMod.MODID, "textures/skills/cave/cave_athletics_4.png"),
		                              new ResourceLocation(DragonSurvivalMod.MODID, "textures/skills/cave/cave_athletics_5.png")};
	}

	@Override
	public int getMaxLevel(){
		return 5;
	}

	@Override
	public int getMinLevel(){
		return 0;
	}

	@Override
	public boolean isDisabled(){
		return super.isDisabled() || !caveAthletics;
	}
}