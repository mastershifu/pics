package com.pics.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PriceServlet extends HttpServlet
{
	
	private static final long serialVersionUID = 1262273389354339111L;
	
	@Override
	protected void service ( HttpServletRequest req, HttpServletResponse res )
			throws ServletException, IOException
	{
		System.out.println( "in price  - service" );
		synchronized (this)
		{
			try
			{
				Properties properties = new Properties();
				InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream( "../../price.properties" );
				// load the inputStream using the Properties
				properties.load( inputStream );
				
				res.getOutputStream().println( properties.getProperty( "price_10_15" ) );
				res.getOutputStream().println( properties.getProperty( "price_13_18" ) );
				res.getOutputStream().println( properties.getProperty( "price_15_21" ) );
				res.getOutputStream().println( properties.getProperty( "price_20_30" ) );
				res.getOutputStream().println( properties.getProperty( "price_30_40" ) );
				res.getOutputStream().println( properties.getProperty( "price_shipment" ) );
				res.getOutputStream().println( properties.getProperty( "price_shipment_super" ) );
				res.getOutputStream().println( properties.getProperty( "price_kavnas_20_30" ) );
				res.getOutputStream().println( properties.getProperty( "price_kavnas_30_40" ) );
				res.getOutputStream().println( properties.getProperty( "price_kavnas_35_35" ) );
				res.getOutputStream().println( properties.getProperty( "price_kavnas_35_50" ) );
				res.getOutputStream().println( properties.getProperty( "price_kavnas_40_40" ) );
				res.getOutputStream().println( properties.getProperty( "price_kavnas_40_60" ) );
				res.getOutputStream().println( properties.getProperty( "price_kavnas_50_50" ) );
				res.getOutputStream().println( properties.getProperty( "price_kavnas_50_70" ) );
				res.getOutputStream().println( properties.getProperty( "price_kavnas_60_60" ) );
				res.getOutputStream().println( properties.getProperty( "price_kavnas_60_90" ) );
				res.getOutputStream().flush();
				res.getOutputStream().close();
				
			}
			catch (Exception e)
			{
				System.out.println( "error- " + e.getMessage() );
			}
		}
	}
}
