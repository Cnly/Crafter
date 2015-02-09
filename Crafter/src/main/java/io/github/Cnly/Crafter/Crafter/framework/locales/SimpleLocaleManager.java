package io.github.Cnly.Crafter.Crafter.framework.locales;

import io.github.Cnly.Crafter.Crafter.utils.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

public class SimpleLocaleManager implements ILocaleManager
{
    
    private String locale;
    private File localeDirectory;
    private Map<String, String> stringMappings;
    
    /**
     * The constructor
     * 
     * @param locale
     *            e.g. "en_UK"
     * @param localeDirectory
     *            the locale directory
     * @param copyDefault
     *            whether to call copyDefaultLocaleFile() automatically
     */
    public SimpleLocaleManager(String locale, File localeDirectory,
            boolean copyDefault)
    {
        
        this.locale = locale;
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
        
        for (String key : yc.getKeys(false))
            this.stringMappings.put(
                    key,
                    ChatColor.translateAlternateColorCodes('&',
                            yc.getString(key)));
        
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
        try
        {
            ResourceUtils.copyFromJar(resourceLocation, new File(
                    localeDirectory, this.locale + ".yml"));
        }
        catch (IOException e)
        {
            throw new RuntimeException(
                    "IOException occurred while copying default locale file", e);
        }
    }
    
}
