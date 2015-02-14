package io.github.Cnly.Crafter.Crafter.framework.locales;

public interface ILocaleManager
{
    
    /**
     * Gets the current locale
     * 
     * @return the current locale
     */
    public String getLocale();
    
    /**
     * Sets the current locale
     * 
     * @param locale
     *            the locale to set to
     */
    public void setLocale(String locale);
    
    /**
     * Gets the localized string by key
     * 
     * @param key
     *            the key of the string
     * @return the localized string
     */
    public String getLocalizedString(String key);
    
    /**
     * Copies the default file from the jar to the file
     */
    public void copyDefaultLocaleFile();
    
    /**
     * Copies the file specified from the jar to the file
     */
    public void copyDefaultLocaleFile(String resourceLocation);
    
}
