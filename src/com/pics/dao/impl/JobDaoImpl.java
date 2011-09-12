package com.pics.dao.impl;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Query;

import com.pics.dal.entity.Job;
import com.pics.dal.entity.User;
import com.pics.dao.AbstractDataAccessObject;
import com.pics.dao.ifc.JobDaoIfc;
import com.pics.dao.ifc.UserDaoIfc;

public class JobDaoImpl extends AbstractDataAccessObject<User> implements
/* Constant, */JobDaoIfc<Job>
{
	
	public JobDaoImpl()
	{
		super();
	}
	
	public void save ( Job jobEntity ) throws Exception
	{
		Job jobEntity1 = getEntityManager().merge( jobEntity );
		
		System.out.println( jobEntity1.getId() );
	}
	
	public List<Job> findByUser ( String key ) throws Exception
	{
		Query namedQuery = getEntityManager().createNamedQuery( "Job.findByUserEmail" );
		
		namedQuery.setParameter( "email", (String) key );
		
		List<Job> resultList = namedQuery.getResultList();
		
		return resultList;
	}
	
	public void updateJob ( Job job ) throws Exception
	{
		Job jobFromDb = findbyPK( job.getId() );
		if (jobFromDb == null)
		{
			throw new Exception( "Constant.NO_USER_FOUND " );
		}
		
		jobFromDb.setStatus( job.getStatus() );
		jobFromDb.setInsertDate( job.getInsertDate() );
		jobFromDb.setUpdateDate( job.getUpdateDate() );
		
		getEntityManager().merge( jobFromDb );
	}
	
	public Job findbyPK ( Integer id ) throws Exception
	{
		Job job = null;
		
		job = getEntityManager().find( Job.class, id );
		
		if (job != null)
		{
			return job;
		}
		else
		{
			throw new Exception( "Constant.NO_USER_FOUND " );
		}
	}
	
}
