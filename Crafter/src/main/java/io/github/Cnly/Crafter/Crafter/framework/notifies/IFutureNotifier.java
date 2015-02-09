package io.github.Cnly.Crafter.Crafter.framework.notifies;

public interface IFutureNotifier extends INotifier
{
    
    public boolean isSet();
    
    public void cancel();
    
}
