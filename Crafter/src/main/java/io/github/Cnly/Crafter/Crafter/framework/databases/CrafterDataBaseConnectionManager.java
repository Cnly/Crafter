package io.github.Cnly.Crafter.Crafter.framework.databases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class CrafterDataBaseConnectionManager implements IDatabaseConnectionManager
{
    
    private Connection connection;
    
    private String dbType;
    private String host;
    private int port;
    private String username;
    private String password;
    private String database;
    
    private boolean isConnected;
    
    /**
     * Connects to the database
     * 
     * @return true if and only if the connection to the database is now
     *         open(and was NOT before). Otherwise false.
     */
    protected boolean connect()
    {
        
        if (this.isConnected)
            return false;
        
        StringBuilder sb = new StringBuilder("jdbc:").append(this.dbType)
                .append("://").append(this.host).append(':').append(this.port);
        if(null != this.database)
            sb.append('/').append(this.database);
        String url = sb.toString();
        
        if (null != this.password && null != this.username)
        {
            
            try
            {
                this.connection = DriverManager.getConnection(url,
                        this.username, this.password);
                return this.isConnected = true;
            }
            catch (Exception e)
            {
                throw new RuntimeException(
                        "Cannot open connection to database", e);
            }
            
        }
        else
        {
            
            try
            {
                this.connection = DriverManager.getConnection(url);
                return this.isConnected = true;
            }
            catch (Exception e)
            {
                throw new RuntimeException(
                        "Cannot open connection to database", e);
            }
            
        }
        
    }
    
    /**
     * Check the connection and try reconnecting automatically if needed.
     * 
     * @return if the connection is ready
     */
    protected boolean ensureConnection()
    {
        
        if (!this.isConnected)
            return this.connect();
        
        try
        {
            if (!this.connection.isValid(1))
            {
                
                this.isConnected = false;
                this.connection.close();
                
                return connect();
            }
            
        }
        catch (SQLException e)
        {
            throw new RuntimeException("Cannot reconnect to the database!", e);
        }
        
        return true;
    }
    
    @Override
    public void close()
    {
        
        if(!this.isConnected)
            return;
        
        try
        {
            this.connection.close();
        }
        catch (SQLException e)
        {
            throw new RuntimeException("Cannot close database connection", e);
        }
        
    }
    
    @Override
    public Connection getConnection()
    {
        
        if(!this.ensureConnection())
            throw new RuntimeException("Error occurred while calling ensureConnection()");
        
        return this.connection;
    }
    
    public String getDbType()
    {
        return dbType;
    }
    
    public CrafterDataBaseConnectionManager setDbType(String dbType)
    {
        this.dbType = dbType;
        return this;
    }
    
    public String getHost()
    {
        return host;
    }
    
    public CrafterDataBaseConnectionManager setHost(String host)
    {
        this.host = host;
        return this;
    }
    
    public int getPort()
    {
        return port;
    }
    
    public CrafterDataBaseConnectionManager setPort(int port)
    {
        this.port = port;
        return this;
    }
    
    public String getUsername()
    {
        return username;
    }
    
    public CrafterDataBaseConnectionManager setUsername(String username)
    {
        this.username = username;
        return this;
    }
    
    public String getPassword()
    {
        return password;
    }
    
    public CrafterDataBaseConnectionManager setPassword(String password)
    {
        this.password = password;
        return this;
    }
    
}
