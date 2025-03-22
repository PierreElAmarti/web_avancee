package dao;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class DAOMapper
{

	private HashMap<String, String> mapAtributeBDD = new HashMap<String, String>();
	private Class<?> classToInstanciate;

	public DAOMapper()
	{

	}

	public DAOMapper setAttribute(String BDDName, String AttributeName)
	{
		mapAtributeBDD.put(AttributeName, BDDName);
		return this;
	}

	public DAOMapper setClass(Class<?> aClass)
	{
		classToInstanciate = aClass;
		return this;
	}

	public Object map(ResultSet aResultSet)
	{
		try
		{
			Constructor<?> constructor = classToInstanciate.getDeclaredConstructor();
			constructor.setAccessible(true);
			Object vRet = constructor.newInstance();

			Field[] fields = classToInstanciate.getDeclaredFields();
			for (int i = 0; i < fields.length; i++)
			{
				if (fields[i].getName() != null && mapAtributeBDD.get(fields[i].getName()) != null)
				{
					fields[i].setAccessible(true);
					if (int.class.equals(fields[i].getType()))
					{
						fields[i].set(vRet, aResultSet.getInt(mapAtributeBDD.get(fields[i].getName())));
					}
					else if (Integer.class.equals(fields[i].getType()))
					{
						fields[i].set(vRet, aResultSet.getInt(mapAtributeBDD.get(fields[i].getName())));
					}
					else if (Long.class.equals(fields[i].getType()))
					{
						fields[i].set(vRet, aResultSet.getLong(mapAtributeBDD.get(fields[i].getName())));
					}
					else if (String.class.equals(fields[i].getType()))
					{
						fields[i].set(vRet, aResultSet.getString(mapAtributeBDD.get(fields[i].getName())));
					}
					else if (Date.class.equals(fields[i].getType()))
					{
						try {
							fields[i].set(vRet, aResultSet.getDate(mapAtributeBDD.get(fields[i].getName())));
						} catch(SQLException e) {
							fields[i].set(vRet, new Date(0));
						}
					}
					else if (boolean.class.equals(fields[i].getType()))
					{
						fields[i].set(vRet, (aResultSet.getInt(mapAtributeBDD.get(fields[i].getName())) == 1) ? true : false);
					}
				}
			}
			return vRet;
		}
		catch (Exception e)
		{

			return null;
		}
	}
}
