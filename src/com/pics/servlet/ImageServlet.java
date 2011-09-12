package com.pics.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pics.mail.SendMail;
import com.pics.misc.Base64;

public class ImageServlet extends HttpServlet
{
	
	private static final long serialVersionUID = 1262273389354339111L;
	private static final long orderNumber = 0;
	
	@Override
	protected void service ( HttpServletRequest req, HttpServletResponse res )
			throws ServletException, IOException
	{
		System.out.println( "in servlet  - service" );
		synchronized (this)
		{
			try
			{
				// retrieve data from request
				
				String imageName = req.getParameter( "imageName" );
				String imageByte = req.getParameter( "image" );
				String imageSize = req.getParameter( "imageSize" );
				String imageCopy = req.getParameter( "imageCopy" );
				String imageFinish = req.getParameter( "imageFinish" );
				String imagePaper = req.getParameter( "imagePaper" );
				String start = req.getParameter( "start" );
				String numberOfPic = req.getParameter( "numberOfPic" );
				String printType = req.getParameter( "printType" );
				String paymentType = req.getParameter( "paymentType" );
				
				String customerName = req.getParameter( "userName" );
				String customerEmail = req.getParameter( "userEmail" );
				String customerPhone = req.getParameter( "userPhone" );
				String customerCity = req.getParameter( "userCity" );
				String customerStreetAndHome = req.getParameter( "userStreet" );
				
				String allTotal = req.getParameter( "allTotal" );
				String shipmentType = req.getParameter( "shipmentType" );
				
				System.out.println( "customerEmail-" + customerEmail + "-" );
				System.out.println( "paymentType - " + paymentType );
				System.out.println( "printType - " + printType );
				System.out.println( "imageCopy - " + imageCopy );
				System.out.println( "imageFinish - " + imageFinish );
				System.out.println( "imagePaper - " + imagePaper );
				System.out.println( "start - " + start );
				
				// get image as byte array
				byte[] imageByteArray = Base64.decode( imageByte );
				
				// calculate current date
				Calendar calendar = new GregorianCalendar();
				int month = calendar.get( Calendar.MONTH );
				int updatedMonth = month + 1;
				String dateString = calendar.get( Calendar.DATE ) + "_"
						+ updatedMonth + "_" + calendar.get( Calendar.YEAR );
				
				// calculate image directory
				String imageDirectory = null;
				if (imageFinish == null && imagePaper == null)
				{
					imageDirectory = "../../images/" + dateString + "/"
							+ paymentType + "/" + printType + "/"
							+ customerPhone + "/" + imageSize + "/" + "/X"
							+ imageCopy;
					
				}
				else
				{
					imageDirectory = "../../images/" + dateString + "/"
							+ paymentType + "/" + printType + "/"
							+ customerPhone + "/" + imageSize + "/"
							+ imagePaper + "/" + imageFinish + "/X" + imageCopy;
					
				}
				
				// create image directory
				new File( imageDirectory ).mkdirs();
				
				// save image
				FileOutputStream fos = new FileOutputStream( imageDirectory
						+ "/" + imageName );
				fos.write( imageByteArray );
				fos.close();
				
				if (start.equals( "1" ))
				{
					
					String subject = "Pic2Print Order number - " + "Haz"
							+ orderNumber + " (1/" + numberOfPic + ")"
							+ "user phone =" + customerPhone;
					StringBuffer body = new StringBuffer( "Customer Details :\n" );
					body.append( "Customer Name = " + customerName + "\n" );
					body.append( "Customer Email = " + customerEmail + "\n" );
					body.append( "Customer Phone = " + customerPhone + "\n" );
					body.append( "Customer City = " + customerCity + "\n" );
					body.append( "Customer Street = " + customerStreetAndHome
							+ "\n" );
					body.append( "Order Details :\n" );
					body.append( "Order - #" + "Haz" + orderNumber + "\n" );
					body.append( "Order - number of images to print = "
							+ numberOfPic + "\n" );
					body.append( "Order - number of copies to print = "
							+ imageCopy + "\n" );
					body.append( "Order - print type  = " + printType + "\n" );
					body.append( "Order - print size  = " + imageSize + "\n" );
					body.append( "Order - print finish  = " + imageFinish
							+ "\n" );
					if (imagePaper != null)
						body.append( "Order - print paper  = " + imagePaper
								+ "\n" );
					body.append( "Order - paymentType  = " + paymentType + "\n" );
					body.append( "Order - Amount(Nis)  = " + allTotal + "\n" );
					body.append( "Order - shippment  = " + shipmentType + "\n" );
					
					try
					{
						// SendMail mail = new SendMail(subject,
						// body.toString());
						String[] toArray = { customerEmail };
						SendMail mail = new SendMail( "Pic2Print", body.toString(), toArray );
						mail.send();
					}
					catch (Exception ee)
					{
						System.out.println( "Error in SendMail - "
								+ ee.getMessage() );
					}
				}
				
			}
			catch (Exception e)
			{
				System.out.println( "Error - " + e.getMessage() );
			}
		}
	}
}
