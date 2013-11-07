package Utilities;

import java.sql.ResultSet;

/**
 * User: Ryan
 * Date: 2013-11-06
 * Time: 12:24 PM
 */
public class RPrinter {

    public void printResults(ResultSet rs, String ... argList){
        //Variables we need
        String title = "";
        String underline = "";
        int tabCounter = 0;

        String wholeLine = "";

        //Start Printing the result set
        try{
            while (rs.next()){
                for (String i : argList){
                    String result = rs.getString(i);
                    wholeLine += (result + String.format("%"+5+"s", ""));
                }
                wholeLine += "\n\n";

            }
            if (wholeLine.length() == 0){
                System.out.println("No results match your query!");
                System.out.println("\n\n");
                return;
            }
        }catch(Exception e){
            e.printStackTrace();
            return;
        }

        //Get the title, and print it out with an underline
        for (String i : argList){
            title += i + String.format("%"+5+"s", "");
        }

        for (int i = 0; i<title.length(); i++){
            underline += "-";
        }
        System.out.println("\n\n");
        System.out.println("Results in the following format:");
        System.out.println(title);
        System.out.println(underline);
        System.out.println(wholeLine);
        System.out.println("\n\n");

    }
}
