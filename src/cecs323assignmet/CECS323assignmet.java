/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cecs323assignmet;

import java.util.*;
import java.sql.*;


public class CECS323assignmet {

    //Database credentials
    static String USER;
    static String PASS;
    static String DBNAME;
    public static Scanner in = new Scanner(System.in);
    
    static final String JDBC_DRIVER = "org.apache.derby.jdbc.ClientDriver";
    static String DB_URL = "jdbc:derby://localhost:1527/Mysonglist;user=Abdullah;password=Abdullah";
/**1
 * Takes the input string and outputs "N/A" if the string is empty or null.
 * @param input The string to be mapped.
 * @return  Either the input string or "N/A" as appropriate.
 */
    
    public static void main(String[] args) {
        //Prompt the user for the database name, and the credentials.
        //If your database has no credentials, you can update this code to
        //remove that from the connection string.
        
        
        //Constructing the database URL connection string
        DB_URL = DB_URL;//+ "Lab#5"+ ";user="+";password=";
        Connection conn = null; //initialize the connection
        Statement stmt = null;  //initialize the statement that we're using
        try {
            //Register JDBC driver
            Class.forName("org.apache.derby.jdbc.ClientDriver");

            //Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL);

            // Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            
            
            boolean run = true; 
            //An finite while loop to display the menus
            while(run == true)
            {
                try{
                 //making the thread sleep to delay the display
                Thread.sleep(1000);
                //making a table for the user to chose
                System.out.println("\b\b\b\b\b\b\b\b\b\b\b\b");
                System.out.println("\nChoose one of the following:"
                        + "\n1.List all album titles within the databas.\n"
                        + "2.List a specific attrabute(s) from Albums\n"
                        + "3.Insert a new album\n"
                        + "4.Insert a studio and update album\n"
                        + "5.Remove an album\n" 
                        + "6.Log out\n");
                //choose vrable to recive the user's chose 
                int choose = in.nextInt();
                
                switch(choose)
                {
                    //list all albums 
                    case 1:
                        System.out.println("Album's Titles:");
                        showtitle(stmt);
                        break;
                     //show a specific attribute from the albums table
                    case 2:
                        showspecific(stmt);
                        break;
                    //insert an attribute to the albums table
                    case 3:
                        insert(stmt);
                        break;
                    //insert a studio and if wanted  update  it
                    case 4:
                        insupdate(stmt);
                        break;
                    //remove an album  
                    case 5:
                        remove(stmt);
                        break;
                    //end the connection 
                    case 6:
                        System.out.println("\nLogging Out...");
                        run = false;
                        break;
                    //if the user input is not listed this will output. 
                    default:
                        System.out.println("Unavaliable Command");
                        break;
                       
                }
             }catch(InputMismatchException e)
            {
               System.out.println("Input Mismatch\nOnly enter integers"); 
            }
            }
           
            
            
            // Clean-up environment
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
       
               
         
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se2) {
            }// nothing we can do
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
                System.out.println("an error occured");
            }//end finally try
        }//end try
        System.out.println("Goodbye!");
    }
    /*
    
    showtitle method to display all the titles from the albums table
    
    */
    public static void showtitle(Statement stmt) throws SQLException{
       
        String sql;
        //the sql command 
            sql = "SELECT atitle FROM albums";
            ResultSet rs = stmt.executeQuery(sql);
            
            int number = 0;//to list with numbers
            
            while (rs.next()) {
                //Retrieve by column name
                String title = rs.getString("atitle");
                number++;

                //Display values
               System.out.println(number+")"+dispNull(title));
                 
            }
            rs.close();
    }
    /*
     * Showstudio method is for displaying all the studio names from the rstudio table
    */
    public static void showstudio (Statement stmt) throws SQLException{
       
        String sql;
        //sql command
            sql = "SELECT DISTINCT  sname FROM albums";
            ResultSet rs = stmt.executeQuery(sql);

            int number = 0;// to list with numbers
            
            while (rs.next()) {
                //Retrieve by column name
                String title = rs.getString("sname");
                number++;

                //Display values
               System.out.println(number+")"+dispNull(title));
                 
            }
            rs.close();
    }
    /*
     * showgroup method is for displaying all the group names from the rgroup table
    */
    
    public static void showgroup (Statement stmt) throws SQLException{
       
        String sql;
        //sql command 
            sql = "SELECT gname FROM albums";
            ResultSet rs = stmt.executeQuery(sql);

            int number = 0;// to be listed with numbers
            
            while (rs.next()) {
                //Retrieve by column name
                String title = rs.getString("gname");
                number++;

                //Display values
               System.out.println(number+")"+dispNull(title));
                 
            }
            rs.close();// closing connection
    }
    /*
     * showspecific  method is for displaying all the attributes the user chooses from the albums table 
    */
    public static void showspecific(Statement stmt) throws SQLException {
       
       System.out.println("Choose which attrabute to insurt then press p to print.");
       ArrayList attrib = new ArrayList<>(); // an array to register every attabute
       boolean run = true;// while finite varable 
       boolean isinlist;//to check if it is already l
       String listedattrib=" ";// collecting every attrabute
       while(run == true)// start of the finite loo[
       {
           System.out.println("Press p to print\n\n1.title\n2.group's name\n3.studio name\n4.date recorded\n5.length of the album\n6.number of songs");
           String chose = in.next();// the user choses what to add 
           switch(chose)// to add the specific attribute
           {
               //to add an attribute
               case "1":
                   //getting the title
                   String atitle = "atitle";
                   //checking if it's already listed
                   isinlist = checkiflisted(attrib, atitle);
                   //if it's not listed then add it to the arraylist
                   if(isinlist == false)
                   {
                        System.out.println("\nInserted album's title\n");
                        attrib.add(atitle);//adding it to the array list
                        listedattrib += "title ";// adding it to the title attribute
                   } else {
                       System.out.println("\nalready in the list\n");//not listed
                   }
                 
                   break;
               
               case "2":
                   String gname = "gname";
                   isinlist = checkiflisted(attrib, gname);
                   if(isinlist == false)
                   {
                        System.out.println("\nInserted group name\n");
                        attrib.add(gname);
                        listedattrib += "group Name ";
                   } else {
                       System.out.println("\nalready in the list\n");
                   }
                   break;
                   
               case "3":
                    String sname = "sname";
                   isinlist = checkiflisted(attrib, sname);
                   if(isinlist == false)
                   {
                        System.out.println("\nInserted studio name\n");
                        attrib.add(sname);
                        listedattrib += "studio  ";
                   } else {
                       System.out.println("\nalready in the list\n");
                   }
                   break;
                   
               case "4":
                    String d_recorded = "daterecorded";
                   isinlist = checkiflisted(attrib, d_recorded);
                   if(isinlist == false)
                   {
                        System.out.println("\nInserted date recorded\n");
                        attrib.add(d_recorded);
                        listedattrib += "recorded  ";
                   } else {
                       System.out.println("\nalready in the list\n");
                   }
                   break;
               case "5":
                String length  = "alength";
                   isinlist = checkiflisted(attrib, length);
                   if(isinlist == false)
                   {
                        System.out.println("\nInserted album's length\n");
                        attrib.add(length);
                        listedattrib += "length  ";
                   } else {
                       System.out.println("\nalready in the list\n");
                   }
                   break;
                   
               case "6":
                    String num_song = "num_song";
                   isinlist = checkiflisted(attrib, num_song);
                   if(isinlist == false)
                   {
                        System.out.println("\nInserted number of songs\n");
                        attrib.add(num_song);
                        listedattrib += "number of songs  ";
                   } else {
                       System.out.println("\nalready in the list\n");
                   }
               break;
                       
               case "p":
                   //printing the listed attributes
                   System.out.println("\nPrinting chosen attrabutes\n");
                   run = false;
                   
                   break;
               
               case "P":
                   //printing with a capitial letter
                   System.out.println("\nPrinting chosen attrabutes\n");
                   run = false;
                   
                   break;
               
               default:
                   //not a listed command
                   System.out.println("\nNot a listed attrabute\n");
                   break;
           }
       }
       
       //getting all of the commads in one sql line
       String list = " "+attrib.get(0); 
       
       for(int x=1;x<attrib.size();x++)
       {
           list += ", "+attrib.get(x);
       }
       
       
       //excuting the line of sql
       try{
       String sql;
            sql = "SELECT" +list+ " FROM albums";
            
            ResultSet rs = stmt.executeQuery(sql);
            
            System.out.println(listedattrib);
           

            
            while (rs.next()) {
                //Retrieve by column name
                
                  for(int i=0;i<attrib.size();i++)
                  {
                      String attrabute = rs.getString((String) attrib.get(i));
                      //Display values
                         System.out.print(dispNull(attrabute)+"  ,");
                        
                        
                  }
                  System.out.println();
               
                
                
            }
       
            rs.close();
       }catch(Exception e)
       {
           System.out.println("Error:"+e.getMessage());
       }
    }
    
    
   
     /*
     * checkiflisted is a method to check if the user has chosen a method to display
    */
    
    public static boolean checkiflisted(ArrayList a, String check){
        //binary search 
        for(int x = 0; x<a.size(); x++)
        {
            if(a.get(x) == check)
            {
                return true;
            }
        }
        
        
        return false;
    }
    
    
     /*
     * dispNull is to return null if the attrabute is null
    */
    
    public static String dispNull(String input) {
        
        if (input == null || input.length() == 0)
            return "N/A";
        else
            return input;
    }
    
    
     /*
     * insert  method is for inserting a new row into the albums table
    */
   
    public static void insert(Statement stmt) throws SQLException {
        
        //getting the title of the inserted album
        System.out.print("Enter title:");
        String title = in.next();
        
        //getting the group name
        System.out.println("choose one of these registered group names");
        //the user has to choose on the listed group in the databace
        showgroup(stmt);
        System.out.print("\nEnter group name:");
        String gname = in.next();
        
        //getting the studio name
        System.out.println("choose one of these registered studios");
        //the user has to chose one of the listed studios in the databace
        showstudio(stmt);
        System.out.print("\nEnter studio name:");
        String sname= in.next();
        
        //getting the date recorded 
        System.out.println("\nEnter date the album's recorded(yyyy-mm-dd):");
        String recdate = in.next();
        //getting the length of the song
        System.out.println("\nEnter the length of the album:");
        String length = in.next();
        //geting the number of songs listed 
        System.out.println("\nEnter the number of songs:");
        String numsongs = in.next();
       
        try {
        String sql;
        //excuting the sql
            sql = "Insert into albums  Values( '"+title+"' , '"+gname+"' , '"+sname+"' , '"+recdate+"' , '"+length+"', '"+numsongs+"' )";
            stmt.executeUpdate(sql);
            System.out.println("have been inserted");
            
            
        }catch(Exception e)//catch any exception if the user does any
            {
                System.out.println("Caught Error:"+e.toString());
            }
    }
   /*
     * remove  method is for deleting a row from the albums table
    */
    public static void remove(Statement stmt) throws SQLException
    {
        
        //show all the title that can be deleted
        showtitle(stmt);
        //getting the deleted album
        System.out.println("Enter the title of the album you would like to delet");
        String delete = in.next();
        try{
        //excuting the sql command
        String sql = "Delete from albums where atitle='"+delete+"'";
        
        stmt.executeUpdate(sql);
        
        System.out.print(delete+" have been deleted");
        }catch(SQLException s)//catch any exception if the user does any
        {
            System.out.println("Not delete");
            s.printStackTrace();
        }
    }


     /*
     * insupdate method is for inserting and updating all of the method of the same studio
    */
    
    public static void insupdate(Statement stmt)throws SQLException 
    {
        System.out.println("know inserting a new studio ...");
       
        try{
            //getting the studio name
            System.out.println("Enter the studio's name");
            String sname = in.next();
            //getting the address
            System.out.println("Enter the studio's address ");
            //address number
            System.out.print("Enter the address number:");
            String addnum = in.next();
            //address street
            System.out.println("Enter the street");
            String addstreet = in.next();
            
            // combining them
            String saddress = addnum +" "+addstreet;
            //getting the studio owner
            System.out.println("Enter the name  of studio's owner");
            String sowner = in.next();
            //getting the studio phone number (needs to not excude 10 digits)
            System.out.println("Enter the studio's phone number(only 10 digits)");
            String sphone = in.next();
        
            try {
                //excuting sql command
                 String sql;
                 sql = "Insert into rstudio  Values( '"+sname+"' , '"+saddress+"' , '"+sowner+"' , '"+sphone+"' )";
            
                 stmt.executeUpdate(sql);
                 System.out.println("have been inserted");
                 //prmopting the user if he/she want to change one to be represented by this studio
                 System.out.println("Albums published by one studio to be published by the new studio:\n1)yes, please.\n2)NO!!");
                 int chose= in.nextInt();
           //if user agrees
            if(chose == 1)
            {
                    //showing all currently userd studios 
                    showstudio(stmt);
                    //enter the user of which should be updated
                    System.out.print("\nEnter the name of the studio that will be updated:");
                    String upstudio = in.next();
                    //excuting sql
                    String sqlup = "update albums set sname= '"+sname+"' where sname= '"+upstudio+"'";
            
                    stmt.execute(sqlup);
                    System.out.println("just updated!");
            
            }
            
            }catch(SQLException e)//catching any sql exceptions
            {
                System.out.println("Syntac Error");
            }
            
        }catch(InputMismatchException e)//catching any mismatch exception s
        {
            System.out.println("Error Input Mismatch");
            insupdate(stmt);
        } catch(Exception e)//catching any exception not listed
        {
            System.out.println("Caught Exception: "+e.getMessage());
            System.out.println("Need to redo!");
            insupdate(stmt); //repeating the prosedure
        }    
    }   
}