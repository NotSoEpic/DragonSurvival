package by.dragonsurvivalteam.dragonsurvival.client.gui.widgets.lists;

import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Option;
import net.minecraft.client.gui.components.*;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.util.Mth;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nullable;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class OptionsList extends ContainerObjectSelectionList<OptionListEntry>{
	public static ConcurrentHashMap<Option, String> configMap = new ConcurrentHashMap<>();
	public static CopyOnWriteArrayList<Integer> activeCats = new CopyOnWriteArrayList<>();
	public int listWidth;

	public OptionsList(int listWidth, int height, int top, int bottom){
		super(Minecraft.getInstance(), listWidth, height, top, bottom, Minecraft.getInstance().font.lineHeight * 2 + 8);
		this.listWidth = listWidth;
		this.setRenderBackground(false);
	}


	public CategoryEntry addCategory(String p_214333_1_, CategoryEntry ent, int catNum){
		String name = p_214333_1_.substring(0, 1).toUpperCase(Locale.ROOT) + p_214333_1_.substring(1).replace("_", " ");
		CategoryEntry entry = new CategoryEntry(this, new TextComponent(name), ent, catNum);
		entry.origName = p_214333_1_;
		this.addEntry(entry);
		return entry;
	}

	@Override
	public int addEntry(OptionListEntry p_230513_1_){
		return super.addEntry(p_230513_1_);
	}

	@Override
	protected int getMaxPosition(){
		int size = this.headerHeight;
		for(OptionListEntry ent : children())
			size += ent.getHeight();
		return size;
	}

	@Override
	public void centerScrollOn(OptionListEntry p_230951_1_){
		int num = this.children().indexOf(p_230951_1_);
		int size = 0;
		for(int i = 0; i < num; i++)
			size += getEntry(i).getHeight();

		this.setScrollAmount(size + p_230951_1_.getHeight() / 2 - (this.y1 - this.y0) / 2);
	}

	@Override
	protected void ensureVisible(OptionListEntry p_230954_1_){
		int i = this.getRowTop(this.children().indexOf(p_230954_1_));
		int j = i - this.y0 - 4 - p_230954_1_.getHeight();
		if(j < 0)
			this.scroll(j);

		int k = this.y1 - i - p_230954_1_.getHeight() - p_230954_1_.getHeight();
		if(k < 0)
			this.scroll(-k);
	}

	public void scroll(int p_230937_1_){
		this.setScrollAmount(this.getScrollAmount() + (double)p_230937_1_);
	}

	@Override
	protected int getRowTop(int p_230962_1_){
		int height = 0;
		for(int i = 0; i < p_230962_1_; i++){
			OptionListEntry e = this.getEntry(i);
			height += e.getHeight();
		}
		return this.y0 + 4 - (int)this.getScrollAmount() + height - 4;
	}

	@Override
	public boolean removeEntry(OptionListEntry p_230956_1_){
		return super.removeEntry(p_230956_1_);
	}

	public void add(Option[] p_214335_1_, CategoryEntry entry){
		for(int i = 0; i < p_214335_1_.length; i++)
			add(p_214335_1_[i], entry);
	}

	public void add(Option option, CategoryEntry entry){
		AbstractWidget widget = option.createButton(this.minecraft.options, getScrollbarPosition() - 165, 0, 140);
		this.addEntry(new OptionEntry(ImmutableMap.of(option, widget), option, option.getCaption(), widget, entry));
	}

	@Override
	public int getScrollbarPosition(){
		return Minecraft.getInstance().screen.width - 32;
	}

	@Override
	public boolean mouseClicked(double p_231044_1_, double p_231044_3_, int p_231044_5_){
		this.updateScrollingState(p_231044_1_, p_231044_3_, p_231044_5_);
		if(!this.isMouseOver(p_231044_1_, p_231044_3_))
			return false;
		else{
			OptionListEntry e = this.getEntryAtPos(p_231044_1_, p_231044_3_);
			if(e != null){
				if(e.mouseClicked(p_231044_1_, p_231044_3_, p_231044_5_)){
					this.setFocused(e);
					this.setDragging(true);
					return true;
				}
			}else if(p_231044_5_ == 0){
				this.clickedHeader((int)(p_231044_1_ - (double)(this.x0 + this.width / 2 - this.getRowWidth() / 2)), (int)(p_231044_3_ - (double)this.y0) + (int)this.getScrollAmount() - 4);
				return true;
			}

			return this.scrolling;
		}
	}

	public OptionListEntry getEntryAtPos(double p_230933_1_, double p_230933_3_){
		int i = this.getRowWidth() / 2;
		int j = this.x0 + this.width / 2;
		int k = j - i;
		int l = j + i;
		int i1 = Mth.floor(p_230933_3_ - (double)this.y0) - this.headerHeight + (int)this.getScrollAmount() - 4;
		int curSize = 0;
		int j1 = 0;
		for(int g = 0; g < children().size(); g++){
			curSize += getEntry(g).getHeight();
			if(curSize >= i1){
				j1 = g;
				break;
			}
		}

		return p_230933_1_ < (double)this.getScrollbarPosition() && p_230933_1_ >= (double)k && p_230933_1_ <= (double)l && j1 >= 0 && i1 >= 0 && j1 < this.getItemCount() ? this.children().get(j1) : null;
	}

	@Override
	public int getRowWidth(){
		return listWidth;
	}

	@Override
	protected void renderList(PoseStack p_238478_1_, int p_238478_2_, int p_238478_3_, int p_238478_4_, int p_238478_5_, float p_238478_6_){
		int i = this.getItemCount();

		for(int j = 0; j < i; ++j){
			int k = this.getRowTop(j);
			int l = this.getRowBottom(j);
			OptionListEntry e = this.getEntry(j);
			e.visible = l >= this.y0 + 16 && k <= this.y1 - 16;

			if(l >= this.y0 && k <= this.y1){
				int j1 = e.getHeight();
				int k1 = this.getRowWidth();
				int j2 = this.getRowLeft();
				boolean mouseOver = this.isMouseOver(p_238478_4_, p_238478_5_) && Objects.equals(this.getEntryAtPos(p_238478_4_, p_238478_5_), e);
				e.render(p_238478_1_, j, k, j2, k1, j1, p_238478_4_, p_238478_5_, mouseOver, p_238478_6_);
			}
		}
	}

	public int getRowBottom(int p_230948_1_){
		OptionListEntry e = this.getEntry(p_230948_1_);
		return this.getRowTop(p_230948_1_) + e.getHeight();
	}

	@Nullable
	public CategoryEntry findCategory(String text, String lastKey){
		for(OptionListEntry optionsrowlist$row : this.children())
			if(optionsrowlist$row instanceof CategoryEntry){
				CategoryEntry cat = (CategoryEntry)optionsrowlist$row;

				if(cat.parent == null || cat.parent.origName.equals(lastKey)){
					if(cat.origName.equals(text)){
						return cat;
					}
				}
			}

		return null;
	}

	@Nullable
	public Widget findWidget(String text){
		for(OptionListEntry optionsrowlist$row : this.children())
			for(GuiEventListener widget : optionsrowlist$row.children()){
				if(widget instanceof Widget){
					if(widget instanceof CycleButton && ((CycleButton)widget).getMessage().getString().equals(text)){
						return (Widget)widget;
					}else if(widget instanceof SliderButton && ((SliderButton)widget).getMessage().getString().equals(text)){
						return (Widget)widget;
					}
				}
			}

		return null;
	}

	@Nullable
	public OptionListEntry findEntry(String text){
		for(OptionListEntry optionsrowlist$row : this.children())
			for(GuiEventListener widget : optionsrowlist$row.children()){
				if(widget instanceof Widget){
					if(widget instanceof CycleButton && ((CycleButton)widget).getMessage().getString().equals(text)){
						return optionsrowlist$row;
					}else if(widget instanceof SliderButton && ((SliderButton)widget).getMessage().getString().equals(text)){
						return optionsrowlist$row;
					}
				}
			}

		return null;
	}

	public OptionEntry findClosest(String text){
		OptionEntry closest = null;
		int dif = -1;

		for(OptionListEntry row : this.children())
			if(row instanceof OptionEntry ent){
				String difText = StringUtils.difference(ent.key.getString().toLowerCase(Locale.ROOT).replace(" ", ""), text.toLowerCase(Locale.ROOT).replace(" ", ""));
				if(difText.length() >= ent.key.getString().length()) continue;

				if(dif == -1 || difText.length() < dif){
					closest = ent;
					dif = difText.length();
				}
			}

		return closest;
	}

	@Nullable
	public AbstractWidget findOption(Option pOption){
		for(OptionListEntry optionslist$entry : this.children())
			if(optionslist$entry instanceof OptionEntry){
				AbstractWidget abstractwidget = ((OptionEntry)optionslist$entry).options.get(pOption);
				if(abstractwidget != null){
					return abstractwidget;
				}
			}

		return null;
	}

	public Optional<AbstractWidget> getMouseOver(double p_238518_1_, double p_238518_3_){
		for(OptionListEntry optionsrowlist$row : this.children())
			for(AbstractWidget widget : optionsrowlist$row.children){
				if(widget.isMouseOver(p_238518_1_, p_238518_3_)){
					return Optional.of(widget);
				}
			}

		return Optional.empty();
	}
}