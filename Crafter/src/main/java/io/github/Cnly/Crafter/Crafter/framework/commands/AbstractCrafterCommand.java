package io.github.Cnly.Crafter.Crafter.framework.commands;

import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class AbstractCrafterCommand implements ICrafterCommand
{
    
    protected String permission = null;
    protected boolean playerNeeded = false;
    
    protected String permissionNotice = ChatColor.RED + "You don't have enough permission!";
    protected String playerNeededNotice = ChatColor.RED + "The command must be executed by a player!";
    
    protected String action = null;
    protected String help = null;
    
    /**
     * The offset for the arguments. The arguments will be shifted before
     * {@link AbstractCrafterCommand#execute(CommandSender, String[])}'s calling
     * itself's
     * {@link AbstractCrafterCommand#executeCommand(CommandSender, String[])}. <br/>
     * <br/>
     * {@code -1} means no shifting will be done. This is also the default
     * value.
     * 
     * @see AbstractCrafterCommand#shiftedArguments(String[])
     * @see AbstractCrafterCommand#execute(CommandSender, String[])
     */
    protected int argumentOffset = -1;
    /**
     * The arguments will be passed to
     * {@link AbstractCrafterCommand#checkArgs(CommandSender, String[])}, which
     * calls {@link IArgumentValidator#validate(String[])}, AFTER being shifted. <br/>
     * If Java 8 is being used, a lambda expression is recommended.
     * 
     * @see AbstractCrafterCommand#checkArgs(CommandSender, String[])
     */
    protected IArgumentValidator argumentValidator;
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        this.execute(sender, args);
        return true;
    }
    
    protected abstract void executeCommand(CommandSender sender, String[] args);
    
    @Override
    public void execute(CommandSender sender, String[] args)
    {
        
        if(!checkPermission(sender) || !checkSender(sender))
            return;
        
        String[] sargs = shiftedArguments(args);
        if(!this.checkArgs(sender, sargs))
            return;
        
        this.executeCommand(sender, sargs);
        
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
    public int getArgumentOffset()
    {
        return argumentOffset;
    }
    
    @Override
    public IArgumentValidator getArgumentValidator()
    {
        return argumentValidator;
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
    public ICrafterCommand setArgumentOffset(int argsOffset)
    {
        this.argumentOffset = argsOffset;
        return this;
    }
    
    @Override
    public ICrafterCommand setArgumentValidator(IArgumentValidator argumentValidator)
    {
        this.argumentValidator = argumentValidator;
        return this;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((action == null) ? 0 : action.hashCode());
        result = prime * result + argumentOffset;
        result = prime * result + ((argumentValidator == null) ? 0 : argumentValidator.hashCode());
        result = prime * result + ((help == null) ? 0 : help.hashCode());
        result = prime * result + ((permission == null) ? 0 : permission.hashCode());
        result = prime * result + ((permissionNotice == null) ? 0 : permissionNotice.hashCode());
        result = prime * result + (playerNeeded ? 1231 : 1237);
        result = prime * result + ((playerNeededNotice == null) ? 0 : playerNeededNotice.hashCode());
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if(this == obj)
            return true;
        if(obj == null)
            return false;
        if(getClass() != obj.getClass())
            return false;
        AbstractCrafterCommand other = (AbstractCrafterCommand)obj;
        if(action == null)
        {
            if(other.action != null)
                return false;
        }
        else if(!action.equals(other.action))
            return false;
        if(argumentOffset != other.argumentOffset)
            return false;
        if(argumentValidator == null)
        {
            if(other.argumentValidator != null)
                return false;
        }
        else if(!argumentValidator.equals(other.argumentValidator))
            return false;
        if(help == null)
        {
            if(other.help != null)
                return false;
        }
        else if(!help.equals(other.help))
            return false;
        if(permission == null)
        {
            if(other.permission != null)
                return false;
        }
        else if(!permission.equals(other.permission))
            return false;
        if(permissionNotice == null)
        {
            if(other.permissionNotice != null)
                return false;
        }
        else if(!permissionNotice.equals(other.permissionNotice))
            return false;
        if(playerNeeded != other.playerNeeded)
            return false;
        if(playerNeededNotice == null)
        {
            if(other.playerNeededNotice != null)
                return false;
        }
        else if(!playerNeededNotice.equals(other.playerNeededNotice))
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
        
        if(this.permission == null)
            return true;
        if(sender.hasPermission(this.permission))
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
        
        if(!this.playerNeeded)
            return true;
        if(sender instanceof Player)
            return true;
        
        sender.sendMessage(playerNeededNotice);
        
        return false;
    }
    
    /**
     * Passes the arguments to {@code this.argumentValidator.validate(args)}, if
     * present.<br/>
     * If that method returns false, this method will send this.help to the
     * sender and return false.<br/>
     * If this.argumentValidator isn't set, this will always return true.
     * 
     * @param sender
     *            The command sender
     * @param args
     *            The arguments to check
     * @return true if the arguments are valid or this.argumentValidator isn't
     *         set. Otherwise false.
     */
    protected boolean checkArgs(CommandSender sender, String[] args)
    {
        
        if(null == this.argumentValidator)
            return true;
        if(this.argumentValidator.validate(args))
            return true;
        
        sender.sendMessage(this.help);
        
        return false;
    }
    
    protected String[] shiftedArguments(String[] args)
    {
        if(-1 == this.argumentOffset)
            return args;
        
        return Arrays.copyOfRange(args, this.argumentOffset, args.length);
    }
    
}
