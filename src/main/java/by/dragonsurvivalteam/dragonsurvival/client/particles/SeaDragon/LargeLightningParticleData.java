package by.dragonsurvivalteam.dragonsurvival.client.particles.SeaDragon;

import by.dragonsurvivalteam.dragonsurvival.client.particles.DSParticles;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Locale;

public class LargeLightningParticleData implements ParticleOptions{
	public static final Deserializer<LargeLightningParticleData> DESERIALIZER = new Deserializer<LargeLightningParticleData>(){
		public LargeLightningParticleData fromCommand(ParticleType<LargeLightningParticleData> particleTypeIn, StringReader reader) throws CommandSyntaxException{
			reader.expect(' ');
			float duration = (float)reader.readDouble();
			reader.expect(' ');
			boolean swirls = reader.readBoolean();
			return new LargeLightningParticleData(duration, swirls);
		}

		public LargeLightningParticleData fromNetwork(ParticleType<LargeLightningParticleData> particleTypeIn, FriendlyByteBuf buffer){
			return new LargeLightningParticleData(buffer.readFloat(), buffer.readBoolean());
		}
	};

	private final float duration;
	private final boolean swirls;

	public static Codec<LargeLightningParticleData> CODEC(ParticleType<LargeLightningParticleData> particleType){
		return RecordCodecBuilder.create((codecBuilder) -> codecBuilder.group(Codec.FLOAT.fieldOf("duration").forGetter(LargeLightningParticleData::getDuration), Codec.BOOL.fieldOf("swirls").forGetter(LargeLightningParticleData::getSwirls)).apply(codecBuilder, LargeLightningParticleData::new));
	}

	public LargeLightningParticleData(float duration, boolean spins){
		this.duration = duration;
		this.swirls = spins;
	}

	@OnlyIn( Dist.CLIENT )
	public float getDuration(){
		return this.duration;
	}

	@OnlyIn( Dist.CLIENT )
	public boolean getSwirls(){
		return this.swirls;
	}	@Override
	public void writeToNetwork(FriendlyByteBuf buffer){
		buffer.writeFloat(this.duration);
		buffer.writeBoolean(this.swirls);
	}



	@SuppressWarnings( "deprecation" )
	@Override
	public String writeToString(){
		return String.format(Locale.ROOT, "%s %.2f %b", Registry.PARTICLE_TYPE.getKey(this.getType()), this.duration, this.swirls);
	}


	@Override
	public ParticleType<LargeLightningParticleData> getType(){
		return DSParticles.LARGE_LIGHTNING.get();
	}
}