package by.dragonsurvivalteam.dragonsurvival.util;


import com.google.gson.annotations.SerializedName;
import net.minecraft.client.resources.language.I18n;

public enum DragonLevel{
	@SerializedName(value = "NEWBORN", alternate = "BABY")
	NEWBORN(14, 1.1f, "newborn"),
	YOUNG(20, 1.6f, "young"),
	ADULT(30, 2.1f, "adult");

	public int size;
	public float jumpHeight;
	public String name;

	DragonLevel(int size, float jumpHeight, String name_){
		this.size = size;
		this.jumpHeight = jumpHeight;
		this.name = name_;
	}

	public String getName(){
		return I18n.get("ds.level." + name);
	}
}