package c4.conarm.armor.traits;

import com.google.common.base.Predicate;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

import javax.annotation.Nullable;

public class TraitUtils {

    public static final Predicate<Entity> IS_LIVING = apply -> apply instanceof EntityLivingBase;
}
