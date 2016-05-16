package com.github.zzycjcg.demo.nio;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class ChannelDemo
{
    
    public static void main(String[] args)
    {
        try
        {
            RandomAccessFile aFile = new RandomAccessFile("F:\\Java_Dev\\nio\\nio-data.txt", "rw");
            FileChannel fileChannel = aFile.getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate(48);
            int n;
            while ((n = fileChannel.read(byteBuffer)) != -1)
            {
                System.out.println("read->" + n);
                System.out.println("Write: capacity->" + byteBuffer.capacity() + "|position->" + byteBuffer.position()
                    + "|limit->" + byteBuffer.limit());
                byteBuffer.flip();
                System.out.println("Read: capacity->" + byteBuffer.capacity() + "|position->" + byteBuffer.position()
                    + "|limit->" + byteBuffer.limit());
                byteBuffer.clear();
            }
            aFile.close();
            fileChannel.close();
            byteBuffer.clear();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
}
