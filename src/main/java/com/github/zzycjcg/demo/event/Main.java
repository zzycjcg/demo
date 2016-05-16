package com.github.zzycjcg.demo.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main
{
    private static final Logger log = LoggerFactory.getLogger(Main.class);
    
    public static void main(String[] args)
    {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring/spring-beans.xml");
        context.start();
        log.info("Application start.");
        context.close();
        log.info("Application close.");
    }
    
}
