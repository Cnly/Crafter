package io.github.Cnly.Crafter.Crafter.utils.regions.boxregions;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class ApproximateBoxRegion implements IRegion
{
    
    private final String worldName;
    private final int maxX;
    private final int minX;
    private final int maxY;
    private final int minY;
    private final int maxZ;
    private final int minZ;
    
    public ApproximateBoxRegion(String worldName, int maxX, int minX, int maxY,
            int minY, int maxZ, int minZ)
    {
        super();
        this.worldName = worldName;
        this.maxX = maxX;
        this.minX = minX;
        this.maxY = maxY;
        this.minY = minY;
        this.maxZ = maxZ;
        this.minZ = minZ;
    }
    
    public ApproximateBoxRegion(Location loc1, Location loc2)
    {
        
        if (!loc1.getWorld().equals(loc2.getWorld()))
            throw new IllegalArgumentException(
                    "Two locations are in different worlds!");
        
        int x1 = loc1.getBlockX();
        int x2 = loc2.getBlockX();
        int y1 = loc1.getBlockY();
        int y2 = loc2.getBlockY();
        int z1 = loc1.getBlockZ();
        int z2 = loc2.getBlockZ();
        
        this.worldName = loc1.getWorld().getName();
        this.maxX = Math.max(x1, x2);
        this.minX = Math.min(x1, x2);
        this.maxY = Math.max(y1, y2);
        this.minY = Math.min(y1, y2);
        this.maxZ = Math.max(z1, z2);
        this.minZ = Math.min(z1, z2);
        
    }
    
    @Override
    public String toString()
    {
        
        return new StringBuilder().append(this.worldName).append(',')
                .append(this.maxX).append(',').append(this.minX).append(',')
                .append(this.maxY).append(',').append(this.minY).append(',')
                .append(this.maxZ).append(',').append(this.minZ).toString();
    }
    
    public static ApproximateBoxRegion fromString(String s)
    {
        
        String[] split = s.split(",");
        
        String worldName = split[0];
        int maxX = Integer.parseInt(split[1]);
        int minX = Integer.parseInt(split[2]);
        int maxY = Integer.parseInt(split[3]);
        int minY = Integer.parseInt(split[4]);
        int maxZ = Integer.parseInt(split[5]);
        int minZ = Integer.parseInt(split[6]);
        
        return new ApproximateBoxRegion(worldName, maxX, minX, maxY, minY,
                maxZ, minZ);
    }
    
    @Override
    public boolean isInRegion(Player p)
    {
        return this.isInRegion(p.getLocation());
    }
    
    @Override
    public boolean isInRegion(Location l)
    {
        
        if (!l.getWorld().getName().equals(this.worldName))
            return false;
        
        int x = l.getBlockX();
        int y = l.getBlockY();
        int z = l.getBlockZ();
        
        if ((x <= maxX && x >= minX) && (y <= maxY && y >= minY)
                && (z <= maxZ && z >= minZ))
            return true;
        else
            return false;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + maxX;
        result = prime * result + maxY;
        result = prime * result + maxZ;
        result = prime * result + minX;
        result = prime * result + minY;
        result = prime * result + minZ;
        result = prime * result
                + ((worldName == null) ? 0 : worldName.hashCode());
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ApproximateBoxRegion other = (ApproximateBoxRegion)obj;
        if (maxX != other.maxX)
            return false;
        if (maxY != other.maxY)
            return false;
        if (maxZ != other.maxZ)
            return false;
        if (minX != other.minX)
            return false;
        if (minY != other.minY)
            return false;
        if (minZ != other.minZ)
            return false;
        if (worldName == null)
        {
            if (other.worldName != null)
                return false;
        }
        else if (!worldName.equals(other.worldName))
            return false;
        return true;
    }
    
    @Override
    public String getWorldName()
    {
        return worldName;
    }
    
    public int getMaxX()
    {
        return maxX;
    }
    
    public int getMinX()
    {
        return minX;
    }
    
    public int getMaxY()
    {
        return maxY;
    }
    
    public int getMinY()
    {
        return minY;
    }
    
    public int getMaxZ()
    {
        return maxZ;
    }
    
    public int getMinZ()
    {
        return minZ;
    }
    
}
