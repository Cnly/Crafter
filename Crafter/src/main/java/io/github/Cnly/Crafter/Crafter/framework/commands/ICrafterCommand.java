package io.github.Cnly.Crafter.Crafter.framework.commands;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public interface ICrafterCommand extends CommandExecutor
{
    
    public boolean getPlayerNeeded();
    
    public String getPlayerNeededNotice();
    
    public String getPermission();
    
    public String getPermissionNeededNotice();
    
    public String getHelp();
    
    public String getAction();
    
    public void execute(CommandSender sender, String[] args);
    
    @Override
    public boolean equals(Object obj);
    
    @Override
    public int hashCode();
    
}
