package com.payu.util.exceptions;

import java.io.Serializable;

//TODO: Auto-generated Javadoc
/**
* The Class CoreException.
*/
@SuppressWarnings("serial")
public class CoreException extends Exception implements Serializable{

 /**
  * Instantiates a new habitat exception.
  *
  * @param msg the msg
  */
 public CoreException(String msg) {
     super(msg);
 }

 /**
  * Instantiates a new habitat exception.
  *
  * @param t the t
  */
 public CoreException(Throwable t) {
     super(t);
 }

 /**
  * Instantiates a new habitat exception.
  *
  * @param msg the msg
  * @param t the t
  */
 public CoreException(String msg, Throwable t) {
     super(msg, t);
 }

}
