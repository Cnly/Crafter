package io.github.Cnly.Crafter.Crafter.framework.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class CrafterMainCommand extends AbstractCrafterCommand
{
    
    protected LinkedHashMap<String, ICrafterCommand> subcommands = new LinkedHashMap<>();
    
    public CrafterMainCommand()
    {
    }
    
    public CrafterMainCommand(JavaPlugin jp)
    {
        super.setHelp(ChatColor.AQUA + jp.getDescription().getName() + " " + jp.getDescription().getVersion() + " By " + jp.getDescription().getAuthors().get(0));
    }
    
    public List<ICrafterCommand> getSubcommands()
    {
        return Collections.unmodifiableList(new ArrayList<>(this.subcommands.values()));
    }
    
    public boolean addSubcommand(ICrafterCommand sub)
    {
        
        String lName = sub.getAction().toLowerCase();
        
        if(subcommands.containsKey(lName))
            return false;
        
        return subcommands.put(lName, sub) == null;
    }
    
    public boolean removeSubcommand(ICrafterCommand sub)
    {
        return subcommands.remove(sub) != null;
    }
    
    @Override
    protected void executeCommand(CommandSender sender, String[] args)
    {
        
        String action;
        
        if(args.length > 0)
            action = args[0].toLowerCase();
        else
            action = "";
        
        ICrafterCommand c = this.searchForCommand(action);
        if(c != null)
            c.execute(sender, args);
        else
        {
            sender.sendMessage(this.getHelp());
            for(ICrafterCommand cc : subcommands.values())
                sender.sendMessage(cc.getHelp());
        }
        
    }
    
    protected ICrafterCommand searchForCommand(String action)
    {
        
        ICrafterCommand result = subcommands.get(action);
        
        if(null != result)
            return result;
        
        if(!action.equals("help"))
            return this.searchForCommand("help");
        else
            return null;
    }
}
