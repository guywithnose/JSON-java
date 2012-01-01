/*
 * File: JSONException.java Author: Robert Bittle <guywithnose@gmail.com>
 */
package json;

/**
 * The JSONException is thrown by the JSON.org classes when things are amiss.
 * 
 * @author JSON.org
 * @version 2010-12-24
 */
public class JSONException extends Exception
{
    private static final long serialVersionUID = 0;
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

    @Override
    public Throwable getCause()
    {
        return cause;
    }
}
