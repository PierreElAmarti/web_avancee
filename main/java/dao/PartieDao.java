package dao;

import java.util.List;

import dao.exception.DAOException;
import model.PartieMaitre;

public interface PartieDao
{
	void creer(PartieMaitre partie) throws DAOException;

	PartieMaitre trouver(long id) throws DAOException;

	List<PartieMaitre> lister() throws DAOException;

	void supprimer(PartieMaitre client) throws DAOException;
}
