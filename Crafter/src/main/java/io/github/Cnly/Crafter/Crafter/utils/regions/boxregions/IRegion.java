package io.github.Cnly.Crafter.Crafter.utils.regions.boxregions;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public interface IRegion
{
    
    public String getWorldName();
    
    public boolean isInRegion(Player p);
    
    public boolean isInRegion(Location l);
    
}
