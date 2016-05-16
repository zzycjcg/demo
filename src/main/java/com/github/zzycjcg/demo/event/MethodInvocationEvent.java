package com.github.zzycjcg.demo.event;

import java.util.EventObject;

public class MethodInvocationEvent extends EventObject
{
    private static final long serialVersionUID = -7347316902579584046L;
    
    private final String methodName;
    
    public MethodInvocationEvent(Object source, String methodName)
    {
        super(source);
        this.methodName = methodName;
    }
    
    public String getMethodName()
    {
        return this.methodName;
    }
    
    /** {@inheritDoc} */
    
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("MethodInvocationEvent [methodName=");
        builder.append(methodName);
        builder.append(", toString()=");
        builder.append(super.toString());
        builder.append("]");
        return builder.toString();
    }
    
}
