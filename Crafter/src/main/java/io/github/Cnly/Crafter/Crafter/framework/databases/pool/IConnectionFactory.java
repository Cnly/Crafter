package io.github.Cnly.Crafter.Crafter.framework.databases.pool;

import java.sql.Connection;

public interface IConnectionFactory
{
    
    public Connection createConnection();
    
}
