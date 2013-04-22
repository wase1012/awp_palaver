/**
 *  Created by Sebastian Walz
 */
package de.hska.awp.palaver2.artikelverwaltung.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "kategorie", catalog = "palaver")
public class Kategorie implements java.io.Serializable 
{
	private static final long 			serialVersionUID = -4647006694762094910L;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;
	
	@Column(name = "name", nullable = false, length = 45)
	private String name;

	public Kategorie() 
	{
		super();
	}

	public Kategorie(String name) 
	{
		this.name = name;
	}

	public Integer getId() 
	{
		return this.id;
	}

	public void setId(Integer id) 
	{
		this.id = id;
	}

	public String getName() 
	{
		return this.name;
	}

	public void setName(String name) 
	{
		this.name = name;
	}
}
