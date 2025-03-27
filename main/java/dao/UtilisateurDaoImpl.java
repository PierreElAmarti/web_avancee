package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.exception.DAOException;
import model.Utilisateur;

public class UtilisateurDaoImpl implements UtilisateurDao {

    private static final String SQL_SELECT        = "SELECT id, nomUtilisateur, adresseMail, motDePasse, elo, questionSecrete, reponseSecrete, permission FROM Utilisateur ORDER BY elo LIMIT 20";
    private static final String SQL_SELECT_PAR_ID = "SELECT id, nomUtilisateur, adresseMail, motDePasse, elo, questionSecrete, reponseSecrete, permission FROM Utilisateur WHERE id = ?";
	private static final String SQL_SELECT_CLASSEMENT = "SELECT count(id) as classement FROM Utilisateur where elo >= ? AND (permission < 10)";
    private static final String SQL_SELECT_PAR_NOMUTILISATEUR = "SELECT id, nomUtilisateur, adresseMail, motDePasse, elo, questionSecrete, reponseSecrete, permission FROM Utilisateur WHERE nomUtilisateur = ?";
    private static final String SQL_SELECT_PAR_ADRESSEMAIL = "SELECT id, nomUtilisateur, adresseMail, motDePasse, elo, questionSecrete, reponseSecrete, permission FROM Utilisateur WHERE adresseMail = ?";
    private static final String SQL_SELECT_PAR_ADRESSEMAILOUNOMUTILISATEUR = "SELECT id, nomUtilisateur, adresseMail, motDePasse, elo, questionSecrete, reponseSecrete, permission FROM Utilisateur WHERE (adresseMail = ? OR nomUtilisateur = ?)";
    private static final String SQL_INSERT        = "INSERT INTO Utilisateur (nomUtilisateur, adresseMail, motDePasse, elo, questionSecrete, reponseSecrete, permission) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE        = "UPDATE Utilisateur SET nomUtilisateur=?, adresseMail=?, motDePasse=?, elo=?, questionSecrete=?, reponseSecrete=?, permission=? WHERE id=? AND (permission < 10)";
    private static final String SQL_DELETE_PAR_ID = "DELETE FROM Utilisateur WHERE id = ? AND (permission < 10)";

    private DAOFactory          daoFactory;
    
    private static DAOMapper 	daoMapperUtilisateur = new DAOMapper().setClass(Utilisateur.class).setAttribute("id", "id").setAttribute("nomUtilisateur", "nomUtilisateur").setAttribute("adresseMail", "adresseMail").setAttribute("motDePasse", "motDePasse").setAttribute("elo", "elo").setAttribute("questionSecrete", "questionSecrete").setAttribute("reponseSecrete", "reponseSecrete").setAttribute("permission", "permission");

    UtilisateurDaoImpl( DAOFactory daoFactory ) {
        this.daoFactory = daoFactory;
        DAOUtils.setDAOFactory(daoFactory);
    }

    /* Implémentation de la méthode définie dans l'interface ClientDao */
    @Override
    public Utilisateur trouver( long id ) throws DAOException {
        return trouver( SQL_SELECT_PAR_ID, id );
    }
    
    @Override
    public Long classement( Utilisateur utilisateur ) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Long vRet = null;

        try {
            /* Récupération d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            /*
             * Préparation de la requête avec les objets passés en arguments
             * (ici, uniquement un id) et exécution.
             */
            preparedStatement = DAOUtils.initialisationRequetePreparee( connexion, SQL_SELECT_CLASSEMENT, false, utilisateur.getElo() );
            resultSet = preparedStatement.executeQuery();
            /* Parcours de la ligne de données retournée dans le ResultSet */
            if ( resultSet.next() ) {
            	vRet = resultSet.getLong( "classement" ) + 1;
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
        	DAOUtils.fermeturesSilencieuses( resultSet, preparedStatement, connexion );
        }

        return vRet;
    }

    @Override
    public Utilisateur getByUsernameOrMail( String eMailouNomUtilisateur ) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet valeursAutoGenerees = null;

        Utilisateur utilisateur = null;
        try {
            /* Récupération d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            /*
             * Préparation de la requête avec les objets passés en arguments
             * (ici, uniquement un id) et exécution.
             */
            preparedStatement = DAOUtils.initialisationRequetePreparee( connexion, SQL_SELECT_PAR_ADRESSEMAILOUNOMUTILISATEUR, false, eMailouNomUtilisateur,eMailouNomUtilisateur );
            ResultSet resultSet = preparedStatement.executeQuery();
            /* Parcours de la ligne de données retournée dans le ResultSet */
            if ( resultSet.next() ) {
            	utilisateur = (Utilisateur) daoMapperUtilisateur.map( resultSet );
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
        	DAOUtils.fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
        }
        return utilisateur;
    }

    @Override
    public boolean emailDejaPris( String eMail ) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet valeursAutoGenerees = null;

        try {
            /* Récupération d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            /*
             * Préparation de la requête avec les objets passés en arguments
             * (ici, uniquement un id) et exécution.
             */
            preparedStatement = DAOUtils.initialisationRequetePreparee( connexion, SQL_SELECT_PAR_ADRESSEMAIL, false, eMail );
            ResultSet resultSet = preparedStatement.executeQuery();
            /* Parcours de la ligne de données retournée dans le ResultSet */
            if ( resultSet.next() ) {
            	DAOUtils.fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
            	return true;
            }
        	DAOUtils.fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
            return false;
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
        	DAOUtils.fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
        }
    }

    @Override
    public boolean nomUtilisateurDejaPris( String nomUtilisateur ) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet valeursAutoGenerees = null;

        try {
            /* Récupération d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            /*
             * Préparation de la requête avec les objets passés en arguments
             * (ici, uniquement un id) et exécution.
             */
            preparedStatement = DAOUtils.initialisationRequetePreparee( connexion, SQL_SELECT_PAR_NOMUTILISATEUR, false, nomUtilisateur );
            ResultSet resultSet = preparedStatement.executeQuery();
            /* Parcours de la ligne de données retournée dans le ResultSet */
            if ( resultSet.next() ) {
            	DAOUtils.fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
            	return true;
            }
        	DAOUtils.fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
            return false;
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
        	DAOUtils.fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
        }
    }

    @Override
    public void modifier( Utilisateur utilisateur ) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet valeursAutoGenerees = null;

        try {
            connexion = daoFactory.getConnection();
            preparedStatement = DAOUtils.initialisationRequetePreparee( connexion, SQL_UPDATE, true,
            		utilisateur.getNomUtilisateur(), utilisateur.getAdresseMail(),
            		utilisateur.getMotsDePasse(), utilisateur.getElo(),
            		utilisateur.getQuestionSecrete(), utilisateur.getReponseSecrete(),
            		utilisateur.getPermission(), utilisateur.getId() );
            int statut = preparedStatement.executeUpdate();
            if ( statut == 0 ) {
                throw new DAOException( "Échec de la mise à jour du client, aucune ligne ajoutée dans la table." );
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
        	DAOUtils.fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
        }
    }

    /* Implémentation de la méthode définie dans l'interface ClientDao */
    @Override
    public void creer( Utilisateur utilisateur ) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet valeursAutoGenerees = null;

        try {
            connexion = daoFactory.getConnection();
            preparedStatement = DAOUtils.initialisationRequetePreparee( connexion, SQL_INSERT, true,
            		utilisateur.getNomUtilisateur(), utilisateur.getAdresseMail(),
            		utilisateur.getMotsDePasse(), utilisateur.getElo(),
            		utilisateur.getQuestionSecrete(), utilisateur.getReponseSecrete(),
            		utilisateur.getPermission() );
            int statut = preparedStatement.executeUpdate();
            if ( statut == 0 ) {
                throw new DAOException( "Échec de la création du client, aucune ligne ajoutée dans la table." );
            }
            valeursAutoGenerees = preparedStatement.getGeneratedKeys();
            if ( valeursAutoGenerees.next() ) {
            	utilisateur.setId( valeursAutoGenerees.getLong( 1 ) );
            } else {
                throw new DAOException( "Échec de la création du client en base, aucun ID auto-généré retourné." );
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
        	DAOUtils.fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
        }
    }

    /* Implémentation de la méthode définie dans l'interface ClientDao */
    @Override
    public List<Utilisateur> lister() throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Utilisateur> utilisateurs = new ArrayList<Utilisateur>();

        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement( SQL_SELECT );
            resultSet = preparedStatement.executeQuery();
            while ( resultSet.next() ) {
            	utilisateurs.add( (Utilisateur) daoMapperUtilisateur.map(resultSet) );
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
        	DAOUtils.fermeturesSilencieuses( resultSet, preparedStatement, connection );
        }

        return utilisateurs;
    }

    /* Implémentation de la méthode définie dans l'interface ClientDao */
    @Override
    public void supprimer( Utilisateur utilisateur ) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;

        try {
            connexion = daoFactory.getConnection();
            preparedStatement = DAOUtils.initialisationRequetePreparee( connexion, SQL_DELETE_PAR_ID, true, utilisateur.getId() );
            int statut = preparedStatement.executeUpdate();
            if ( statut == 0 ) {
                throw new DAOException( "Échec de la suppression du client, aucune ligne supprimée de la table." );
            } else {
            	utilisateur.setId( null );
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
        	DAOUtils.fermeturesSilencieuses( preparedStatement, connexion );
        }
    }

    /*
     * Méthode générique utilisée pour retourner un client depuis la base de
     * données, correspondant à la requête SQL donnée prenant en paramètres les
     * objets passés en argument.
     */
    private Utilisateur trouver( String sql, Object... objets ) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Utilisateur utilisateur = null;

        try {
            /* Récupération d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            /*
             * Préparation de la requête avec les objets passés en arguments
             * (ici, uniquement un id) et exécution.
             */
            preparedStatement = DAOUtils.initialisationRequetePreparee( connexion, sql, false, objets );
            resultSet = preparedStatement.executeQuery();
            /* Parcours de la ligne de données retournée dans le ResultSet */
            if ( resultSet.next() ) {
            	utilisateur = (Utilisateur) daoMapperUtilisateur.map( resultSet );
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
        	DAOUtils.fermeturesSilencieuses( resultSet, preparedStatement, connexion );
        }

        return utilisateur;
    }

}