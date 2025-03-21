package dao;

import java.util.List;

import dao.exception.DAOException;
import model.Utilisateur;


public interface UtilisateurDao {
    void creer( Utilisateur client ) throws DAOException;

    Utilisateur trouver( long id ) throws DAOException;
    
    Utilisateur getByUsernameOrMail( String eMailouNomUtilisateur ) throws DAOException;

    List<Utilisateur> lister() throws DAOException;

    void supprimer( Utilisateur client ) throws DAOException;
    
    void modifier( Utilisateur client ) throws DAOException;
    
    boolean emailDejaPris(String eMail) throws DAOException;
    
    boolean nomUtilisateurDejaPris(String nomUtilisateur) throws DAOException;
}
