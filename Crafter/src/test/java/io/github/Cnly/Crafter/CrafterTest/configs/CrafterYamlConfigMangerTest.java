package io.github.Cnly.Crafter.CrafterTest.configs;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

import io.github.Cnly.Crafter.Crafter.framework.configs.CrafterYamlConfigManager;
import io.github.Cnly.Crafter.CrafterTest.Definitions;

import org.junit.Test;

import junit.framework.TestCase;

public class CrafterYamlConfigMangerTest extends TestCase
{
    
    @Test
    public void test()
    {
        
//        try
//        {
//            Files.createDirectories(Paths.get("target/test/config"));
//        }
//        catch (IOException e)
//        {
//            e.printStackTrace();
//        }
//        
//        CrafterYamlConfigManager sycm = new CrafterYamlConfigManager(new File(Definitions.testConfigDir.toFile(), "testConfig.yml"), true);
//        
//        assertTrue(Files.exists(Paths.get("target/test/config/testConfig.yml")));
//        
//        assertTrue(sycm.isSet("root.one"));
//        assertFalse(sycm.isSet("root.orz"));
//        
//        assertEquals("a", sycm.getString("root.two.alpha"));
//        
//        HashMap<String, String> testMap = new HashMap<String, String>();
//        testMap.put("alpha", "a");
//        testMap.put("beta", "b");
//        testMap.put("gamma", "y");
//        assertEquals(testMap, sycm.getStringMap("root.two"));
//        
//        ArrayList<String> testList = new ArrayList<String>();
//        testList.add("a");
//        testList.add("b");
//        assertEquals(testList, sycm.getStringList("root.one.list"));
//        
//        sycm = new CrafterYamlConfigManager(new File(Definitions.testConfigDir.toFile(), "404.yml"), false);
//        sycm.set("a", "v");
//        sycm.save();
//        assertEquals("v", sycm.getString("a"));
        
    }
    
}
