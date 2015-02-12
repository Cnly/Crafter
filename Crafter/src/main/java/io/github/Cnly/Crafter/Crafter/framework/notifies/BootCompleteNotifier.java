package io.github.Cnly.Crafter.Crafter.framework.notifies;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class BootCompleteNotifier extends AbstractFutureNotifier implements
        Listener
{
    
    private long lastPluginEnableTime;
    private int delay;
    
    private BukkitTask checkTask;
    
    /**
     * Calls the constructor BootCompleteNotifier(jp, 2000)
     * 
     * @param jp
     *            JavaPlugin used to register Listener and BukkitRunnable
     */
    public BootCompleteNotifier(JavaPlugin jp)
    {
        this(jp, 2000);
    }
    
    /**
     * BootCompleteNotifier send its notify message when
     * System.currentTimeMillis() - lastPluginEnableTime >= delay.
     * 
     * @param jp
     *            JavaPlugin used to register Listener and BukkitRunnable
     * @param delay
     *            delay time in milliseconds
     */
    public BootCompleteNotifier(JavaPlugin jp, int delay)
    {
        super(jp);
        Bukkit.getPluginManager().registerEvents(this, jp);
        
        this.delay = delay;
        this.checkTask = new CheckTask().runTaskTimer(jp, 20L, 20L);
        
    }
    
    @EventHandler
    public void onPluginEnable(PluginEnableEvent e)
    {
        this.lastPluginEnableTime = System.currentTimeMillis();
    }
    
    private class CheckTask extends BukkitRunnable
    {
        
        @Override
        public void run()
        {
            
            if (!(System.currentTimeMillis()
                    - BootCompleteNotifier.this.lastPluginEnableTime >= delay))
                return;
            
            BootCompleteNotifier.this.doNotify();
            BootCompleteNotifier.this.cancel();
            
        }
        
    }
    
    @Override
    public boolean isSet()
    {
        return null != this.checkTask;
    }
    
    @Override
    public void cancel()
    {
        if (null != this.checkTask)
        {
            this.checkTask.cancel();
            this.checkTask = null;
            HandlerList.unregisterAll(this);
        }
    }
    
}
