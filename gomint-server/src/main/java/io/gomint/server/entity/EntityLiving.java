package io.gomint.server.entity;

import io.gomint.GoMint;
import io.gomint.entity.potion.PotionEffect;
import io.gomint.event.entity.EntityDamageByEntityEvent;
import io.gomint.event.entity.EntityDamageEvent;
import io.gomint.event.entity.EntityHealEvent;
import io.gomint.math.MathUtils;
import io.gomint.math.Vector;
import io.gomint.server.entity.component.AIBehaviourComponent;
import io.gomint.server.entity.metadata.MetadataContainer;
import io.gomint.server.entity.pathfinding.PathfindingEngine;
import io.gomint.server.entity.potion.effect.Effect;
import io.gomint.server.inventory.InventoryHolder;
import io.gomint.server.network.packet.Packet;
import io.gomint.server.network.packet.PacketEntityEvent;
import io.gomint.server.network.packet.PacketSpawnEntity;
import io.gomint.server.player.EffectManager;
import io.gomint.server.util.EnumConnectors;
import io.gomint.server.util.Values;
import io.gomint.server.world.WorldAdapter;
import io.gomint.taglib.NBTTagCompound;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Common base class for all entities that live. All living entities possess
 * an AI which is the significant characteristic that marks an entity as being
 * alive in GoMint's definition.
 *
 * @author BlackyPaw
 * @version 1.0
 */
public abstract class EntityLiving extends Entity implements InventoryHolder, io.gomint.entity.EntityLiving {

    private static final Logger LOGGER = LoggerFactory.getLogger(EntityLiving.class);

    // AI of the entity:
    protected AIBehaviourComponent behaviour;
    // Pathfinding engine of the entity:
    protected PathfindingEngine pathfinding;

    protected Map<String, AttributeInstance> attributes = new HashMap<>();

    private float lastUpdateDT = 0;
    private final Set<io.gomint.entity.Entity> attachedEntities = new HashSet<>();

    private byte attackCoolDown = 0;

    protected int deadTimer = 0;
    private int fireTicks = 0;

    // Damage stats
    protected float lastDamage = 0;
    protected EntityDamageEvent.DamageSource lastDamageSource;
    protected io.gomint.entity.Entity lastDamageEntity;

    // Effects
    private final EffectManager effectManager = new EffectManager(this);

    /**
     * Constructs a new EntityLiving
     *
     * @param type  The type of the Entity
     * @param world The world in which this entity is in
     */
    protected EntityLiving(EntityType type, WorldAdapter world) {
        super(type, world);
        this.behaviour = new AIBehaviourComponent();
        this.pathfinding = new PathfindingEngine(this.getTransform());
        this.initAttributes();

        this.metadataContainer.putFloat(MetadataContainer.DATA_SCALE, 1.0f);
    }

    private void initAttributes() {
        addAttribute(Attribute.ABSORPTION);
        addAttribute(Attribute.ATTACK_DAMAGE);
        addAttribute(Attribute.FOLLOW_RANGE);
        addAttribute(Attribute.HEALTH);
        addAttribute(Attribute.MOVEMENT_SPEED);
        addAttribute(Attribute.KNOCKBACK_RESISTANCE);
    }

    protected float addAttribute(Attribute attribute) {
        AttributeInstance instance = attribute.create();
        this.attributes.put(instance.getKey(), instance);
        return instance.getValue();
    }

    public float getAttribute(Attribute attribute) {
        AttributeInstance instance = this.attributes.get(attribute.getKey());
        if (instance != null) {
            return instance.getValue();
        }

        return addAttribute(attribute);
    }

    public AttributeInstance getAttributeInstance(Attribute attribute) {
        return this.attributes.get(attribute.getKey());
    }

    public void setAttribute(Attribute attribute, float value) {
        AttributeInstance instance = this.attributes.get(attribute.getKey());
        if (instance != null) {
            instance.setValue(value);
        }
    }

    @Override
    protected void fall() {
        // Check for jump potion
        float distanceReduce = 0.0f;
        int jumpAmplifier = getEffectAmplifier(PotionEffect.JUMP);
        if (jumpAmplifier != -1) {
            distanceReduce = jumpAmplifier + 1;
        }

        float damage = MathUtils.fastFloor(this.fallDistance - 3f - distanceReduce);
        if (damage > 0) {
            this.attack(damage, EntityDamageEvent.DamageSource.FALL);
        }
    }

    // ==================================== UPDATING ==================================== //

    @Override
    public void update(long currentTimeMS, float dT) {
        if (!(this.isDead() || this.getHealth() <= 0)) {
            super.update(currentTimeMS, dT);
            this.behaviour.update(currentTimeMS, dT);
        }

        // Check if last hit entity is still alive
        if (this.lastDamageEntity != null && this.lastDamageEntity.isDead()) {
            this.lastDamageEntity = null;
        }

        // Only update when alive
        if (!(this.isDead() || this.getHealth() <= 0)) {
            // Update effects
            this.effectManager.update(currentTimeMS, dT);
        }

        // Check for client tick stuff
        this.lastUpdateDT += dT;
        if (Values.CLIENT_TICK_RATE - this.lastUpdateDT < MathUtils.EPSILON) {
            // Calc death stuff
            if (this.getHealth() <= 0) {
                if (this.deadTimer > 0 && this.deadTimer-- > 1) {
                    despawn();
                    this.deadTimer = 0;
                }
            } else {
                this.deadTimer = 0;
            }

            if (this.isDead() || this.getHealth() <= 0) {
                this.lastUpdateDT = 0;
                return;
            }

            // Reset attack cooldown
            if (this.attackCoolDown > 0) {
                this.attackCoolDown--;
            }

            // Fire?
            if (this.fireTicks > 0) {
                if (this.fireTicks % 20 == 0) {
                    EntityDamageEvent damageEvent = new EntityDamageEvent(this, EntityDamageEvent.DamageSource.ON_FIRE, 1.0f);
                    damage(damageEvent);
                }

                this.fireTicks--;
                if (this.fireTicks == 0) {
                    setOnFire(false);
                }
            }

            io.gomint.server.world.block.Block standingIn = this.world.getBlockAt(this.getPosition().toBlockPosition());
            standingIn.onEntityStanding(this);

            this.lastUpdateDT = 0;
        }
    }

    /**
     * Set health without checking for correct death cause
     *
     * @param amount of health which should be set. <= 0 kills the entity
     */
    public void setHealthInternal(float amount) {
        if (amount <= 0) {
            this.kill();
        }

        AttributeInstance attributeInstance = this.attributes.get(Attribute.HEALTH.getKey());
        attributeInstance.setValue(Math.min(attributeInstance.getMaxValue(), Math.max(attributeInstance.getMinValue(), amount)));
    }

    @Override
    public void setHealth(float amount) {
        if (amount <= 0) {
            this.lastDamageSource = EntityDamageEvent.DamageSource.API;
            this.lastDamage = 999999;
            this.lastDamageEntity = null;
        }

        this.setHealthInternal(amount);
    }

    @Override
    public float getHealth() {
        return this.getAttribute(Attribute.HEALTH);
    }

    @Override
    public Packet createSpawnPacket(EntityPlayer receiver) {
        // Broadcast spawn entity packet:
        PacketSpawnEntity packet = new PacketSpawnEntity();
        packet.setEntityId(this.id);
        packet.setEntityType(this.type);
        packet.setX(this.getPositionX());
        packet.setY(this.getPositionY());
        packet.setZ(this.getPositionZ());
        packet.setVelocityX(this.getMotionX());
        packet.setVelocityY(this.getMotionY());
        packet.setVelocityZ(this.getMotionZ());
        packet.setPitch(this.getPitch());
        packet.setYaw(this.getYaw());
        packet.setHeadYaw(this.getHeadYaw());
        packet.setAttributes(this.attributes.values());
        packet.setMetadata(this.getMetadata());
        return packet;
    }

    @Override
    public void setAbsorptionHearts(float amount) {
        AttributeInstance attributeInstance = this.attributes.get(Attribute.ABSORPTION.getKey());
        attributeInstance.setValue(MathUtils.clamp(amount, attributeInstance.getMinValue(), attributeInstance.getMaxValue()));
    }

    @Override
    public float getAbsorptionHearts() {
        return this.getAttribute(Attribute.ABSORPTION);
    }

    @Override
    public void setMaxHealth(float amount) {
        this.getAttributeInstance(Attribute.HEALTH).setMaxValue(amount);
    }

    @Override
    public float getMaxHealth() {
        return this.getAttributeInstance(Attribute.HEALTH).getMaxValue();
    }

    /**
     * Heal this entity by given amount and cause
     *
     * @param amount of heal
     * @param cause  of this heal
     */
    public void heal(float amount, EntityHealEvent.Cause cause) {
        EntityHealEvent event = new EntityHealEvent(this, amount, cause);
        this.world.getServer().getPluginManager().callEvent(event);

        if (!event.isCancelled()) {
            this.setHealth(this.getHealth() + amount);
        }
    }

    @Override
    public void attack(float damage, EntityDamageEvent.DamageSource source) {
        EntityDamageEvent damageEvent = new EntityDamageEvent(this, source, damage);
        this.damage(damageEvent);
    }

    @Override
    public boolean damage(EntityDamageEvent damageEvent) {
        // Don't damage dead entities
        if (this.getHealth() <= 0) {
            return false;
        }

        // Check for effect blocking
        if (hasEffect(PotionEffect.FIRE_RESISTANCE) && (
            damageEvent.getDamageSource() == EntityDamageEvent.DamageSource.FIRE ||
                damageEvent.getDamageSource() == EntityDamageEvent.DamageSource.LAVA ||
                damageEvent.getDamageSource() == EntityDamageEvent.DamageSource.ON_FIRE
        )) {
            return false;
        }

        // Armor calculations
        float damage = applyArmorReduction(damageEvent, false);
        damage = applyEffectReduction(damageEvent, damage);

        // Absorption
        float absorptionHearts = this.getAbsorptionHearts();
        if (absorptionHearts > 0) {
            damage = Math.max(damage - absorptionHearts, 0f);
        }

        // Check for attack timer
        if (this.attackCoolDown > 0 && damage <= this.lastDamage) {
            return false;
        }

        // Call event
        damageEvent.setFinalDamage(damage);
        if (!super.damage(damageEvent)) {
            return false;
        }

        // Did the final damage change?
        float damageToBeDealt;
        if (damage != damageEvent.getFinalDamage()) {
            damageToBeDealt = damageEvent.getFinalDamage();
        } else {
            damageToBeDealt = applyArmorReduction(damageEvent, true);
            damageToBeDealt = applyEffectReduction(damageEvent, damageToBeDealt);

            absorptionHearts = this.getAbsorptionHearts();
            if (absorptionHearts > 0) {
                float oldDamage = damageToBeDealt;
                damageToBeDealt = Math.max(damage - absorptionHearts, 0f);
                this.setAbsorptionHearts(absorptionHearts - (oldDamage - damageToBeDealt));
            }
        }

        float health = MathUtils.fastCeil(this.getHealth() - damageToBeDealt);

        // Send animation
        if (health > 0) {
            PacketEntityEvent entityEvent = new PacketEntityEvent();
            entityEvent.setEntityId(this.id);
            entityEvent.setEventId(EntityEvent.HURT.getId());

            for (io.gomint.entity.Entity attachedEntity : this.attachedEntities) {
                EntityPlayer entityPlayer = (EntityPlayer) attachedEntity;
                entityPlayer.getConnection().addToSendQueue(entityEvent);
            }

            if (this instanceof EntityPlayer) {
                ((EntityPlayer) this).getConnection().addToSendQueue(entityEvent);
            }
        }

        if (damageEvent instanceof EntityDamageByEntityEvent) {
            // Knockback
            Entity entity = (Entity) ((EntityDamageByEntityEvent) damageEvent).getAttacker();
            float diffX = this.getPositionX() - entity.getPositionX();
            float diffZ = this.getPositionZ() - entity.getPositionZ();

            float distance = (float) Math.sqrt(diffX * diffX + diffZ * diffZ);
            if (distance > 0.0) {
                float baseModifier = 0.4F;

                distance = 1 / distance;

                Vector motion = this.getVelocity();
                motion = motion.divide(2f, 2f, 2f);
                motion = motion.add(
                    (diffX * distance * baseModifier),
                    baseModifier,
                    (diffZ * distance * baseModifier)
                );

                if (motion.getY() > baseModifier) {
                    motion.setY(baseModifier);
                }

                this.setVelocity(motion, true);
            }
        }

        this.lastDamage = damage;
        this.lastDamageSource = damageEvent.getDamageSource();
        this.lastDamageEntity = (damageEvent instanceof EntityDamageByEntityEvent) ? ((EntityDamageByEntityEvent) damageEvent).getAttacker() : null;

        // Set health
        this.setHealthInternal(health <= 0 ? 0 : health);

        this.attackCoolDown = 10;
        return true;
    }

    protected float applyEffectReduction(EntityDamageEvent damageEvent, float damage) {
        // Starve is absolute damage
        if (damageEvent.getDamageSource() == EntityDamageEvent.DamageSource.STARVE) {
            return damage;
        }

        int damageResistanceAmplifier = getEffectAmplifier(PotionEffect.DAMAGE_RESISTANCE);
        if (damageResistanceAmplifier != -1 && damageEvent.getDamageSource() != EntityDamageEvent.DamageSource.VOID) {
            float maxReductionDiff = 25f - ((damageResistanceAmplifier + 1) * 5);
            float amplifiedDamage = damage * maxReductionDiff;
            damage = amplifiedDamage / 25.0F;
        }

        if (damage <= 0.0f) {
            return 0.0f;
        }

        return damage;
    }

    /**
     * Reset fire status on kill
     */
    protected void kill() {
        this.deadTimer = 20;

        // Send animation
        PacketEntityEvent entityEvent = new PacketEntityEvent();
        entityEvent.setEntityId(this.id);
        entityEvent.setEventId(EntityEvent.DEATH.getId());

        for (io.gomint.entity.Entity attachedEntity : this.attachedEntities) {
            EntityPlayer entityPlayer = (EntityPlayer) attachedEntity;
            entityPlayer.getConnection().addToSendQueue(entityEvent);
        }

        this.fireTicks = 0;
        setOnFire(false);

        this.effectManager.removeAll();
    }

    /**
     * Apply reduction based on the armor value of a entity
     *
     * @param damageEvent which wants to deal damage
     * @param damageArmor should we damage the armor?
     * @return damage left over after removing armor reductions
     */
    protected float applyArmorReduction(EntityDamageEvent damageEvent, boolean damageArmor) {
        return damageEvent.getDamage();
    }

    @Override
    public void attach(EntityPlayer player) {
        if (!GoMint.instance().isMainThread()) {
            LOGGER.warn("Attaching entities from another thread than the main one can cause crashes", new Exception());
        }

        this.attachedEntities.add(player);
        this.effectManager.sendForPlayer(player);
    }

    @Override
    public void detach(EntityPlayer player) {
        if (!GoMint.instance().isMainThread()) {
            LOGGER.warn("Detaching entities from another thread than the main one can cause crashes", new Exception());
        }

        this.attachedEntities.remove(player);
    }

    public void resetAttributes() {
        for (AttributeInstance instance : this.attributes.values()) {
            instance.reset();
        }
    }

    @Override
    void dealVoidDamage() {
        EntityDamageEvent damageEvent = new EntityDamageEvent(this,
            EntityDamageEvent.DamageSource.VOID, 4.0F);
        this.damage(damageEvent);
    }

    @Override
    public void setBurning(long duration, TimeUnit unit) {
        int newFireTicks = (int) (unit.toMillis(duration) / 50);
        if (newFireTicks > this.fireTicks) {
            this.fireTicks = newFireTicks;
            setOnFire(true);
        } else if (newFireTicks == 0) {
            this.fireTicks = 0;
            setOnFire(false);
        }
    }

    @Override
    public void extinguish() {
        this.setBurning(0, TimeUnit.SECONDS);
    }

    @Override
    public io.gomint.entity.potion.Effect addEffect(PotionEffect effect, int amplifier, long duration, TimeUnit timeUnit) {
        byte effectId = (byte) EnumConnectors.POTION_EFFECT_CONNECTOR.convert(effect).getId();
        Effect effectInstance = this.world.getServer().getEffects().generate(effectId, amplifier,
            this.world.getServer().getCurrentTickTime() + timeUnit.toMillis(duration), this.effectManager);

        if (effectInstance != null) {
            this.effectManager.addEffect(effectId, effectInstance);
        }

        return effectInstance;
    }

    @Override
    public boolean hasEffect(PotionEffect effect) {
        byte effectId = (byte) EnumConnectors.POTION_EFFECT_CONNECTOR.convert(effect).getId();
        return this.effectManager.hasEffect(effectId);
    }

    @Override
    public int getEffectAmplifier(PotionEffect effect) {
        byte effectId = (byte) EnumConnectors.POTION_EFFECT_CONNECTOR.convert(effect).getId();
        return this.effectManager.getEffectAmplifier(effectId);
    }

    @Override
    public void removeEffect(PotionEffect effect) {
        byte effectId = (byte) EnumConnectors.POTION_EFFECT_CONNECTOR.convert(effect).getId();
        this.effectManager.removeEffect(effectId);
    }

    @Override
    public void removeAllEffects() {
        this.effectManager.removeAll();
    }

    @Override
    public float getMovementSpeed() {
        return this.getAttribute(Attribute.MOVEMENT_SPEED);
    }

    @Override
    public void setMovementSpeed(float value) {
        this.setAttribute(Attribute.MOVEMENT_SPEED, value);
    }

    @Override
    public void initFromNBT(NBTTagCompound compound) {
        super.initFromNBT(compound);

        if (compound.containsKey("AbsorptionAmount")) {
            this.setAbsorptionHearts(compound.getFloat("AbsorptionAmount", 0.0f));
        }

        this.effectManager.initFromNBT(compound);

        List<Object> nbtAttributes = compound.getList("Attributes", false);
        if (nbtAttributes != null) {
            for (Object attribute : nbtAttributes) {
                NBTTagCompound nbtAttribute = (NBTTagCompound) attribute;
                String name = nbtAttribute.getString("Name", null);
                if (name != null) {
                    AttributeInstance instance = null;

                    for (Attribute value : Attribute.values()) {
                        if (value.getKey().equals(name)) {
                            instance = value.create();
                        }
                    }

                    if (instance != null) {
                        instance.initFromNBT(nbtAttribute);
                        this.attributes.put(name, instance);
                    }
                }
            }
        }

        this.deadTimer = compound.getShort("DeathTime", (short) 0);
        this.attackCoolDown = compound.getShort("HurtTime", (short) 0).byteValue();
    }

    @Override
    public NBTTagCompound persistToNBT() {
        NBTTagCompound compound = super.persistToNBT();

        if (this.getAbsorptionHearts() > 0) {
            compound.addValue("AbsorptionAmount", this.getAbsorptionHearts());
        }

        if (this.effectManager.hasActiveEffect()) {
            this.effectManager.persistToNBT(compound);
        }

        List<NBTTagCompound> nbtAttributes = new ArrayList<>();
        for (Map.Entry<String, AttributeInstance> entry : this.attributes.entrySet()) {
            nbtAttributes.add(entry.getValue().persistToNBT());
        }

        compound.addValue("Attributes", nbtAttributes);
        compound.addValue("DeathTime", (short) this.deadTimer);
        compound.addValue("HurtTime", (short) this.attackCoolDown);

        return compound;
    }

    public Set<io.gomint.entity.Entity> getAttachedEntities() {
        return attachedEntities;
    }

    public EntityDamageEvent.DamageSource getLastDamageSource() {
        return lastDamageSource;
    }

    public io.gomint.entity.Entity getLastDamageEntity() {
        return lastDamageEntity;
    }

}
