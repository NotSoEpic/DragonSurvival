package by.dragonsurvivalteam.dragonsurvival.magic.abilities.SeaDragon.innate;


import by.dragonsurvivalteam.dragonsurvival.DragonSurvivalMod;
import by.dragonsurvivalteam.dragonsurvival.magic.common.RegisterDragonAbility;
import by.dragonsurvivalteam.dragonsurvival.magic.common.innate.InnateDragonAbility;
import by.dragonsurvivalteam.dragonsurvival.config.ServerConfig;
import by.dragonsurvivalteam.dragonsurvival.util.DragonType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@RegisterDragonAbility
public class AmphibianAbility extends InnateDragonAbility{
	@Override
	public Component getDescription(){
		return new TranslatableComponent("ds.skill.description." + getName(), ServerConfig.seaDehydrationDamage, 2);
	}

	@Override
	public int getSortOrder(){
		return 4;
	}

	@Override
	public String getName(){
		return "amphibian";
	}

	@Override
	public DragonType getDragonType(){
		return DragonType.SEA;
	}

	@Override
	public ResourceLocation[] getSkillTextures(){
		return new ResourceLocation[]{new ResourceLocation(DragonSurvivalMod.MODID, "textures/skills/sea/amphibian_0.png"),
		                              new ResourceLocation(DragonSurvivalMod.MODID, "textures/skills/sea/amphibian_1.png")};
	}
	@Override
	public int getLevel(){
		return ServerConfig.penalties && ServerConfig.seaTicksWithoutWater != 0.0 ? 1 : 0;
	}

	@Override
	@OnlyIn( Dist.CLIENT )
	public boolean isDisabled(){
		return super.isDisabled() || !ServerConfig.penalties || ServerConfig.seaTicksWithoutWater == 0.0;
	}
}