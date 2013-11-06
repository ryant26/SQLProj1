package Search;

import Connector.DBConnector;
import java.util.Scanner;

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
            int inputNumber = Integer.parseInt(input);
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
                    System.out.println("Invalid Selection");
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
            //need error catching
            this.searchOb.searchTestResults(input);
        }
    }
    private void optionTwo(){
        while (true){
            printOptions("Search for prescription records");
            System.out.println("Please enter a doctor employee number a start date and an end date\n"+
                                "sepparated by spaces. Type 'exit' to return to search.");
            Scanner reader = new Scanner(System.in);
            System.out.print("Enter your search:  ");
            String input = reader.nextLine();
            if(input.equalsIgnoreCase("exit")){
                return;
            }
            //Need error catching
            String [] tokens = input.split();
            this.searchOb.searchPerscribe(tokens[0],tokens[1],tokens[2]);
        }
    }
    private void optionThree(){
        while(true){
            printOptions("Search for alarming age");
            System.out.println("Please enter the name of a test to see all patients who have reached\n"+
                                "the alarming age. Type 'exit' to return to search.");
            Scanner reader = new Scanner(System.in);
            System.out.print("Enter your search:  ");
            String input = reader.nextLine();
            if(input.equalsIgnoreCase("exit")){
                return;
            }
            //NEED ERROR CATCHING
            this.searchOb.searchAlarmAge(input);
        }
    }
}
