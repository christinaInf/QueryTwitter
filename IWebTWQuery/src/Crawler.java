/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
*/
 

import java.applet.*;
import twitter4j.*;
import java.awt.*;
import java.awt.List;
import javax.swing.*;
import java.net.*;
import java.util.*;
import java.util.regex.*;
import java.io.*;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
 
/**
*
* @author Obi
*/


public class Crawler {
	
	static String userUrl, line;
	static int responseCode;
	public boolean crawling;
	static User userName;
    private final static String CONSUMER_KEY = "icyNdYdy38KYczlW8oF8Q";
    private final static String CONSUMER_KEY_SECRET = "z05sakQC5wXUqloOV0MSZYmGL2Q1E9kjZfj7ilZRRTY";
	
    /**
    * @param args the command line arguments
    */
	public static String readPage() {
        try {
            // Open connection to URL for reading.
        	// -------------------------------------
        	//userName = JOptionPane.showInputDialog("Enter Username: ");
        	//pageUrl = "http://www.twiter.com/" + userName;
        	URL pageUrl = new URL("http://www.mangahead.com");
        	responseCode = ((HttpURLConnection) pageUrl.openConnection()).getResponseCode();
            BufferedReader reader = new BufferedReader(new InputStreamReader(pageUrl.openStream()));
            
            // Read page into buffer.
            StringBuffer pageBuffer = new StringBuffer();
            while ((line = reader.readLine()) != null) {
                pageBuffer.append(line);
                //System.out.println(line);
               // reader.close();
            }
            System.out.println(responseCode);
            return pageBuffer.toString();
        } catch (Exception e) {
        }
        
        return null;
    }
	
	private static ArrayList retrieveLinks(URL pageUrl, String pageContents) 
	 		{
		// Compile link matching pattern.
        	Pattern p = Pattern.compile("<a\\s+href\\s*=\\s*\"?(.*?)[\"|>]",
            Pattern.CASE_INSENSITIVE);
        	Matcher m = p.matcher(pageContents);
		     //return (linkList);
		     
		     // Create list of link matches.
		        ArrayList linkList = new ArrayList();
		        while (m.find()) {
		            String link = m.group(1).trim();
		            
		            // Skip empty links.
		            if (link.length() < 1) {
		                continue;
		            }
		            
		            // Skip links that are just page anchors.
		            if (link.charAt(0) == '#') {
		                continue;
		            }
		            
		            // Skip mailto links.
		            if (link.indexOf("mailto:") != -1) {
		                continue;
		            }
		            
		            // Skip JavaScript links.
		            if (link.toLowerCase().indexOf("javascript") != -1) {
		                continue;
		            }
		         // Prefix absolute and relative URLs if necessary.
		            if (link.indexOf("://") == -1) {
		                // Handle absolute URLs.
		                if (link.charAt(0) == '/') {
		                    link = "http://" + pageUrl.getHost() + link;
		                    // Handle relative URLs.
		                } else {
		                    String file = pageUrl.getFile();
		                    if (file.indexOf('/') == -1) {
		                        link = "http://" + pageUrl.getHost() + "/" + link;
		                    } else {
		                        String path =
		                                file.substring(0, file.lastIndexOf('/') + 1);
		                        link = "http://" + pageUrl.getHost() + path + link;
		                    }
		                }
		            }
		            
		            // Remove anchors from link.
		            int index = link.indexOf('#');
		            if (index != -1) {
		                link = link.substring(0, index);
		            }
		            // Add link to list.
		            linkList.add(link);
		        }
		        System.out.println(linkList);
		        return (linkList);
		            
		    }
	
	public static void retrieveTweets() {
        try {
            // gets Twitter instance with default credentials
            Twitter twitter = new TwitterFactory().getInstance();
            userName = twitter.verifyCredentials();
            List<Status> statuses = twitter.getHomeTimeline();
            System.out.println("Showing @" + userName.getScreenName() + "'s home timeline.");
            for (Status status : statuses) {
                System.out.println("@" + status.getUser().getScreenName() + " - " + status.getText());
            }
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to get timeline: " + te.getMessage());
            System.exit(-1);
        }
    }
	
	private static URL URL(String urlAddress) 
	{ try {
	URL pageUrl = new URL(urlAddress);
	return pageUrl;
	 } catch (Exception e) {
        }
	return null;}
	
	 /* Determine whether or not search string is
    matched in the given page contents. 
   private boolean searchStringMatches(
           String pageContents, String searchString,
           boolean caseSensitive) {
       String searchContents = pageContents;
       
    If case sensitive search, lowercase
      page contents for comparison. 
       if (!caseSensitive) {
           searchContents = pageContents.toLowerCase();
       }
       
       // Split search string into individual terms.
       Pattern p = Pattern.compile("[\\s]+");
       String[] terms = p.split(searchString);
       
       // Check to see if each term matches.
       for (int i = 0; i < terms.length; i++) {
           if (caseSensitive) {
               if (searchContents.indexOf(terms[i]) == -1) {
                   return false;
               }
           } else {
               if (searchContents.indexOf(terms[i].toLowerCase()) == -1) {
                   return false;
               }
           }
       }
       
       return true;
   }*/
	
    public static String getSavedAccessTokenSecret() {
		 // consider this is method to get your previously saved Access Token
		 // Secret
		 return "oC8tImRFL6i8TuRkTEaIcWsF8oY4SL5iTGNkG9O0Q";
		    }
    
    private static String getSavedAccessToken() {
		 // consider this is method to get your previously saved Access Token
		 return "102333999-M4W1Jtp8y8QY8RH7OxGWbM5Len5xOeeTUuG7QfcY";
		    }

	 public static void connectTwitter() throws TwitterException, IOException {

		 Twitter twitter = new TwitterFactory().getInstance();
		 twitter.setOAuthConsumer(CONSUMER_KEY, CONSUMER_KEY_SECRET);

		 // here's the difference
		 String accessToken = getSavedAccessToken();
		 String accessTokenSecret = getSavedAccessTokenSecret();
		 AccessToken oathAccessToken = new AccessToken(accessToken,
		  accessTokenSecret);

		 twitter.setOAuthAccessToken(oathAccessToken);
		 // end of difference

		 twitter.updateStatus("Hi, im updating status again from Namex Tweet for Demo");

		 System.out.println("\nMy Timeline:");

		 // I'm reading your timeline
		 ResponseList list = twitter.getHomeTimeline();
		 for (Status each : list) {

		     System.out.println("Sent by: @" + each.getUser().getScreenName()
		      + " - " + each.getUser().getName() + "\n" + each.getText()
		      + "\n");
		 }

		    }
	    

	
    public static void main(String[] args)  throws Exception {
    	
    	//String pageContents = readPage();
    	//URL pageUrl = new URL("http://www.mangahead.com");
    	/*if (pageContents != null && pageContents.length() > 0) 
    		{
            // Retrieve list of valid links from page.
            ArrayList links = retrieveLinks(URL ("http://www.mangahead.com"), pageContents);
            }*/
    	new Crawler().connectTwitter();
    	//retrieveTweets();
    }



}