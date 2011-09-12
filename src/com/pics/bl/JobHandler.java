package com.pics.bl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.transaction.annotation.Transactional;

import com.pics.dal.entity.Job;
import com.pics.dal.entity.User;
import com.pics.dao.impl.JobDaoImpl;
import com.pics.dao.impl.UserDaoImpl;

public class JobHandler
{
	private static final Logger LOG = Logger.getLogger( JobHandler.class.getName() );
	
	private JobDaoImpl jobDao;
	
	public JobHandler()
	{
		super();
	}
	
	@Required
	public void setJobDao ( JobDaoImpl jobDao )
	{
		this.jobDao = jobDao;
	}
	
	@Transactional
	public void addJob ( Job jobEntity ) throws Exception
	{
		try
		{
			jobDao.save( jobEntity );
		}
		catch (Exception e)
		{
			LOG.info( "PROBLEM = " + e.getMessage() );
			e.printStackTrace();
			throw new Exception( "Constant.USER_WITH_SAME_EMAIL_ALREADY_EXIST " );
		}
	}
	
	public void updateJob ( Job jobEntity ) throws Exception
	{
		jobDao.updateJob( jobEntity );
	}
	
	public List<Job> findJobs ( String email ) throws Exception
	{
		return jobDao.findByUser( email );
	}
	
}
