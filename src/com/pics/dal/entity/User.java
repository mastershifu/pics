package com.pics.dal.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.pics.dao.BaseEntity;

@Entity
@NamedQueries( { @NamedQuery(name = "User.findByEmail", query = "select user from User as user where user.email  = :email")

})
@Table(name = "user")
@Cache(region = "couponRegion", usage = CacheConcurrencyStrategy.READ_WRITE)
public class User extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2347355666372707717L;

	private Integer id;

	private String email;

	private String name;

	private String phone;

	@Id
	@GeneratedValue(generator = "hibernateSequence")
	@SequenceGenerator(name = "hibernateSequence", sequenceName = "HIBERNATE_SEQUENCE")
	@Column(/*name = "USER_ID",*/ nullable = false, length = 12)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(nullable = false, unique = true)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		return (id != null ? id.hashCode() : 0);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		User that = (User) o;

		if (id != null ? !id.equals(that.id) : that.id != null)
			return false;

		return true;
	}

	/*
	 * private Collection<Coupon> coupons;
	 * 
	 * @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch =
	 * FetchType.EAGER)
	 * 
	 * @Fetch(value = FetchMode.SUBSELECT)
	 * 
	 * @Cache(region = "couponRegion", usage =
	 * CacheConcurrencyStrategy.READ_WRITE) public Collection<Coupon>
	 * getCoupons() { return coupons; }
	 * 
	 * public void setCoupons(Collection<Coupon> coupons) { this.coupons =
	 * coupons; }
	 */

}
