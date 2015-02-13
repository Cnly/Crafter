package io.github.Cnly.Crafter.Crafter.framework.configs.autoreloading;

import io.github.Cnly.Crafter.Crafter.framework.configs.IConfigManager;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CrafterConfigReloader
{
    private final String group;
    private ArrayList<Object> classes = new ArrayList<Object>();
    
    public CrafterConfigReloader()
    {
        this("");
    }
    
    public CrafterConfigReloader(String group)
    {
        this.group = group;
    }
    
    public void doReload()
    {
        this.reloadConfigs(this.searchForConfigManagers());
    }
    
    public CrafterConfigReloader addClass(Object classInstance)
    {
        
        this.classes.add(classInstance);
        
        return this;
    }
    
    public List<Object> getClasses()
    {
        return Collections.unmodifiableList(this.classes);
    }
    
    public boolean removeClass(Object classInstance)
    {
        return this.classes.remove(classInstance);
    }
    
    protected ArrayList<IConfigManager> searchForConfigManagers()
    {
        
        ArrayList<ConfigContainer> containers = new ArrayList<ConfigContainer>();
        ArrayList<IConfigManager> result = new ArrayList<>();
        
        for (Object cls : this.classes)
        {
            
            for (Field f : cls.getClass().getDeclaredFields())
            {
                
                f.setAccessible(true);
                
                if (!f.isAnnotationPresent(ReloadableConfig.class))
                    continue;
                if (!IConfigManager.class.isAssignableFrom(f.getType()))
                    continue;
                
                ReloadableConfig rc = f.getAnnotation(ReloadableConfig.class);
                if (!rc.group().equals(this.group))
                    continue;
                int priority = rc.priority();
                
                try
                {
                    containers.add(new ConfigContainer(priority,
                            (IConfigManager)f.get(cls)));
                }
                catch (IllegalArgumentException | IllegalAccessException e)
                {
                    throw new RuntimeException(
                            "Error occurred while searching for reloadable configs",
                            e);
                }
                
            }
            
        }
        
        Collections.sort(containers);
        
        for (ConfigContainer cc : containers)
            result.add(cc.getConfigManager());
        
        return result;
    }
    
    protected void reloadConfigs(List<IConfigManager> managers)
    {
        for (IConfigManager m : managers)
            m.load();
    }
    
    private static class ConfigContainer implements Comparable<ConfigContainer>
    {
        
        private final int priority;
        private final IConfigManager configManager;
        
        public ConfigContainer(int priority, IConfigManager configManager)
        {
            super();
            this.priority = priority;
            this.configManager = configManager;
        }
        
        @Override
        public int compareTo(ConfigContainer o)
        {
            return this.priority > o.getPriority() ? -1
                    : (this.priority < o.priority ? 1 : 0);
        }
        
        public int getPriority()
        {
            return priority;
        }
        
        public IConfigManager getConfigManager()
        {
            return configManager;
        }
        
    }
    
}
