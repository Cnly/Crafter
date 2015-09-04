package io.github.Cnly.Crafter.CrafterTest.databases.pool;

import io.github.Cnly.Crafter.Crafter.framework.databases.pool.CrafterConnectionFactory;
import io.github.Cnly.Crafter.Crafter.framework.databases.pool.CrafterConnectionPool;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class ConnectionPoolTest
{
    
    @Test
    public void testPool() throws SQLException
    {
        
        final ArrayList<Connection> createdConnections = new ArrayList<>();
        
        CrafterConnectionFactory spyFactory = spy(CrafterConnectionFactory.class);
        doAnswer(new Answer<Connection>(){
            @Override
            public Connection answer(InvocationOnMock invocation) throws Throwable
            {
                
                Connection mockedConnection = mock(Connection.class);
                when(mockedConnection.isValid(anyInt())).thenReturn(true);
                
                createdConnections.add(mockedConnection);
                
                return mockedConnection;
            }
        }).when(spyFactory).createConnection();
        
        CrafterConnectionPool pool = new CrafterConnectionPool(spyFactory);
        
        Connection conn = pool.getConnection();
        assertEquals(1, createdConnections.size());
        assertSame(createdConnections.get(0), conn);
        
        Connection conn2 = pool.getConnection();
        assertEquals(2, createdConnections.size());
        assertNotSame(conn, conn2);
        assertSame(createdConnections.get(1), conn2);
        
        pool.releaseConnection(conn2);
        conn2 = pool.getConnection();
        assertNotSame(conn, conn2);
        assertSame(createdConnections.get(1), conn2);
        
        doAnswer(new Answer<Connection>()
        {
            @Override
            public Connection answer(InvocationOnMock invocation) throws Throwable
            {
                
                Connection mockedBrokenConnection = mock(Connection.class);
                when(mockedBrokenConnection.isValid(anyInt())).thenReturn(false);
                
                createdConnections.add(mockedBrokenConnection);
                
                return mockedBrokenConnection;
            }
        }).when(spyFactory).createConnection();
        
        Connection broken = pool.getConnection();
        pool.releaseConnection(broken);
        assertEquals(3, createdConnections.size());
        assertSame(broken, createdConnections.get(2));
        
        Connection broken2 = pool.getConnection();
        assertEquals(4, createdConnections.size());
        for(Connection c : createdConnections)
        {
            if(c != broken2) assertNotSame(c, broken2);
        }
        
        for(Connection c : createdConnections)
        {
            verify(c, never()).close();
        }
        
        pool.releaseConnection(conn);
        pool.releaseConnection(broken);
        pool.releaseConnection(broken2);
        
        pool.shutdownGracefully();
        
        for(Connection c : createdConnections)
        {
            if(c != conn2) verify(c).close();
        }
        verify(conn2, never()).close();
        
        assertNull(pool.getConnection());
        
        pool.releaseConnection(conn2);
        verify(conn2).close();
        
    }
    
}
