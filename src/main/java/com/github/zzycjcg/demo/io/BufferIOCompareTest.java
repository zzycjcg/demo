package com.github.zzycjcg.demo.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class BufferIOCompareTest
{
    static final Object notifor = new Object();
    
    static final String source = "F:/source/lang.wmv";
    
    static final String buffered_dist = "I:/dist/buffered.mkv";
    
    static final String nonbuffered_dist = "I:/dist/nonbuffered.mkv";
    
    static final int buffer_size = 1024 * 1024;
    
    public static class BufferedIOTask implements Runnable
    {
        @Override
        public void run()
        {
            long start = System.currentTimeMillis();
            InputStream is = null;
            OutputStream os = null;
            try
            {
                is = new BufferedInputStream(new FileInputStream(source), buffer_size);
                os = new BufferedOutputStream(new FileOutputStream(buffered_dist), buffer_size);
                byte[] buffer = new byte[buffer_size];
                int n;
                while ((n = is.read(buffer)) != -1)
                {
                    os.write(buffer, 0, n);
                }
                os.flush();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            finally
            {
                try
                {
                    if (is != null)
                    {
                        is.close();
                    }
                    if (os != null)
                    {
                        os.close();
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
            long slecp = System.currentTimeMillis() - start;
            System.out.println("Buffer Cost " + String.valueOf(slecp));
            synchronized (notifor)
            {
                notifor.notify();
            }
        }
    }
    
    public static class NonBufferedIOTask implements Runnable
    {
        
        @Override
        public void run()
        {
            synchronized (notifor)
            {
                try
                {
                    notifor.wait();
                }
                catch (InterruptedException e1)
                {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
            
            long start = System.currentTimeMillis();
            InputStream is = null;
            OutputStream os = null;
            try
            {
                is = new FileInputStream(source);
                os = new FileOutputStream(nonbuffered_dist);
                byte[] buffer = new byte[buffer_size];
                int n;
                while ((n = is.read(buffer)) != -1)
                {
                    os.write(buffer, 0, n);
                }
                os.flush();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            finally
            {
                try
                {
                    if (is != null)
                    {
                        is.close();
                    }
                    if (os != null)
                    {
                        os.close();
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
            long slecp = System.currentTimeMillis() - start;
            System.out.println("Non Buffer Cost " + String.valueOf(slecp));
        }
        
    }
    
    public static void main(String[] args)
    {
        BufferedIOTask buffered = new BufferedIOTask();
        NonBufferedIOTask non_buffered = new NonBufferedIOTask();
        new Thread(buffered).start();
        new Thread(non_buffered).start();
    }
    
}
