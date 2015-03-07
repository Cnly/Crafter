package io.github.Cnly.Crafter.Crafter.utils;

import java.io.File;
import java.io.IOException;

import org.bukkit.plugin.java.JavaPlugin;

public class ResourceUtils
{
    
    private ResourceUtils()
    {
        throw new AssertionError("This is a util class");
    }
    
    public static void copyFromJar(JavaPlugin jp, String location, File dest) throws IOException
    {
        try
        {
            IOUtils.copyFileFromStream(
                    jp.getResource(location.startsWith("/") ? location.substring(1)
                            : location), dest);
        }
        catch(IOException e)
        {
            throw new IOException(String.format("Unable to copy %s from jar to %s!", location, dest.getAbsolutePath()), e);
        }
    }
    
}
