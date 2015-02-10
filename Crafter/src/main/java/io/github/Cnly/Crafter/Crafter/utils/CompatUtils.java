package io.github.Cnly.Crafter.Crafter.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class CompatUtils
{
    
    private CompatUtils()
    {
        throw new AssertionError("This is a util class");
    }
    
    @SuppressWarnings("unchecked")
    public static Collection<Player> getOnlinePlayers()
    {
        
        Method m;
        Object obj = null;
        try
        {
            m = Bukkit.class.getMethod("getOnlinePlayers");
            obj = m.invoke(null, (Object[])null);
        }
        catch (NoSuchMethodException | SecurityException
                | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException e)
        {
            throw new RuntimeException(
                    "Cannot invoke Bukkit.getOnlinePlayers()", e);
        }
        
        if (obj instanceof Player[])
        {
            return Arrays.asList((Player[])obj);
        }
        else if (obj instanceof Collection)
        {
            return (Collection<Player>)obj;
        }
        else
        {
            throw new RuntimeException(
                    "Unknown getOnlinePlayers() return type!");
        }
        
    }
    
}
