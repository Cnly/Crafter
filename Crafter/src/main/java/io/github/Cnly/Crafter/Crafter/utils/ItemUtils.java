package io.github.Cnly.Crafter.Crafter.utils;

import org.bukkit.inventory.ItemStack;

public class ItemUtils
{
    
    private ItemUtils()
    {
        throw new AssertionError("This is an util class");
    }
    
    @SuppressWarnings("deprecation")
    public static ItemStack getItemByIdString(String idString)
    {
        
        if (idString.contains(":"))
        {
            String[] split = idString.split(":");
            return new ItemStack(Integer.parseInt(split[0]), 1,
                    Short.parseShort(split[1]));
        }
        else
        {
            return new ItemStack(Integer.parseInt(idString));
        }
        
    }
    
}
