package io.github.Cnly.Crafter.CrafterTest.configs.autoreloading;

import java.io.File;
import java.io.IOException;

import io.github.Cnly.Crafter.Crafter.framework.configs.CrafterYamlConfigManager;
import io.github.Cnly.Crafter.Crafter.framework.configs.autoreloading.ReloadableConfig;
import io.github.Cnly.Crafter.Crafter.framework.configs.autoreloading.CrafterConfigReloader;
import io.github.Cnly.Crafter.Crafter.utils.IOUtils;
import io.github.Cnly.Crafter.CrafterTest.Definitions;

import org.junit.Test;

import junit.framework.TestCase;

public class ReloadTest extends TestCase
{
    
    static
    {
        try
        {
            IOUtils.copyFileFromStream(ReloadOrderTest.class.getResourceAsStream("/reloadTestConfig.yml"), new File(Definitions.testConfigDir.toFile(), "reloadTestConfig.yml"));
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    
    @ReloadableConfig
    private CrafterYamlConfigManager scm = new CrafterYamlConfigManager(new File(Definitions.testConfigDir.toFile(), "reloadTestConfig.yml"), false, null);
    
    @Test
    public void test() throws IOException
    {
        
        assertEquals("value", scm.getString("key"));
        
        CrafterConfigReloader reloader = new CrafterConfigReloader();
        reloader.addClass(this);
        
        IOUtils.copyFileFromStream(this.getClass().getResourceAsStream("/reloadTestConfig1.yml"), new File(Definitions.testConfigDir.toFile(), "reloadTestConfig.yml"));
        
        assertEquals("value", scm.getString("key"));
        
        reloader.doReload();
        
        assertEquals("valve", scm.getString("key"));
        
    }
    
}
