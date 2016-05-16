package com.github.zzycjcg.demo.event;

import java.util.ArrayList;
import java.util.List;

public class MethodInvocationEventPublisher
{
    private List<MethodInvocationListener> eventListeners = new ArrayList<MethodInvocationListener>();
    
    protected void methodToMonitor()
    {
        MethodInvocationEvent beginEvent = new MethodInvocationEvent(this, "methodToMonitor");
        publishEvent(1, beginEvent);
        //... do something
        MethodInvocationEvent endEvent = new MethodInvocationEvent(this, "methodToMonitor");
        publishEvent(2, endEvent);
    }
    
    protected void publishEvent(int type, MethodInvocationEvent event)
    {
        List<MethodInvocationListener> copyEventListeners = new ArrayList<MethodInvocationListener>(eventListeners);
        for (MethodInvocationListener listener : copyEventListeners)
        {
            if (type == 1)
            {
                listener.onMethodInvocationBegin(event);
            }
            else
            {
                listener.onMethodInvocationEnd(event);
            }
        }
    }
}
