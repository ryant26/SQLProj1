package Search;

import Connector.DBConnector;

import java.text.ParseException;
import java.util.Locale;
import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.*;

/**
 * User: Ryan
 * Date: 2013-11-05
 * Time: 10:34 PM
 * This is to be called by the main interface in order to use the search functions
 */
public class searchInterface{
    private Search searchOb;
    public searchInterface(DBConnector conn){
        searchOb = new Search(conn);
    }

    public void mainLoop(){
        boolean exit = false;
        while(!exit){
            printOptions("search engine", "Search for test results", "Search for prescription records",
                         "Search for alarming age", "Exit");
            Scanner reader = new Scanner(System.in);
            System.out.print("Enter your selection:  ");
            String input = reader.nextLine();
            int inputNumber = 0;
            try{
                inputNumber = Integer.parseInt(input);
            }catch (Exception e){
                System.out.println();
                System.out.println("Invalid Selection");
                System.out.println();
                continue;
            }
            switch (inputNumber){
                case 1:
                    this.optionOne();
                    break;
                case 2:
                    this.optionTwo();
                    break;
                case 3:
                    this.optionThree();
                    break;
                case 4:
                    exit = true;
                    break;
                default:
                    System.out.println();
                    System.out.println("Invalid Selection");
                    System.out.println();
                    break;
            }
        }
    }

    private void printOptions(String title, String ... options){
        int optNumber = 1;
        System.out.println(title.toUpperCase());
        for (int i = 0; i < title.length(); i++){
            System.out.print("=");
        }
        System.out.println();
        System.out.println();
        System.out.println("Please Choose from the following options:");
        for (String i : options){
            System.out.println("(" + optNumber + ")" + i);
            optNumber++;
        }
        System.out.println();
        System.out.println();
    }
    private void optionOne(){
        while (true){
            printOptions("Search for test results");
            System.out.println("Please enter a patient name OR health care number\n"
                    + "or type 'exit' to return to search");
            Scanner reader = new Scanner(System.in);
            System.out.print("Enter your search:  ");
            String input = reader.nextLine();
            if(input.equalsIgnoreCase("exit")){
                return;
            }
            //Error Catching

            //Non-word chars
            Pattern p1 = Pattern.compile("\\W+?");
            Matcher m1 = p1.matcher(input);

            //Digits
            Pattern p2 = Pattern.compile("\\d+?");
            Matcher m2 = p2.matcher(input);

            //Alphabet
            Pattern p3 = Pattern.compile("[a-zA-Z]+?");
            Matcher m3 = p3.matcher(input);

            if (m1.find() || (m2.find() && m3.find())){
                System.out.println("Invalid Input");
                System.out.println("\n\n");
                continue;
            }

            this.searchOb.searchTestResults(input);
        }
    }
    private void optionTwo(){
        while (true){
            printOptions("Search for prescription records");
            System.out.println("Please enter a doctor employee number a start date and an end date\n"+
                                "separated by spaces. Type 'exit' to return to search.\n"+
                                "Proper date format is as follows: 'dd-MMM-yyyy'\n" +
                                "Example: 01-JAN-2013");
            Scanner reader = new Scanner(System.in);
            System.out.print("Enter your search:  ");
            String input = reader.nextLine();
            if(input.equalsIgnoreCase("exit")){
                return;
            }
            //Error catching

            //Dates
            String [] tokens = null;
            try{
                tokens = input.split(" ");
            }catch (Exception e){
                System.out.println("Invalid Input");
                System.out.println("\n\n");
                continue;
            }if (tokens.length < 3){
                System.out.println("Invalid Input");
                System.out.println("\n\n");
                continue;
            }
            //Employee num
            Pattern p = Pattern.compile("\\D+?");
            Matcher m = p.matcher(tokens[0]);
            if(m.find()){
                System.out.println("Invalid Employee Num");
                System.out.println("\n\n");
                continue;
            }


            try{
                Date firstDate = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH).parse(tokens[1]);
                Date secondDate = new SimpleDateFormat("dd-MMM-yyy", Locale.ENGLISH).parse(tokens[2]);
                this.searchOb.searchPerscribe(tokens[0],tokens[1],tokens[2]);
            }catch (ParseException e){
                System.out.println("Invalid Dates");
                System.out.println("\n\n");
                continue;
            }
        }
    }
    private void optionThree(){
        while(true){
            printOptions("Search for alarming age");
            System.out.println("Please enter the name of a test to see all patients who have reached\n" +
                    "the alarming age. Type 'exit' to return to search.");
            Scanner reader = new Scanner(System.in);
            System.out.print("Enter your search:  ");
            String input = reader.nextLine();
            if(input.equalsIgnoreCase("exit")){
                return;
            }
            //I dont think this one requires error catching
            this.searchOb.searchAlarmAge(input);
        }
    }
}
