package by.jackraidenph.dragonsurvival.client.skinPartSystem.objects;

import by.jackraidenph.dragonsurvival.common.capability.NBTInterface;
import by.jackraidenph.dragonsurvival.common.capability.subcapabilities.SkinCap;
import net.minecraft.nbt.CompoundNBT;

public class LayerSettings implements NBTInterface
{
	public String selectedSkin = SkinCap.defaultSkinValue;
	
	public float hue = 0f, saturation = 0f, brightness = 1f;
	public boolean modifiedColor = false;
	
	public boolean glowing = false;
	
	public LayerSettings() {}
	public LayerSettings(String selectedSkin)
	{
		this.selectedSkin = selectedSkin;
	}
	
	@Override
	public CompoundNBT writeNBT()
	{
		CompoundNBT nbt = new CompoundNBT();
		nbt.putString("skin", selectedSkin);
		
		nbt.putFloat("hue", hue);
		nbt.putFloat("saturation", saturation);
		nbt.putFloat("brightness", brightness);
		
		nbt.putBoolean("modifiedColor", modifiedColor);
		nbt.putBoolean("glowing", glowing);
		return nbt;
	}
	
	@Override
	public void readNBT(CompoundNBT base)
	{
		selectedSkin = base.getString("skin");
		
		hue = base.getFloat("hue");
		saturation = base.getFloat("saturation");
		brightness = base.getFloat("brightness");
		
		modifiedColor = base.getBoolean("modifiedColor");
		glowing = base.getBoolean("glowing");
	}
}