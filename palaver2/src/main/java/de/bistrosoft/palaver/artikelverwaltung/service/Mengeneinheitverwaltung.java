package de.bistrosoft.palaver.artikelverwaltung.service;

import java.sql.SQLException;
import java.util.List;

import de.bistrosoft.palaver.artikelverwaltung.domain.Mengeneinheit;
import de.bistrosoft.palaver.data.ConnectException;
import de.bistrosoft.palaver.data.DAOException;
import de.bistrosoft.palaver.data.MengeneinheitDAO;

public class Mengeneinheitverwaltung extends MengeneinheitDAO {

private static Mengeneinheitverwaltung	instance = null;
private MengeneinheitDAO dao;

private Mengeneinheitverwaltung()
{
super();
}

public static Mengeneinheitverwaltung getInstance()
{
if (instance == null)
{
instance = new Mengeneinheitverwaltung();
}
return instance;
}

public List<Mengeneinheit> getAllMengeneinheit() throws ConnectException, DAOException, SQLException
{
List<Mengeneinheit> result = null;

result = super.getAllMengeneinheit();

return result;
}

public Mengeneinheit getMengeneinheitById(Long id) throws ConnectException, DAOException, SQLException
{
Mengeneinheit result = null;

result = super.getMengeneinheitById(id);

return result;
}

public List<Mengeneinheit> getMengeneinheitByName(String name) throws ConnectException, DAOException, SQLException
{
List<Mengeneinheit> result = null;

result = super.getMengeneinheitByName(name);

return result;
}

public void createMengeneinheit(Mengeneinheit mengeneinheit) throws ConnectException, DAOException, SQLException
{
dao.createNewMengeneinheit(mengeneinheit);
}

public void updateMengeneinheit(Mengeneinheit mengeneinheit) throws ConnectException, DAOException, SQLException
{
super.updateMengeneinheit(mengeneinheit);
}
}