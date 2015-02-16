package io.github.Cnly.Crafter.Crafter.utils.regions.boxregions;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public interface IBoxRegionSettingListener
{
    
    public void onEnterSettingMode(Player p);
    
    public void onExitSettingMode(Player p);
    
    public void onLocationSet(Player p, int index, Location l);
    
    public void onRegionBuilt(Player p, IRegion region);
    
}
