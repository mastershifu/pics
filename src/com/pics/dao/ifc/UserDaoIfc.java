package com.pics.dao.ifc;

import com.pics.dal.entity.User;
import com.pics.dao.BaseEntity;

public interface UserDaoIfc<E extends BaseEntity>
{
	
	public void save ( E userEntity ) throws Exception;
	
	public User findbyPK ( String key ) throws Exception;
	
	public void updateUser ( E userEntity ) throws Exception;
	
}
