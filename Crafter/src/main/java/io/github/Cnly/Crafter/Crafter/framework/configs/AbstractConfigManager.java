package io.github.Cnly.Crafter.Crafter.framework.configs;

import io.github.Cnly.Crafter.Crafter.utils.ResourceUtils;

import java.io.File;
import java.io.IOException;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public abstract class AbstractConfigManager implements IConfigManager
{
    
    protected File file;
    protected AutoSaveTask autoSaveTask = null;
    protected JavaPlugin jp;
    
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
    public AbstractConfigManager(File file, boolean copyDefault, JavaPlugin jp)
    {
        this.file = file;
        this.jp = jp;
        
        if (copyDefault && !file.exists())
            this.copyDefaultConfig();
        
    }
    
    /**
     * Creates an empty file if there isn't one. Any directory that doesn't
     * exist will be created as well.
     */
    protected void initFile()
    {
        
        if (file.exists())
            return;
        
        file.getParentFile().mkdirs();
        try
        {
            file.createNewFile();
        }
        catch (IOException e)
        {
            throw new RuntimeException("Unable to create new empty file!", e);
        }
        
    }
    
    @Override
    public AbstractConfigManager setJavaPlugin(JavaPlugin jp)
    {
        this.jp = jp;
        return this;
    }
    
    @Override
    public JavaPlugin getJavaPlugin()
    {
        return this.jp;
    }
    
    @Override
    public AbstractConfigManager copyDefaultConfig()
    {
        this.copyDefaultConfig("/" + this.file.getName());
        return this;
    }
    
    @Override
    public AbstractConfigManager copyDefaultConfig(String resourceLocation)
    {
        
        if (null == this.jp)
            throw new NullPointerException("JavaPlugin is null!");
        
        try
        {
            ResourceUtils.copyFromJar(jp, resourceLocation, this.file);
        }
        catch (IOException e)
        {
            throw new RuntimeException("Unable to copy default file", e);
        }
        return this;
    }
    
    @Override
    public boolean isAutoSaveSet()
    {
        return this.autoSaveTask != null;
    }
    
    @Override
    public void setAutoSaveInterval(int seconds)
    {
        
        if (null == this.jp)
            throw new NullPointerException("JavaPlugin is null!");
        
        if (seconds == 0)
        {// Turn it off!
        
            if (this.autoSaveTask != null)
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
            AbstractConfigManager.this.save();
        }
        
    }
    
}
