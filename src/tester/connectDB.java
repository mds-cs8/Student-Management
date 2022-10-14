/**
 * in this class we do :
 *Create connection with database .
 * Read file contain info about database (URLDB , USER , PASSWORD).
 * Create 3 methods Add , Read and Search that communicates with the database.
 * Create 3 methods to handle any problem with name ,date ,GPA
 *
 */
package tester;

import com.mysql.cj.jdbc.Driver;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

class connectDB {

    Scanner scan = new Scanner(System.in);

    public static Connection getConnection() throws SQLException, FileNotFoundException, IOException {
        Driver driver = new com.mysql.jdbc.Driver();
        DriverManager.registerDriver(driver); // in netbeans we do not need this 

        Properties connectDB = new Properties();
        FileInputStream info = new FileInputStream("jdbcConnect.txt");
        connectDB.load(info);
        Connection connect = DriverManager.getConnection(connectDB.getProperty("urlDB"), connectDB.getProperty("userDB"), connectDB.getProperty("password"));
        // here we read URLDB , user , password frpm file <NameFile.txt> and write this info into getConnection method

        return connect;
    }

    public void readAll() throws SQLException, IOException { // this method to read all records and print it

        String Query = "select * from StudentsTBL_ahmed_ahmed"; // this query to connect with MYSQL
        PreparedStatement statement = getConnection().prepareStatement(Query); //send query to MYSQL
        ResultSet printResult = statement.executeQuery();
        System.out.printf(" %-5s  %-40s  %-15s  %4s %n", "ID", "NAME", "DATE", "GPA"); // format to print the records like table
        System.out.println();
        System.out.println("-------------------------------------------------------------------------------------");

        while (printResult.next()) { // while loop To go through all the records and print them
            System.out.printf(" %-5s  %-40s  %-15s  %4.2f %n", printResult.getInt(1), printResult.getString(2), printResult.getDate(3), printResult.getDouble(4));
            System.out.println();
        }
        statement.close();
    } // end while

    public void addStudent(String fullName, String dates, Double GPA) throws SQLException, IOException { //this method to add student in database

        String Query = "Insert into StudentsTBL_Ahmed_Ahmed (FullName,DateOfBirth,GPA) values(?,?,?)"; // this query to connect with MYSQL
        PreparedStatement statement = getConnection().prepareStatement(Query); //send query to MYSQL
        statement.setString(1, fullName); // set name in first <?>
        Date date = Date.valueOf(dates);
        statement.setDate(2, date); // set date in second <?>
        statement.setDouble(3, GPA); // set GPA in third <?>
        int i = statement.executeUpdate();
        System.out.println("Student record Inserted Successfully..."); // if everything is OK print this message
        statement.close();
    }

    public void search() throws SQLException, IOException { // this method to search  in database
        System.out.println("Enter your search:");
        String search = scan.nextLine();
        String searchquery = "SELECT * FROM StudentsTBL_ahmed_ahmed WHERE FullName LIKE ? ";
        PreparedStatement statement = getConnection().prepareStatement(searchquery); //send query to MYSQL
        search = "%" + search + "%";
        statement.setString(1, search);  // set user input in first <?>
        ResultSet searchResult = statement.executeQuery();
        if (!searchResult.isBeforeFirst()) {
            // check If  does not find records print message
            System.out.println("there is no records available for this search criteria");
            return;
        }
        System.out.println("--------------------------------------------------------------------");
        System.out.printf(" %-5s  %-40s  %-15s  %12s %n", "ID", "NAME", "DATE", "GPA");
        System.out.println();
        System.out.println("--------------------------------------------------------------------");
        while (searchResult.next()) {
            System.out.printf(" %-5s  %-40s  %-15s  %4s %n", searchResult.getInt(1), searchResult.getString(2), searchResult.getDate(3), searchResult.getDouble(4));
            System.out.println();
        }// end while
        statement.close();
    }

    public String checkName() { // this method to check name and handle any problem if found
        String NAMES_PATTER = "[A-Za-z ]{3,40}";
        // we use REGEX To determine Name pattern and character limit >> the name most be letters from 3  - 40 characters
        System.out.println("Enter Full Name:");
        String name = scan.nextLine().trim();
        while (!name.matches(NAMES_PATTER)) { // check if there any problem and print message about it
            if (name.length() > 40 || name.length() < 3) {
                System.out.println("your name must be between 3 - 40 letters !!! ");
                System.out.println("Enter Full Name:");
                name = scan.nextLine().trim();
            }
            System.out.println("your name must contain letters only !!!");
            System.out.println("Enter Full Name:");
            name = scan.nextLine().trim();
        } // end while
        return name;
    }

    public String checkDate() { // this method to check Date and handle any problem if found

        String DATE_PATTER = "((18|19|20)[0-9]{2}[-](0?[13578]|1[02])[-](0?[1-9]|[12][0-9]|3[01]))|"
                + "(18|19|20)[0-9]{2}[-](0?[469]|11)[-](0?[1-9]|[12][0-9]|30)|"
                + "(18|19|20)[0-9]{2}[-](0?[2])[-](0?[1-9]|1[0-9]|2[0-8])|"
                + "(((18|19|20)(04|08|[2468][048]|[13579][26]))|2000)[-](0?[2])[-]29";
        // we use REGEX To determine Date  pattern YYYY-MM-DD

        System.out.println("Enter birth date in yyyy-mm-dd form:");

        String date = scan.nextLine();
        while (!date.matches(DATE_PATTER)) {  // check if there any problem and print message about it
            System.out.println("Enter correct birth date in yyyy-mm-dd form:");
            date = scan.nextLine();
        } // end while
        return date;
    }

    public double checkGPA() { //this method to check GPA and handle any problem if found
        System.out.println("Enter student GPA \"must be between 0-4\"");
        String GPA_PATTER = "[4](.[0]+)?|([0-3])([.][0-9]+)?";
        // we use REGEX To determine GPA  pattern 
        String GPA = scan.nextLine();
        while (!GPA.matches(GPA_PATTER)) { // check if there any problem and print message about it
            System.out.println("Enter correct student GPA \"must be between 0-4\"");
            GPA = scan.nextLine();
        } // end while
        if (GPA.length() > 4) {
            GPA = GPA.substring(0, 4);
        }
        return Double.parseDouble(GPA);
    }
}
