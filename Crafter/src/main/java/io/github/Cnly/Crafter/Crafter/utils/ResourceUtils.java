package io.github.Cnly.Crafter.Crafter.utils;

import java.io.File;
import java.io.IOException;

public class ResourceUtils
{
    
    private ResourceUtils()
    {
        throw new AssertionError("This is an util class");
    }
    
    public static void copyFromJar(String location, File dest)
            throws IOException
    {
        IOUtils.copyFileFromStream(
                ResourceUtils.class.getResourceAsStream(location), dest);
    }
    
}
