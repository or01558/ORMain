package classes;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.ordahan.ormain.utils.Providers;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import static net.ordahan.ormain.ORMain.LOGGER;

public class PlayerStats {
    private static final Map<String, Attribute> attributesMap = new HashMap<>(
            Map.of(
                    "MAX_HEALTH", Attributes.MAX_HEALTH,
                    "ATTACK_DAMAGE", Attributes.ATTACK_DAMAGE,
                    "ATTACK_KNOCKBACK", Attributes.ATTACK_KNOCKBACK,
                    "ATTACK_SPEED", Attributes.ATTACK_SPEED,
                    "MOVEMENT_SPEED", Attributes.MOVEMENT_SPEED,
                    "LUCK", Attributes.LUCK));
    private int LEVEL = 0;
    private Map<String, Double> statsValues = new HashMap<>();

    public PlayerStats() {
    }
    public double getStat(String name) {
        return this.statsValues.getOrDefault(name, 0.0);
    }

    public static void addModifiers(Player player) {
           attributesMap.forEach((statName, attribute) -> {
               AttributeInstance attributeInstance = player.getAttribute(attribute);
               PlayerStatsChangeAttributeModifier attributeModifier = new PlayerStatsChangeAttributeModifier(UUID.randomUUID(), "playerstats", player, Providers.makeProvider(statName));
               attributeInstance.addTransientModifier(attributeModifier);
           });
    }

    public boolean isEnoughForNextLevel(float count) {

        return false;
    }
    public void setMAX_HEALTH(double MAX_HEALTH) {
        this.statsValues.put("MAX_HEALTH", MAX_HEALTH);
    }

    public void setATTACK_DAMAGE(double ATTACK_DAMAGE) {
        this.statsValues.put("ATTACK_DAMAGE", ATTACK_DAMAGE);
    }

    public void setATTACK_KNOCKBACK(double ATTACK_KNOCKBACK) {
        this.statsValues.put("ATTACK_KNOCKBACK", ATTACK_KNOCKBACK);
    }

    public void setATTACK_SPEED(double ATTACK_SPEED) {
        this.statsValues.put("ATTACK_SPEED", ATTACK_SPEED);
    }

    public void setMOVEMENT_SPEED(double MOVEMENT_SPEED) {
        this.statsValues.put("MOVEMENT_SPEED", MOVEMENT_SPEED);
    }

    public void setLUCK(double LUCK) {
        this.statsValues.put("LUCK", LUCK);
    }

    public void addLevel(int level) {
        this.LEVEL += level;
    }

    public void addHealth(double count) {
        this.statsValues.put("MAX_HEALTH", getStat("MAX_HEALTH") + count);
    }

    public void addDamage(double count) {
        this.statsValues.put("ATTACK_DAMAGE", getStat("ATTACK_DAMAGE") + count);
    }

    public void addKnockback(double count) {
        this.statsValues.put("ATTACK_KNOCKBACK", getStat("ATTACK_KNOCKBACK") + count);
    }

    public void addAttackSpeed(double count) {
        this.statsValues.put("ATTACK_SPEED", getStat("ATTACK_SPEED") + count);
    }

    public void addSpeed(double count) {
        this.statsValues.put("MOVEMENT_SPEED", getStat("MOVEMENT_SPEED") + count);
    }

    public void addLuck(double count) {
        this.statsValues.put("LUCK", getStat("LUCK") + count);
    }

    public void copyFrom(PlayerStats source) {
        this.LEVEL = source.getLEVEL();
        setMAX_HEALTH(source.getMAX_HEALTH());
        setATTACK_DAMAGE(source.getATTACK_DAMAGE());
        setATTACK_KNOCKBACK(source.getATTACK_KNOCKBACK());
        setATTACK_SPEED(source.getATTACK_SPEED());
        setMOVEMENT_SPEED(source.getMOVEMENT_SPEED());
        setLUCK(source.getLUCK());
    }

    public int getLEVEL() {
        return LEVEL;
    }

    public double getMAX_HEALTH() {
        return getStat("MAX_HEALTH");
    }

    public double getATTACK_DAMAGE() {
        return getStat("ATTACK_DAMAGE");
    }

    public double getATTACK_KNOCKBACK() {
        return getStat("ATTACK_KNOCKBACK");
    }

    public double getATTACK_SPEED() {
        return getStat("ATTACK_SPEED");
    }

    public double getMOVEMENT_SPEED() {
        return getStat("MOVEMENT_SPEED");
    }

    public double getLUCK() {
        return getStat("LUCK");

    }

    public void saveNBTData(CompoundTag nbt) {
        nbt.putInt("level", LEVEL);
        nbt.putDouble("health", getMAX_HEALTH());
        nbt.putDouble("damage", getATTACK_DAMAGE());
        nbt.putDouble("knockback", getATTACK_KNOCKBACK());
        nbt.putDouble("attack_speed", getATTACK_SPEED());
        nbt.putDouble("speed", getMOVEMENT_SPEED());
        nbt.putDouble("luck", getLUCK());
    }

    public void loadNBTData(CompoundTag nbt) {
        this.LEVEL = nbt.getInt("level");
        setMAX_HEALTH(nbt.getDouble("health"));
        setATTACK_DAMAGE(nbt.getDouble("damage"));
        setATTACK_KNOCKBACK(nbt.getDouble("knockback"));
        setATTACK_SPEED(nbt.getDouble("attack_speed"));
        setMOVEMENT_SPEED(nbt.getDouble("speed"));
        setLUCK(nbt.getDouble("luck"));
    }
}
