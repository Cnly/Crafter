package io.github.Cnly.Crafter.CrafterTest.configs;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.github.Cnly.Crafter.Crafter.framework.configs.CrafterYamlConfigManager;
import io.github.Cnly.Crafter.CrafterTest.Definitions;

import org.bukkit.plugin.java.JavaPlugin;
import org.junit.After;
import org.junit.Test;

public class CrafterYamlConfigMangerTest
{
    
    @After
    public void cleanup() throws IOException
    {
        for(File f : Definitions.testConfigDir.toFile().listFiles())
        {
            f.delete();
        }
        Files.delete(Definitions.testConfigDir);
    }
    
    @Test
    public void testFileCreation() throws IOException
    {
        
        
        JavaPlugin mockedPlugin = mock(JavaPlugin.class);
        when(mockedPlugin.getResource("testConfig.yml")).thenReturn(this.getClass().getResourceAsStream("/testConfig.yml"));
        
        new CrafterYamlConfigManager(new File(Definitions.testConfigDir.toFile(), "testConfig.yml"), true, mockedPlugin);
        
        assertTrue(Files.exists(Paths.get("target/test/config/testConfig.yml")));
        
        assertFalse(Files.exists(Paths.get("target/test/config/404.yml")));
        CrafterYamlConfigManager sycm = new CrafterYamlConfigManager(new File(Definitions.testConfigDir.toFile(), "404.yml"), false, null);
        sycm.save();
        assertTrue(Files.exists(Paths.get("target/test/config/404.yml")));
        
    }
    
    @Test
    public void testValues()
    {
        
        JavaPlugin mockedPlugin = mock(JavaPlugin.class);
        when(mockedPlugin.getResource("testConfig.yml")).thenReturn(this.getClass().getResourceAsStream("/testConfig.yml"));
        
        CrafterYamlConfigManager sycm = new CrafterYamlConfigManager(new File(Definitions.testConfigDir.toFile(), "testConfig.yml"), true, mockedPlugin);
        
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
        
    }
    
    @Test
    public void testAddToList() throws IOException
    {
        
        JavaPlugin mockedPlugin = mock(JavaPlugin.class);
        when(mockedPlugin.getResource("testConfig.yml")).thenReturn(this.getClass().getResourceAsStream("/testConfig.yml"));
        
        CrafterYamlConfigManager cycm = new CrafterYamlConfigManager(new File(Definitions.testConfigDir.toFile(), "testConfig.yml"), true, mockedPlugin);
        
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
        
        JavaPlugin mockedPlugin = mock(JavaPlugin.class);
        when(mockedPlugin.getResource("testConfig.yml")).thenReturn(this.getClass().getResourceAsStream("/testConfig.yml"));
        
        CrafterYamlConfigManager cycm = new CrafterYamlConfigManager(new File(Definitions.testConfigDir.toFile(), "testConfig.yml"), true, mockedPlugin);
        
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
        
        JavaPlugin mockedPlugin = mock(JavaPlugin.class);
        when(mockedPlugin.getResource("testConfig.yml")).thenReturn(this.getClass().getResourceAsStream("/testConfig.yml"));
        
        CrafterYamlConfigManager cycm = new CrafterYamlConfigManager(new File(Definitions.testConfigDir.toFile(), "testConfig.yml"), true, mockedPlugin);
        
        assertFalse(cycm.isSet("root.testGetConfigurationSection"));
        
        assertEquals(cycm.getConfigurationSection("testGetConfigurationSection"), cycm.getConfigurationSection("testGetConfigurationSection"));
        
    }
    
}
