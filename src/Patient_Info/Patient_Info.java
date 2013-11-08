package Patient_Info;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Date;

import Connector.DBConnector;

public class Patient_Info {
	
	private Scanner scan = new Scanner(System.in);
    private String ID = null;
    public Patient_Info(){
            
    }
    public void mainLoop(DBConnector conn){
            scan.useDelimiter(System.getProperty("line.separator"));
            Get_Patient(conn);
    }
    
    private void Get_Patient(DBConnector conn){
            System.out.println("Please Enter the name or Health Care Number of the patient you wish to edit: ");
            String Id_name = scan.next();
            ResultSet rs = Get_Info(Id_name, conn);
            int num_rows = printResults(rs, "health_care_no", "name", "address", "birth_day", "phone");
            
            if(num_rows > 1){
                    System.out.println("Please enter the Health Care Number of the patient you wish to edit: ");
                    Id_name = scan.next();
                    Update_Patient(Id_name, conn);
            }
            else{
                    try {
                            rs.beforeFirst();
                            if(rs.next() == false){
                                    New_Patient(Id_name, conn);
                            }
                            else{
                                    rs.first();
                                    ID = rs.getString("health_care_no");
                                    Update_Patient(Id_name, conn);
                            }
                    } catch (SQLException e) {
                            e.printStackTrace();
                    }
            }
    }
    
    private String ID_Check(String check){
		Pattern p1 = Pattern.compile("\\d+?");
        Matcher m1 = p1.matcher(check);
        Pattern p2 = Pattern.compile("[a-zA-Z]+?");
        Matcher m2 = p2.matcher(check);
        if(m1.matches()){
        	return "ID";
        }
        else if(m2.matches()){
        	return "Name";
        }
        
		return "Invalid";
	}
    
    private void Update_Patient(String ID_name, DBConnector conn){
    System.out.println("Options to Update:");
    System.out.println("(1) Name");
    System.out.println("(2) Address");
    System.out.println("(3) Birthday");
    System.out.println("(4) Phone Number");
    System.out.println("(5) Exit");
    
    String inp = scan.next();
    if(inp.equals("1")){
            Update_Name(ID_name, conn);
    }
    else if(inp.equals("2")){
            Update_Addr(ID_name, conn);
    }
    else if(inp.equals("3")){
            Update_Birth(ID_name, conn);
    }
    else if(inp.equals("4")){
            Update_Phn(ID_name, conn);
    }
    else if(inp.equals("5")){
            return;
    }
    else{
            System.out.println("Invalid Input, Please try again");
            Update_Patient(ID_name, conn);
    }
    }
    
    private void Update_Name(String ID_name, DBConnector conn){
            String name = null;
            ResultSet rs = null;
            Statement stmt = null;
            System.out.println("Please Enter new Name for this patient: ");
            name = scan.next();
            String check = ID_Check(name);
  
            if(check.equals("Name")){
                    try{
                           stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                           rs = stmt.executeQuery(
                                "UPDATE patient "
                                +" SET name= '"+name
                                +"' WHERE health_care_no= '"+ID
                                +"'");
                
                    }catch (Exception e){
                    	e.printStackTrace();
                    }
                    Update_Patient(ID_name, conn);
            }
            else{
                    System.out.println("Invalid Name");
                    Update_Name(ID_name, conn);
            }
    }
    
    private void Update_Addr(String ID_name, DBConnector conn){
            String addr = null;
            ResultSet rs = null;
            Statement stmt = null;
            System.out.println("Please Enter new address for this patient: ");
            addr = scan.next();
            try{
                    stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    rs = stmt.executeQuery(
                        "UPDATE patient "
                        +" SET address= '"+addr
                        +"' WHERE health_care_no= '"+ID
                        +"'"
                        );
        
            }catch (Exception e){
            	e.printStackTrace();
            }
            Update_Patient(ID_name, conn);
    }
    
    private void Update_Birth(String ID_name, DBConnector conn){
            String birth = null;
            ResultSet rs = null;
            Statement stmt = null;
            Date date = new Date();
            
            System.out.println("Please Enter new Birthday for this patient(yyyy/MM/dd): ");
            birth = scan.next();
            String check = Check_Date(birth);
            if(check.equals("Date")){
                    try{
                    stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    rs = stmt.executeQuery(
                                    "UPDATE patient "
                                    +" SET birth_day= TO_DATE('"+birth
                                    +"', 'yyyy/MM/dd') WHERE health_care_no= '"+ID
                                    +"'");
        
                    }catch (Exception e){
                    	e.printStackTrace();
                    }
                    Update_Patient(ID_name, conn);
            }
            else{
                    System.out.println("Invalid Input");
                    Update_Birth(ID_name, conn);
            }
    }
    
    private void Update_Phn(String ID_name, DBConnector conn){
            String Phn = null;
            ResultSet rs = null;
            Statement stmt = null;
            System.out.println("Please Enter new phone number for this patient: ");
            Phn = scan.next();
            String check = ID_Check(Phn);
            if(check.equals("ID")){
                    try{
                            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                            rs = stmt.executeQuery(
                                "UPDATE patient "
                                +" SET phone= '"+Phn
                                +"' WHERE health_care_no= '"+ID
                                +"'");
                
                    }catch (Exception e){
                    	e.printStackTrace();
                    }
                    Update_Patient(ID_name, conn);
            }
            else{
                    System.out.println("Invalid Phone Number");
                    Update_Phn(ID_name, conn);
            }
    }
    
    private String Check_Date(String date){
            String [] tokens = null;
            try{
            	tokens = date.split("/");
            }catch (Exception e){
            	System.out.println("Invalid Input");
            	System.out.println("\n\n");
            }
            if (tokens.length != 3){
            	return "Not Date";
            }
            else if(tokens[0].length() != 4 || tokens[1].length() != 2 || tokens[2].length() != 2){
            	return "Not Date";
            }
            else{
            	String check = null;
            	for(String i : tokens){
            		check = ID_Check(i);
            		if(check.equals("Name") || check.equals("Invalid")){
            			return "Not Date";
            		}
            	}
            }
            	return "Date";
    }
    
    private String Check_HCN(String hcn, DBConnector conn){
            String Check_id = ID_Check(hcn);
            if(Check_id.equals("ID")){
                    ResultSet rs = null;
                    Statement stmt = null;
                    stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    try{
                    	rs = stmt.executeQuery(
                                "Select health_care_no "
                                + " From patient  "
                                + " Where health_care_no = "+hcn+""
                                );  
                    }catch (Exception e){
                    	e.printStackTrace();
                    }
                    try {
                            if(!rs.next()){
                                    return "Valid";
                            }
                            else{
                                    return "Invalid";
                            }
                    } catch (SQLException e) {
                            e.printStackTrace();
                            return "Invalid";
                    }
            }
            else{
                    return "Invalid";
            }
    }
    
    
    private void New_Patient(String ID_name, DBConnector conn){
            System.out.println("This patient is not currently in the system, would you like to add them to the system?(Y/N) ");
            String ans = scan.next();
            
            if(ans.equals("Y") || ans.equals("y")){
                    
                    System.out.println("Please enter the patient's Health Care Number, address, Birthday(yyyy/MM/dd) and phone number:\n"
                                    + "Seperate each field by commas please, and enter in the order above.");
                    
                    String info = scan.next();
                    String[] sep = info.split(",");
                    
                    String h_c_n = sep[0];
                    String hcn = Check_HCN(h_c_n, conn);
                    
                    if(hcn.equals("Valid")){
                            String addr = sep[1];
                            
                            String birth = sep[2];
                            String birthch = Check_Date(birth);
                            
                            if(birthch.equals("Date")){
                                    String phn = sep[3];
                                    String check = ID_Check(phn);
                                    
                                    if(check.equals("ID")){
                                            
                                            ResultSet rs = null;
                                            Statement stmt = null;
                                            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                                            
                                            try{
                                        rs = stmt.executeQuery(
                                                        "INSERT INTO patient"
                                                        +" VALUES ('"+h_c_n
                                                        +"' ,'"+ID_name
                                                        +"' ,'"+addr
                                                        +"' ,TO_DATE('"+birth
                                                        +"', 'yyyy/MM/dd') ,'"+phn
                                                        +"')"
                                                        );
                                        
                                    }catch (Exception e){
                                        e.printStackTrace();
                                    }
                                    }
                            }
                            else{
                                    System.out.println("Invalid Input, Please retry");
                                    
                            }
                    }
                    else{
                            System.out.println("Invalid Input, Please retry");
                    }
            }
            else if(ans.equals("N") || ans.equals("n")){
                    mainLoop(conn);
            }
            else{
                    System.out.println("This is not a valid response");
                    New_Patient(ID_name, conn);
            }

    }
    
    private ResultSet Get_Info(String ID_name, DBConnector conn){
            String check = ID_Check(ID_name);
            ResultSet rs = null;
            Statement stmt = null;
            if (check.equals("ID")){
                    stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    try{
                    	rs = stmt.executeQuery(
                                "SELECT * "
                                +" FROM patient "
                                +" WHERE health_care_no='"+ID_name
                                +"'");
                
                    }catch (Exception e){
                    	e.printStackTrace();
                    }
                    ID = ID_name;
            }
            else{
                    stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    try{
                    	rs = stmt.executeQuery(
                            "SELECT * "
                            +" FROM patient "
                            +" WHERE name='"+ID_name
                            +"'");
                
                    }catch (Exception e){
                    	e.printStackTrace();
                    }
            }
            return rs;
    }
    
    private int printResults(ResultSet rs, String ... argList){
            //Variables we need
            String title = "";
            String underline = "";
            int tabCounter = 0;
            int num_rows = 0;
            String wholeLine = "";

            //Start Printing the result set
            try{
            	while (rs.next()){
            		num_rows = num_rows +1;
            for (String i : argList){
                String result = rs.getString(i);
                wholeLine += (result + String.format("%"+5+"s", ""));
            }
            wholeLine += "\n\n";

            }
            if (wholeLine.length() == 0){
            System.out.println("No results match your query!");
            System.out.println("\n\n");
            }
            }catch(Exception e){
            	e.printStackTrace();

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
            return num_rows;
    }
		
}
