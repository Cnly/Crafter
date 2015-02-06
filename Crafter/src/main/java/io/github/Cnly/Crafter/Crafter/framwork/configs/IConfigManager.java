package io.github.Cnly.Crafter.Crafter.framwork.configs;

import java.util.List;
import java.util.Map;

import org.bukkit.plugin.java.JavaPlugin;

public interface IConfigManager
{
    
    public Object getObject(String path);
    
    public String getString(String path);
    
    public int getInt(String path);
    
    public double getDouble(String path);
    
    public boolean getBoolean(String path);
    
    public byte getByte(String path);
    
    /**
     * Copies the default file from the jar to the file if there isn't one
     */
    public void copyDefaultConfig();
    
    /**
     * Copies the file specified from the jar to the file if there isn't one
     */
    public void copyDefaultConfig(String resourceLocation);
    
    public void save();
    
    public void load();
    
    public boolean isSet(String path);
    
    public void set(String path, Object value);
    
    public Map<String, String> getStringMap(String path);
    
    public Map<String, Integer> getIntegerMap(String path);
    
    public Map<String, Double> getDoubleMap(String path);
    
    public List<String> getStringList(String path);
    
    public List<Integer> getIntegerList(String path);
    
    public List<Double> getDoubleList(String path);
    
    public boolean isAutoSaveSet();
    
    /**
     * Sets the interval for auto saving.
     * 
     * @param jp
     *            the JavaPlugin to register task with
     * @param seconds
     *            0 for off
     */
    public void setAutoSaveInterval(JavaPlugin jp, int seconds);
    
}
