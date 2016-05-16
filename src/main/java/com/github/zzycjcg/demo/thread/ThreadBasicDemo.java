package com.github.zzycjcg.demo.thread;

public class ThreadBasicDemo
{
    static Object notifior = new Object();
    
    static class Thread1 extends Thread
    {
        @Override
        public void run()
        {
            System.out.println("Thread1 start.");
            synchronized (notifior)
            {
                try
                {
                    System.out.println("Thread1 wait.");
                    notifior.wait();
                    System.out.println("Thread1 run.");
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
            System.out.println("Thread1 called.");
            System.out.println("Thread1 end.");
        }
    }
    
    static class Thread2 extends Thread
    {
        @Override
        public void run()
        {
            System.out.println("Thread2 start.");
            synchronized (notifior)
            {
                System.out.println("Thread2 notify.");
                notifior.notify();
                System.out.println("Thread2 exit synchronized.");
            }
            System.out.println("Thread2 called.");
            System.out.println("Thread2 end.");
        }
    }
    
    public static void main(String[] args)
    {
        new Thread1().start();
        new Thread2().start();
    }
}
