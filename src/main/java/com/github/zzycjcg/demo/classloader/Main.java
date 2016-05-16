package com.github.zzycjcg.demo.classloader;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.github.zzycjcg.demo.patterns.Singlton;

public class Main
{
    public static void main(String[] args)
    {
        Singlton singlton = Singlton.getInstance();
        TestClassLoader loader = new TestClassLoader();
        try
        {
            Class<?> clazz = (Class<?>)loader.loadClass("com.github.zzycjcg.demo.patterns.Singlton");
            System.out.println(clazz.getClassLoader().getClass().getName());
            System.out.println(singlton.getClass().getClassLoader().getClass().getName());
            Method m = clazz.getMethod("getInstance");
            Singlton newSinglton = (Singlton)m.invoke(null);
            System.out.println(Main.class.getClassLoader().getClass().getName());
            System.out.println(singlton.equals(newSinglton));
        }
        catch (ClassNotFoundException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (NoSuchMethodException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (SecurityException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IllegalAccessException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IllegalArgumentException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (InvocationTargetException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
