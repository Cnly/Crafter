package io.github.Cnly.Crafter.CrafterTest.commands;

import io.github.Cnly.Crafter.Crafter.framework.commands.AbstractCrafterCommand;
import io.github.Cnly.Crafter.Crafter.framework.commands.CrafterMainCommand;
import io.github.Cnly.Crafter.Crafter.framework.commands.ICrafterCommand;

import org.bukkit.command.CommandSender;
import org.junit.Test;

import junit.framework.TestCase;

public class ArgsShiftTest extends TestCase
{
    
    @Test
    public void test()
    {
        
        ICrafterCommand cc = new AbstractCrafterCommand()
        {
            @Override
            protected void executeCommand(CommandSender sender, String[] args)
            {
                assertEquals(1, args.length);
                assertEquals("second", args[0]);
            }
        };
        
        cc.setArgumentOffset(1);
        
        cc.execute(null, new String[]{"first", "second"});
        
        ////
        
        ICrafterCommand subsub = new AbstractCrafterCommand()
        {
            @Override
            protected void executeCommand(CommandSender sender, String[] args)
            {
                assertEquals(1, args.length);
                assertEquals("subsubFunction", args[0]);
            }
        }.setAction("subsubFunction");
        
        CrafterMainCommand sub = (CrafterMainCommand)new CrafterMainCommand().setArgumentOffset(1).setAction("subFunction");
        sub.addSubcommand(subsub);
        
        CrafterMainCommand main = new CrafterMainCommand();
        main.addSubcommand(sub);
        
        main.execute(null, new String[]{"subFunction", "subsubFunction"});
        
    }
    
}
