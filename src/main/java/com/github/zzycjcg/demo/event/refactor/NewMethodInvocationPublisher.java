package com.github.zzycjcg.demo.event.refactor;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

import com.github.zzycjcg.demo.event.refactor.NewMethodInvocationEvent.MethodInvocationType;

public class NewMethodInvocationPublisher implements ApplicationEventPublisherAware
{
    private ApplicationEventPublisher aep;
    
    /** {@inheritDoc} */
    
    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher)
    {
        this.aep = applicationEventPublisher;
    }
    
    public void methodMonitor(AtomicInteger n)
        throws Exception
    {
        NewMethodInvocationEvent startEvent =
            new NewMethodInvocationEvent(this, "methodMonitor", MethodInvocationType.START);
        aep.publishEvent(startEvent);
        if (n.decrementAndGet() <= 0)
            return;
        for (int i = 0; i < 10000000; i++)
        {
            for (int j = 0; j < 10000; j++)
            {
                for (int t = 0; t < 10; t++)
                {
                    int m = i << j;
                    m = m--;
                }
            }
        }
        methodMonitor(n);
        NewMethodInvocationEvent endEvent =
            new NewMethodInvocationEvent(this, "methodMonitor", MethodInvocationType.END);
        aep.publishEvent(endEvent);
    }
    
    public void run()
        throws Exception
    {
        methodMonitor(new AtomicInteger(10));
    }
}
