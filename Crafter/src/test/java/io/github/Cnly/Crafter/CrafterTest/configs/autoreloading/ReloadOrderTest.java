package io.github.Cnly.Crafter.CrafterTest.configs.autoreloading;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import junit.framework.TestCase;

import org.junit.Test;

import io.github.Cnly.Crafter.Crafter.framework.configs.IConfigManager;
import io.github.Cnly.Crafter.Crafter.framework.configs.CrafterYamlConfigManager;
import io.github.Cnly.Crafter.Crafter.framework.configs.autoreloading.ReloadableConfig;
import io.github.Cnly.Crafter.Crafter.framework.configs.autoreloading.CrafterConfigReloader;
import io.github.Cnly.Crafter.Crafter.utils.IOUtils;
import io.github.Cnly.Crafter.CrafterTest.Definitions;

public class ReloadOrderTest extends TestCase
{
    
    static
    {
        try
        {
            IOUtils.copyFileFromStream(ReloadOrderTest.class.getResourceAsStream("/testConfig.yml"), new File(Definitions.testConfigDir.toFile(), "testConfig.yml"));
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    
    @ReloadableConfig(priority = Integer.MAX_VALUE, group = "group1")
    private CrafterYamlConfigManager config1 = new CrafterYamlConfigManager(
            new File(Definitions.testConfigDir.toString(), "testConfig.yml"),
            false, null);
    @ReloadableConfig
    private IConfigManager config2 = new CrafterYamlConfigManager(new File(
            Definitions.testConfigDir.toString(), "testConfig2.yml"), false, null);
    @ReloadableConfig(priority = Integer.MIN_VALUE, group = "group1")
    private IConfigManager config3 = new CrafterYamlConfigManager(new File(
            Definitions.testConfigDir.toString(), "testConfig3.yml"), false, null);
    
    @SuppressWarnings("unused")
    private CrafterYamlConfigManager neverReloadThis = new CrafterYamlConfigManager(new File(
            Definitions.testConfigDir.toString(), "testConfig4.yml"), false, null);
    
    @Test
    public void test() throws NoSuchMethodException, SecurityException,
            IllegalAccessException, IllegalArgumentException,
            InvocationTargetException
    {
        
        CrafterConfigReloader scr = new CrafterConfigReloader("group1");
        scr.addClass(this);
        
        Method m = scr.getClass().getDeclaredMethod("searchForConfigManagers");
        m.setAccessible(true);
        
        @SuppressWarnings("unchecked")
        ArrayList<IConfigManager> actual = (ArrayList<IConfigManager>)m.invoke(
                scr, new Object[0]);
        
        ArrayList<IConfigManager> expected = new ArrayList<>();
        expected.add(config1);
        expected.add(config3);
        
        assertEquals(expected, actual);
        
    }
    
}
