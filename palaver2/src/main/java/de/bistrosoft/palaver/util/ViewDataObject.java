package de.bistrosoft.palaver.util;

/**
 * @author Michael Marschall
 * 
 */
public class ViewDataObject<T> implements ViewData {
	private T data;

	public ViewDataObject(T data) {
		this.data = data;
	}

	public T getData() {
		return data;
	}
}