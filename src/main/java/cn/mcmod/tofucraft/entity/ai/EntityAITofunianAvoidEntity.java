package cn.mcmod.tofucraft.entity.ai;

import cn.mcmod.tofucraft.entity.EntityTofunian;
import com.google.common.base.Predicates;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityAIAvoidEntity;

@SuppressWarnings("rawtypes")
public class EntityAITofunianAvoidEntity<T extends Entity> extends EntityAIAvoidEntity {
    protected EntityTofunian entity;
    @SuppressWarnings("unchecked")
	public EntityAITofunianAvoidEntity(EntityTofunian entityIn, Class<T> classToAvoidIn, float avoidDistanceIn, double farSpeedIn, double nearSpeedIn) {
        super(entityIn, classToAvoidIn, Predicates.alwaysTrue(), avoidDistanceIn, farSpeedIn, nearSpeedIn);

        this.entity = entityIn;
    }

    public boolean shouldExecute()
    {
        return super.shouldExecute();
    }
}
