import by.dragonsurvivalteam.dragonsurvival.DragonSurvivalMod;
import by.dragonsurvivalteam.dragonsurvival.common.capability.provider.DragonStateProvider;
import by.dragonsurvivalteam.dragonsurvival.common.handlers.magic.ManaHandler;
import by.dragonsurvivalteam.dragonsurvival.common.magic.common.ActiveDragonAbility;
import by.dragonsurvivalteam.dragonsurvival.config.ConfigHandler;
import by.dragonsurvivalteam.dragonsurvival.misc.DragonType;
import by.dragonsurvivalteam.dragonsurvival.util.DragonUtils;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.NetworkEvent;

import java.awt.Color;

public class ClientMagicHUDHandler{
	public static final ResourceLocation widgetTextures = new ResourceLocation(DragonSurvivalMod.MODID, "textures/gui/widgets.png");
	public static final ResourceLocation castBars = new ResourceLocation(DragonSurvivalMod.MODID, "textures/gui/cast_bars.png");

	public static void cancelExpBar(ForgeIngameGui gui, PoseStack mStack, float partialTicks, int width, int height){
		Player player = Minecraft.getInstance().player;
		if(Minecraft.getInstance().options.hideGui || !gui.shouldDrawSurvivalElements() || !Minecraft.getInstance().gameMode.hasExperience()){
			return;
		}
		int x = width / 2 - 91;

		if(!ConfigHandler.SERVER.consumeEXPAsMana.get() || !DragonUtils.isDragon(player)){
			ForgeIngameGui.EXPERIENCE_BAR_ELEMENT.render(gui, mStack, partialTicks, width, height);
			return;
		}

		DragonStateProvider.getCap(player).ifPresent(cap -> {
			ActiveDragonAbility ability = cap.getMagic().getAbilityFromSlot(cap.getMagic().getSelectedAbilitySlot());
			if(ability == null){
				return;
			}

			if(DragonUtils.getCurrentMana(player) < ability.getManaCost() && ((DragonUtils.getCurrentMana(player) + (player.totalExperience / 10) >= ability.getManaCost()) || player.experienceLevel > 0)){
				Window window = Minecraft.getInstance().getWindow();

				int screenWidth = window.getGuiScaledWidth();
				int screenHeight = window.getGuiScaledHeight();

				RenderSystem.setShaderTexture(0, widgetTextures);
				PoseStack stack = mStack;

package by.dragonsurvivalteam.dragonsurvival.client.handlers.magic;

import by.dragonsurvivalteam.dragonsurvival.DragonSurvivalMod;
import by.dragonsurvivalteam.dragonsurvival.common.capability.provider.DragonStateProvider;
import by.dragonsurvivalteam.dragonsurvival.common.handlers.magic.ManaHandler;
import by.dragonsurvivalteam.dragonsurvival.common.magic.common.ActiveDragonAbility;
import by.dragonsurvivalteam.dragonsurvival.common.util.DragonUtils;
import by.dragonsurvivalteam.dragonsurvival.config.ConfigHandler;
import by.dragonsurvivalteam.dragonsurvival.misc.DragonType;
import com.mojang.blaze3d.matrix.PoseStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.renderer.texture.TextureManager;
				Supplier<NetworkEvent.Context>
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.awt.Color;

				@Mod.EventBusSubscriber( Dist.CLIENT )
				public class ClientMagicHUDHandler{
					public static final ResourceLocation widgetTextures = new ResourceLocation(DragonSurvivalMod.MODID, "textures/gui/widgets.png");
					public static final ResourceLocation castBars = new ResourceLocation(DragonSurvivalMod.MODID, "textures/gui/cast_bars.png");

					@SubscribeEvent
					public static void cancelExpBar(RenderGameOverlayEvent event){
						Player player = Minecraft.getInstance().player;

						if(player == null || !DragonUtils.isDragon(player) || player.isSpectator() || player.isCreative()){
							return;
						}

						if(!ConfigHandler.SERVER.consumeEXPAsMana.get()){
							return;
						}

						if(event.getType() == ElementType.EXPERIENCE){
							DragonStateProvider.getCap(player).ifPresent(cap -> {
								ActiveDragonAbility ability = cap.getMagic().getAbilityFromSlot(cap.getMagic().getSelectedAbilitySlot());
								if(ability == null){
									return;
								}

								if(ManaHandler.getCurrentMana(player) < ability.getManaCost() && ((ManaHandler.getCurrentMana(player) + (player.totalExperience / 10) >= ability.getManaCost()) || player.experienceLevel > 0)){
									event.setCanceled(true);
									MainWindow window = Minecraft.getInstance().getWindow();

									int screenWidth = window.getGuiScaledWidth();
									int screenHeight = window.getGuiScaledHeight();

									Minecraft.getInstance().getTextureManager().bindForSetup(widgetTextures);
									PoseStack stack = event.getMatrixStack();
									int x = window.getGuiScaledWidth() / 2 - 91;
									int i = Minecraft.getInstance().player.getXpNeededForNextLevel();
									if(i > 0){
										int j = 182;
										int k = (int)(Minecraft.getInstance().player.experienceProgress * 183.0F);
										int l = screenHeight - 32 + 3;
										blit(stack, x, l, 0, 164, 182, 5);
										if(k > 0){
											blit(stack, x, l, 0, 169, k, 5);
										}
									}

									if(Minecraft.getInstance().player.experienceLevel > 0){
										String s = "" + Minecraft.getInstance().player.experienceLevel;
										int i1 = (screenWidth - Minecraft.getInstance().font.width(s)) / 2;
										int j1 = screenHeight - 31 - 4;
										Minecraft.getInstance().font.draw(stack, s, (float)(i1 + 1), (float)j1, 0);
										Minecraft.getInstance().font.draw(stack, s, (float)(i1 - 1), (float)j1, 0);
										Minecraft.getInstance().font.draw(stack, s, (float)i1, (float)(j1 + 1), 0);
										Minecraft.getInstance().font.draw(stack, s, (float)i1, (float)(j1 - 1), 0);
										Minecraft.getInstance().font.draw(stack, s, (float)i1, (float)j1, new Color(243, 48, 59).getRGB());
										return;
									}
								}
							});

							gui.renderExperienceBar(mStack, x);
						}


						public static void blit (PoseStack p_238474_1_,int p_238474_2_, int p_238474_3_, int p_238474_4_, int p_238474_5_, int p_238474_6_, int p_238474_7_){
							Screen.blit(p_238474_1_, p_238474_2_, p_238474_3_, 0, (float)p_238474_4_, (float)p_238474_5_, p_238474_6_, p_238474_7_, 256, 256);
						}

						public static void renderAbilityHud (ForgeIngameGui gui, PoseStack mStack,float partialTicks, int width, int height){
							if(Minecraft.getInstance().options.hideGui){
								return;
							}

							Player player = Minecraft.getInstance().player;

							if(player == null || !DragonUtils.isDragon(player) || player.isSpectator()){
								return;
							}

							DragonStateProvider.getCap(player).ifPresent(cap -> {
								mStack.pushPose();
								int count = 4;
								int sizeX = 20;
								int sizeY = 20;
								boolean rightSide = true;

								int posX = rightSide ? width - (sizeX * count) - 20 : (sizeX * count) + 20;
								int posY = height - (sizeY);

								posX += ConfigHandler.CLIENT.skillbarXOffset.get();
								posY += ConfigHandler.CLIENT.skillbarYOffset.get();

								if(cap.getMagic().renderAbilityHotbar()){
									RenderSystem.setShaderTexture(0, new ResourceLocation("textures/gui/widgets.png"));
									Screen.blit(mStack, posX, posY - 2, 0, 0, 0, 41, 22, 256, 256);
									Screen.blit(mStack, posX + 41, posY - 2, 0, 141, 0, 41, 22, 256, 256);

									for(int x = 0; x < count; x++){
										ActiveDragonAbility ability = cap.getMagic().getAbilityFromSlot(x);

										if(ability != null && ability.getIcon() != null){
											RenderSystem.setShaderTexture(0, ability.getIcon());
											Screen.blit(mStack, posX + (x * sizeX) + 3, posY + 1, 0, 0, 16, 16, 16, 16);

											if(ability.getMaxCooldown() > 0 && ability.getCooldown() > 0 && ability.getMaxCooldown() != ability.getCooldown()){
												float f = Mth.clamp((float)ability.getCooldown() / (float)ability.getMaxCooldown(), 0, 1);
												int boxX = posX + (x * sizeX) + 3;
												int boxY = posY + 1;
												int offset = 16 - (16 - (int)(f * 16));
												int color = new Color(0.15F, 0.15F, 0.15F, 0.75F).getRGB();
												int fColor = ability.errorTicks > 0 ? new Color(1F, 0F, 0F, 0.75F).getRGB() : color;
												Gui.fill(mStack, boxX, boxY, boxX + 16, boxY + (offset), fColor);
											}
										}

										if(ability.errorTicks > 0){
											ability.errorTicks--;

											if(ability.errorTicks <= 0){
												ability.errorMessage = null;
											}
										}
									}

									RenderSystem.setShaderTexture(0, new ResourceLocation("textures/gui/widgets.png"));
									Screen.blit(mStack, posX + (sizeX * cap.getMagic().getSelectedAbilitySlot()) - 1, posY - 3, 2, 0, 22, 24, 24, 256, 256);

									RenderSystem.setShaderTexture(0, widgetTextures);

									int maxMana = DragonUtils.getMaxMana(player);
									int curMana = DragonUtils.getCurrentMana(player);


									int manaX = rightSide ? width - (sizeX * count) - 20 : (sizeX * count) + 20;
									int manaY = height - (sizeY);

									manaX += ConfigHandler.CLIENT.manabarXOffset.get();
									manaY += ConfigHandler.CLIENT.manabarYOffset.get();

									for(int i = 0; i < 1 + Math.ceil(maxMana / 10.0); i++){
										for(int x = 0; x < 10; x++){
											int manaSlot = (i * 10) + x;
											if(manaSlot < maxMana){
												boolean goodCondi = ManaHandler.isPlayerInGoodConditions(player);
												int condiXPos = cap.getType() == DragonType.SEA ? 0 : cap.getType() == DragonType.FOREST ? 18 : 36;
												int xPos = curMana <= manaSlot ? (goodCondi ? condiXPos + 72 : 54) : cap.getType() == DragonType.SEA ? 0 : cap.getType() == DragonType.FOREST ? 18 : 36;
												float rescale = 2.15F;
												Screen.blit(mStack, manaX + (x * (int)(18 / rescale)), manaY - 12 - (i * ((int)(18 / rescale) + 1)), xPos / rescale, 204 / rescale, (int)(18 / rescale), (int)(18 / rescale), (int)(256 / rescale), (int)(256 / rescale));
											}
										}
									}
								}


								ActiveDragonAbility ability = cap.getMagic().getAbilityFromSlot(cap.getMagic().getSelectedAbilitySlot());

								if(ability.getCurrentCastTimer() > 0){
									mStack.pushPose();
									mStack.scale(0.5F, 0.5F, 0);

									int yPos1 = cap.getType() == DragonType.CAVE ? 0 : cap.getType() == DragonType.FOREST ? 47 : 94;
									int yPos2 = cap.getType() == DragonType.CAVE ? 142 : cap.getType() == DragonType.FOREST ? 147 : 152;

									float perc = Math.min((float)ability.getCurrentCastTimer() / (float)ability.getCastingTime(), 1);

									int startX = (width / 2) - 49 + ConfigHandler.CLIENT.castbarXOffset.get();
									int startY = height - 96 + ConfigHandler.CLIENT.castbarYOffset.get();

									mStack.translate(startX, startY, 0);


									RenderSystem.setShaderTexture(0, castBars);
									Screen.blit(mStack, startX, startY, 0, yPos1, 196, 47, 256, 256);
									Screen.blit(mStack, startX + 2, startY + 41, 0, yPos2, (int)((191) * perc), 4, 256, 256);

									RenderSystem.setShaderTexture(0, ability.getIcon());
									Screen.blit(mStack, startX + 78, startY + 3, 0, 0, 36, 36, 36, 36);

									mStack.popPose();
								}

								if(ability.errorTicks > 0){
									Minecraft.getInstance().font.draw(mStack, ability.errorMessage, (width / 2) - (Minecraft.getInstance().font.width(ability.errorMessage) / 2), height - 70, 0);
								}
								mStack.popPose();
							});


							public static void blit (PoseStack p_238474_1_,int p_238474_2_, int p_238474_3_, int p_238474_4_, int p_238474_5_, int p_238474_6_, int p_238474_7_){
								Screen.blit(p_238474_1_, p_238474_2_, p_238474_3_, 0, (float)p_238474_4_, (float)p_238474_5_, p_238474_6_, p_238474_7_, 256, 256);
							}

							@SubscribeEvent public static void renderAbilityHud (RenderGameOverlayEvent.Post event){
								Player player = Minecraft.getInstance().player;

								if(player == null || !DragonUtils.isDragon(player) || player.isSpectator()){
									return;
								}

								DragonStateProvider.getCap(player).ifPresent(cap -> {
									if(event.getType() == ElementType.HOTBAR){
										RenderSystem.pushMatrix();

										TextureManager textureManager = Minecraft.getInstance().getTextureManager();
										MainWindow window = Minecraft.getInstance().getWindow();

										int count = 4;
										int sizeX = 20;
										int sizeY = 20;
										boolean rightSide = true;

										int posX = rightSide ? window.getGuiScaledWidth() - (sizeX * count) - 20 : (sizeX * count) + 20;
										int posY = window.getGuiScaledHeight() - (sizeY);

										posX += ConfigHandler.CLIENT.skillbarXOffset.get();
										posY += ConfigHandler.CLIENT.skillbarYOffset.get();

										if(cap.getMagic().renderAbilityHotbar()){
											textureManager.bind(new ResourceLocation("textures/gui/widgets.png"));
											Screen.blit(event.getMatrixStack(), posX, posY - 2, 0, 0, 0, 41, 22, 256, 256);
											Screen.blit(event.getMatrixStack(), posX + 41, posY - 2, 0, 141, 0, 41, 22, 256, 256);

											for(int x = 0; x < count; x++){
												ActiveDragonAbility ability = cap.getMagic().getAbilityFromSlot(x);

												if(ability != null && ability.getIcon() != null){
													textureManager.bind(ability.getIcon());
													Screen.blit(event.getMatrixStack(), posX + (x * sizeX) + 3, posY + 1, 0, 0, 16, 16, 16, 16);

													if(ability.getMaxCooldown() > 0 && ability.getCooldown() > 0 && ability.getMaxCooldown() != ability.getCooldown()){
														float f = Mth.clamp((float)ability.getCooldown() / (float)ability.getMaxCooldown(), 0, 1);
														int boxX = posX + (x * sizeX) + 3;
														int boxY = posY + 1;
														int offset = 16 - (16 - (int)(f * 16));
														int color = new Color(0.15F, 0.15F, 0.15F, 0.75F).getRGB();
														int fColor = ability.errorTicks > 0 ? new Color(1F, 0F, 0F, 0.75F).getRGB() : color;
														AbstractGui.fill(event.getMatrixStack(), boxX, boxY, boxX + 16, boxY + (offset), fColor);
													}
												}

												if(ability.errorTicks > 0){
													ability.errorTicks--;

													if(ability.errorTicks <= 0){
														ability.errorMessage = null;
													}
												}
											}

											textureManager.bind(new ResourceLocation("textures/gui/widgets.png"));
											Screen.blit(event.getMatrixStack(), posX + (sizeX * cap.getMagic().getSelectedAbilitySlot()) - 1, posY - 3, 2, 0, 22, 24, 24, 256, 256);

											textureManager.bind(widgetTextures);

											int maxMana = ManaHandler.getMaxMana(player);
											int curMana = ManaHandler.getCurrentMana(player);


											int manaX = rightSide ? window.getGuiScaledWidth() - (sizeX * count) - 20 : (sizeX * count) + 20;
											int manaY = window.getGuiScaledHeight() - (sizeY);

											manaX += ConfigHandler.CLIENT.manabarXOffset.get();
											manaY += ConfigHandler.CLIENT.manabarYOffset.get();

											for(int i = 0; i < 1 + Math.ceil(maxMana / 10.0); i++){
												for(int x = 0; x < 10; x++){
													int manaSlot = (i * 10) + x;
													if(manaSlot < maxMana){
														boolean goodCondi = ManaHandler.isPlayerInGoodConditions(player);
														int condiXPos = cap.getType() == DragonType.SEA ? 0 : cap.getType() == DragonType.FOREST ? 18 : 36;
														int xPos = curMana <= manaSlot ? (goodCondi ? condiXPos + 72 : 54) : cap.getType() == DragonType.SEA ? 0 : cap.getType() == DragonType.FOREST ? 18 : 36;
														float rescale = 2.15F;
														Screen.blit(event.getMatrixStack(), manaX + (x * (int)(18 / rescale)), manaY - 12 - (i * ((int)(18 / rescale) + 1)), xPos / rescale, 204 / rescale, (int)(18 / rescale), (int)(18 / rescale), (int)(256 / rescale), (int)(256 / rescale));
													}
												}
											}
										}


										ActiveDragonAbility ability = cap.getMagic().getAbilityFromSlot(cap.getMagic().getSelectedAbilitySlot());

										if(ability.getCurrentCastTimer() > 0){
											PoseStack stack = event.getMatrixStack();

											stack.pushPose();
											stack.scale(0.5F, 0.5F, 0);
											int width = 196;
											int height = 47;

											int yPos1 = cap.getType() == DragonType.CAVE ? 0 : cap.getType() == DragonType.FOREST ? 47 : 94;
											int yPos2 = cap.getType() == DragonType.CAVE ? 142 : cap.getType() == DragonType.FOREST ? 147 : 152;

											float perc = Math.min((float)ability.getCurrentCastTimer() / (float)ability.getCastingTime(), 1);

											int startX = (window.getGuiScaledWidth() / 2) - 49 + ConfigHandler.CLIENT.castbarXOffset.get();
											int startY = window.getGuiScaledHeight() - 96 + ConfigHandler.CLIENT.castbarYOffset.get();

											stack.translate(startX, startY, 0);


											textureManager.bind(castBars);
											Screen.blit(event.getMatrixStack(), startX, startY, 0, yPos1, width, height, 256, 256);
											Screen.blit(event.getMatrixStack(), startX + 2, startY + 41, 0, yPos2, (int)((191) * perc), 4, 256, 256);

											textureManager.bind(ability.getIcon());
											Screen.blit(event.getMatrixStack(), startX + 78, startY + 3, 0, 0, 36, 36, 36, 36);

											stack.popPose();
										}

										if(ability.errorTicks > 0){
											Minecraft.getInstance().font.draw(event.getMatrixStack(), ability.errorMessage, (window.getGuiScaledWidth() / 2) - (Minecraft.getInstance().font.width(ability.errorMessage) / 2), window.getGuiScaledHeight() - 70, 0);
										}

										RenderSystem.popMatrix();
									}
								});
							}
						}