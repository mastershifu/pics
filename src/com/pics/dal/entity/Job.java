package com.pics.dal.entity;

import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.pics.dao.BaseEntity;

@Entity
@NamedQueries({ @NamedQuery(name = "Job.findByUserEmail", query = "select job from Job as job where job.user.email  = :email")

})
@Table(name = "job")
@Cache(region = "jobRegion", usage = CacheConcurrencyStrategy.READ_WRITE)
public class Job extends BaseEntity
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2347355666372707717L;
	
	private Integer id;
	
	private User user;
	
	private Date insertDate;
	
	private Date updateDate;
	
	private Integer status;
	
	@Id
	@GeneratedValue(generator = "hibernateSequence")
	@SequenceGenerator(name = "hibernateSequence", sequenceName = "HIBERNATE_SEQUENCE")
	@Column(/* name = "USER_ID", */nullable = false, length = 12)
	public Integer getId ()
	{
		return id;
	}
	
	public void setId ( Integer id )
	{
		this.id = id;
	}
	
	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, updatable = false, insertable = true)
	public User getUser ()
	{
		return user;
	}
	
	public void setUser ( User user )
	{
		this.user = user;
	}
	
	@Override
	public int hashCode ()
	{
		return (id != null ? id.hashCode() : 0);
	}
	
	@Override
	public boolean equals ( Object o )
	{
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		
		Job that = (Job) o;
		
		if (id != null ? !id.equals( that.id ) : that.id != null)
			return false;
		
		return true;
	}

	@Column(name = "insert_date")
	public Date getInsertDate ()
	{
		return insertDate;
	}

	public void setInsertDate ( Date insertDate )
	{
		this.insertDate = insertDate;
	}

	@Column(name = "update_date")
	public Date getUpdateDate ()
	{
		return updateDate;
	}

	public void setUpdateDate ( Date updateDate )
	{
		this.updateDate = updateDate;
	}

	@Column(name = "status_id")
	public Integer getStatus ()
	{
		return status;
	}

	public void setStatus ( Integer status )
	{
		this.status = status;
	}
	
	
}
