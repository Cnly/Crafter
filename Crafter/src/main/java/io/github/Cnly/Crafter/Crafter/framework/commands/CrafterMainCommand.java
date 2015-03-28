package io.github.Cnly.Crafter.Crafter.framework.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class CrafterMainCommand extends AbstractCrafterCommand
{
    
    protected List<ICrafterCommand> subcommands = new ArrayList<ICrafterCommand>();
    
    public CrafterMainCommand()
    {
    }
    
    public CrafterMainCommand(JavaPlugin jp)
    {
        super.setHelp(ChatColor.AQUA + jp.getDescription().getName() + " "
                + jp.getDescription().getVersion() + " By "
                + jp.getDescription().getAuthors().get(0));
    }
    
    public List<ICrafterCommand> getSubcommands()
    {
        return Collections.unmodifiableList(this.subcommands);
    }
    
    public boolean addSubcommand(ICrafterCommand sub)
    {
        
        for (ICrafterCommand c : subcommands)
        {
            
            if (c.equals(sub))
            {
                return false;
            }
            
        }
        
        return subcommands.add(sub);
    }
    
    public boolean removeSubcommand(ICrafterCommand sub)
    {
        return subcommands.remove(sub);
    }
    
    @Override
    protected void executeCommand(CommandSender sender, String[] args)
    {
        
        String action;
        
        if (args.length > 0)
            action = args[0].toLowerCase();
        else
            action = "";
        
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
    
    protected ICrafterCommand searchForCommand(String action)
    {
        
        for (ICrafterCommand c : subcommands)
        {
            
            if (c.getAction().toLowerCase().equals(action))
            {
                return c;
            }
            
        }
        
        if (!action.equals("help"))
            return this.searchForCommand("help");
        else
            return null;
    }
}
