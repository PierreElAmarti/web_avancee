package dao;

import java.util.List;

import dao.exception.DAOException;
import model.Partie;

public interface PartieDao {
    void creer( Partie partie ) throws DAOException;

    Partie trouver( long id ) throws DAOException;

    List<Partie> lister() throws DAOException;

    void supprimer( Partie client ) throws DAOException;
}
