package io.github.Cnly.Crafter.Crafter.framework.databases;

import java.sql.Connection;

public interface IDatabaseConnectionManager
{
    
    public Connection getConnection();
    
    public void close();
    
}
