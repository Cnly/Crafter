package io.github.Cnly.Crafter.Crafter.framework.databases.pool;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.LinkedBlockingQueue;

public class CrafterConnectionPool implements IConnectionPool
{
    
    protected IConnectionFactory factory;
    protected LinkedBlockingQueue<Connection> pool = new LinkedBlockingQueue<>();
    protected volatile boolean closed;
    
    public CrafterConnectionPool(IConnectionFactory factory)
    {
        this.factory = factory;
    }
    
    @Override
    public Connection getConnection()
    {
        
        if(closed)
            return null;
        
        Connection c = pool.poll();
        if(null == c)
        {
            return factory.createConnection();
        }
        else
        {
            if(!checkConnection(c))
            {
                return factory.createConnection();
            }
            else
            {
                return c;
            }
        }
        
    }
    
    @Override
    public void releaseConnection(Connection c)
    {
        if(!closed)
        {
            pool.offer(c);
        }
        else
        {
            try
            {
                c.close();
            }
            catch(SQLException e)
            {
                throw new RuntimeException("Unable to close connection!", e);
            }
        }
    }
    
    @Override
    public void shutdownGracefully()
    {
        closed = true;
        for(Connection c : pool)
        {
            try
            {
                c.close();
            }
            catch(SQLException e)
            {
                throw new RuntimeException("Unable to close connection!", e);
            }
        }
        pool = null;
    }
    
    /**
     * Check the connection status.
     * 
     * @param connection
     *            the connection to check
     * @return if the connection is ready
     */
    protected boolean checkConnection(Connection connection)
    {
        
        try
        {
            if(!connection.isValid(1))
            {
                return false;
            }
        }
        catch(SQLException e)
        {
            throw new RuntimeException("Cannot check connection status!", e);
        }
        
        return true;
    }
    
}
