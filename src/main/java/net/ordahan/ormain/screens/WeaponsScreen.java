package net.ordahan.ormain.screens;

import com.google.common.collect.Multimap;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import net.ordahan.ormain.enums.SortMode;

import java.util.ArrayList;
import java.util.Collection;

import static net.ordahan.ormain.ORMain.MODID;

public class WeaponsScreen extends Screen {
    private static final Component TITLE = Component.translatable("gui." + MODID + ".weapons_screen");

    private static final ResourceLocation TEXTURE = new ResourceLocation(MODID, "textures/gui/gray_background.png");
    ArrayList<Item> items;

    ArrayList<Item> sortedItems = new ArrayList<>();
    SortMode sortMode = SortMode.AttackDamage;

    private final int imageWidth, imageHeight;

    private int leftPos, topPos;

    public WeaponsScreen() {
        super(TITLE);
        this.imageWidth = 250;
        this.imageHeight = 250;
        items = new ArrayList<>(ForgeRegistries.ITEMS.getValues());
        sortItems();
    }

    private void sortItems() {
        this.sortedItems = new ArrayList<>();
        for (int i = 0, j = i + 1; i < items.size() - 1; i++) {
            Item firstItem = items.get(i);
            Item secondItem = items.get(j);
            this.sortedItems.add(firstItem);
            this.sortedItems.add(secondItem);
            switch (sortMode) {
                case AttackDamage -> sortItemsByAttribute(firstItem, secondItem, Attributes.ATTACK_DAMAGE);
                case AttackSpeed -> sortItemsByAttribute(firstItem, secondItem, Attributes.ATTACK_SPEED);
                case Both -> {
                    sortItemsByAttribute(firstItem, secondItem, Attributes.ATTACK_DAMAGE);
                    sortItemsByAttribute(firstItem, secondItem, Attributes.ATTACK_SPEED);
                }
                case DPS -> {

                }
            }
        }
    }

    private void sortItemsByAttribute(Item firstItem, Item secondItem, Attribute attribute) {
        if (calculateAttributeValue(firstItem, attribute) < calculateAttributeValue(secondItem, attribute)) {
            sortedItems.set(sortedItems.indexOf(firstItem), secondItem);
            sortedItems.set(sortedItems.indexOf(secondItem), firstItem);
        }
    }


    private double calculateAttributeValue(Item item, Attribute attribute) {
        Multimap<Attribute, AttributeModifier> itemAttributeModifiers = new ItemStack(item).getAttributeModifiers(EquipmentSlot.MAINHAND);
        return calculateAttributeModifierValue(itemAttributeModifiers.get(attribute));
    }

    private double calculateAttributeModifierValue(Collection<AttributeModifier> modifiers) {
        double value = 1;
        ArrayList<Double> totals = new ArrayList<>();


        for (AttributeModifier modifier : modifiers) {
            double amount = modifier.getAmount();
            AttributeModifier.Operation operation = modifier.getOperation();

            if (operation.equals(AttributeModifier.Operation.MULTIPLY_TOTAL)) totals.add(amount);
            else {
                if (operation.equals(AttributeModifier.Operation.ADDITION)) {
                    value += amount;
                } else {
                    value *= amount;
                }
            }
        }

        for (Double total : totals) {
            value *= total;
        }

        return value;
    }


    @Override
    protected void init() {
        super.init();
        this.leftPos = (this.width - this.imageWidth) / 2;
        this.topPos = (this.height - this.imageHeight) / 2;


        if (this.minecraft == null) return;

        Level level = this.minecraft.level;
        if (level == null) return;
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        renderBackground(poseStack);

        blit(poseStack, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
        super.render(poseStack, mouseX, mouseY, partialTicks);
        this.drawString(poseStack, this.font, TITLE, this.imageWidth / 2, this.topPos, 0xFF0000);
    }

}
