package com.github.zzycjcg.demo.event;

import java.util.EventListener;

public interface MethodInvocationListener extends EventListener
{
    void onMethodInvocationBegin(MethodInvocationEvent event);
    
    void onMethodInvocationEnd(MethodInvocationEvent event);
}
