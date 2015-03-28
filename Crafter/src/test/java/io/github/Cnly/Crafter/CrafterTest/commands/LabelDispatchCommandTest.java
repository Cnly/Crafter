package io.github.Cnly.Crafter.CrafterTest.commands;

import io.github.Cnly.Crafter.Crafter.framework.commands.AbstractCrafterCommand;
import io.github.Cnly.Crafter.Crafter.framework.commands.LabelDispatchCommand;

import org.bukkit.command.CommandSender;
import org.junit.Test;

import junit.framework.TestCase;

public class LabelDispatchCommandTest extends TestCase
{
    
    private boolean labelCmd1Pass = false;
    private boolean helpCmdPass = false;
    
    @Test
    public void test()
    {
        
        AbstractCrafterCommand labelCmd1 = new AbstractCrafterCommand()
        {
            @Override
            protected void executeCommand(CommandSender sender, String[] args)
            {
                labelCmd1Pass = true;
            }
        };
        labelCmd1.setAction("l1");
        
        AbstractCrafterCommand helpCmd = new AbstractCrafterCommand()
        {
            @Override
            protected void executeCommand(CommandSender sender, String[] args)
            {
                helpCmdPass = true;
            }
        };
        helpCmd.setAction("help");
        
        LabelDispatchCommand mainCmd = new LabelDispatchCommand();
        mainCmd.addSubcommand(helpCmd);
        mainCmd.addSubcommand(labelCmd1);
        
        assertFalse(labelCmd1Pass);
        assertFalse(helpCmdPass);
        
        mainCmd.onCommand(null, null, "unknownLabel", new String[]{"aaa", "bbb"});
        assertFalse(labelCmd1Pass);
        assertTrue(helpCmdPass);
        helpCmdPass = false;
        
        mainCmd.onCommand(null, null, "help", new String[]{"aaa", "bbb"});
        assertFalse(labelCmd1Pass);
        assertTrue(helpCmdPass);
        helpCmdPass = false;
        
        mainCmd.onCommand(null, null, "l1", new String[]{"aaa"});
        assertTrue(labelCmd1Pass);
        assertFalse(helpCmdPass);
        
    }
    
}
