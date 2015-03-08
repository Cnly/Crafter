package io.github.Cnly.Crafter.Crafter.framework.commands;

public class ArgumentLengthValidator implements IArgumentValidator
{
    
    private ValidateMode mode = ValidateMode.EXACT;
    
    private int exactLength;
    
    private int minLength;
    private int maxLength;
    
    private int[] allowedLengths;
    
    @Override
    public boolean validate(String[] args)
    {
        switch(this.mode)
        {
        case EXACT:
            return args.length == this.exactLength;
        case RANGE:
            return args.length >= this.minLength
                    && args.length <= this.maxLength;
        case LIST:
            for(int allowed : this.allowedLengths)
                if(allowed == args.length)
                    return true;
            
            return false;
        default:
            return false;
        }
    }
    
    public ValidateMode getMode()
    {
        return mode;
    }
    
    public ArgumentLengthValidator setMode(ValidateMode mode)
    {
        this.mode = mode;
        return this;
    }
    
    public int getExactLength()
    {
        this.checkMode(ValidateMode.EXACT);
        return exactLength;
    }
    
    public ArgumentLengthValidator setExactLength(int exactLength)
    {
        this.checkMode(ValidateMode.EXACT);
        this.exactLength = exactLength;
        return this;
    }
    
    public int getMinLength()
    {
        this.checkMode(ValidateMode.RANGE);
        return minLength;
    }
    
    public ArgumentLengthValidator setMinLength(int minLength)
    {
        this.checkMode(ValidateMode.RANGE);
        this.minLength = minLength;
        return this;
    }
    
    public int getMaxLength()
    {
        this.checkMode(ValidateMode.RANGE);
        return maxLength;
    }
    
    public ArgumentLengthValidator setMaxLength(int maxLength)
    {
        this.checkMode(ValidateMode.RANGE);
        this.maxLength = maxLength;
        return this;
    }
    
    public int[] getAllowedLengths()
    {
        this.checkMode(ValidateMode.LIST);
        return allowedLengths;
    }
    
    public ArgumentLengthValidator setAllowedLengths(int[] allowedLengths)
    {
        this.checkMode(ValidateMode.LIST);
        this.allowedLengths = allowedLengths;
        return this;
    }
    
    protected boolean checkMode(ValidateMode expectedMode)
    {
        if(expectedMode != this.mode)
            throw new IllegalStateException(String.format(
                    "ValidateMode %s is required to act on this field!",
                    expectedMode));
        
        return true;
    }
    
    public enum ValidateMode
    {
        EXACT, RANGE, LIST;
    }
    
}
