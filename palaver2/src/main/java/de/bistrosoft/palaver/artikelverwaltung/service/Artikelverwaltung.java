/**
* Created by Sebastian Walz
* 24.04.2013 16:08:18
*/
package de.bistrosoft.palaver.artikelverwaltung.service;

import java.sql.SQLException;
import java.util.List;

import de.bistrosoft.palaver.artikelverwaltung.domain.Artikel;
import de.bistrosoft.palaver.artikelverwaltung.domain.Kategorie;
import de.bistrosoft.palaver.artikelverwaltung.domain.Mengeneinheit;
import de.bistrosoft.palaver.data.ArtikelDAO;
import de.bistrosoft.palaver.data.ConnectException;
import de.bistrosoft.palaver.data.DAOException;
import de.bistrosoft.palaver.data.KategorieDAO;
import de.bistrosoft.palaver.data.MengeneinheitDAO;

public class Artikelverwaltung extends ArtikelDAO
{
private static Artikelverwaltung	instance = null;

private Artikelverwaltung()
{
super();
}

public static Artikelverwaltung getInstance()
{
if (instance == null)
{
instance = new Artikelverwaltung();
}
return instance;
}

public List<Artikel> getAllArtikel() throws ConnectException, DAOException, SQLException
{
List<Artikel> result = null;

result = super.getAllArtikel();

return result;
}

public Artikel getArtikelById(Long id) throws ConnectException, DAOException, SQLException
{
Artikel result = null;

result = super.getArtikelById(id);

return result;
}

public List<Artikel> getArtikelByName(String name) throws ConnectException, DAOException, SQLException
{
List<Artikel> result = null;

result = super.getArtikelByName(name);

return result;
}

public void createArtikel(Artikel artikel) throws ConnectException, DAOException
{
super.createArtikel(artikel);
}

public void updateArtikel(Artikel artikel) throws ConnectException, DAOException
{
super.updateArtikel(artikel);
}

public List<Kategorie> getAllKategorien() throws ConnectException, DAOException, SQLException
{
List<Kategorie> result = null;

result = KategorieDAO.getInstance().getAllKategories();

return result;
}
}