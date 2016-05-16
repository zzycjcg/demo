package com.github.zzycjcg.demo.event.refactor;

import java.util.Stack;

import org.springframework.context.ApplicationListener;

import com.github.zzycjcg.demo.event.refactor.NewMethodInvocationEvent.MethodInvocationType;

public class NewMethodInvocationListener implements ApplicationListener<NewMethodInvocationEvent>
{
    private ThreadLocal<Stack<NewMethodInvocationEvent>> threadLocalEvents =
        new ThreadLocal<Stack<NewMethodInvocationEvent>>()
        {
            @Override
            protected java.util.Stack<NewMethodInvocationEvent> initialValue()
            {
                return new Stack<NewMethodInvocationEvent>();
            };
        };
        
    @Override
    public void onApplicationEvent(NewMethodInvocationEvent event)
    {
        if (event.getType() == MethodInvocationType.START)
        {
            threadLocalEvents.get().push(event);
        }
        else
        {
            NewMethodInvocationEvent start = threadLocalEvents.get().pop();
            NewMethodInvocationEvent end = event;
            System.out.printf("[%s]Method %s cost %d \n",
                Thread.currentThread().getName(),
                start.getMethodName(),
                end.getTimestamp() - start.getTimestamp());
        }
    }
}
