package com.pics.bl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.transaction.annotation.Transactional;

import com.pics.dal.entity.User;
import com.pics.dao.impl.UserDaoImpl;

public class UserHandler
{
	private static final Logger LOG = Logger.getLogger( UserHandler.class.getName() );
	
	private UserDaoImpl userDao;
	
	public UserHandler()
	{
		super();
	}
	
	@Required
	public void setUserDao ( UserDaoImpl userDao )
	{
		this.userDao = userDao;
	}
	
	@Transactional
	public void addUser ( User userEntity ) throws Exception
	{
		try
		{
			userDao.save( userEntity );
		}
		catch (Exception e)
		{
			LOG.info( "PROBLEM = " + e.getMessage() );
			e.printStackTrace();
			throw new Exception( "Constant.USER_WITH_SAME_EMAIL_ALREADY_EXIST " );
		}
	}
	
	public void updateUser ( User userEntity ) throws Exception
	{
		userDao.updateUser( userEntity );
	}
	
	public User findUser ( String email ) throws Exception
	{
		return userDao.findByBusinessKeys( email );
	}
	
}
