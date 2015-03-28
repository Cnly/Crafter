package io.github.Cnly.Crafter.Crafter.framework.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class LabelDispatchCommand extends CrafterMainCommand
{
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label,
            String[] args)
    {
        this.execute(sender, new String[]{label});
        return true;
    }
    
    @Override
    public void execute(CommandSender sender, String[] args)
    {
        
        if(!checkPermission(sender) || !checkSender(sender))
            return;
        
        this.executeCommand(sender, args);
        
    }
    
    @Override
    protected void executeCommand(CommandSender sender, String[] args)
    {
        
        String action = args[0];
        
        ICrafterCommand c = this.searchForCommand(action);
        if (c != null)
            c.execute(sender, args);
        else
        {
            sender.sendMessage(this.getHelp());
            for (ICrafterCommand cc : subcommands)
                sender.sendMessage(cc.getHelp());
        }
        
    }
    
}
