package com.coupon.search;

import org.compass.annotations.Index;
import org.compass.annotations.Searchable;
import org.compass.annotations.SearchableId;
import org.compass.annotations.SearchableProperty;
import org.compass.annotations.Store;
import org.compass.annotations.SupportUnmarshall;

@Searchable(supportUnmarshall = SupportUnmarshall.TRUE)
public class Coupon
{

	private Long	id;

	private String	name;

	private String	category;

	private String	area;

	public Coupon()
	{

	}

	public Coupon( Long id, String name, String category, String area )
	{
		this.id = id;
		this.name = name;
		this.category = category;
		this.area = area;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals( Object obj )
	{
		if( this == obj )
			return true;
		if( obj == null )
			return false;
		if( getClass() != obj.getClass() )
			return false;
		Coupon other = (Coupon) obj;
		if( id == null )
		{
			if( other.id != null )
				return false;
		}
		else if( !id.equals( other.id ) )
			return false;
		if( name == null )
		{
			if( other.name != null )
				return false;
		}
		else if( !name.equals( other.name ) )
			return false;
		return true;
	}

	public String toString()
	{
		return id + ", " + name;
	}

	@SearchableId(store = Store.YES, index = Index.UN_TOKENIZED)
	public Long getId()
	{
		return id;
	}

	public void setId( Long id )
	{
		this.id = id;
	}

	@SearchableProperty(store = Store.YES, index = Index.TOKENIZED)
	public String getName()
	{
		return name;
	}

	public void setName( String name )
	{
		this.name = name;
	}

	@SearchableProperty(store = Store.YES, index = Index.UN_TOKENIZED)
	public String getCategory()
	{
		return category;
	}

	public void setCategory( String category )
	{
		this.category = category;
	}

	@SearchableProperty(store = Store.YES, index = Index.UN_TOKENIZED)
	public String getArea()
	{
		return area;
	}

	public void setArea( String area )
	{
		this.area = area;
	}
}
