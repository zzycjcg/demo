package com.github.zzycjcg.demo.io;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileReaderTest
{
    
    public static void main(String[] args)
        throws IOException
    {
        test();
    }
    
    public static void test()
        throws IOException
    {
        FileReader reader = new FileReader(new File("F:/transcode/file.txt"));
        
        File dest = new File("D:/file.txt");
        if (!dest.exists())
        {
            dest.createNewFile();
        }
        FileWriter writer = new FileWriter(dest);
        
        char[] buffer = new char[32];
        int n;
        while ((n = reader.read(buffer)) != -1)
        {
            writer.write(buffer, 0, n);
        }
        writer.flush();
        reader.close();
        writer.close();
    }
}
