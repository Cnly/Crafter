package io.github.Cnly.Crafter.Crafter.framework.locales;

import io.github.Cnly.Crafter.Crafter.utils.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class CrafterLocaleManager implements ILocaleManager
{
    
    private String locale;
    private File localeDirectory;
    private Map<String, String> stringMappings;
    private JavaPlugin jp;
    
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
    public CrafterLocaleManager(String locale, File localeDirectory,
            boolean copyDefault, JavaPlugin jp)
    {
        
        this.locale = locale;
        this.jp = jp;
        this.localeDirectory = localeDirectory;
        this.stringMappings = new HashMap<String, String>();
        
        if (copyDefault
                && !new File(localeDirectory, this.locale + ".yml").exists())
            this.copyDefaultLocaleFile();
        
        loadLocaleFile();
        
    }
    
    /**
     * Loads the locale file
     * 
     * @throws FileNotFoundException
     * @throws IOException
     * @throws InvalidConfigurationException
     */
    public void loadLocaleFile()
    {
        
        File localeFile = new File(this.localeDirectory, this.locale + ".yml");
        if (!localeFile.exists())
            throw new RuntimeException("Locale file: " + this.locale
                    + ".yml does not exist!");
        
        YamlConfiguration yc = new YamlConfiguration();
        try
        {
            yc.load(localeFile);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            throw new RuntimeException(
                    "IOException occurred while loading locale file: "
                            + this.locale + ".yml!", e);
        }
        catch (InvalidConfigurationException e)
        {
            throw new RuntimeException("Locale file: " + this.locale
                    + ".yml is not valid!", e);
        }
        
        for (String key : yc.getKeys(true))
        {
            
            Object o = yc.get(key);
            
            if (o instanceof ConfigurationSection)
                continue;
            
            this.stringMappings.put(key,
                    ChatColor.translateAlternateColorCodes('&', (String)o));
            
        }
        
    }
    
    @Override
    public String getLocale()
    {
        return this.locale;
    }
    
    @Override
    public void setLocale(String locale)
    {
        this.locale = locale;
        this.loadLocaleFile();
    }
    
    @Override
    public String getLocalizedString(String key)
    {
        return stringMappings.get(key);
    }
    
    @Override
    public void copyDefaultLocaleFile()
    {
        this.copyDefaultLocaleFile("/locales/" + this.locale + ".yml");
    }
    
    @Override
    public void copyDefaultLocaleFile(String resourceLocation)
    {
        
        if (null == this.jp)
            throw new NullPointerException("JavaPlugin is null!");
        
        try
        {
            ResourceUtils.copyFromJar(this.jp, resourceLocation, new File(
                    localeDirectory, this.locale + ".yml"));
        }
        catch (IOException e)
        {
            throw new RuntimeException(
                    "IOException occurred while copying default locale file", e);
        }
    }
    
}
