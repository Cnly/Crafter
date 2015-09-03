package io.github.Cnly.Crafter.Crafter.framework.locales;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class CrafterLocaleManager extends AbstractLocaleManager
{
    
    /**
     * The constructor
     * 
     * @param locale
     *            e.g. "en_UK"
     * @param localeDirectory
     *            the locale directory
     * @param copyDefault
     *            whether to call copyDefaultLocaleFile() automatically
     * @param jp
     *            the JavaPlugin used for resource files obtaining. If this is
     *            null, this function will throw exceptions.
     */
    public CrafterLocaleManager(String locale, File localeDirectory, boolean copyDefault, JavaPlugin jp)
    {
        super(locale, localeDirectory, copyDefault, jp, ".yml");
    }
    
    /**
     * Loads the locale file
     */
    @Override
    public void loadLocaleFile()
    {
        
        File localeFile = new File(this.localeDirectory, this.locale + ".yml");
        if(!localeFile.exists())
            throw new RuntimeException("Locale file: " + this.locale + ".yml does not exist!");
        
        YamlConfiguration yc = new YamlConfiguration();
        try
        {
            yc.load(localeFile);
        }
        catch(FileNotFoundException e)
        {
            throw new RuntimeException("Locale file: " + this.locale + ".yml not found!", e);
        }
        catch(IOException e)
        {
            throw new RuntimeException("IOException occurred while loading locale file: " + this.locale + ".yml!", e);
        }
        catch(InvalidConfigurationException e)
        {
            throw new RuntimeException("Locale file: " + this.locale + ".yml is not valid!", e);
        }
        
        for(String key : yc.getKeys(true))
        {
            
            Object o = yc.get(key);
            
            if(o instanceof ConfigurationSection)
                continue;
            
            this.stringMappings.put(key, ChatColor.translateAlternateColorCodes('&', (String)o));
            
        }
        
    }
    
}
