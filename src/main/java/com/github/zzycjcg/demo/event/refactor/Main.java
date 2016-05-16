package com.github.zzycjcg.demo.event.refactor;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main
{
    
    public static void main(String[] args)
    {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring/spring-beans.xml");
        context.start();
        context.close();
    }
    
}
