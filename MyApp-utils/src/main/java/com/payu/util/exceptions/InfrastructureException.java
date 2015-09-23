package com.payu.util.exceptions;

import java.io.Serializable;

@SuppressWarnings("serial")
public class InfrastructureException extends CoreException implements Serializable{

    /**
     * Instantiates a new habitat exception.
     *
     * @param msg the msg
     */
    public InfrastructureException(String msg) {
        super(msg);
    }

    /**
     * Instantiates a new habitat exception.
     *
     * @param t the t
     */
    public InfrastructureException(Throwable t) {
        super(t);
    }

    /**
     * Instantiates a new habitat exception.
     *
     * @param msg the msg
     * @param t the t
     */
    public InfrastructureException(String msg, Throwable t) {
        super(msg, t);
    }

}

