package com.coupon.search;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Category;
import org.apache.log4j.Logger;
import org.compass.core.Compass;
import org.compass.core.CompassCallback;
import org.compass.core.CompassCallbackWithoutResult;
import org.compass.core.CompassDetachedHits;
import org.compass.core.CompassException;
import org.compass.core.CompassHit;
import org.compass.core.CompassHits;
import org.compass.core.CompassSession;
import org.compass.core.CompassTemplate;
import org.compass.core.Property;
import org.compass.core.config.CompassConfiguration;
import org.compass.core.config.CompassEnvironment;
import org.compass.core.lucene.LuceneEnvironment;
import org.compass.core.lucene.LuceneEnvironment.MergePolicy;

public class CompassWrapper
{
	private static final Logger		logger			= Logger.getLogger( CompassWrapper.class );

	private static CompassWrapper	instance		= null;

	private Compass					compass			= null;

	private CompassTemplate			compassTemplate	= null;

	public static synchronized CompassWrapper getInstance()
	{
		try
		{
			if( instance == null )
			{
				instance = new CompassWrapper();
			}
		}
		catch ( Exception e )
		{
			e.printStackTrace();

			logger.error( "CompassWrapper failed", e );

			throw new ExceptionInInitializerError( "CompassWrapper failed" );
		}

		return instance;
	}

	private CompassWrapper()
	{
		CompassConfiguration conf = new org.compass.annotations.config.CompassAnnotationsConfiguration().configure( CompassWrapper.class.getClassLoader().getResource( "compass.cfg.xml" ) );
		conf.setSetting( "compass.property.all.enabled", "false" );

		// conf.setSetting(
		// CompassEnvironment.ExecutorManager.EXECUTOR_MANAGER_TYPE,
		// "disabled");
		conf.setSetting( MergePolicy.TYPE, LuceneEnvironment.MergePolicy.LogDoc.NAME );
		conf.setSetting( LuceneEnvironment.MergePolicy.LogDoc.MAX_MERGE_DOCS, "5000" );
		compass = conf.buildCompass();
		compass.getSearchEngineOptimizer().stop();
		compassTemplate = new CompassTemplate( compass );
		
		logger.info( "compass init done. size: " + getSize() );
	}

	/*
	 * @SuppressWarnings("unchecked") public SearchContext searchInIndexPhase1(
	 * final String contentId, final int size ) { SearchContext result =
	 * (SearchContext) compassTemplate.execute( new CompassCallback() { public
	 * SearchContext doInCompass( CompassSession session ) throws
	 * CompassException { StringBuilder queryBuilder = new StringBuilder();
	 * 
	 * //queryBuilder.append( "+$/MigPageMemoryRecord/contentId:" ).append(
	 * contentId ); queryBuilder.append( "+content:" ).append( contentId );
	 * 
	 * logger.info( "Searching compass with: " + queryBuilder.toString() );
	 * 
	 * CompassHits hits = session.queryBuilder().queryString(
	 * queryBuilder.toString() ).toQuery().hits();
	 * 
	 * logger.info( "Searching compass with: " + queryBuilder.toString() +
	 * " returned total " + hits.length() );
	 * 
	 * SearchContext searchContext = new SearchContext();
	 * 
	 * String s = null;
	 * 
	 * for( CompassHit hit : hits ) { Property property =
	 * hit.getResource().getProperty( "timestamp" );
	 * 
	 * if( new Random().nextInt( 100 ) > 70 ) searchContext.good++; else
	 * searchContext.bad++;
	 * 
	 * s = property.getStringValue(); }
	 * 
	 * logger.info( "Searching compass with: " + queryBuilder.toString() +
	 * " last s is " + s );
	 * 
	 * //CompassDetachedHits detachedHits = hits.detach( 0, size );
	 * 
	 * //searchContext.hits = detachedHits;
	 * 
	 * hits.close();
	 * 
	 * return searchContext; } } );
	 * 
	 * logger.info( "searchInIndexPhase1. end." );
	 * 
	 * return result; }
	 */

	@SuppressWarnings("unchecked")
	public CompassDetachedHits searchInIndexPhase1( final String contentId, final int start,
		final int end )
	{
		CompassDetachedHits result = (CompassDetachedHits) compassTemplate.execute( new CompassCallback()
		{
			public CompassDetachedHits doInCompass( CompassSession session )
				throws CompassException
			{
				StringBuilder queryBuilder = new StringBuilder();

				// queryBuilder.append( "+$/MigPageMemoryRecord/contentId:"
				// ).append( contentId );
				queryBuilder.append( "+name:" ).append( contentId ).append( '~' );

				logger.info( "Searching compass with: " + queryBuilder.toString() );

				CompassHits hits = session.queryBuilder().queryString( queryBuilder.toString() ).toQuery().hits();

				logger.info( "Searching compass with: " + queryBuilder.toString() + " returned total " + hits.length() );

				CompassDetachedHits detachedHits = hits.detach( start, end );

				hits.close();

				return detachedHits;
			}
		} );

		logger.info( "searchInIndexPhase1. end." );

		return result;
	}

	@SuppressWarnings("unchecked")
	public CompassDetachedHits searchInIndexPhase2( final String name, final int start,
		final int end )
	{
		CompassDetachedHits result = (CompassDetachedHits) compassTemplate.execute( new CompassCallback()
		{
			public CompassDetachedHits doInCompass( CompassSession session )
				throws CompassException
			{
				StringBuilder queryBuilder = new StringBuilder();

				// queryBuilder.append( "+$/MigPageMemoryRecord/contentId:"
				// ).append( contentId );
				queryBuilder.append( "+name:" ).append( name ).append( '*' );

				logger.info( "Searching compass with: " + queryBuilder.toString() );

				CompassHits hits = session.queryBuilder().queryString( queryBuilder.toString() ).toQuery().hits();

				logger.info( "Searching compass with: " + queryBuilder.toString() + " returned total " + hits.length() );

				CompassDetachedHits detachedHits = hits.detach( start, end );

				hits.close();

				return detachedHits;
			}
		} );

		logger.info( "searchInIndexPhase2. end." );

		return result;
	}
	
	@SuppressWarnings("unchecked")
	public CompassDetachedHits searchInIndexPhase3( final String name, final String category, final String area, final int start,
		final int end )
	{
		CompassDetachedHits result = (CompassDetachedHits) compassTemplate.execute( new CompassCallback()
		{
			public CompassDetachedHits doInCompass( CompassSession session )
				throws CompassException
			{
				StringBuilder queryBuilder1 = new StringBuilder();
				StringBuilder queryBuilder2 = new StringBuilder();

				if( name != null && !name.isEmpty() )
				{
					//queryBuilder.append( "+name:\"" ).append( name ).append( "*\"" );
					
					queryBuilder1.append( "+name:" ).append( name ).append( "*" );
				}
				
				if( category != null && !category.isEmpty() )
					queryBuilder2.append( " +category:" ).append( category );
				
				if( area != null && !area.isEmpty() )
					queryBuilder2.append( " +area:" ).append( area );

				String try1 = queryBuilder1.toString() + queryBuilder2.toString();
				
				logger.info( "Searching compass with: " + try1 );

				CompassHits hits = session.queryBuilder().queryString( try1 ).toQuery().hits();

				logger.info( "Searching compass with: " + try1 + " returned total " + hits.length() );

				if( hits.length() == 0 )
				{
					String try2 = "+name:\"" + name + "*\"" + queryBuilder2.toString();
					
					logger.info( "Searching compass with: " + try2 );

					hits = session.queryBuilder().queryString( try2 ).toQuery().hits();

					logger.info( "Searching compass with: " + try2 + " returned total " + hits.length() );
				}
				
				CompassDetachedHits detachedHits = hits.detach( start, end );

				hits.close();

				return detachedHits;
			}
		} );

		logger.info( "searchInIndexPhase2. end." );

		return result;
	}

	public void addToIndex( final Coupon coupon )
	{
		logger.debug( "addToIndex start. id: " + coupon.getId() );

		compassTemplate.execute( new CompassCallbackWithoutResult()
		{
			protected void doInCompassWithoutResult( CompassSession session )
				throws CompassException
			{
				// Entering the message inside compass.
				session.save( coupon );
			}
		} );
	}

	public void close()
	{
		compass.close();
	}
	
	@SuppressWarnings("unchecked")
	public synchronized Long getSize()
	{
		/*if( stopped )
		{
			logger.info( "cant getSize, Compass stopped" );

			return null;
		}*/

		Long count = (Long) compassTemplate.execute( new CompassCallback()
		{
			public Long doInCompass( CompassSession session ) throws CompassException
			{
				//StringBuilder queryBuilder = new StringBuilder();

				//queryBuilder.append( "+timestamp:[" ).append( 0 ).append( " TO " ).append( 1597233600000L ).append( "] " );

				//logger.info( "Sizing compass with: " + queryBuilder.toString() );

				//Long count = session.queryBuilder().queryString( queryBuilder.toString() ).toQuery().count();

				logger.info( "Sizing compass with: matchAll" );

				Long count = null;

				try
				{
					//lock.lockWrite();

					count = session.queryBuilder().matchAll().count();
				}
				catch ( Exception e )
				{
					logger.error( "TOTO exception", e );
				}
				finally
				{
					/*try
					{
						lock.unlockWrite();
					}
					catch ( InterruptedException e )
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}*/
				}

				logger.info( "Sizing compass with matchAll, count: " + count );

				return count;
			}
		} );

		return count;
	}

	public static void main( String[] args )
	{
		CompassWrapper.getInstance().addToIndex( new Coupon( 1L, "ארומה", "food", "center" ) );
		CompassWrapper.getInstance().addToIndex( new Coupon( 2L, "סיימון", "food", "center" ) );
		CompassWrapper.getInstance().addToIndex( new Coupon( 3L, "הבית של פיסטוק", "toys", "soutch" ) );
		CompassWrapper.getInstance().addToIndex( new Coupon( 4L, "הביסלי של פיסטוק", "food", "east" ) );
		CompassWrapper.getInstance().addToIndex( new Coupon( 5L, "קפה ארומה", "food", "center" ) );

		//CompassDetachedHits hits = CompassWrapper.getInstance().searchInIndexPhase2( "א", 0, 100 );
		
		CompassDetachedHits hits = CompassWrapper.getInstance().searchInIndexPhase3( "ק", "food", "center", 0, 100 );

		Coupon hit = (Coupon) hits.data( 0 );

		System.out.println( hit.getId() );
		System.out.println( hit.getName() );
	}
}
