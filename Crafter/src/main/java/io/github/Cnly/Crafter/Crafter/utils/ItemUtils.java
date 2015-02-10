package io.github.Cnly.Crafter.Crafter.utils;

import org.bukkit.inventory.ItemStack;

public class ItemUtils
{
    
    private ItemUtils()
    {
        throw new AssertionError("This is a util class");
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
    
    public static String getIdString(ItemStack is)
    {
        
        @SuppressWarnings("deprecation")
        String result = String.valueOf(is.getTypeId());
        short durability = is.getDurability();
        
        if(0 != durability)
            result += ":" + String.valueOf(durability);
        
        return result;
    }
    
}
