package io.github.Cnly.Crafter.Crafter.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class LocationUtils
{
    
    private LocationUtils()
    {
        throw new AssertionError("This is a util class");
    }
    
    public static String getLocationString(Location l)
    {
        
        return new StringBuilder(90).append(l.getWorld().getName()).append(',')
                .append(l.getX()).append(',').append(l.getY()).append(',')
                .append(l.getZ()).append(',').append(l.getYaw()).append(',')
                .append(l.getPitch()).toString();
    }
    
    public static String getBlockLocationString(Location l)
    {
        
        return new StringBuilder(20).append(l.getWorld().getName()).append(',')
                .append(l.getBlockX()).append(',').append(l.getBlockY())
                .append(',').append(l.getBlockZ()).toString();
    }
    
    public static Location getLocationByString(String s)
    {
        
        String[] split = s.split(",");
        
        if (split.length == 4)
        { // block location
        
            return new Location(Bukkit.getWorld(split[0]),
                    Double.valueOf(split[1]), Double.valueOf(split[2]),
                    Double.valueOf(split[3]));
        }
        else
        {
            
            return new Location(Bukkit.getWorld(split[0]),
                    Double.valueOf(split[1]), Double.valueOf(split[2]),
                    Double.valueOf(split[3]), Float.valueOf(split[4]),
                    Float.valueOf(split[5]));
        }
        
    }
    
}
