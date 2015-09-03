package io.github.Cnly.Crafter.Crafter.framework.databases.pool;

import java.sql.Connection;
import java.sql.DriverManager;

public class CrafterConnectionFactory implements IConnectionFactory
{
    
    protected String dbType;
    protected String host;
    protected int port;
    protected String username;
    protected String password;
    protected String database;
    
    protected String url;
    
    @Override
    public Connection createConnection()
    {
        
        if(null == url)
        {
            StringBuilder sb = new StringBuilder("jdbc:").append(this.dbType).append("://").append(this.host).append(':').append(this.port);
            if(null != this.database)
            {
                sb.append('/').append(this.database);
            }
            url = sb.toString();
        }
        
        if(null != this.password && null != this.username)
        {
            
            try
            {
                return DriverManager.getConnection(url, this.username, this.password);
            }
            catch(Exception e)
            {
                throw new RuntimeException("Cannot open connection to database", e);
            }
            
        }
        else
        {
            
            try
            {
                return DriverManager.getConnection(url);
            }
            catch(Exception e)
            {
                throw new RuntimeException("Cannot open connection to database", e);
            }
            
        }
        
    }
    
    public String getDbType()
    {
        return dbType;
    }
    
    public CrafterConnectionFactory setDbType(String dbType)
    {
        this.dbType = dbType;
        this.url = null;
        return this;
    }
    
    public String getHost()
    {
        return host;
    }
    
    public CrafterConnectionFactory setHost(String host)
    {
        this.host = host;
        this.url = null;
        return this;
    }
    
    public int getPort()
    {
        return port;
    }
    
    public CrafterConnectionFactory setPort(int port)
    {
        this.port = port;
        this.url = null;
        return this;
    }
    
    public String getUsername()
    {
        return username;
    }
    
    public CrafterConnectionFactory setUsername(String username)
    {
        this.username = username;
        this.url = null;
        return this;
    }
    
    public String getPassword()
    {
        return password;
    }
    
    public CrafterConnectionFactory setPassword(String password)
    {
        this.password = password;
        this.url = null;
        return this;
    }

    public String getDatabase()
    {
        return database;
    }

    public CrafterConnectionFactory setDatabase(String database)
    {
        this.database = database;
        this.url = null;
        return this;
    }
    
}
