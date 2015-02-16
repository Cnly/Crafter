package io.github.Cnly.Crafter.Crafter.utils.regions.boxregions;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class BoxRegionSetter implements Listener
{
    
    private HashMap<UUID, Location[]> locationMap = new HashMap<>();
    private boolean ignoring = false;
    private Constructor<? extends IRegion> regionConstructor;
    private IBoxRegionSettingListener listener;
    
    public BoxRegionSetter(JavaPlugin jp, Class<? extends IRegion> regionClass,
            IBoxRegionSettingListener listener)
    {
        super();
        Bukkit.getPluginManager().registerEvents(this, jp);
        this.listener = listener;
        
        try
        {
            this.regionConstructor = regionClass.getConstructor(Location.class,
                    Location.class);
        }
        catch (NoSuchMethodException | SecurityException e)
        {
            throw new RuntimeException("Unable to get an IRegion constructor!",
                    e);
        }
        
    }
    
    public boolean isIgnoring()
    {
        return this.ignoring;
    }
    
    public BoxRegionSetter setIgnoring(boolean ignoring)
    {
        this.ignoring = ignoring;
        return this;
    }
    
    @EventHandler
    public void onClick(PlayerInteractEvent e)
    {
        
        if (this.ignoring)
            return;
        
        Player p = e.getPlayer();
        UUID pid = p.getUniqueId();
        
        if (!this.locationMap.containsKey(pid))
            return;
        
        switch (e.getAction())
        {
        
        case LEFT_CLICK_AIR:
            // fall through
        case RIGHT_CLICK_AIR:
            return;
            
        case LEFT_CLICK_BLOCK:
            e.setCancelled(true);
            this.setLocation(p, e.getClickedBlock().getLocation(), 1);
            break;
        case RIGHT_CLICK_BLOCK:
            e.setCancelled(true);
            this.setLocation(p, e.getClickedBlock().getLocation(), 2);
            break;
        default:
            return;
        }
        
    }
    
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e)
    {
        this.exitSettingMode(e.getPlayer());
    }
    
    /**
     * Enter setting mode for a player
     * 
     * @param p
     *            player
     * @return true if and only if the player is now entering the setting
     *         mode(it is NOT in the setting mode before)
     */
    public boolean enterSettingMode(Player p)
    {
        
        UUID pid = p.getUniqueId();
        if (null == this.locationMap.get(pid))
        {
            this.locationMap.put(pid, new Location[2]);
            this.listener.onEnterSettingMode(p);
            return true;
        }
        else
        {
            return false;
        }
        
    }
    
    /**
     * Exit setting mode for a player
     * 
     * @param p
     *            player
     * @return true if and only if the player is now exiting the setting
     *         mode(it is in the setting mode before)
     */
    public boolean exitSettingMode(Player p)
    {
        
        UUID pid = p.getUniqueId();
        if (null == this.locationMap.get(pid))
        {
            return false;
        }
        else
        {
            this.locationMap.remove(pid);
            this.listener.onExitSettingMode(p);
            return true;
        }
        
    }
    
    protected void setLocation(Player p, Location l, int index)
    {
        UUID pid = p.getUniqueId();
        Location[] currentLocations = this.locationMap.get(pid);
        currentLocations[index - 1] = l;
        
        Location loc1 = currentLocations[0];
        Location loc2 = currentLocations[1];
        
        this.listener.onLocationSet(p, index, l);
        
        if (null == loc1 || null == loc2)
            return;
        IRegion r = null;
        try
        {
            r = this.regionConstructor.newInstance(loc1, loc2);
        }
        catch (InstantiationException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException e)
        {
            throw new RuntimeException("Unable to create IRegion instance!", e);
        }
        
        this.listener.onRegionBuilt(p, r);
        
        this.exitSettingMode(p);
        
    }
    
}
