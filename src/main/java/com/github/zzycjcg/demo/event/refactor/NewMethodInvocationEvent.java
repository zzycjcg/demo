package com.github.zzycjcg.demo.event.refactor;

import org.springframework.context.ApplicationEvent;

public class NewMethodInvocationEvent extends ApplicationEvent
{
    private static final long serialVersionUID = 4574741005024383502L;
    
    private final String methodName;
    
    private final MethodInvocationType type;
    
    public NewMethodInvocationEvent(Object source, String methodName, MethodInvocationType type)
    {
        super(source);
        this.methodName = methodName;
        this.type = type;
    }
    
    /**
     * @return the methodName
     */
    public String getMethodName()
    {
        return methodName;
    }
    
    /**
     * @return the type
     */
    public MethodInvocationType getType()
    {
        return type;
    }
    
    public enum MethodInvocationType
    {
        START, END
    }
    
}
