package org.esupportail.lecture.exceptions;

/**
 * Exception throwed when there is a fatal error
 * @author gbouteil
 *
 */public class FatalException extends RuntimeException {
	public FatalException(){
		super();
	}
	public FatalException(String msg){
		super(msg);
	}
}