package cn.mcmod.tofucraft.item.fulintlock;

import cn.mcmod.tofucraft.TofuMain;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemFlintlock extends Item {


    public ItemFlintlock() {
        super();
        setUnlocalizedName(TofuMain.MODID + "." + "fulintlock");
        setMaxStackSize(1);
        setMaxDamage(200);
//        this.addPropertyOverride(new ResourceLocation("state"), new IItemPropertyGetter() {
//            @SideOnly(Side.CLIENT)
//            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
//             switch (NBTUtil.getInteger(stack.getTagCompound(), "state", 0)) {
//                 case 0:
//                	 return entityIn != null && entityIn.isHandActive() && entityIn.getActiveItemStack() == stack ? 1.0F : 0.0F;
//                 case 1:
//                	 return entityIn != null && entityIn.isHandActive() && entityIn.getActiveItemStack() == stack ? 1.0F : 0.0F;
//                 case 2:
//                	 return 0F;
//                 }
//			return 0F;
//            }
//        });
    }


    @Override
    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.NONE;
    }

}
