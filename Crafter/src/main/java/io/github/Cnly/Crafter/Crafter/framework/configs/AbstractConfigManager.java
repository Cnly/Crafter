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
    protected String resourceLocation = null;
    
    /**
     * The constructor. This constructor will set a {@code resourceLocation} for
     * this ConfigManager.
     * 
     * @see AbstractConfigManager#copyDefaultConfig()
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
    public AbstractConfigManager(File file, String resourceLocation,
            boolean copyDefault, JavaPlugin jp)
    {
        
        this.file = file;
        this.jp = jp;
        this.resourceLocation = resourceLocation;
        
        if(copyDefault && !file.exists())
            this.copyDefaultConfig();
        
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
    public AbstractConfigManager(File file, boolean copyDefault, JavaPlugin jp)
    {
        this(file, null, copyDefault, jp);
    }
    
    /**
     * Creates an empty file if there isn't one. Any directory that doesn't
     * exist will be created as well.
     */
    protected void initFile()
    {
        
        if(file.exists())
            return;
        
        file.getParentFile().mkdirs();
        try
        {
            file.createNewFile();
        }
        catch(IOException e)
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
    
    /**
     * Calls {@link AbstractConfigManager#copyDefaultConfig(String)} with the
     * following parameter:<br/>
     * {@code null == this.resourceLocation ? "/"
                + this.file.getName() : this.resourceLocation}
     * 
     * @see AbstractConfigManager#copyDefaultConfig(String)
     * @return this
     */
    @Override
    public AbstractConfigManager copyDefaultConfig()
    {
        this.copyDefaultConfig(null == this.resourceLocation ? "/"
                + this.file.getName() : this.resourceLocation);
        return this;
    }
    
    /**
     * Copies the resource from the jar at the location {@code resourceLocation}
     * .
     * 
     * @param resourceLocation
     *            The location of the resource in the jar.
     * @return this
     */
    @Override
    public AbstractConfigManager copyDefaultConfig(String resourceLocation)
    {
        
        if(null == this.jp)
            throw new NullPointerException("JavaPlugin is null!");
        
        try
        {
            ResourceUtils.copyFromJar(jp, resourceLocation, this.file);
        }
        catch(IOException e)
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
        
        if(null == this.jp)
            throw new NullPointerException("JavaPlugin is null!");
        
        if(seconds == 0)
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
            AbstractConfigManager.this.save();
        }

        @Override
        public synchronized void cancel() throws IllegalStateException
        {
            AbstractConfigManager.this.save();
            super.cancel();
        }
        
    }
    
}
