package io.github.Cnly.Crafter.Crafter.framework.configs;

import io.github.Cnly.Crafter.Crafter.utils.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class SimpleYamlConfigManager implements IConfigManager
{
    
    private final File file;
    private YamlConfiguration yml;
    private AutoSaveTask autoSaveTask = null;
    
    /**
     * The constructor
     * 
     * @param file
     *            the config file
     * @param copyDefault
     *            whether to call this.copyDefaultConfig() automatically
     */
    public SimpleYamlConfigManager(File file, boolean copyDefault)
    {
        this.file = file;
        
        if (copyDefault && !file.exists())
            this.copyDefaultConfig();
        
        this.yml = YamlConfiguration.loadConfiguration(file);
        
    }
    
    @Override
    public boolean isSet(String path)
    {
        return this.yml.isSet(path);
    }
    
    @Override
    public SimpleYamlConfigManager set(String path, Object value)
    {
        this.yml.set(path, value);
        return this;
    }
    
    @Override
    public Object getObject(String path)
    {
        return this.yml.get(path);
    }
    
    @Override
    public String getString(String path)
    {
        return this.yml.getString(path);
    }
    
    @Override
    public int getInt(String path)
    {
        return this.yml.getInt(path);
    }
    
    @Override
    public double getDouble(String path)
    {
        return this.yml.getDouble(path);
    }
    
    @Override
    public boolean getBoolean(String path)
    {
        return this.yml.getBoolean(path);
    }
    
    @Override
    public byte getByte(String path)
    {
        return (byte)this.yml.getInt(path);
    }
    
    @Override
    public SimpleYamlConfigManager copyDefaultConfig()
    {
        this.copyDefaultConfig("/" + this.file.getName());
        return this;
    }
    
    @Override
    public SimpleYamlConfigManager copyDefaultConfig(String resourceLocation)
    {
        try
        {
            ResourceUtils.copyFromJar(resourceLocation, this.file);
        }
        catch (IOException e)
        {
            throw new RuntimeException("Cannot copy default file", e);
        }
        return this;
    }
    
    @Override
    public SimpleYamlConfigManager save()
    {
        try
        {
            this.yml.save(this.file);
        }
        catch (IOException e)
        {
            throw new RuntimeException(
                    "Error occurred while saving config file", e);
        }
        return this;
    }
    
    @Override
    public SimpleYamlConfigManager load()
    {
        this.yml = YamlConfiguration.loadConfiguration(this.file);
        return this;
    }
    
    @Override
    public HashMap<String, String> getStringMap(String path)
    {
        HashMap<String, String> result = new HashMap<String, String>();
        ConfigurationSection section = this.yml.getConfigurationSection(path);
        
        for (String key : section.getKeys(false))
            result.put(key, section.getString(key));
        
        return result;
    }
    
    @Override
    public HashMap<String, Integer> getIntegerMap(String path)
    {
        HashMap<String, Integer> result = new HashMap<String, Integer>();
        ConfigurationSection section = this.yml.getConfigurationSection(path);
        
        for (String key : section.getKeys(false))
            result.put(key, section.getInt(key));
        
        return result;
    }
    
    @Override
    public Map<String, Double> getDoubleMap(String path)
    {
        HashMap<String, Double> result = new HashMap<String, Double>();
        ConfigurationSection section = this.yml.getConfigurationSection(path);
        
        for (String key : section.getKeys(false))
            result.put(key, section.getDouble(key));
        
        return result;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<String> getStringList(String path)
    {
        return (List<String>)this.yml.getList(path);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<Integer> getIntegerList(String path)
    {
        return (List<Integer>)this.yml.getList(path);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<Double> getDoubleList(String path)
    {
        return (List<Double>)this.yml.getList(path);
    }
    
    public YamlConfiguration getYamlConfig()
    {
        return this.yml;
    }
    
    @Override
    public boolean isAutoSaveSet()
    {
        return this.autoSaveTask != null;
    }
    
    @Override
    public void setAutoSaveInterval(JavaPlugin jp, int seconds)
    {
        
        if (seconds == 0)
        {// Turn it off!
            
            if(this.autoSaveTask != null)
            {
                this.autoSaveTask.cancel();
                this.autoSaveTask = null;
            }
            
        }
        else
        {
            
            this.autoSaveTask = new AutoSaveTask();
            this.autoSaveTask.runTaskTimer(jp, seconds * 20, seconds * 20);
            
        }
        
    }
    
    private class AutoSaveTask extends BukkitRunnable
    {
        
        @Override
        public void run()
        {
            SimpleYamlConfigManager.this.save();
        }
        
    }
    
}
