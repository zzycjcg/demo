package com.github.zzycjcg.demo.patterns;

public class Singlton
{
    private static Singlton instance;
    
    private Singlton()
    {
    }
    
    public static Singlton getInstance()
    {
        if (instance != null)
            return instance;
        synchronized (Singlton.class)
        {
            if (instance == null)
            {
                instance = new Singlton();
            }
        }
        return instance;
    }
}
