package org.breizhjug.clientcloudbees;

import com.cloudbees.api.ApplicationInfo;
import com.cloudbees.api.ApplicationListResponse;
import com.cloudbees.api.BeesClient;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

/**
 * Demo DevoxxFr 2013 - 3615 Cloud@Devoxx
 *
 */
public class DemoDevoxx 
{
    public static void main( String[] args ) throws CmdLineException, Exception
    {
        // Get api Key / Secret / Url from command line
        CmdLineOptions cmdArgs = new CmdLineOptions();
        CmdLineParser parser = new CmdLineParser(cmdArgs);
        parser.parseArgument(args);
        
        // Cloudbees client
        BeesClient client = new BeesClient(cmdArgs.apiUrl, cmdArgs.apiKey, cmdArgs.apiSecret, "xml", "1.0");
        
        // List applications
        ApplicationListResponse appList = client.applicationList();
        for (ApplicationInfo appinfo : appList.getApplications()) {
             System.out.println(appinfo.getTitle() + " - " + appinfo.getStatus());
        }
    }
}
