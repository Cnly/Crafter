package io.github.Cnly.Crafter.CrafterTest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Definitions
{
    
    public static Path testConfigDir = Paths.get("target/test/config");
    public static Path testLocaleDir = Paths.get("target/test/locale");
    
    static
    {
        try
        {
            Files.createDirectories(testConfigDir);
            Files.createDirectories(testLocaleDir);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    
    
}
