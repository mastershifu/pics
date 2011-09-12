package com.pics.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

 @Transactional 
public abstract class AbstractDataAccessObject<E extends BaseEntity>{

	private static final Logger LOG = Logger.getLogger(AbstractDataAccessObject.class.getName());

	private EntityManager picsEntityManager;

	@PersistenceContext(unitName = "picsPersistent")
	public void setJustPrintEntityManager(EntityManager picsEntityManager) {
		this.picsEntityManager = picsEntityManager;
	}

	public EntityManager getEntityManager() {
		return picsEntityManager;
	}

	@SuppressWarnings("unchecked")
	public AbstractDataAccessObject() {

	}

}
