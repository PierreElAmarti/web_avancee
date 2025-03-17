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

    private static final String SQL_SELECT        = "SELECT id, nomUtilisateur, adresseMail, motDePasse, elo, questionSecrete, reponseSecrete, permission FROM Client ORDER BY elo LIMIT 10";
    private static final String SQL_SELECT_PAR_ID = "SELECT id, nomUtilisateur, adresseMail, motDePasse, elo, questionSecrete, reponseSecrete, permission FROM Client WHERE id = ?";
    private static final String SQL_INSERT        = "INSERT INTO Client (nomUtilisateur, adresseMail, motDePasse, elo, questionSecrete, reponseSecrete, permission) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_DELETE_PAR_ID = "DELETE FROM Client WHERE id = ?";

    private DAOFactory          daoFactory;

    UtilisateurDaoImpl( DAOFactory daoFactory ) {
        this.daoFactory = daoFactory;
    }

    /* Implémentation de la méthode définie dans l'interface ClientDao */
    @Override
    public Utilisateur trouver( long id ) throws DAOException {
        return trouver( SQL_SELECT_PAR_ID, id );
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
            	utilisateurs.add( map( resultSet ) );
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
            	utilisateur = map( resultSet );
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
        	DAOUtils.fermeturesSilencieuses( resultSet, preparedStatement, connexion );
        }

        return utilisateur;
    }

    /*
     * Simple méthode utilitaire permettant de faire la correspondance (le
     * mapping) entre une ligne issue de la table des clients (un ResultSet) et
     * un bean Client.
     */
    private static Utilisateur map( ResultSet resultSet ) throws SQLException {
    	Utilisateur utilisateur = new Utilisateur();
    	utilisateur.setId( resultSet.getLong( "id" ) );
    	utilisateur.setNomUtilisateur( resultSet.getString( "nomUtilisateur" ) );
    	utilisateur.setAdresseMail( resultSet.getString( "adresseMail" ) );
    	utilisateur.setMotsDePasse( resultSet.getString( "motDePasse" ) );
    	utilisateur.setElo( resultSet.getInt( "elo" ) );
    	utilisateur.setQuestionSecrete( resultSet.getInt( "questionSecrete" ) );
    	utilisateur.setReponseSecrete( resultSet.getString( "reponseSecrete" ) );
    	utilisateur.setPermission( resultSet.getInt( "permission" ) );
        return utilisateur;
    }

}