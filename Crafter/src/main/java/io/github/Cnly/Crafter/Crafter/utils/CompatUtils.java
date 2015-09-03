package io.github.Cnly.Crafter.Crafter.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class CompatUtils
{
    
    private static Method CACHED_GETONLINEPLAYERS = null;
    
    private CompatUtils()
    {
        throw new AssertionError("This is a util class");
    }
    
    @SuppressWarnings("unchecked")
    public static Collection<Player> getOnlinePlayers()
    {
        
        Method m = CACHED_GETONLINEPLAYERS;
        if(null == m)
        {
            try
            {
                m = Bukkit.class.getMethod("getOnlinePlayers");
            }
            catch(NoSuchMethodException | SecurityException e)
            {
                throw new RuntimeException("Cannot get method Bukkit.getOnlinePlayers()", e);
            }
            CACHED_GETONLINEPLAYERS = m;
        }
        
        Object obj = null;
        try
        {
            obj = m.invoke(null, (Object[])null);
        }
        catch(SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
        {
            throw new RuntimeException("Cannot invoke Bukkit.getOnlinePlayers()", e);
        }
        
        if(obj instanceof Player[])
        {
            return Arrays.asList((Player[])obj);
        }
        else if(obj instanceof Collection)
        {
            return (Collection<Player>)obj;
        }
        else
        {
            throw new RuntimeException("Unknown getOnlinePlayers() return type!");
        }
        
    }
    
    public static Player getPlayer(UUID uuid)
    {
        for(Player p : CompatUtils.getOnlinePlayers())
        {
            if(p.getUniqueId().equals(uuid))
                return p;
        }
        return null;
    }
    
}
