package io.github.Cnly.Crafter.Crafter.framework.commands;

import java.util.List;

import io.github.Cnly.Crafter.Crafter.framework.configs.autoreloading.CrafterConfigReloader;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class CrafterReloadCommand extends AbstractCrafterCommand
{
    
    private CrafterConfigReloader reloader;
    
    private String reloadMessage = ChatColor.AQUA + "Configuration reloaded!";
    
    public CrafterReloadCommand(String group)
    {
        this.reloader = new CrafterConfigReloader(group);
        this.setAction("reload");
    }
    
    public CrafterReloadCommand(String group, String permission, String help)
    {
        this.reloader = new CrafterConfigReloader(group);
        this.setAction("reload");
        this.setPermission(permission);
        this.setHelp(help);
    }
    
    public CrafterReloadCommand(String permission, String help)
    {
        this("", permission, help);
    }
    
    @Override
    protected void executeCommand(CommandSender sender, String[] args)
    {
        
        this.reloader.doReload();
        sender.sendMessage(this.reloadMessage);
        
    }
    
    public CrafterReloadCommand addClass(Object classInstance)
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
