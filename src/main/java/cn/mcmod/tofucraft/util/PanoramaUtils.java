package cn.mcmod.tofucraft.util;

import com.google.common.base.Objects;
import net.minecraft.util.ResourceLocation;

public class PanoramaUtils {
    public final ResourceLocation north, east, south, west, top, bottom;

    public PanoramaUtils(ResourceLocation north, ResourceLocation east, ResourceLocation south, ResourceLocation west, ResourceLocation top, ResourceLocation bottom) {
        this.north = north;
        this.east = east;
        this.south = south;
        this.west = west;
        this.top = top;
        this.bottom = bottom;
    }

    public ResourceLocation getPath(int i) {
        switch (i) {
            case 0:
                return north;
            case 1:
                return east;
            case 2:
                return south;
            case 3:
                return west;
            case 4:
                return top;
            case 5:
                return bottom;
            default:
                return top;
        }
    }

    public ResourceLocation[] getPaths() {
        ResourceLocation[] paths = new ResourceLocation[6];

        for (int i = 0; i < paths.length; ++i) {
            paths[i] = getPath(i);
        }

        return paths;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null || !(obj instanceof PanoramaUtils)) {
            return false;
        }

        PanoramaUtils paths = (PanoramaUtils) obj;

        return north.equals(paths.north) && east.equals(paths.east) && south.equals(paths.south) && west.equals(paths.west) && top.equals(paths.top) && bottom.equals(paths.bottom);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(north, east, south, west, top, bottom);
    }
}