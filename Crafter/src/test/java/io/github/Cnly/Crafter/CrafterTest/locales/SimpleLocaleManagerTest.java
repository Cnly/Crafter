package io.github.Cnly.Crafter.CrafterTest.locales;

import java.nio.file.Files;

import io.github.Cnly.Crafter.Crafter.framwork.locales.SimpleLocaleManager;
import io.github.Cnly.Crafter.CrafterTest.Definitions;

import org.bukkit.ChatColor;
import org.junit.Test;

import junit.framework.TestCase;

public class SimpleLocaleManagerTest extends TestCase
{
    
    @Test
    public void testSimpleLocaleManager()
    {
        
        SimpleLocaleManager slm = new SimpleLocaleManager("en_UK",
                Definitions.testLocaleDir.toFile(), true);
        
        assertTrue(Files.exists(Definitions.testLocaleDir));
        
        assertEquals("AAA", slm.getLocalizedString("stringA"));
        assertEquals(ChatColor.GREEN.toString() + ChatColor.RED + "CCC", slm.getLocalizedString("stringColour"));
        
    }
    
}
