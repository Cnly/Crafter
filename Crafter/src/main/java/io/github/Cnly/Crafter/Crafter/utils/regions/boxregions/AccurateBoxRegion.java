package io.github.Cnly.Crafter.Crafter.utils.regions.boxregions;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class AccurateBoxRegion implements IRegion
{
    
    private final String worldName;
    private final double maxX;
    private final double minX;
    private final double maxY;
    private final double minY;
    private final double maxZ;
    private final double minZ;
    
    public AccurateBoxRegion(String worldName, double maxX, double minX,
            double maxY, double minY, double maxZ, double minZ)
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
    
    public AccurateBoxRegion(Location loc1, Location loc2)
    {
        
        if (!loc1.getWorld().equals(loc2.getWorld()))
            throw new IllegalArgumentException(
                    "Two locations are in different worlds!");
        
        double x1 = loc1.getX();
        double x2 = loc2.getX();
        double y1 = loc1.getY();
        double y2 = loc2.getY();
        double z1 = loc1.getZ();
        double z2 = loc2.getZ();
        
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
    
    public static AccurateBoxRegion fromString(String s)
    {
        
        String[] split = s.split(",");
        
        String worldName = split[0];
        double maxX = Double.parseDouble(split[1]);
        double minX = Double.parseDouble(split[2]);
        double maxY = Double.parseDouble(split[3]);
        double minY = Double.parseDouble(split[4]);
        double maxZ = Double.parseDouble(split[5]);
        double minZ = Double.parseDouble(split[6]);
        
        return new AccurateBoxRegion(worldName, maxX, minX, maxY, minY, maxZ,
                minZ);
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
        
        double x = l.getX();
        double y = l.getY();
        double z = l.getZ();
        
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
        long temp;
        temp = Double.doubleToLongBits(maxX);
        result = prime * result + (int)(temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(maxY);
        result = prime * result + (int)(temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(maxZ);
        result = prime * result + (int)(temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(minX);
        result = prime * result + (int)(temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(minY);
        result = prime * result + (int)(temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(minZ);
        result = prime * result + (int)(temp ^ (temp >>> 32));
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
        AccurateBoxRegion other = (AccurateBoxRegion)obj;
        if (Double.doubleToLongBits(maxX) != Double
                .doubleToLongBits(other.maxX))
            return false;
        if (Double.doubleToLongBits(maxY) != Double
                .doubleToLongBits(other.maxY))
            return false;
        if (Double.doubleToLongBits(maxZ) != Double
                .doubleToLongBits(other.maxZ))
            return false;
        if (Double.doubleToLongBits(minX) != Double
                .doubleToLongBits(other.minX))
            return false;
        if (Double.doubleToLongBits(minY) != Double
                .doubleToLongBits(other.minY))
            return false;
        if (Double.doubleToLongBits(minZ) != Double
                .doubleToLongBits(other.minZ))
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
    
    public double getMaxX()
    {
        return maxX;
    }
    
    public double getMinX()
    {
        return minX;
    }
    
    public double getMaxY()
    {
        return maxY;
    }
    
    public double getMinY()
    {
        return minY;
    }
    
    public double getMaxZ()
    {
        return maxZ;
    }
    
    public double getMinZ()
    {
        return minZ;
    }
    
}
