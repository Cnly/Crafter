package io.github.Cnly.Crafter.CrafterTest.utils;

import io.github.Cnly.Crafter.Crafter.utils.IOUtils;

import org.junit.Test;

import junit.framework.TestCase;

public class IOUtilsTest extends TestCase
{
    
    @Test
    public void test()
    {
        
        String expected = "testingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtestingtesting\n";
        
        assertEquals(expected, IOUtils.readStringFromStream(this.getClass()
                .getResourceAsStream("/iotest.txt"), "utf-8"));
        
    }
    
}
