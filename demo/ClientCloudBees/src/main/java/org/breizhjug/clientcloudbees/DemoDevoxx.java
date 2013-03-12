package org.breizhjug.clientcloudbees;

import com.cloudbees.api.ApplicationInfo;
import com.cloudbees.api.ApplicationListResponse;
import com.cloudbees.api.BeesClient;
import com.cloudbees.api.DatabaseInfo;
import com.cloudbees.api.DatabaseListResponse;
import java.util.Scanner;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

/**
 * Demo DevoxxFr 2013 - 3615 Cloud@Devoxx
 *
 */
public class DemoDevoxx {

    private static BeesClient client;

    public static void main(String[] args) throws CmdLineException, Exception {
        
        // Get api Key / Secret / Url from command line
        CmdLineOptions cmdArgs = new CmdLineOptions();
        CmdLineParser parser = new CmdLineParser(cmdArgs);
        parser.parseArgument(args);

        init(cmdArgs);
        
        Scanner scan = new Scanner(System.in);
        boolean end = false;
        
        while (!end) {
            printMenu();
            
            String cmd = scan.next();
            
            switch (cmd) {
                case "1":
                    printListApplication();
                    break;
                case "2":
                    printListDatabase();
                    break;
                case "Q":
                case "q":
                    end = true;
                    break;
                default:
                    System.out.println("---------------------");
            }
        }
        
        System.out.println("Bye !");

    }

    private static void init(CmdLineOptions cmdArgs) {
        // Cloudbees client
        client = new BeesClient(cmdArgs.apiUrl, cmdArgs.apiKey, cmdArgs.apiSecret, "xml", "1.0");
    }
    
    private static void printMenu() {
        System.out.println("-----------------------");
        System.out.println("-- 3615 Cloud@Devoxx --");
        System.out.println("-----------------------");
        System.out.println("1- List applications");
        System.out.println("2- List databases");
        System.out.println("Q- Quit");
    }

    private static void printListApplication() throws Exception {
        ApplicationListResponse appList = client.applicationList();
        for (ApplicationInfo appinfo : appList.getApplications()) {
            System.out.println(appinfo.getTitle() + " - " + appinfo.getStatus() + " - Id : " + appinfo.getId());
        }
    }

    private static void printListDatabase() throws Exception {
        DatabaseListResponse dbList = client.databaseList();
        for (DatabaseInfo dbInfo : dbList.getDatabases()) {
            System.out.println(dbInfo.getName() + " - " + dbInfo.getStatus() + " - " + dbInfo.getUsername());
        }
    }
}
