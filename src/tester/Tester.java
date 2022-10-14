/**
 * This program is simply about managing a student system connected to an MYSQL database by a JDBC connector.
 * Users can add student information,
 * Read all records,
 * Searching.
 *
 * @author AHMED ALGOWIHI
 * @author AHMED ALAMOUDI
 *
 *
 */
package tester;

import java.sql.SQLException;
import java.util.Scanner;

public class Tester {

    public static void main(String[] args) throws SQLException {
        try {
            Scanner scan = new Scanner(System.in);
            String choice;
            connectDB connect = new connectDB(); // configuration database connection and create an object from connectDB class

            // print  Welcoming message
            System.out.println("*******************************************************************************");
            System.out.println("\t\t WELCOME TO STUDENT SYSTEM CREATED BY \n\t\t AHMED ALGOWIHI:438018480 \n\t\t AHMED ALAMOUDI:439006725");
            System.out.println("*******************************************************************************");

            showMenu();
            choice = scan.nextLine().trim();
            // print menu and till user enter number 1-4 
            while (!"4".equals(choice)) { //start while ,,,,, loop still run until user enter number 4
                switch (choice) { // start switch
                    case "1":
                        connect.addStudent(connect.checkName(), connect.checkDate(), connect.checkGPA()); // if choice #1 >>> call function add student
                        break;

                    case "2":
                        connect.readAll(); //if choice #2 >>> call function readAll
                        break;

                    case "3":
                        connect.search(); // if choice #3 >>> call function search
                        break;

                    default: // if enter anything else 
                        System.out.println("Enter correct input");
                }// end switch

                showMenu();
                choice = scan.next();
                scan.nextLine();
            } // end while loop

            System.out.println("Thank You For Using Application..."); // //if choice #4  >>> we print this message and close program
        } catch (Exception e) { // use try and catch to till us if there some problem and print it 
            System.out.println("there is error " + e);
        }
    } 

    public static void showMenu() {
        System.out.println("**************************************************");
        System.out.println("PRESS 1 to ADD student");
        System.out.println("PRESS 2 to SHOW ALL student");
        System.out.println("PRESS 3 to SERCHE student");
        System.out.println("PRESS 4 to EXIT App");
        System.out.println("**************************************************");

    }

}
