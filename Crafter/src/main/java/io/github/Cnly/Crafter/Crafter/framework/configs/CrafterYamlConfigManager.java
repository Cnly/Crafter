package io.github.Cnly.Crafter.Crafter.framework.configs;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class CrafterYamlConfigManager extends AbstractConfigManager
{
    
    private YamlConfiguration yml;
    
    /**
     * The constructor. This constructor will set a {@code resourceLocation} for
     * this ConfigManager.
     * 
     * @see CrafterYamlConfigManager#copyDefaultConfig()
     * @param file
     *            the config file
     * @param resourceLocation
     *            The location of the relative resource in the jar.
     * @param copyDefault
     *            whether to call this.copyDefaultConfig() automatically
     * @param jp
     *            the JavaPlugin used for resource files obtaining and task
     *            registering. If this is null, these functions will throw
     *            exceptions.
     */
    public CrafterYamlConfigManager(File file, String resourceLocation,
            boolean copyDefault, JavaPlugin jp)
    {
        super(file, resourceLocation, copyDefault, jp);
        this.yml = YamlConfiguration.loadConfiguration(file);
    }

    /**
     * The constructor
     * 
     * @param file
     *            the config file
     * @param copyDefault
     *            whether to call this.copyDefaultConfig() automatically
     * @param jp
     *            the JavaPlugin used for resource files obtaining and task
     *            registering. If this is null, these functions will throw
     *            exceptions.
     */
    public CrafterYamlConfigManager(File file, boolean copyDefault,
            JavaPlugin jp)
    {
        this(file, null, copyDefault, jp);
    }
    
    public boolean isSet(String path)
    {
        return this.yml.isSet(path);
    }
    
    public CrafterYamlConfigManager set(String path, Object value)
    {
        this.yml.set(path, value);
        return this;
    }
    
    public Object getObject(String path)
    {
        return this.yml.get(path);
    }
    
    public String getString(String path)
    {
        return this.yml.getString(path);
    }
    
    public int getInt(String path)
    {
        return this.yml.getInt(path);
    }
    
    public double getDouble(String path)
    {
        return this.yml.getDouble(path);
    }
    
    public boolean getBoolean(String path)
    {
        return this.yml.getBoolean(path);
    }
    
    public byte getByte(String path)
    {
        return (byte)this.yml.getInt(path);
    }
    
    /**
     * Get the specified ConfigurationSection. If there isn't one, a new one
     * will be created.
     * 
     * @param path
     * @return ConfigurationSection
     */
    public ConfigurationSection getConfigurationSection(String path)
    {
        
        ConfigurationSection section = this.yml.getConfigurationSection(path);
        if (null == section)
        {
            section = this.yml.createSection(path);
        }
        
        return section;
    }
    
    @Override
    public CrafterYamlConfigManager save()
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
    public CrafterYamlConfigManager load()
    {
        this.yml = YamlConfiguration.loadConfiguration(this.file);
        return this;
    }
    
    public HashMap<String, String> getStringMap(String path)
    {
        HashMap<String, String> result = new HashMap<String, String>();
        ConfigurationSection section = this.yml.getConfigurationSection(path);
        
        for (String key : section.getKeys(false))
            result.put(key, section.getString(key));
        
        return result;
    }
    
    public HashMap<String, Integer> getIntegerMap(String path)
    {
        HashMap<String, Integer> result = new HashMap<String, Integer>();
        ConfigurationSection section = this.yml.getConfigurationSection(path);
        
        for (String key : section.getKeys(false))
            result.put(key, section.getInt(key));
        
        return result;
    }
    
    public Map<String, Double> getDoubleMap(String path)
    {
        HashMap<String, Double> result = new HashMap<String, Double>();
        ConfigurationSection section = this.yml.getConfigurationSection(path);
        
        for (String key : section.getKeys(false))
            result.put(key, section.getDouble(key));
        
        return result;
    }
    
    @SuppressWarnings("unchecked")
    public List<String> getStringList(String path)
    {
        return (List<String>)this.yml.getList(path);
    }
    
    @SuppressWarnings("unchecked")
    public List<Integer> getIntegerList(String path)
    {
        return (List<Integer>)this.yml.getList(path);
    }
    
    @SuppressWarnings("unchecked")
    public List<Double> getDoubleList(String path)
    {
        return (List<Double>)this.yml.getList(path);
    }
    
    public <E> CrafterYamlConfigManager addToList(String path, E obj)
    {
        
        @SuppressWarnings("unchecked")
        List<E> l = (List<E>)this.yml.getList(path);
        
        if (null == l)
            l = new ArrayList<E>();
        
        l.add(obj);
        
        return this;
    }
    
    public <E> CrafterYamlConfigManager removeFromList(String path, E obj)
    {
        
        @SuppressWarnings("unchecked")
        List<E> l = (List<E>)this.yml.getList(path);
        
        if (null == l)
            return this;
        
        l.remove(obj);
        
        return this;
    }
    
    @Override
    protected Runnable getAsynchronousSaveRunnable()
    {
        return new SimpleAsyncSaveRunnable();
    }

    public YamlConfiguration getYamlConfig()
    {
        return this.yml;
    }
    
    protected class SimpleAsyncSaveRunnable implements Runnable
    {
        
        protected Map<String, Object> snapshotMap;
        
        public SimpleAsyncSaveRunnable()
        {
            
            snapshotMap = yml.getValues(true);
            
            for(Entry<String, Object> e : snapshotMap.entrySet())
            {
                
                Object o = e.getValue();
                
                if(o instanceof Cloneable)
                {
                    
                    Object clonedObject;
                    try
                    {
                        Method cloneMethod = o.getClass().getMethod("clone", (Class<?>[])null);
                        clonedObject = cloneMethod.invoke(o, (Object[])null);
                    }
                    catch(NoSuchMethodException e1)
                    {
                        continue;
                    }
                    catch(SecurityException e1)
                    {
                        continue;
                    }
                    catch(IllegalAccessException e1)
                    {
                        continue;
                    }
                    catch(IllegalArgumentException e1)
                    {
                        continue;
                    }
                    catch(InvocationTargetException e1)
                    {
                        continue;
                    }
                    
                    e.setValue(clonedObject);
                    
                }
                
            }
            
        }

        @Override
        public void run()
        {
            
            YamlConfiguration yml = new YamlConfiguration();
            
            for(Entry<String, Object> e : snapshotMap.entrySet())
            {
                Object o = e.getValue();
                if(o instanceof ConfigurationSection) continue;
                yml.set(e.getKey(), e.getValue());
            }
            
            try
            {
                yml.save(file);
            }
            catch(IOException e1)
            {
                throw new RuntimeException(
                        "Error occurred while saving config file", e1);
            }
            
        }
        
    }
    
}
