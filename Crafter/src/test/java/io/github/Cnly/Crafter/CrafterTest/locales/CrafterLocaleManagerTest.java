package io.github.Cnly.Crafter.CrafterTest.locales;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.nio.file.Files;

import io.github.Cnly.Crafter.Crafter.framework.locales.CrafterLocaleManager;
import io.github.Cnly.Crafter.CrafterTest.Definitions;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.junit.After;
import org.junit.Test;

public class CrafterLocaleManagerTest
{
    
    @After
    public void cleanup() throws IOException
    {
        Files.delete(Definitions.testLocaleDir.resolve("en_UK.yml"));
        Files.delete(Definitions.testLocaleDir);
    }
    
    @Test
    public void testSimpleLocaleManager() throws IOException
    {
        
        JavaPlugin mockedPlugin = mock(JavaPlugin.class);
        when(mockedPlugin.getResource("locales/en_UK.yml")).thenReturn(this.getClass().getResourceAsStream("/locales/en_UK.yml"));
        
        CrafterLocaleManager slm = new CrafterLocaleManager("en_UK", Definitions.testLocaleDir.toFile(), true, mockedPlugin);
        
        assertTrue(Files.exists(Definitions.testLocaleDir));
        
        assertEquals("AAA", slm.getLocalizedString("stringA"));
        assertEquals(ChatColor.GREEN.toString() + ChatColor.RED + "CCC", slm.getLocalizedString("stringColour"));
        
        assertEquals("888", slm.getLocalizedString("here.msg1"));
        assertEquals("hey!", slm.getLocalizedString("here.greeting.hey"));
        
    }
    
}
