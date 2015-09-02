package io.github.Cnly.Crafter.CrafterTest.locales;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import io.github.Cnly.Crafter.Crafter.framework.locales.CrafterLocaleManager;
import io.github.Cnly.Crafter.Crafter.utils.IOUtils;
import io.github.Cnly.Crafter.CrafterTest.Definitions;

import org.bukkit.ChatColor;
import org.junit.Test;

import junit.framework.TestCase;

public class CrafterLocaleManagerTest extends TestCase
{
    
    @Test
    public void testSimpleLocaleManager() throws IOException
    {
        
        IOUtils.copyFileFromStream(this.getClass().getResourceAsStream("/locales/en_UK.yml"), new File(Definitions.testLocaleDir.toFile(), "en_UK.yml"));
        
        CrafterLocaleManager slm = new CrafterLocaleManager("en_UK",
                Definitions.testLocaleDir.toFile(), false, null);
        
        assertTrue(Files.exists(Definitions.testLocaleDir));
        
        assertEquals("AAA", slm.getLocalizedString("stringA"));
        assertEquals(ChatColor.GREEN.toString() + ChatColor.RED + "CCC",
                slm.getLocalizedString("stringColour"));
        
        assertEquals("888", slm.getLocalizedString("here.msg1"));
        assertEquals("hey!", slm.getLocalizedString("here.greeting.hey"));
        
    }
    
}
