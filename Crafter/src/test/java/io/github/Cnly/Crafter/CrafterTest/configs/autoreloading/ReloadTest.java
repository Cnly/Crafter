package io.github.Cnly.Crafter.CrafterTest.configs.autoreloading;

import java.io.File;
import java.io.IOException;

import io.github.Cnly.Crafter.Crafter.framework.configs.SimpleYamlConfigManager;
import io.github.Cnly.Crafter.Crafter.framework.configs.autoreloading.ReloadableConfig;
import io.github.Cnly.Crafter.Crafter.framework.configs.autoreloading.SimpleConfigReloader;
import io.github.Cnly.Crafter.Crafter.utils.ResourceUtils;
import io.github.Cnly.Crafter.CrafterTest.Definitions;

import org.junit.Test;

import junit.framework.TestCase;

public class ReloadTest extends TestCase
{
    
    @ReloadableConfig
    private SimpleYamlConfigManager scm = new SimpleYamlConfigManager(new File(
            Definitions.testConfigDir.toFile(), "reloadTestConfig.yml"), true);
    
    @Test
    public void test() throws IOException
    {
        
        assertEquals("value", scm.getString("key"));
        
        SimpleConfigReloader reloader = new SimpleConfigReloader();
        reloader.addClass(this);
        
        ResourceUtils.copyFromJar("/reloadTestConfig1.yml", new File(
                Definitions.testConfigDir.toFile(), "reloadTestConfig.yml"));
        
        assertEquals("value", scm.getString("key"));
        
        reloader.doReload();
        
        assertEquals("valve", scm.getString("key"));
        
    }
    
}
