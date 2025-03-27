package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.exception.DAOException;
import model.Partie;
import model.PartieMaitre;
import model.Utilisateur;

public class PartieDaoImpl implements PartieDao
{

	private static final String SQL_SELECT_MAITRE = "SELECT id, dateInf, time FROM PartieMaitre LIMIT 10";
	private static final String SQL_SELECT_IDMAITRE_PAR_IDUTILISATEUR = "select distinct idMaitre from Partie where idUtilisateur=? order by idMaitre desc limit 10";
//	private static final String SQL_SELECT_PARTIE = "SELECT id, idMaitre, score, gagnant, idUtilisateur FROM Partie ";
//	private static final String SQL_SELECT_UTILISATEUR_PAR_ID = "SELECT id, nomUtilisateur, adresseMail, motDePasse, elo, questionSecrete, reponseSecrete, permission FROM Utilisateur WHERE id = ?";
	private static final String SQL_SELECT_PAR_ID_MAITRE = "SELECT id, dateInf, time FROM PartieMaitre WHERE id = ? LIMIT 10";
	private static final String SQL_SELECT_PARTIE_PAR_ID_MAITRE = "SELECT id, idMaitre, score, gagnant, idUtilisateur FROM Partie WHERE idMaitre = ? ";
	private static final String SQL_INSERT_MAITRE = "INSERT INTO PartieMaitre (dateInf, time) VALUES (?, ?)";
	private static final String SQL_INSERT_PARTIE = "INSERT INTO Partie (idMaitre, score, gagnant, idUtilisateur) VALUES (?, ?, ?, ?)";
	private static final String SQL_DELETE_PAR_ID_MAITRE = "DELETE FROM PartieMaitre WHERE id = ?";
//    private static final String SQL_DELETE_PAR_IDMAITRE_PARTIE = "DELETE FROM Partie WHERE idMaitre = ?";

	private DAOFactory daoFactory;

	private static DAOMapper daoMapperPartieMaitre = new DAOMapper().setClass(PartieMaitre.class)
			.setAttribute("id", "id").setAttribute("dateInf", "date").setAttribute("time", "temps");
	private static DAOMapper daoMapperPartie = new DAOMapper().setClass(Partie.class).setAttribute("id", "id")
			.setAttribute("idMaitre", "idMaitre").setAttribute("score", "score").setAttribute("gagnant", "gagnant");

	PartieDaoImpl(DAOFactory daoFactory)
	{
		this.daoFactory = daoFactory;
		DAOUtils.setDAOFactory(daoFactory);
	}
	
	@Override
	public List<PartieMaitre> historique(Utilisateur utilisateur) throws DAOException
	{
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<PartieMaitre> parties = new ArrayList<PartieMaitre>();

		try
		{
			connection = daoFactory.getConnection();
			preparedStatement = DAOUtils.initialisationRequetePreparee(connection,
					SQL_SELECT_IDMAITRE_PAR_IDUTILISATEUR, false, utilisateur.getId());
			resultSet = preparedStatement.executeQuery();
			List<Long> idMaitres = new ArrayList<Long>();
			while (resultSet.next())
			{
				idMaitres.add(resultSet.getLong("idMaitre"));
			}
			for (Long idMaitre : idMaitres)
			{
				parties.add(trouver(idMaitre));
			}
		}
		catch (SQLException e)
		{
			throw new DAOException(e);
		}
		finally
		{
			DAOUtils.fermeturesSilencieuses(resultSet, preparedStatement, connection);
		}

		return parties;
	}

	/* Implémentation de la méthode définie dans l'interface PartieDao */
	@Override
	public PartieMaitre trouver(long id) throws DAOException
	{
		return trouver(SQL_SELECT_PAR_ID_MAITRE, id);
	}

	/* Implémentation de la méthode définie dans l'interface PartieDao */
	@Override
	public void creer(PartieMaitre partie) throws DAOException
	{
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet valeursAutoGenerees = null;

		try
		{
			connexion = daoFactory.getConnection();
			preparedStatement = DAOUtils.initialisationRequetePreparee(connexion, SQL_INSERT_MAITRE, true,
					partie.getDate(), partie.getTemps());
			int statut = preparedStatement.executeUpdate();
			if (statut == 0)
			{
				throw new DAOException("Échec de la création de la partie maitre, aucune ligne ajoutée dans la table.");
			}
			valeursAutoGenerees = preparedStatement.getGeneratedKeys();
			if (valeursAutoGenerees.next())
			{
				partie.setId(valeursAutoGenerees.getLong(1));
				for (Partie vPartieFils : partie.getPartieFils())
				{
					PreparedStatement tempStatement = DAOUtils.initialisationRequetePreparee(connexion,
							SQL_INSERT_PARTIE, true, partie.getId(), vPartieFils.getScore(),
							((vPartieFils.isGagnant()) ? 1 : 0), vPartieFils.getUtilisateur().getId());
					int tempStatut = tempStatement.executeUpdate();
					if (tempStatut == 0)
					{
						throw new DAOException(
								"Échec de la création de la partie, aucune ligne ajoutée dans la table.");
					}
				}
			}
			else
			{
				throw new DAOException(
						"Échec de la création de la partie maitre en base, aucun ID auto-généré retourné.");
			}
		}
		catch (SQLException e)
		{
			throw new DAOException(e);
		}
		finally
		{
			DAOUtils.fermeturesSilencieuses(valeursAutoGenerees, preparedStatement, connexion);
		}
	}

	/* Implémentation de la méthode définie dans l'interface PartieDao */
	@Override
	public List<PartieMaitre> lister() throws DAOException
	{
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<PartieMaitre> parties = new ArrayList<PartieMaitre>();

		try
		{
			connection = daoFactory.getConnection();
			preparedStatement = connection.prepareStatement(SQL_SELECT_MAITRE);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next())
			{
				parties.add(map(resultSet));
			}
		}
		catch (SQLException e)
		{
			throw new DAOException(e);
		}
		finally
		{
			DAOUtils.fermeturesSilencieuses(resultSet, preparedStatement, connection);
		}

		return parties;
	}

	/* Implémentation de la méthode définie dans l'interface PartieDao */
	@Override
	public void supprimer(PartieMaitre partie) throws DAOException
	{
		Connection connexion = null;
		PreparedStatement preparedStatement = null;

		try
		{
			connexion = daoFactory.getConnection();
			preparedStatement = DAOUtils.initialisationRequetePreparee(connexion, SQL_DELETE_PAR_ID_MAITRE, true,
					partie.getId());
			int statut = preparedStatement.executeUpdate();
			if (statut == 0)
			{
				throw new DAOException(
						"Échec de la suppression de la partieMaitre, aucune ligne supprimée de la table.");
			}
			else
			{
				partie.setId(null);
			}
		}
		catch (SQLException e)
		{
			throw new DAOException(e);
		}
		finally
		{
			DAOUtils.fermeturesSilencieuses(preparedStatement, connexion);
		}
	}

	/*
	 * Méthode générique utilisée pour retourner une partie depuis la base de
	 * données, correspondant à la requête SQL donnée prenant en paramètres les
	 * objets passés en argument.
	 */
	private PartieMaitre trouver(String sql, Object... objets) throws DAOException
	{
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		PartieMaitre partie = null;

		try
		{
			/* Récupération d'une connexion depuis la Factory */
			connexion = daoFactory.getConnection();
			/*
			 * Préparation de la requête avec les objets passés en arguments (ici,
			 * uniquement un id) et exécution.
			 */
			preparedStatement = DAOUtils.initialisationRequetePreparee(connexion, sql, false, objets);
			resultSet = preparedStatement.executeQuery();
			/* Parcours de la ligne de données retournée dans le ResultSet */
			if (resultSet.next())
			{
				partie = map(resultSet);
			}
		}
		catch (SQLException e)
		{
			throw new DAOException(e);
		}
		finally
		{
			DAOUtils.fermeturesSilencieuses(resultSet, preparedStatement, connexion);
		}

		return partie;
	}

	/*
	 * Simple méthode utilitaire permettant de faire la correspondance (le mapping)
	 * entre une ligne issue de la table des parties (un ResultSet) et un bean
	 * partie.
	 */
	private PartieMaitre map(ResultSet resultSet) throws SQLException
	{
		PartieMaitre partie = (PartieMaitre) daoMapperPartieMaitre.map(resultSet);

		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		try
		{
			/* Récupération d'une connexion depuis la Factory */
			connexion = daoFactory.getConnection();
			/*
			 * Préparation de la requête avec les objets passés en arguments (ici,
			 * uniquement un id) et exécution.
			 */
			preparedStatement = DAOUtils.initialisationRequetePreparee(connexion, SQL_SELECT_PARTIE_PAR_ID_MAITRE,
					false, partie.getId());

			ResultSet tempResultSet = preparedStatement.executeQuery();
			/* Parcours de la ligne de données retournée dans le ResultSet */
			List<Partie> partieFils = new ArrayList<Partie>();
			while (tempResultSet.next())
			{
				Partie tempPartieFils = (Partie) daoMapperPartie.map(tempResultSet);
				int idUtilisateur = tempResultSet.getInt("idUtilisateur");
				tempPartieFils.setUtilisateur(daoFactory.getUtilisateurDao().trouver(idUtilisateur));
				partieFils.add(tempPartieFils);
			}
			partie.setPartieFils(partieFils);
		}
		catch (SQLException e)
		{
			throw new DAOException(e);
		}
		finally
		{
			DAOUtils.fermeturesSilencieuses(preparedStatement, connexion);
		}
		return partie;
	}
}
