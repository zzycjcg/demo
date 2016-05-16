package com.github.zzycjcg.demo.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class DbBenchmark
{
    static String url = "jdbc:mysql://192.168.1.111:3306/test?user=zhu&password=brave123";
    
    // static String url = "jdbc:postgresql://localhost/test?user=test&password=test";
    static Random random = new Random();
    
    static CountDownLatch latch;
    
    public static void main(String[] args)
        throws Exception
    {
        benchmark();
    }
    
    static Connection getConnection()
        throws Exception
    {
        return DriverManager.getConnection(url);
    }
    
    static class MyThread extends Thread
    {
        Statement stmt;
        
        Connection conn;
        
        long read_time;
        
        long randow_read_time;
        
        long write_time;
        
        int start;
        
        int end;
        
        MyThread(int start, int count)
            throws Exception
        {
            super("MyThread-" + start);
            conn = getConnection();
            stmt = conn.createStatement();
            this.start = start;
            this.end = start + count;
        }
        
        void write()
            throws Exception
        {
            long t1 = System.currentTimeMillis();
            for (int i = start; i < end; i++)
            {
                String sql = "INSERT INTO test(f1, f2) VALUES(" + i + "," + i * 10 + ")";
                stmt.executeUpdate(sql);
            }
            
            long t2 = System.currentTimeMillis();
            write_time = t2 - t1;
            System.out.println(getName() + " write end, time=" + write_time + " ms");
        }
        
        void read(boolean randow)
            throws Exception
        {
            long t1 = System.currentTimeMillis();
            for (int i = start; i < end; i++)
            {
                ResultSet rs;
                if (!randow)
                    rs = stmt.executeQuery("SELECT * FROM test where f1 = " + i);
                else
                    rs = stmt.executeQuery("SELECT * FROM test where f1 = " + random.nextInt(end));
                while (rs.next())
                {
                    // System.out.println("f1=" + rs.getInt(1) + " f2=" + rs.getLong(2));
                }
            }
            
            long t2 = System.currentTimeMillis();
            
            if (randow)
                randow_read_time = t2 - t1;
            else
                read_time = t2 - t1;
            if (randow)
                System.out.println(getName() + " randow read end, time=" + randow_read_time + " ms");
            else
                System.out.println(getName() + "  read end, time=" + read_time + " ms");
        }
        
        @Override
        public void run()
        {
            try
            {
                write();
                read(false);
                read(true);
                stmt.close();
                conn.close();
                latch.countDown();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
    
    static void benchmark()
        throws Exception
    {
        Connection conn = getConnection();
        Statement stmt = conn.createStatement();
        stmt.executeUpdate("DROP TABLE IF EXISTS test");
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS test (f1 int primary key, f2 real)");
        stmt.close();
        conn.close();
        
        int threadsCount = Runtime.getRuntime().availableProcessors();
        int loop = 1000;
        latch = new CountDownLatch(threadsCount);
        
        MyThread[] threads = new MyThread[threadsCount];
        for (int i = 0; i < threadsCount; i++)
        {
            threads[i] = new MyThread(i * loop, loop);
        }
        
        for (int i = 0; i < threadsCount; i++)
        {
            threads[i].start();
        }
        
        latch.await();
        
        long write_sum = 0;
        for (int i = 0; i < threadsCount; i++)
        {
            write_sum += threads[i].write_time;
        }
        
        long read_sum = 0;
        for (int i = 0; i < threadsCount; i++)
        {
            read_sum += threads[i].read_time;
        }
        long randow_read_sum = 0;
        for (int i = 0; i < threadsCount; i++)
        {
            randow_read_sum += threads[i].randow_read_time;
        }
        
        System.out.println();
        System.out.println("threads: " + threadsCount + ", loop: " + loop + ", rows: " + (threadsCount * loop));
        System.out.println("==========================================================");
        System.out.println("write_sum=" + write_sum + ", avg=" + (write_sum / threadsCount) + " ms");
        System.out.println("read_sum=" + read_sum + ", avg=" + (read_sum / threadsCount) + " ms");
        System.out.println("randow_read_sum=" + randow_read_sum + ", avg=" + (randow_read_sum / threadsCount) + " ms");
    }
}
