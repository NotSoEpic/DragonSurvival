package by.dragonsurvivalteam.dragonsurvival.client.render.blocks;

import by.dragonsurvivalteam.dragonsurvival.client.particles.DSParticles;
import by.dragonsurvivalteam.dragonsurvival.common.blocks.DSBlocks;
import by.dragonsurvivalteam.dragonsurvival.common.items.DSItems;
import by.dragonsurvivalteam.dragonsurvival.server.tileentity.DragonBeaconBlockEntity;
import com.mojang.blaze3d.matrix.PoseStack;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.BlockEntityRenderer;
import net.minecraft.client.renderer.tileentity.BlockEntityRendererDispatcher;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.vector.Vector3f;

import java.util.Random;

public class DragonBeaconRenderer extends BlockEntityRenderer<DragonBeaconBlockEntity>{
	public DragonBeaconRenderer(BlockEntityRendererDispatcher p_i226006_1_){
		super(p_i226006_1_);
	}

	@Override
	public void render(DragonBeaconBlockEntity dragonBeacon, float v, PoseStack matrixStack, MultiBufferSource iRenderTypeBuffer, int light, int overlay){
		matrixStack.pushPose();
		DragonBeaconBlockEntity.Type type = dragonBeacon.type;
		Item item = DSBlocks.dragonBeacon.asItem();
		ClientLevel clientLevel = (ClientLevel)dragonBeacon.getLevel();
		if(dragonBeacon.getLevel().getBlockState(dragonBeacon.getBlockPos().below()).is(DSBlocks.dragonMemoryBlock)){
			Minecraft minecraft = Minecraft.getInstance();
			Random random = clientWorld.random;
			double x = 0.25 + random.nextInt(5) / 10d;
			double z = 0.25 + random.nextInt(5) / 10d;
			switch(type){
				case PEACE:
					item = DSItems.passivePeaceBeacon;
					try{
						if(!minecraft.isPaused() && dragonBeacon.tick % 5 == 0){
							clientWorld.addParticle(DSParticles.peaceBeaconParticle.getDeserializer().fromCommand(DSParticles.peaceBeaconParticle, new StringReader("")), dragonBeacon.getX() + x, dragonBeacon.getY() + 0.5, dragonBeacon.getZ() + z, 0, 0, 0);
						}
					}catch(CommandSyntaxException e){
						e.printStackTrace();
					}
					break;
				case MAGIC:
					item = DSItems.passiveMagicBeacon;
					try{
						if(!minecraft.isPaused() && dragonBeacon.tick % 5 == 0){
							clientWorld.addParticle(DSParticles.magicBeaconParticle.getDeserializer().fromCommand(DSParticles.magicBeaconParticle, new StringReader("")), dragonBeacon.getX() + x, dragonBeacon.getY() + 0.5, dragonBeacon.getZ() + z, 0, 0, 0);
						}
					}catch(CommandSyntaxException e){
						e.printStackTrace();
					}
					break;
				case FIRE:
					item = DSItems.passiveFireBeacon;
					try{
						if(!minecraft.isPaused() && dragonBeacon.tick % 5 == 0){
							clientWorld.addParticle(DSParticles.fireBeaconParticle.getDeserializer().fromCommand(DSParticles.fireBeaconParticle, new StringReader("")), dragonBeacon.getX() + x, dragonBeacon.getY() + 0.5, dragonBeacon.getZ() + z, 0, 0, 0);
						}
					}catch(CommandSyntaxException e){
						e.printStackTrace();
					}
					break;
			}
		}else{
			switch(type){
				case PEACE:
					item = DSBlocks.peaceDragonBeacon.asItem();
					break;
				case MAGIC:
					item = DSBlocks.magicDragonBeacon.asItem();
					break;
				case FIRE:
					item = DSBlocks.fireDragonBeacon.asItem();
					break;
			}
		}
		matrixStack.translate(0.5, 0.25, 0.5);
		matrixStack.mulPose(Vector3f.YP.rotationDegrees(dragonBeacon.tick));
		matrixStack.scale(2, 2, 2);
		Minecraft.getInstance().getItemRenderer().renderStatic(new ItemStack(item), ItemCameraTransforms.TransformType.GROUND, light, overlay, matrixStack, iRenderTypeBuffer);
		matrixStack.popPose();
	}
}