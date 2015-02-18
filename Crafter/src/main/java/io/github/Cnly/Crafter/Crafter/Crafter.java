package io.github.Cnly.Crafter.Crafter;

import org.bukkit.plugin.java.JavaPlugin;

public class Crafter extends JavaPlugin
{
    
    private static Crafter instance;

    @Override
    public void onEnable()
    {
        instance = this;
    }

    public static Crafter getInstance()
    {
        return instance;
    }
    
}
