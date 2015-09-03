package io.github.Cnly.Crafter.Crafter.framework.locales;

import io.github.Cnly.Crafter.Crafter.utils.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.plugin.java.JavaPlugin;

public abstract class AbstractLocaleManager implements ILocaleManager
{
    
    protected String locale;
    protected File localeDirectory;
    protected Map<String, String> stringMappings;
    protected JavaPlugin jp;
    protected String fileExtension;
    
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
     * @param fileExtension
     *            e.g. ".yml"
     */
    public AbstractLocaleManager(String locale, File localeDirectory, boolean copyDefault, JavaPlugin jp, String fileExtension)
    {
        
        this.locale = locale;
        this.jp = jp;
        this.localeDirectory = localeDirectory;
        this.stringMappings = new HashMap<String, String>();
        this.fileExtension = fileExtension;
        
        if(copyDefault && !new File(localeDirectory, this.locale + this.fileExtension).exists())
            this.copyDefaultLocaleFile();
        
        loadLocaleFile();
        
    }
    
    /**
     * Loads the locale file
     */
    public abstract void loadLocaleFile();
    
    @Override
    public String getLocale()
    {
        return this.locale;
    }
    
    /**
     * Sets the locale and loads the new locale file.
     */
    @Override
    public void setLocale(String locale)
    {
        this.locale = locale;
        this.loadLocaleFile();
    }
    
    @Override
    public String getLocalizedString(String key) throws NullPointerException
    {
        String result = stringMappings.get(key);
        if(null == result)
        {
            throw new NullPointerException(String.format("The current locale %s doesn't contain the entry %s! You may have to update your locale file!", this.locale, key));
        }
        return result;
    }
    
    @Override
    public void copyDefaultLocaleFile()
    {
        this.copyDefaultLocaleFile("/locales/" + this.locale + this.fileExtension);
    }
    
    @Override
    public void copyDefaultLocaleFile(String resourceLocation)
    {
        
        if(null == this.jp)
            throw new NullPointerException("JavaPlugin is null!");
        
        try
        {
            ResourceUtils.copyFromJar(this.jp, resourceLocation, new File(localeDirectory, this.locale + this.fileExtension));
        }
        catch(IOException e)
        {
            throw new RuntimeException("IOException occurred while copying default locale file", e);
        }
    }
    
}
