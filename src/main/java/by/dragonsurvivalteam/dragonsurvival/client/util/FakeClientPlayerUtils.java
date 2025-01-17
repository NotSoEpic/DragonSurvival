package by.dragonsurvivalteam.dragonsurvival.client.util;

import by.dragonsurvivalteam.dragonsurvival.common.capability.DragonStateHandler;
import by.dragonsurvivalteam.dragonsurvival.registry.DSEntities;
import by.dragonsurvivalteam.dragonsurvival.common.entity.DragonEntity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.manager.AnimationData;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@OnlyIn( Dist.CLIENT )
@EventBusSubscriber( Dist.CLIENT )
public class FakeClientPlayerUtils{
	private static final ConcurrentHashMap<Integer, FakeClientPlayer> fakePlayers = new ConcurrentHashMap<>();
	private static final ConcurrentHashMap<Integer, DragonEntity> fakeDragons = new ConcurrentHashMap<>();

	public static DragonEntity getFakeDragon(int num, DragonStateHandler handler){
		FakeClientPlayer clientPlayer = getFakePlayer(num, handler);

		fakeDragons.computeIfAbsent(num, (n) -> new DragonEntity(DSEntities.DRAGON, clientPlayer.level){
			@Override
			public void registerControllers(AnimationData animationData){
				animationData.shouldPlayWhilePaused = true;
				animationData.addAnimationController(new AnimationController<DragonEntity>(this, "fake_player_controller", 2, (event) -> {

					if(getPlayer() instanceof FakeClientPlayer){
						AnimationBuilder builder = new AnimationBuilder();

						if(clientPlayer.animationSupplier != null){
							builder.addAnimation(clientPlayer.animationSupplier.get(), true);
						}

						event.getController().setAnimation(builder);
						return PlayState.CONTINUE;
					}else{
						return PlayState.STOP;
					}
				}));
			}

			@Override
			public Player getPlayer(){
				return clientPlayer;
			}
		});

		return fakeDragons.get(num);
	}

	public static FakeClientPlayer getFakePlayer(int num, DragonStateHandler handler){
		fakePlayers.computeIfAbsent(num, FakeClientPlayer::new);
		fakePlayers.get(num).handler = handler;
		fakePlayers.get(num).lastAccessed = System.currentTimeMillis();
		return fakePlayers.get(num);
	}

	@SubscribeEvent
	public static void clientTick(ClientTickEvent event){
		fakePlayers.forEach((i, v) -> {
			if(System.currentTimeMillis() - v.lastAccessed >= TimeUnit.MILLISECONDS.convert(10, TimeUnit.MINUTES)){
				v.remove(RemovalReason.DISCARDED);
				fakeDragons.get(i).remove(RemovalReason.DISCARDED);

				fakeDragons.remove(i);
				fakePlayers.remove(i);
			}
		});
	}
}