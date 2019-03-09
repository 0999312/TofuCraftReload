package cn.mcmod.tofucraft.entity.ai;

import cn.mcmod.tofucraft.entity.EntityTofunian;
import cn.mcmod.tofucraft.entity.TofuVillages;
import com.google.common.base.Predicates;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.monster.EntityEvoker;

public class EntityAITofunianAvoidEntity<T extends Entity> extends EntityAIAvoidEntity {
    protected EntityTofunian entity;
    public EntityAITofunianAvoidEntity(EntityTofunian entityIn, Class<T> classToAvoidIn, float avoidDistanceIn, double farSpeedIn, double nearSpeedIn) {
        super(entityIn, classToAvoidIn, Predicates.alwaysTrue(), avoidDistanceIn, farSpeedIn, nearSpeedIn);

        this.entity = entityIn;
    }

    public boolean shouldExecute()
    {
        if(this.entity.getProfessionForge()== TofuVillages.ProfessionHunterTofunian){
            return false;
        }else {
            return super.shouldExecute();
        }
    }
}
