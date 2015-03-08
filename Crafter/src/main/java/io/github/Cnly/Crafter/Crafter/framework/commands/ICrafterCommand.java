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
    
    public int getArgumentOffset();
    
    public IArgumentValidator getArgumentValidator();
    
    public ICrafterCommand setPlayerNeeded(boolean playerNeeded);
    
    public ICrafterCommand setPlayerNeededNotice(String playerNeededNotice);
    
    public ICrafterCommand setPermission(String permission);
    
    public ICrafterCommand setPermissionNeededNotice(String permissionNotice);
    
    public ICrafterCommand setHelp(String help);
    
    public ICrafterCommand setAction(String action);
    
    public ICrafterCommand setArgumentOffset(int offset);
    
    public ICrafterCommand setArgumentValidator(IArgumentValidator validator);
    
    public void execute(CommandSender sender, String[] args);
    
    @Override
    public boolean equals(Object obj);
    
    @Override
    public int hashCode();
    
}
