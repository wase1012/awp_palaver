/**
 * Created by Sebastian Walz
 * 06.05.2013 12:11:49
 */
package de.hska.awp.palaver2.util;

import org.vaadin.risto.stepper.IntStepper;

public class BestellungData
{
	private String		name;
	private	String		artnr;
	private IntStepper 	menge;
	
	/**
	 * @param name
	 * @param artnr
	 * @param menge
	 */
	public BestellungData(String name, String artnr, Integer menge)
	{
		super();
		this.name = name;
		this.artnr = artnr;
		this.menge = new IntStepper();
		this.menge.setValue(menge);
	}
	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}
	/**
	 * @return the artnr
	 */
	public String getArtnr()
	{
		return artnr;
	}
	/**
	 * @param artnr the artnr to set
	 */
	public void setArtnr(String artnr)
	{
		this.artnr = artnr;
	}
	/**
	 * @return the menge
	 */
	public IntStepper getMenge()
	{
		return menge;
	}
	/**
	 * @param menge the menge to set
	 */
	public void setMenge(IntStepper menge)
	{
		this.menge = menge;
	}
}
