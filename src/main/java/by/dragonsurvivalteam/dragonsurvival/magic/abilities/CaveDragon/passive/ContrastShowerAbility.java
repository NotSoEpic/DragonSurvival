package by.dragonsurvivalteam.dragonsurvival.magic.abilities.CaveDragon.passive;


import by.dragonsurvivalteam.dragonsurvival.DragonSurvivalMod;
import by.dragonsurvivalteam.dragonsurvival.magic.common.RegisterDragonAbility;
import by.dragonsurvivalteam.dragonsurvival.magic.common.passive.PassiveDragonAbility;
import by.dragonsurvivalteam.dragonsurvival.config.obj.ConfigOption;
import by.dragonsurvivalteam.dragonsurvival.config.obj.ConfigSide;
import by.dragonsurvivalteam.dragonsurvival.util.DragonType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;

@RegisterDragonAbility
public class ContrastShowerAbility extends PassiveDragonAbility{
	@ConfigOption( side = ConfigSide.SERVER, category = {"magic", "abilities", "cave_dragon", "passives"}, key = "contrastShower", comment = "Whether the contrast shower ability should be enabled" )
	public static Boolean contrastShower = true;

	@Override
	public int getSortOrder(){
		return 3;
	}

	@Override
	public Component getDescription(){
		return new TranslatableComponent("ds.skill.description." + getName(), getDuration());
	}

	@Override
	public String getName(){
		return "contrast_shower";
	}

	@Override
	public DragonType getDragonType(){
		return DragonType.CAVE;
	}

	@Override
	public ResourceLocation[] getSkillTextures(){
		return new ResourceLocation[]{new ResourceLocation(DragonSurvivalMod.MODID, "textures/skills/cave/contrast_shower_0.png"),
		                              new ResourceLocation(DragonSurvivalMod.MODID, "textures/skills/cave/contrast_shower_1.png"),
		                              new ResourceLocation(DragonSurvivalMod.MODID, "textures/skills/cave/contrast_shower_2.png"),
		                              new ResourceLocation(DragonSurvivalMod.MODID, "textures/skills/cave/contrast_shower_3.png"),
		                              new ResourceLocation(DragonSurvivalMod.MODID, "textures/skills/cave/contrast_shower_4.png"),
		                              new ResourceLocation(DragonSurvivalMod.MODID, "textures/skills/cave/contrast_shower_5.png")};
	}


	public int getDuration(){
		return 30 * getLevel();
	}

	@OnlyIn( Dist.CLIENT )
	public ArrayList<Component> getLevelUpInfo(){
		ArrayList<Component> list = super.getLevelUpInfo();
		list.add(new TranslatableComponent("ds.skill.duration.seconds", "+30"));
		return list;
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
		return super.isDisabled() || !contrastShower;
	}
}