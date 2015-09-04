package io.github.Cnly.Crafter.CrafterTest.commands;

import static org.junit.Assert.*;
import io.github.Cnly.Crafter.Crafter.framework.commands.ArgumentLengthValidator;
import io.github.Cnly.Crafter.Crafter.framework.commands.ArgumentLengthValidator.ValidateMode;

import org.junit.Test;

public class ArgsLengthValidatorTest
{
    
    @Test
    public void test()
    {
        
        ArgumentLengthValidator exact = new ArgumentLengthValidator().setExactLength(5);
        ArgumentLengthValidator range = new ArgumentLengthValidator().setMode(ValidateMode.RANGE).setMinLength(3).setMaxLength(6);
        ArgumentLengthValidator list = new ArgumentLengthValidator().setMode(ValidateMode.LIST).setAllowedLengths(3, 6, 9);
        
        assertFalse(exact.validate(new String[]{"1"})); // 1
        assertTrue(exact.validate(new String[]{"1", "1", "1", "1", "1"})); // 5
        
        assertFalse(range.validate(new String[]{"1"})); // 1
        assertTrue(range.validate(new String[]{"1", "1", "1"})); // 3
        assertTrue(range.validate(new String[]{"1", "1", "1", "1"})); // 4
        assertTrue(range.validate(new String[]{"1", "1", "1", "1", "1", "1"})); // 6
        assertFalse(range.validate(new String[]{"1", "1", "1", "1", "1", "1", "1"})); // 7
        
        assertFalse(list.validate(new String[]{"1", "1"})); // 2
        assertTrue(list.validate(new String[]{"1", "1", "1"})); // 3
        assertTrue(list.validate(new String[]{"1", "1", "1", "1", "1", "1"})); // 6
        assertTrue(list.validate(new String[]{"1", "1", "1", "1", "1", "1", "1", "1", "1"})); // 9
        
    }
    
}
