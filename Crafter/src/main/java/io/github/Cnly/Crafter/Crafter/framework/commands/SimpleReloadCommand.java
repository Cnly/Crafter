package io.github.Cnly.Crafter.Crafter.framework.commands;

import java.util.List;

import io.github.Cnly.Crafter.Crafter.framework.configs.autoreloading.SimpleConfigReloader;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class SimpleReloadCommand extends AbstractCrafterCommand
{
    
    private SimpleConfigReloader reloader;
    
    private String reloadMessage = ChatColor.AQUA + "Configuration reloaded!";
    
    public SimpleReloadCommand(String group, String permission, String help)
    {
        this.reloader = new SimpleConfigReloader(group);
        this.setAction("reload");
        this.setPermission(permission);
        this.setHelp(help);
    }
    
    public SimpleReloadCommand(String permission, String help)
    {
        this("", permission, help);
    }
    
    @Override
    protected void executeCommand(CommandSender sender, String[] args)
    {
        
        this.reloader.doReload();
        sender.sendMessage(this.reloadMessage);
        
    }
    
    public SimpleReloadCommand addClass(Object classInstance)
    {
        this.reloader.addClass(classInstance);
        return this;
    }
    
    public boolean removeClass(Object classInstance)
    {
        return this.reloader.removeClass(classInstance);
    }
    
    public List<Object> getClasses()
    {
        return this.reloader.getClasses();
    }
    
    public String getReloadMessage()
    {
        return reloadMessage;
    }
    
    public void setReloadMessage(String reloadMessage)
    {
        this.reloadMessage = reloadMessage;
    }
    
}
