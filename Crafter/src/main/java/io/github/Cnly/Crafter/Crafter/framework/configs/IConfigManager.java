package io.github.Cnly.Crafter.Crafter.framework.configs;

import org.bukkit.plugin.java.JavaPlugin;

public interface IConfigManager
{
    
    public IConfigManager setJavaPlugin(JavaPlugin jp);
    
    public JavaPlugin getJavaPlugin();
    
    /**
     * Copies the default file from the jar to the file
     */
    public IConfigManager copyDefaultConfig();
    
    /**
     * Copies the file specified from the jar to the file
     */
    public IConfigManager copyDefaultConfig(String resourceLocation);
    
    public IConfigManager save();
    
    public IConfigManager load();
    
    public boolean isAutoSaveSet();
    
    /**
     * Sets the interval for auto saving.
     * 
     * @param jp
     *            the JavaPlugin to register task with
     * @param seconds
     *            0 for off
     */
    public void setAutoSaveInterval(int seconds);
    
}
