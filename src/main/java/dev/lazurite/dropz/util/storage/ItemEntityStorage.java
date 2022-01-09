package dev.lazurite.dropz.util.storage;

import dev.lazurite.dropz.Dropz;
import dev.lazurite.dropz.config.Config;
import dev.lazurite.dropz.mixin.common.ItemEntityMixin;
import dev.lazurite.dropz.mixin.common.access.ItemEntityAccess;
import dev.lazurite.dropz.util.DropType;
import dev.lazurite.rayon.api.EntityPhysicsElement;
import dev.lazurite.rayon.api.PhysicsElement;
import net.minecraft.entity.ItemEntity;
import net.minecraft.world.World;

import java.util.concurrent.Executor;

/**
 * This interface allows access to the {@link DropType} enum object
 * being stored in {@link ItemEntityMixin}. It also has a static helper
 * method for handling item collisions.
 * @see ItemEntityMixin
 * @see Dropz
 */
public interface ItemEntityStorage {
    static void onCollide(PhysicsElement body1, PhysicsElement body2, float impulse) {
        if (Config.getInstance().merge && body1 instanceof ItemEntity && body2 instanceof ItemEntity) {
            ItemEntity item1 = ((ItemEntity) ((EntityPhysicsElement) body1).cast());
            ItemEntity item2 = ((ItemEntity) ((EntityPhysicsElement) body2).cast());
            World world = item1.getEntityWorld();

            if (!world.isClient()) {
                ((ItemEntityAccess) item1).invokeTryMerge(item2);
            }
        }
    }

    DropType getDropType();
}
