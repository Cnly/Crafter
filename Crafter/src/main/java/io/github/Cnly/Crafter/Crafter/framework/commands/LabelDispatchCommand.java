package io.github.Cnly.Crafter.Crafter.framework.commands;

import java.util.Arrays;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class LabelDispatchCommand extends CrafterMainCommand
{
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label,
            String[] args)
    {
        
        String[] nargs = new String[args.length + 1];
        nargs[0] = label;
        for(int i = 0; i < args.length; i++)
            nargs[i + 1] = args[i];
        
        this.execute(sender, nargs);
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
            c.execute(sender, Arrays.copyOfRange(args, 1, args.length));
        else
        {
            sender.sendMessage(this.getHelp());
            for (ICrafterCommand cc : subcommands.values())
                sender.sendMessage(cc.getHelp());
        }
        
    }
    
}
