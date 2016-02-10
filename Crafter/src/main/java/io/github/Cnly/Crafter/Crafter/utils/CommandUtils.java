package io.github.Cnly.Crafter.Crafter.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.command.*;
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
        catch(NoSuchMethodException | SecurityException e)
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
        catch(InstantiationException | IllegalAccessException | InvocationTargetException e)
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
        catch(NoSuchFieldException | SecurityException e)
        {
            throw new RuntimeException("Error occurred while getting field commandMap from SimplePluginManager", e);
        }
        
        boolean accessible = commandMapField.isAccessible();
        commandMapField.setAccessible(true);
        
        try
        {
            return (CommandMap)commandMapField.get(pm);
        }
        catch(IllegalArgumentException | IllegalAccessException e)
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

    /**
     * Unregisters a command registered in SimpleCommandMap. Remember to also unregister your commands with fallbackPrefix added.<br />
     * E.g. <b>Myplugin:mycommand</b> and <b>mycommand</b>
     * @param key The key in the knownCommands map.
     */
    public static void unregister(String key)
    {

        CommandMap cm = getCommandMap();
        if(!(cm instanceof SimpleCommandMap))
        {
            throw new RuntimeException("CommandMap is not SimpleCommandMap!");
        }

        cm = (SimpleCommandMap)cm;

        Field knownCommandsField = null;
        try
        {
            knownCommandsField = SimpleCommandMap.class.getDeclaredField("knownCommands");
        }
        catch(NoSuchFieldException | SecurityException e)
        {
            throw new RuntimeException("Error occurred while getting field knownCommands from SimpleCommandMap", e);
        }

        boolean accessible = knownCommandsField.isAccessible();
        knownCommandsField.setAccessible(true);

        Map<String, Command> knownCommands = null;
        try
        {
            knownCommands = (Map<String, Command>)knownCommandsField.get(cm);
        }
        catch(IllegalAccessException e)
        {
            throw new RuntimeException("Error occurred while getting knownCommands from SimpleCommandMap", e);
        }
        finally
        {
            knownCommandsField.setAccessible(accessible);
        }

        knownCommands.remove(key);

    }
    
}
