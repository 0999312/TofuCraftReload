package cn.mcmod.tofucraft.util;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileScanner {

    private final World world;
    private final BlockPos centrePos;
	
    public TileScanner(World world, BlockPos pos)
    {
        this.world = world;
        this.centrePos = pos;
    }
	
    public <T> T scan(int size,  Impl<T> impl)
    {
        return this.scan(size, Method.partial, impl);
    }

    public <T> T scan(int size, Method method,  Impl<T> impl)
    {
    	impl.pos = this.centrePos;

        for (int x = -size; x <= size; x++)
        {
            for (int y = -size; y <= size; y++)
            {
                for (int z = -size; z <= size; z++)
                {
                    if (x == 0 && y == 0 && z == 0) continue;
                    
                    BlockPos absTargPos = this.centrePos.add(x, y, z);
                    
                    method.impl.apply(world, new BlockPos(x, y, z), absTargPos, size, impl);
                }
            }
        }
        return impl.getReturn();
    }
	
    public enum Method
    {
    	/** Manhattan distance - side only*/
        partial(new IScanMethod()
        {
            @Override
            public void apply(World world, BlockPos relPos, BlockPos absTargPos, int size, Impl<?> impl)
            {
                int dist = Math.abs(relPos.getX()) + Math.abs(relPos.getY()) + Math.abs(relPos.getZ());
                
                if (dist == size)
                {
                    impl.apply(world, absTargPos);
                }
            }
        }),
        
        /** Manhattan distance - include inside*/
        full(new IScanMethod()
        {
            @Override
            public void apply(World world, BlockPos relPos, BlockPos absTargPos, int size, Impl<?> impl)
            {
                int dist = Math.abs(relPos.getX()) + Math.abs(relPos.getY()) + Math.abs(relPos.getZ());
                if (dist <= size)
                {
                    impl.apply(world, absTargPos);
                }
            }
        }),
        
        /** Chebyshev distance - include inside*/
        fullSimply(new IScanMethod()
        {
            @Override
            public void apply(World world, BlockPos relPos, BlockPos absTagPos, int size, Impl<?> impl)
            {
                impl.apply(world, absTagPos);
            }
        });

        public IScanMethod impl;

        Method(IScanMethod impl)
        {
           this.impl = impl;
        }
    }
    
    private interface IScanMethod
    {
        void apply(World world, BlockPos relPos, BlockPos absTargPos, int size, Impl<?> impl);
    }
	
    abstract public static class Impl<T>
    {
        public BlockPos pos;
        abstract public void apply(World world, BlockPos absTargPos);
        public T getReturn()
        {
            return null;
        }
    }
}
