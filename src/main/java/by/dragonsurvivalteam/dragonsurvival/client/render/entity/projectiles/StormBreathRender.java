package by.dragonsurvivalteam.dragonsurvival.client.render.entity.projectiles;

import by.dragonsurvivalteam.dragonsurvival.common.entity.projectiles.StormBreath;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

@OnlyIn( Dist.CLIENT )
public class StormBreathRender extends GeoProjectilesRenderer<StormBreath>{
	public StormBreathRender(EntityRendererManager renderManager, AnimatedGeoModel<StormBreath> modelProvider){
		super(renderManager, modelProvider);
	}
}