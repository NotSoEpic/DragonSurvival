package by.dragonsurvivalteam.dragonsurvival.client.gui.widgets.buttons;

import by.dragonsurvivalteam.dragonsurvival.client.gui.AbilityScreen;
import by.dragonsurvivalteam.dragonsurvival.common.capability.provider.DragonStateProvider;
import by.dragonsurvivalteam.dragonsurvival.common.magic.DragonAbilities;
import by.dragonsurvivalteam.dragonsurvival.common.magic.common.DragonAbility;
import by.dragonsurvivalteam.dragonsurvival.common.magic.common.PassiveDragonAbility;
import by.dragonsurvivalteam.dragonsurvival.misc.DragonType;
import by.dragonsurvivalteam.dragonsurvival.network.NetworkHandler;
import by.dragonsurvivalteam.dragonsurvival.network.magic.ChangeSkillLevel;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.TextComponent;
 
import net.minecraft.util.text.TextFormatting;
 

import java.util.ArrayList;
import java.util.Arrays;


public class IncreaseLevelButton extends ArrowButton{
	private final int slot;
	private final AbilityScreen screen;
	public int skillCost = 0;
	private DragonAbility ability;
	private DragonType type;

	public IncreaseLevelButton(int x, int y, int slot, AbilityScreen screen){

		super(x, y, 15, 17, true, (button) -> {});
		this.slot = slot;
		this.screen = screen;

		DragonStateProvider.getCap(Minecraft.getInstance().player).ifPresent(cap -> {
			type = cap.getType();
		});
	}


	@Override
	public void onPress(){

		super.onPress();

		ability = DragonAbilities.PASSIVE_ABILITIES.get(type).get(slot);

		DragonStateProvider.getCap(Minecraft.getInstance().player).ifPresent(cap -> {
			if(ability != null){
				if(cap.getMagic().getAbilityLevel(ability) + 1 <= ability.getMaxLevel()){

					if(ability != null){
						PassiveDragonAbility currentAbility = (PassiveDragonAbility)cap.getMagic().getAbility(ability);

						if(ability != null && currentAbility == null){
							currentAbility = (PassiveDragonAbility)ability;
						}

						if(currentAbility != null && currentAbility.getLevel() < currentAbility.getMaxLevel()){
							PassiveDragonAbility newActivty = currentAbility.createInstance();
							newActivty.setLevel(currentAbility.getLevel() + 1);

							if(Minecraft.getInstance().player.experienceLevel >= newActivty.getLevelCost() || Minecraft.getInstance().player.isCreative()){
								NetworkHandler.CHANNEL.sendToServer(new ChangeSkillLevel(cap.getMagic().getAbilityLevel(ability) + 1, ability.getId(), 1));
							}
						}else{
							skillCost = -1;
						}
					}
				}
			}
		});
	}

	@Override

	public void renderToolTip(MatrixStack stack, int mouseX, int mouseY){
		DragonStateProvider.getCap(Minecraft.getInstance().player).ifPresent(cap -> {
			ability = DragonAbilities.PASSIVE_ABILITIES.get(type).get(slot);
			TextFormatting format = cap.getType() == DragonType.CAVE ? TextFormatting.DARK_RED : cap.getType() == DragonType.SEA ? TextFormatting.AQUA : cap.getType() == DragonType.FOREST ? TextFormatting.GREEN : TextFormatting.WHITE;
			ArrayList<Component> description = new ArrayList<>(Arrays.asList(new TranslatableComponent("ds.skill.level.up", skillCost).withStyle(format)));

			if(ability != null){

				PassiveDragonAbility currentAbility = (PassiveDragonAbility)cap.getMagic().getAbility(ability);

				if(ability != null && currentAbility == null){
					currentAbility = (PassiveDragonAbility)ability;
				}

				if(ability.getLevelUpInfo().size() > 0){
					description.add(new TextComponent(""));
					description.addAll(ability.getLevelUpInfo());
				}


				if(currentAbility != null && currentAbility.getLevel() < currentAbility.getMaxLevel()){
					PassiveDragonAbility newActivty = currentAbility.createInstance();
					newActivty.setLevel(currentAbility.getLevel() + 1);
					skillCost = newActivty.getLevelCost();
					screen.renderComponentTooltip(stack, description, mouseX, mouseY);
				}
			}
		});
	}
}