package com.pics.dao.impl;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Query;

import com.pics.dal.entity.User;
import com.pics.dao.AbstractDataAccessObject;
import com.pics.dao.ifc.UserDaoIfc;

public class UserDaoImpl extends AbstractDataAccessObject<User> implements
/* Constant, */UserDaoIfc<User>
{
	
	public UserDaoImpl()
	{
		super();
	}
	
	public void save ( User userEntity ) throws Exception
	{
		getEntityManager().persist( userEntity );
	}
	
	public User findbyPK ( String key ) throws Exception
	{
		User userEntity = null;
		userEntity = getEntityManager().find( User.class, key );
		if (userEntity != null)
		{
			return userEntity;
		}
		else
		{
			throw new Exception( "Constant.NO_USER_FOUND " );
		}
	}
	
	@SuppressWarnings("unchecked")
	public User findByBusinessKeys ( Serializable... businessKeys )
	{
		assert businessKeys != null;
		assert businessKeys.length == 1;
		Query namedQuery = getEntityManager().createNamedQuery( "User.findByEmail" );
		namedQuery.setParameter( "email", (String) businessKeys[0] );
		List<User> resultList = namedQuery.getResultList();
		if (resultList.isEmpty())
		{
			return null;
		}
		else
		{
			return (User) resultList.get( 0 );
		}
	}
	
	public void updateUser ( User userEntity ) throws Exception
	{
		User userDB = findByBusinessKeys( userEntity.getEmail() );
		if (userDB == null)
		{
			throw new Exception( "Constant.NO_USER_FOUND " );
		}
		
		userDB.setPhone( userEntity.getPhone() );
		userDB.setEmail( userEntity.getEmail() );
		getEntityManager().merge( userDB );
	}
	
}
