package classes;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

import java.util.UUID;

public class PlayerStatsChangeAttributeModifier extends AttributeModifier {
    private Provider<Double, LivingEntity> provider;
    private LivingEntity livingEntity;

    public PlayerStatsChangeAttributeModifier(String name, LivingEntity livingEntity, Provider<Double, LivingEntity> provider) {
        super(name, 0, Operation.ADDITION);
        this.livingEntity = livingEntity;
        this.provider = provider;
    }

    public PlayerStatsChangeAttributeModifier(UUID uuid, String name, LivingEntity livingEntity, Provider<Double, LivingEntity> provider) {
        super(uuid, name, 0, Operation.ADDITION);
        this.livingEntity = livingEntity;
        this.provider = provider;
    }

    @Override
    public double getAmount() {
        return provider.provide(livingEntity);
    }
}
