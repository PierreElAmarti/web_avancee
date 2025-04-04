package dao;

import java.sql.Connection;

public interface DAOFactory
{

	public static DAOFactory getInstance(TypeDAO type)
	{
		if (TypeDAO.SQL.equals(type)) return DAOFactorySQL.getInstance();
		else return null;
	}

	public UtilisateurDao getUtilisateurDao();

	public PartieDao getPartieDao();

	public void initConnection(int n);

	public Connection getConnection() throws Exception;

	public void closeConnection(Connection aConnection);

	public void destroy();

}
