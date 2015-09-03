package io.github.Cnly.Crafter.Crafter.framework.databases.pool;

import java.sql.Connection;

public interface IConnectionPool
{
    
    public Connection getConnection();
    
    public void releaseConnection(Connection c);
    
    public void shutdownGracefully();
    
}
