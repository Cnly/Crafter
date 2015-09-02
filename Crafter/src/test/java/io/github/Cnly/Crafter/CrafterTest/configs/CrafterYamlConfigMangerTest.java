package io.github.Cnly.Crafter.CrafterTest.configs;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.github.Cnly.Crafter.Crafter.framework.configs.CrafterYamlConfigManager;
import io.github.Cnly.Crafter.Crafter.utils.IOUtils;
import io.github.Cnly.Crafter.CrafterTest.Definitions;

import org.junit.Test;

import junit.framework.TestCase;

public class CrafterYamlConfigMangerTest extends TestCase
{
    
    @Test
    public void testBasic() throws IOException
    {
        
        prepareConfigFile();
        
        CrafterYamlConfigManager sycm = new CrafterYamlConfigManager(new File(Definitions.testConfigDir.toFile(), "testConfig.yml"), false, null);
        
        assertTrue(Files.exists(Paths.get("target/test/config/testConfig.yml")));
        
        assertTrue(sycm.isSet("root.one"));
        assertFalse(sycm.isSet("root.orz"));
        
        assertEquals("a", sycm.getString("root.two.alpha"));
        
        HashMap<String, String> testMap = new HashMap<String, String>();
        testMap.put("alpha", "a");
        testMap.put("beta", "b");
        testMap.put("gamma", "y");
        assertEquals(testMap, sycm.getStringMap("root.two"));
        
        ArrayList<String> testList = new ArrayList<String>();
        testList.add("a");
        testList.add("b");
        assertEquals(testList, sycm.getStringList("root.one.list"));
        
        sycm = new CrafterYamlConfigManager(new File(Definitions.testConfigDir.toFile(), "404.yml"), false, null);
        sycm.set("a", "v");
        sycm.save();
        assertEquals("v", sycm.getString("a"));
        
    }
    
    private void prepareConfigFile() throws IOException
    {
        IOUtils.copyFileFromStream(this.getClass().getResourceAsStream("/testConfig.yml"), new File(Definitions.testConfigDir.toFile(), "testConfig.yml"));
    }
    
    @Test
    public void testAddToList() throws IOException
    {
        
        prepareConfigFile();
        
        CrafterYamlConfigManager cycm = new CrafterYamlConfigManager(new File(Definitions.testConfigDir.toFile(), "testConfig.yml"), false, null);
        
        List<String> expected = new ArrayList<>();
        expected.add("a");
        expected.add("b");
        assertEquals(expected, cycm.getStringList("root.one.list"));
        
        expected.add("testAddToList");
        cycm.addToList("root.one.list", "testAddToList");
        assertEquals(expected, cycm.getStringList("root.one.list"));
        
    }
    
    @Test
    public void testRemoveFromList() throws IOException
    {
        
        prepareConfigFile();
        
        CrafterYamlConfigManager cycm = new CrafterYamlConfigManager(new File(Definitions.testConfigDir.toFile(), "testConfig.yml"), false, null);
        
        List<String> expected = new ArrayList<>();
        expected.add("a");
        expected.add("b");
        assertEquals(expected, cycm.getStringList("root.one.list"));
        
        expected.remove("b");
        cycm.removeFromList("root.one.list", "b");
        assertEquals(expected, cycm.getStringList("root.one.list"));
        
    }
    
    @Test
    public void testGetConfigurationSection() throws IOException
    {
        
        prepareConfigFile();
        
        CrafterYamlConfigManager cycm = new CrafterYamlConfigManager(new File(Definitions.testConfigDir.toFile(), "testConfig.yml"), false, null);
        
        assertFalse(cycm.isSet("root.testGetConfigurationSection"));
        
        assertEquals(cycm.getConfigurationSection("testGetConfigurationSection"), cycm.getConfigurationSection("testGetConfigurationSection"));
        
    }
    
}
