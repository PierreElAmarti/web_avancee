package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.exception.DAOException;
import model.Partie;
import model.Utilisateur;

public class PartieDaoImpl implements PartieDao {

	private static final String SQL_SELECT_MAITRE = "SELECT id, date, time FROM PartieMaitre";
	private static final String SQL_SELECT_PARTIE = "SELECT id, idMaitre, score, gagnant, idUtilisateur FROM Partie ";
	private static final String SQL_SELECT_UTILISATEUR_PAR_ID = "SELECT id, nomUtilisateur, adresseMail, motDePasse, elo, questionSecrete, reponseSecrete, permission FROM Utilisateur WHERE id = ?";
	private static final String SQL_SELECT_PAR_ID_MAITRE = "SELECT id, date, time FROM PartieMaitre WHERE id = ?";
	private static final String SQL_SELECT_PARTIE_PAR_ID_MAITRE = "SELECT id, idMaitre, score, gagnant, idUtilisateur FROM Partie WHERE idMaitre = ? ";
	private static final String SQL_INSERT_MAITRE = "INSERT INTO PartieMaitre (date, time) VALUES (?, ?)";
    private static final String SQL_INSERT_PARTIE = "INSERT INTO Partie (idMaitre, score, gagnant, idUtilisateur) VALUES (?, ?, ?, ?)";
    private static final String SQL_DELETE_PAR_ID_MAITRE = "DELETE FROM PartieMaitre WHERE id = ?";
    private static final String SQL_DELETE_PAR_ID_PARTIE = "DELETE FROM Partie WHERE id = ?";

    private DAOFactory          daoFactory;

    PartieDaoImpl( DAOFactory daoFactory ) {
        this.daoFactory = daoFactory;
    }

    /* Implémentation de la méthode définie dans l'interface PartieDao */
    @Override
    public Partie trouver( long id ) throws DAOException {
        return trouver( SQL_SELECT_PARTIE_PAR_ID_MAITRE, id );
    }

    /* Implémentation de la méthode définie dans l'interface PartieDao */
    @Override
    public void creer( Partie partie ) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet valeursAutoGenerees = null;

        try {
            connexion = daoFactory.getConnection();
            preparedStatement = DAOUtils.initialisationRequetePreparee( connexion, SQL_INSERT_MAITRE, true,
            		partie.getDate(), partie.getTemps() );
            int statut = preparedStatement.executeUpdate();
            if ( statut == 0 ) {
                throw new DAOException( "Échec de la création de la partie maitre, aucune ligne ajoutée dans la table." );
            }
            valeursAutoGenerees = preparedStatement.getGeneratedKeys();
            if ( valeursAutoGenerees.next() ) {
            	partie.setId( valeursAutoGenerees.getLong( 1 ) );
            	Long max = 0L;
            	for(Utilisateur vUtilisateur : partie.getUtilisateurs()) {
            		if(max < partie.getScores().get(vUtilisateur.getId().intValue())) {
            			max = partie.getScores().get(vUtilisateur.getId().intValue());
            		}
            	}
            	for(Utilisateur vUtilisateur : partie.getUtilisateurs()) {
            		PreparedStatement tempStatement = DAOUtils.initialisationRequetePreparee( connexion, SQL_INSERT_PARTIE, true,
                    		partie.getId(), partie.getScores().get(vUtilisateur.getId().intValue()),
                    		((max == partie.getScores().get(vUtilisateur.getId().intValue())) ? 1 : 0), vUtilisateur.getId());
            		int tempStatut = preparedStatement.executeUpdate();
            		if(tempStatut == 0) {
                        throw new DAOException( "Échec de la création de la partie, aucune ligne ajoutée dans la table." );
            		}
            	}
            } else {
                throw new DAOException( "Échec de la création de la partie maitre en base, aucun ID auto-généré retourné." );
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
        	DAOUtils.fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
        }
    }

    /* Implémentation de la méthode définie dans l'interface PartieDao */
    @Override
    public List<Partie> lister() throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Partie> parties = new ArrayList<Partie>();

        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement( SQL_SELECT_MAITRE );
            resultSet = preparedStatement.executeQuery();
            while ( resultSet.next() ) {
            	parties.add( map( resultSet ) );
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
        	DAOUtils.fermeturesSilencieuses( resultSet, preparedStatement, connection );
        }

        return parties;
    }

    /* Implémentation de la méthode définie dans l'interface PartieDao */
    @Override
    public void supprimer( Partie partie ) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;

        try {
            connexion = daoFactory.getConnection();
            preparedStatement = DAOUtils.initialisationRequetePreparee( connexion, SQL_DELETE_PAR_ID_MAITRE, true, partie.getId() );
            int statut = preparedStatement.executeUpdate();
            if ( statut == 0 ) {
                throw new DAOException( "Échec de la suppression de la partie, aucune ligne supprimée de la table." );
            } else {
            	partie.setId( null );
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
        	DAOUtils.fermeturesSilencieuses( preparedStatement, connexion );
        }
    }

    /*
     * Méthode générique utilisée pour retourner une partie depuis la base de
     * données, correspondant à la requête SQL donnée prenant en paramètres les
     * objets passés en argument.
     */
    private Partie trouver( String sql, Object... objets ) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Partie partie = null;

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
            	partie = map( resultSet );
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
        	DAOUtils.fermeturesSilencieuses( resultSet, preparedStatement, connexion );
        }

        return partie;
    }

    /*
     * Simple méthode utilitaire permettant de faire la correspondance (le
     * mapping) entre une ligne issue de la table des parties (un ResultSet) et
     * un bean partie.
     */
    private Partie map( ResultSet resultSet ) throws SQLException {
    	Partie partie = new Partie();
    	partie.setId( resultSet.getLong( "id" ) );
    	partie.setDate( resultSet.getDate( "date" ) );
    	partie.setTemps( resultSet.getTime( "time" ).toString() );

        Connection connexion = null;
        PreparedStatement preparedStatement = null;
    	try {
            /* Récupération d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            /*
             * Préparation de la requête avec les objets passés en arguments
             * (ici, uniquement un id) et exécution.
             */
            preparedStatement = DAOUtils.initialisationRequetePreparee( connexion, SQL_SELECT_PAR_ID_MAITRE, false, partie.getId() );
            
            ResultSet tempResultSet = preparedStatement.executeQuery();
            /* Parcours de la ligne de données retournée dans le ResultSet */
            List<Utilisateur> utilisateurs = new ArrayList<Utilisateur>();
            List<Long> scores = new ArrayList<Long>();
            while ( tempResultSet.next() ) {
            	int id = tempResultSet.getInt( "id" );
            	int idMaitre = tempResultSet.getInt( "idMaitre" );
            	Long score = Long.valueOf(tempResultSet.getInt( "score" ));
            	int gagant = tempResultSet.getInt( "gagnant" );
            	int idUtilisateur = tempResultSet.getInt( "idUtilisateur" );
            	scores.add(score);
                PreparedStatement tempStatement = DAOUtils.initialisationRequetePreparee( connexion, SQL_SELECT_UTILISATEUR_PAR_ID, false, idUtilisateur );

                ResultSet tempResultSetUtilisateur = tempStatement.executeQuery();
                Utilisateur utilisateur = new Utilisateur();
                if ( tempResultSetUtilisateur.next() ) {
                	utilisateur.setId( resultSet.getLong( "id" ) );
                	utilisateur.setNomUtilisateur( resultSet.getString( "nomUtilisateur" ) );
                	utilisateur.setAdresseMail( resultSet.getString( "adresseMail" ) );
                	utilisateur.setMotsDePasse( resultSet.getString( "motDePasse" ) );
                	utilisateur.setElo( resultSet.getInt( "elo" ) );
                	utilisateur.setQuestionSecrete( resultSet.getInt( "questionSecrete" ) );
                	utilisateur.setReponseSecrete( resultSet.getString( "reponseSecrete" ) );
                	utilisateur.setPermission( resultSet.getInt( "permission" ) );
                }
                utilisateurs.add(utilisateur);
            }
            partie.setUtilisateurs(utilisateurs);
            partie.setScores(scores);
    	} catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
        	DAOUtils.fermeturesSilencieuses( resultSet, preparedStatement, connexion );
        }
        return partie;
    }
}
