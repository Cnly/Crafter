package io.github.Cnly.Crafter.Crafter.framework.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class AbstractCrafterCommand implements ICrafterCommand
{
    
    protected String permission = null;
    protected boolean playerNeeded = false;
    
    protected String permissionNotice = ChatColor.RED
            + "You don't have enough permission!";
    protected String playerNeededNotice = ChatColor.RED
            + "The command must be executed by a player!";
    
    protected String action = null;
    protected String help = null;
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label,
            String[] args)
    {
        this.execute(sender, args);
        return true;
    }
    
    protected abstract void executeCommand(CommandSender sender, String[] args);
    
    @Override
    public void execute(CommandSender sender, String[] args)
    {
        if (checkPermission(sender) && checkSender(sender))
            this.executeCommand(sender, args);
    }
    
    @Override
    public boolean getPlayerNeeded()
    {
        return this.playerNeeded;
    }
    
    @Override
    public String getPlayerNeededNotice()
    {
        return this.playerNeededNotice;
    }
    
    @Override
    public String getPermission()
    {
        return this.permission;
    }
    
    @Override
    public String getPermissionNeededNotice()
    {
        return this.permissionNotice;
    }
    
    @Override
    public String getHelp()
    {
        return this.help;
    }
    
    @Override
    public String getAction()
    {
        return this.action;
    }
    
    @Override
    public ICrafterCommand setPermission(String permission)
    {
        this.permission = permission;
        return this;
    }
    
    @Override
    public ICrafterCommand setPlayerNeeded(boolean playerNeeded)
    {
        this.playerNeeded = playerNeeded;
        return this;
    }
    
    @Override
    public ICrafterCommand setPermissionNeededNotice(String permissionNotice)
    {
        this.permissionNotice = permissionNotice;
        return this;
    }
    
    @Override
    public ICrafterCommand setPlayerNeededNotice(String playerNeededNotice)
    {
        this.playerNeededNotice = playerNeededNotice;
        return this;
    }
    
    @Override
    public ICrafterCommand setAction(String action)
    {
        this.action = action;
        return this;
    }
    
    @Override
    public ICrafterCommand setHelp(String help)
    {
        this.help = help;
        return this;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((action == null) ? 0 : action.hashCode());
        result = prime * result
                + ((permission == null) ? 0 : permission.hashCode());
        result = prime
                * result
                + ((permissionNotice == null) ? 0 : permissionNotice.hashCode());
        result = prime * result + (playerNeeded ? 1231 : 1237);
        result = prime
                * result
                + ((playerNeededNotice == null) ? 0 : playerNeededNotice
                        .hashCode());
        result = prime * result + ((help == null) ? 0 : help.hashCode());
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        AbstractCrafterCommand other = (AbstractCrafterCommand) obj;
        if (action == null)
        {
            if (other.action != null)
                return false;
        }
        else if (!action.equals(other.action))
            return false;
        if (permission == null)
        {
            if (other.permission != null)
                return false;
        }
        else if (!permission.equals(other.permission))
            return false;
        if (permissionNotice == null)
        {
            if (other.permissionNotice != null)
                return false;
        }
        else if (!permissionNotice.equals(other.permissionNotice))
            return false;
        if (playerNeeded != other.playerNeeded)
            return false;
        if (playerNeededNotice == null)
        {
            if (other.playerNeededNotice != null)
                return false;
        }
        else if (!playerNeededNotice.equals(other.playerNeededNotice))
            return false;
        if (help == null)
        {
            if (other.help != null)
                return false;
        }
        else if (!help.equals(other.help))
            return false;
        return true;
    }
    
    /**
     * Checks if the permission meets
     * 
     * @param sender
     *            the command sender
     * @return true if meet
     */
    protected boolean checkPermission(CommandSender sender)
    {
        
        if (this.permission == null)
            return true;
        if (sender.hasPermission(this.permission))
            return true;
        
        sender.sendMessage(permissionNotice);
        
        return false;
    }
    
    /**
     * Checks if the sender meets
     * 
     * @param sender
     *            the command sender
     * @return true if meet
     */
    protected boolean checkSender(CommandSender sender)
    {
        
        if (!this.playerNeeded)
            return true;
        if (sender instanceof Player)
            return true;
        
        sender.sendMessage(playerNeededNotice);
        
        return false;
    }
    
}
