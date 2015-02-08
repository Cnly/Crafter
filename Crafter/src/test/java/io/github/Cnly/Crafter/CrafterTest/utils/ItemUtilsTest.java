package io.github.Cnly.Crafter.CrafterTest.utils;

import io.github.Cnly.Crafter.Crafter.utils.ItemUtils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.junit.Test;

import junit.framework.TestCase;

public class ItemUtilsTest extends TestCase
{
    
    @SuppressWarnings("deprecation")
    @Test
    public void test()
    {
        
        String expected0 = "373";
        assertEquals(expected0, ItemUtils.getIdString(new ItemStack(373)));
        
        String expected1 = "373:8192";
        assertEquals(expected1, ItemUtils.getIdString(new ItemStack(Material.POTION, 1, (short)8192)));
        
    }
    
}
