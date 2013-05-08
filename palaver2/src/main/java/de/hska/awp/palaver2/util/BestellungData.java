/**
 * Created by Sebastian Walz
 * 06.05.2013 12:11:49
 */
package de.hska.awp.palaver2.util;

import org.vaadin.risto.stepper.IntStepper;

import com.vaadin.ui.TextField;

import de.hska.awp.palaver2.artikelverwaltung.domain.Artikel;
import de.hska.awp.palaver2.artikelverwaltung.domain.Kategorie;

public class BestellungData
{
	private String		name;
	private	String		gebinde;
	private Kategorie	kategorie;
	private TextField	durchschnitt;
	private TextField	kantine;
	private TextField	gesamt;
	private IntStepper 	freitag;
	private IntStepper	montag;
	
	/**
	 * @param name
	 * @param artnr
	 * @param menge
	 */
	public BestellungData(String name, String gebinde, Kategorie kategorie, Integer durchschnitt, Integer kantine)
	{
		super();
		this.name = name;
		this.gebinde = gebinde;
		this.kategorie = kategorie;
		this.durchschnitt = new TextField();
		this.durchschnitt.setValue(durchschnitt.toString());
		this.kantine = new TextField();
		this.kantine.setValue(kantine.toString());
		this.gesamt = new TextField();
		this.gesamt.setValue("" + (durchschnitt + kantine));
		this.gesamt.setEnabled(false);
		this.freitag = new IntStepper();
		this.freitag.setStyleName("stepper-palaver");
		this.freitag.setValue(durchschnitt + kantine);
		this.montag = new IntStepper();
		this.montag.setValue(0);
	}
	
	public BestellungData(Artikel artikel)
	{
		super();
		this.name = artikel.getName();
		this.gebinde = artikel.getBestellgroesse() + " " + artikel.getMengeneinheit().getKurz();
		this.kategorie = artikel.getKategorie();
		this.durchschnitt = new TextField();
		this.durchschnitt.setValue(artikel.getDurchschnitt() + "");
		this.kantine = new TextField();
		this.kantine.setValue(0 + "");
		this.gesamt = new TextField();
		this.gesamt.setValue("" + artikel.getDurchschnitt());
		this.gesamt.setEnabled(false);
		this.freitag = new IntStepper();
		this.freitag.setValue(artikel.getDurchschnitt());
		this.montag = new IntStepper();
		this.montag.setValue(0);
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
	public String getGebinde()
	{
		return gebinde;
	}
	/**
	 * @param artnr the artnr to set
	 */
	public void setGebinde(String artnr)
	{
		this.gebinde = artnr;
	}
	/**
	 * @return the menge
	 */
	public IntStepper getFreitag()
	{
		return freitag;
	}
	/**
	 * @param menge the menge to set
	 */
	public void setFreitag(IntStepper menge)
	{
		this.freitag = menge;
	}
	/**
	 * @return the kategorie
	 */
	public Kategorie getKategorie()
	{
		return kategorie;
	}
	/**
	 * @param kategorie the kategorie to set
	 */
	public void setKategorie(Kategorie kategorie)
	{
		this.kategorie = kategorie;
	}
	/**
	 * @return the durchschnitt
	 */
	public TextField getDurchschnitt()
	{
		return durchschnitt;
	}
	/**
	 * @param durchschnitt the durchschnitt to set
	 */
	public void setDurchschnitt(TextField durchschnitt)
	{
		this.durchschnitt = durchschnitt;
	}
	/**
	 * @return the kantine
	 */
	public TextField getKantine()
	{
		return kantine;
	}
	/**
	 * @param kantine the kantine to set
	 */
	public void setKantine(TextField kantine)
	{
		this.kantine = kantine;
	}
	/**
	 * @return the gesamt
	 */
	public TextField getGesamt()
	{
		return gesamt;
	}
	/**
	 * @param gesamt the gesamt to set
	 */
	public void setGesamt(TextField gesamt)
	{
		this.gesamt = gesamt;
	}
	/**
	 * @return the montag
	 */
	public IntStepper getMontag()
	{
		return montag;
	}
	/**
	 * @param montag the montag to set
	 */
	public void setMontag(IntStepper montag)
	{
		this.montag = montag;
	}
}
