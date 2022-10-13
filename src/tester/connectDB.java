/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
//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
package tester;

import java.sql.Connection;
import java.sql.Date;
//import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

//  @author AHMED ALGOWIHI
//  @author AHMED ALAMOUDI
class connectDB {

    String urlDB;
    String userDB;
    String password;
    Connection connect;
    Scanner scan = new Scanner(System.in);

    connectDB(String urlDB, String userDB, String password) throws SQLException {
        this.urlDB = urlDB;
        this.userDB = userDB;
        this.password = password;
        connect = DriverManager.getConnection(urlDB, userDB, password);
//         Driver md = new com.mysql.jdbc.Driver();
//         DriverManager.registerDriver(md);

    }

    public void readAll() throws SQLException {

        String selectQuery = "select * from StudentsTBL_ahmed_ahmed";
        PreparedStatement statement = connect.prepareStatement(selectQuery);
        ResultSet printResult = statement.executeQuery();
        System.out.printf(" %-5s  %-40s  %-15s  %4s %n", "ID", "NAME", "DATE", "GPA");
        System.out.println();
        System.out.println("-------------------------------------------------------------------------------------");

        while (printResult.next()) {
            System.out.printf(" %-5s  %-40s  %-15s  %4.2f %n", printResult.getInt(1), printResult.getString(2), printResult.getDate(3), printResult.getDouble(4));

            System.out.println();

        }
        statement.close();

// end while
    }

    public void addStudent(String fullName, String dat, Double GPA) throws SQLException {

        String sql = "Insert into StudentsTBL_Ahmed_Ahmed (FullName,DateOfBirth,GPA) values(?,?,?)";
        PreparedStatement pStmt = connect.prepareStatement(sql);
        pStmt.setString(1, fullName);
        Date date = Date.valueOf(dat);

        pStmt.setDate(2, date);

        pStmt.setDouble(3, GPA);
        int i = pStmt.executeUpdate();
        System.out.println("Student record Inserted Successfully...");
        pStmt.close();
    }

    public void search() throws SQLException {
        System.out.println("Enter Full Name:");
        String fullName = scan.nextLine();

        String searchquery = "SELECT * FROM StudentsTBL_ahmed_ahmed WHERE FullName LIKE ? ";

        PreparedStatement statement = connect.prepareStatement(searchquery);
        fullName = "%" + fullName + "%";
        statement.setString(1, fullName);
        ResultSet searchResult = statement.executeQuery();
//        searchResult.isBeforeFirst()
        if (!searchResult.isBeforeFirst()) {
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
        }
        statement.close();
    }

    public String checkName() {
        String NAMES_PATTER = "[A-Za-z ]{3,40}";
        System.out.println("Enter Full Name:");
        String name = scan.nextLine().trim();
        while (!name.matches(NAMES_PATTER)) {
            if (name.length() > 40 || name.length() < 3) {
                System.out.println("your name must be between 3 - 40 letters !!! ");
                System.out.println("Enter Full Name:");
                name = scan.nextLine();
            }
            System.out.println("your name must contain letters only !!!");
            System.out.println("Enter Full Name:");
            name = scan.nextLine();
        }
        return name;
    }

    public String checkDate() {

        String DATE_PATTER = "((18|19|20)[0-9]{2}[-](0?[13578]|1[02])[-](0?[1-9]|[12][0-9]|3[01]))|"
                + "(18|19|20)[0-9]{2}[-](0?[469]|11)[-](0?[1-9]|[12][0-9]|30)|"
                + "(18|19|20)[0-9]{2}[-](0?[2])[-](0?[1-9]|1[0-9]|2[0-8])|"
                + "(((18|19|20)(04|08|[2468][048]|[13579][26]))|2000)[-](0?[2])[-]29";

        System.out.println("Enter birth date in yyyy-mm-dd form:");

        String date = scan.nextLine();
        while (!date.matches(DATE_PATTER)) {
            System.out.println("Enter correct birth date in yyyy-mm-dd form:");
            date = scan.nextLine();

        }

        return date;
    }

    public double checkGPA() {
        System.out.println("Enter student GPA \"must be between 0-4\"");
        String GPA_PATTER = "[4](.[0]+)?|([0-3])([.][0-9]+)?";
        String GPA = scan.nextLine();

        while (!GPA.matches(GPA_PATTER)) {
            System.out.println("Enter correct student GPA \"must be between 0-4\"");
            GPA = scan.nextLine();

        }
        if (GPA.length() > 4) {
            GPA = GPA.substring(0, 4);
        }

        return Double.parseDouble(GPA);

    }
}
