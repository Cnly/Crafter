package io.github.Cnly.Crafter.CrafterTest.utils;

import static org.junit.Assert.*;
import io.github.Cnly.Crafter.Crafter.utils.IOUtils;

import org.junit.Test;

public class IOUtilsTest
{
    
    @Test
    public void test()
    {
        
        String expected = "testingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtesting"
                + System.getProperty("line.separator");
        
        assertEquals(expected, IOUtils.readStringFromStream(this.getClass().getResourceAsStream("/iotest.txt"), "utf-8"));
        
    }
    
}
