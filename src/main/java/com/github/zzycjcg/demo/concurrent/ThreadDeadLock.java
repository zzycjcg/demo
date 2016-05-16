package com.github.zzycjcg.demo.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
/**
 * 线程饥饿死锁：单线程化的线程池，当一个任务将另一个任务提交到相同的Executor中，
 * 并等待新任务的结果，总会发生死锁。
 */
public class ThreadDeadLock
{
    static ExecutorService exec = Executors.newSingleThreadExecutor();
    
    public static class LoadFileTask implements Callable<String>
    {
        private final String fileName;
        
        public LoadFileTask(String fileName)
        {
            this.fileName = fileName;
        }
        
        public String call()
            throws Exception
        {
            // Here's where we would actually read the file
            return fileName;
        }
    }
    
    public static class RenderPageTask implements Callable<String>
    {
        public String call()
            throws Exception
        {
            Future<String> header, footer;
            header = exec.submit(new LoadFileTask("header.html"));
            footer = exec.submit(new LoadFileTask("footer.html"));
            String page = renderBody();
            // Will deadlock -- task waiting for result of subtask
            return header.get() + page + footer.get();
        }
        
        private String renderBody()
        {
            // Here's where we would actually render the page
            return "";
        }
    }
    
    public static void main(String[] args)
    {
        Future<String> result = exec.submit(new RenderPageTask());
        try
        {
            System.out.println(result.get());
        }
        catch (InterruptedException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (ExecutionException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        exec.shutdown();
    }
}
