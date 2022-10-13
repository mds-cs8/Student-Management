package tester;

import java.sql.SQLException;

import java.util.Scanner;

/**
 *
 * @author meed-
 */
public class Tester {

    public static void main(String[] args) throws SQLException {
        try{
            
        
        Scanner scan = new Scanner(System.in);
        String urlDB = "jdbc:mysql://localhost:3306/Students_Alamoudi_ALgowihi";
        String userDB = "ahmed";
        String password = "0534889865";
        connectDB connect = new connectDB(urlDB, userDB, password);
// confugration databsae connection and create object from connectDB class 

        String choice;

        System.out.println("*******************************************************************************");
        System.out.println("\t\t WELCOME TO STUDENT SYSTEM CREATED BY \n\t\t AHMED ALGOWIHI:438018480 \n\t\t AHMED ALAMOUDI:111111111");
        System.out.println("*******************************************************************************");

        showMenu();
        choice = scan.nextLine().trim();
//        scan.nextLine();

        while (!"4".equals(choice)) {
            switch (choice) {
                case "1":

                    connect.addStudent(connect.checkName(), connect.checkDate(), connect.checkGPA());
                    break;
                case "2":
                    connect.readAll();
                    break;
                case "3":
                    connect.search();
                    break;
                default:
                    System.out.println("Enter correct input");
            }

            showMenu();
            choice = scan.next();
            scan.nextLine();
        }
        System.out.println("Thank You For Using Application...");
        }catch(Exception e){
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
