package com.github.zzycjcg.demo.io;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.StringWriter;

/**
 * PipedStream
 * 同一个进程内2个线程可以管道通信.
 */
public class CrossThreadIODemo
{
    
    public static void main(String[] args)
        throws IOException
    {
        PipedIODemo demo = new PipedIODemo();
        demo.connect();
    }
    
    static class PipedIODemo
    {
        final PipedOutputStream pipedOutputStream;
        
        final PipedInputStream pipedInputStream;
        
        public PipedIODemo()
            throws IOException
        {
            this.pipedOutputStream = new PipedOutputStream();
            this.pipedInputStream = new PipedInputStream(pipedOutputStream);
        }
        
        public void connect()
        {
            Thread outputStream = new Thread("output")
            {
                @Override
                public void run()
                {
                    try
                    {
                        pipedOutputStream.write(("我们Hello, this msg comes from: " + getName()).getBytes());
                        pipedOutputStream.flush();
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                    finally
                    {
                        try
                        {
                            pipedOutputStream.close();
                        }
                        catch (IOException e)
                        {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
            };
            Thread inputThread = new Thread("input")
            {
                @Override
                public void run()
                {
                    InputStreamReader reader = new InputStreamReader(pipedInputStream);
                    try
                    {
                        char[] buffer = new char[32];
                        StringWriter writer = new StringWriter();
                        int n;
                        while ((n = reader.read(buffer)) != -1)
                        {
                            writer.write(buffer, 0, n);
                        }
                        System.out.println(writer.toString());
                    }
                    catch (IOException e)
                    {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    finally
                    {
                        try
                        {
                            pipedInputStream.close();
                        }
                        catch (IOException e)
                        {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
            };
            outputStream.start();
            inputThread.start();
        }
    }
}
