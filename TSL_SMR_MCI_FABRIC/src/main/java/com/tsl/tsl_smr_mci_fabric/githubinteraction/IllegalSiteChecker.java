package com.tsl.tsl_smr_mci_fabric.githubinteraction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public interface IllegalSiteChecker {
	public static boolean check(String url) {
		URL oracle;
		try {
			oracle = new URL("https://api.stopmodreposts.org/minecraft/sites.txt");
			BufferedReader in = new BufferedReader(new InputStreamReader(oracle.openStream()));
			String url2;
		    while ((url2 = in.readLine()) != null)
		        if(matches(url,url2)) return true;
		    in.close();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return false;
	}
	
	private static boolean matches(String url,String url2) {
		if(url.matches(url2)) return true;
		return false;
	}
}
