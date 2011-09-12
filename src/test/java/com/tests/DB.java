package test.java.com.tests;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.coupon.search.Coupon;
import com.pics.bl.JobHandler;
import com.pics.bl.UserHandler;
import com.pics.dal.entity.Job;
import com.pics.dal.entity.User;

public class DB
{
	
	/**
	 * @param args
	 * @throws CouponException 
	 */
	public static void main ( String[] args ) throws Exception
	{
		//ApplicationContext appContext = AppContext.getSpringContext();
		
		ApplicationContext appContext = new ClassPathXmlApplicationContext(new String [] {"classpath:applicationContext.xml"});
		
		UserHandler userHandler = (UserHandler) appContext.getBean( "userHandler" );
		
		User user = userHandler.findUser( "dori@gmail.com" );
		
		System.out.println( user.getId() );
		
		Job job = new Job();
		
		job.setUser( user );
		
		job.setStatus( 13 );
		
		job.setInsertDate( new java.sql.Date( System.currentTimeMillis() ) );
		
		JobHandler jobrHandler = (JobHandler) appContext.getBean( "jobHandler" );
		
		jobrHandler.addJob( job );
		
		System.out.println( job.getId() );
	}
	
}
