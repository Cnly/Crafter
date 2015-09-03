package io.github.Cnly.Crafter.Crafter.framework.notifies;

import io.github.Cnly.Crafter.Crafter.utils.CompatUtils;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class AbstractFutureNotifier implements IFutureNotifier
{
    
    private boolean notifyPlayers = false;
    private String permission;
    
    private String message;
    private boolean redPrefix = true;
    
    private boolean notifyConsole = true;
    private Level logLevel = Level.SEVERE;
    
    private JavaPlugin jp;
    
    public AbstractFutureNotifier(JavaPlugin jp)
    {
        this.jp = jp;
    }
    
    @Override
    public void doNotify()
    {
        
        StringBuilder sb = new StringBuilder(48);
        if(this.redPrefix)
            sb.append(ChatColor.RED.toString()).append('[').append(jp.getDescription().getName()).append(' ').append(this.logLevel.toString()).append("] ").append(this.message);
        
        String msgToSend = sb.toString();
        
        if(notifyConsole)
        {
            Bukkit.getConsoleSender().sendMessage(msgToSend);
        }
        
        if(notifyPlayers)
        {
            
            for(Player p : CompatUtils.getOnlinePlayers())
            {
                
                if(null == this.permission || !p.hasPermission(this.permission))
                    continue;
                
                p.sendMessage(msgToSend);
                
            }
            
        }
        
    }
    
    public boolean isNotifyPlayers()
    {
        return notifyPlayers;
    }
    
    public AbstractFutureNotifier setNotifyPlayers(boolean notifyPlayers)
    {
        this.notifyPlayers = notifyPlayers;
        return this;
    }
    
    public String getPermission()
    {
        return permission;
    }
    
    public AbstractFutureNotifier setPermission(String permission)
    {
        this.permission = permission;
        return this;
    }
    
    public String getMessage()
    {
        return message;
    }
    
    public AbstractFutureNotifier setMessage(String message)
    {
        this.message = message;
        return this;
    }
    
    public boolean isRedPrefix()
    {
        return redPrefix;
    }
    
    public AbstractFutureNotifier setRedPrefix(boolean redPrefix)
    {
        this.redPrefix = redPrefix;
        return this;
    }
    
    public boolean isNotifyConsole()
    {
        return notifyConsole;
    }
    
    public AbstractFutureNotifier setNotifyConsole(boolean notifyConsole)
    {
        this.notifyConsole = notifyConsole;
        return this;
    }
    
    public Level getLogLevel()
    {
        return logLevel;
    }
    
    public AbstractFutureNotifier setLogLevel(Level logLevel)
    {
        this.logLevel = logLevel;
        return this;
    }
    
}
