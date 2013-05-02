/**
 * Created by Sebastian Walz
 * 02.05.2013 17:12:17
 */
package de.hska.awp.palaver2.util;

public class ViewDataObject<T> implements ViewData
{
	private T	data;
	
	public ViewDataObject(T data)
	{
		this.data = data;
	}
	
	public T getData()
	{
		return data;
	}
}
