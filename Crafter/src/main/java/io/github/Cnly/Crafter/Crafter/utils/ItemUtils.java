package io.github.Cnly.Crafter.Crafter.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemUtils
{
    
    private ItemUtils()
    {
        throw new AssertionError("This is a util class");
    }

    @Deprecated
    public static ItemStack getItemByIdString(String idString)
    {
        
        if(idString.contains(":"))
        {
            String[] split = idString.split(":");
            return new ItemStack(Integer.parseInt(split[0]), 1, Short.parseShort(split[1]));
        }
        else
        {
            return new ItemStack(Integer.parseInt(idString));
        }
        
    }

    @Deprecated
    public static String getIdString(ItemStack is)
    {

        String result = String.valueOf(is.getTypeId());
        short durability = is.getDurability();
        
        if(0 != durability)
            result += ":" + String.valueOf(durability);
        
        return result;
    }

    public static ItemStack getItemByTypeString(String typeString)
    {
        if(typeString.contains(":"))
        {
            String[] split = typeString.split(":");
            Material material = Material.getMaterial(split[0]);
            if(null == material)
            {
                throw new NoSuchElementException("Material not found: " + split[0]);
            }
            return new ItemStack(material, 1, Short.parseShort(split[1]));
        }
        else
        {
            Material material = Material.getMaterial(typeString);
            if(null == material)
            {
                throw new NoSuchElementException("Material not found: " + typeString);
            }
            return new ItemStack(material);
        }
    }

    public static String getTypeString(ItemStack is)
    {
        String result = is.getType().toString();
        short durability = is.getDurability();

        if(0 != durability)
            result += ":" + String.valueOf(durability);

        return result;
    }
    
    public static List<String> getLores(ItemStack item)
    {
        
        if(!item.hasItemMeta())
            return Collections.emptyList();
        
        ItemMeta meta = item.getItemMeta();
        
        if(!meta.hasLore())
            return Collections.emptyList();
        
        return meta.getLore();
    }
    
    public static ItemStack addLore(ItemStack item, String lore)
    {
        
        ItemMeta meta = item.getItemMeta();
        List<String> lores = meta.hasLore() ? meta.getLore() : new ArrayList<String>();
        lores.add(lore);
        meta.setLore(lores);
        item.setItemMeta(meta);
        
        return item;
    }
    
    public static ItemStack setLores(ItemStack item, List<String> lores)
    {
        
        ItemMeta meta = item.getItemMeta();
        meta.setLore(lores);
        item.setItemMeta(meta);
        
        return item;
    }
    
    public static String getDisplayName(ItemStack item)
    {
        
        ItemMeta meta = item.getItemMeta();
        
        if(!meta.hasDisplayName())
            return null;
        
        return meta.getDisplayName();
    }
    
    public static ItemStack setDisplayName(ItemStack item, String displayName)
    {
        
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(displayName);
        item.setItemMeta(meta);
        
        return item;
    }
    
}
