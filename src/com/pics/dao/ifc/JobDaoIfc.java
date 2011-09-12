package com.pics.dao.ifc;

import java.util.List;

import com.pics.dal.entity.Job;
import com.pics.dal.entity.User;
import com.pics.dao.BaseEntity;

public interface JobDaoIfc<E extends BaseEntity>
{
	public void save ( E jobEntity ) throws Exception;
	
	public List<Job> findByUser ( String key ) throws Exception;
	
	public void updateJob ( E jobEntity ) throws Exception;
	
}
