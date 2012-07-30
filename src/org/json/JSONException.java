/*
 * File:         JSONException.java
 * Author:       JSON.org
 */
package org.json;

// TODO: Auto-generated Javadoc
/**
 * The JSONException is thrown by the JSON.org classes when things are amiss.
 * 
 * @author JSON.org
 * @version 2010-12-24
 */
public class JSONException extends Exception
{
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 0;
    
    /** The cause. */
    private Throwable cause;

    /**
     * Constructs a JSONException with an explanatory message.
     * 
     * @param message
     *            Detail about the reason for the exception.
     */
    public JSONException(String message)
    {
        super(message);
    }

    /**
     * Instantiates a new JSON exception.
     * 
     * @param Cause
     *            the cause
     */
    public JSONException(Throwable Cause)
    {
        super(Cause.getMessage());
        cause = Cause;
    }

    /* (non-Javadoc)
     * @see java.lang.Throwable#getCause()
     */
    @Override
    public Throwable getCause()
    {
        return cause;
    }
}
