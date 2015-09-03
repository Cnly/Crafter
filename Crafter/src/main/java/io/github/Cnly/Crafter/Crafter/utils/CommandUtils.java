package io.github.Cnly.Crafter.Crafter.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.SimplePluginManager;

public class CommandUtils
{
    
    private CommandUtils()
    {
        throw new AssertionError("This is a util class");
    }
    
    public static PluginCommand newPluginCommand(String name, Plugin plugin)
    {
        
        Constructor<PluginCommand> constructor = null;
        try
        {
            constructor = PluginCommand.class.getDeclaredConstructor(String.class, Plugin.class);
        }
        catch(NoSuchMethodException e)
        {
            throw new RuntimeException("Error occurred while getting constructor from PluginCommand", e);
        }
        catch(SecurityException e)
        {
            throw new RuntimeException("Error occurred while getting constructor from PluginCommand", e);
        }
        
        boolean accessible = constructor.isAccessible();
        constructor.setAccessible(true);
        
        PluginCommand pc = null;
        try
        {
            pc = constructor.newInstance(name, plugin);
        }
        catch(InstantiationException e)
        {
            throw new RuntimeException("Error occurred while creating new instance of PluginCommand", e);
        }
        catch(IllegalAccessException e)
        {
            throw new RuntimeException("Error occurred while creating new instance of PluginCommand", e);
        }
        catch(IllegalArgumentException e)
        {
            throw new RuntimeException("Error occurred while creating new instance of PluginCommand", e);
        }
        catch(InvocationTargetException e)
        {
            throw new RuntimeException("Error occurred while creating new instance of PluginCommand", e);
        }
        finally
        {
            constructor.setAccessible(accessible);
        }
        
        return pc;
    }
    
    public static CommandMap getCommandMap()
    {
        
        PluginManager pm = Bukkit.getPluginManager();
        if(!(pm instanceof SimplePluginManager))
        {
            throw new RuntimeException("Plugin manager is not SimplePluginManager!");
        }
        
        pm = (SimplePluginManager)pm;
        
        Field commandMapField = null;
        try
        {
            commandMapField = SimplePluginManager.class.getDeclaredField("commandMap");
        }
        catch(NoSuchFieldException e)
        {
            throw new RuntimeException("Error occurred while getting field commandMap from SimplePluginManager", e);
        }
        catch(SecurityException e)
        {
            throw new RuntimeException("Error occurred while getting field commandMap from SimplePluginManager", e);
        }
        
        boolean accessible = commandMapField.isAccessible();
        commandMapField.setAccessible(true);
        
        try
        {
            return (CommandMap)commandMapField.get(pm);
        }
        catch(IllegalArgumentException e)
        {
            throw new RuntimeException("Error occurred while getting command map from SimplePluginManager", e);
        }
        catch(IllegalAccessException e)
        {
            throw new RuntimeException("Error occurred while getting command map from SimplePluginManager", e);
        }
        finally
        {
            commandMapField.setAccessible(accessible);
        }
        
    }
    
    public static boolean register(String fallbackPrefix, Command command)
    {
        return getCommandMap().register(fallbackPrefix, command);
    }
    
    public static boolean register(Plugin plugin, Command command)
    {
        return register(plugin.getDescription().getName(), command);
    }
    
    public static boolean register(Plugin plugin, String commandName, CommandExecutor executor, String usage, String description, String permission, String... aliases)
    {
        
        PluginCommand pc = newPluginCommand(commandName, plugin);
        
        pc.setExecutor(executor);
        if(usage != null)
            pc.setUsage(usage);
        if(description != null)
            pc.setDescription(description);
        if(permission != null)
            pc.setPermission(permission);
        if(aliases != null)
            pc.setAliases(Arrays.asList(aliases));
        
        return register(plugin, pc);
    }
    
    public static boolean register(Plugin plugin, String commandName, CommandExecutor executor, String... aliases)
    {
        return register(plugin, commandName, executor, null, null, null, aliases);
    }
    
}
