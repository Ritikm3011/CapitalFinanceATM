package com.atm;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Ritik Mondal
 */
public class Admin {

    public static void adminOperations() {

        try {

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("\t\t....Capital Finance ATM....");
            System.out.print("Enter User ID: ");
            String id = br.readLine();
            System.out.print("Enter Password: ");
            String password = br.readLine();

            if (checkAdmin(id, password)) {
                System.out.println("Login Successfully...\n");
                while (true) {
                    System.out.println("Employee ID: " + id);
                    System.out.println(" 1.Show All Accounts\n 2.Create New Account\n 3.Exit");
                    System.out.print("Enter Choice: ");
                    int ch = Integer.parseInt(br.readLine().trim());

                    // menu
                    if (ch == 1) {
                        showAccount();
                    } else if (ch == 2) {
                        if (newAccount()) {
                            System.out.println("New Account Created Successfully...");
                        }

                    } else if (ch == 3) {
                        System.out.println("Log out Successfully...\n");
                        return;
                    } else {
                        System.out.println("Invalid Option!...");
                    }

                }

            } else {
                System.out.println("Wrong ID and Password!..");
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Problem in Admin.java-> main");
        }
    }

// checks the correctness of admin login credentials by comparing them with records in the database.
// return ture if the provided ID and password match the database records
    public static boolean checkAdmin(String id, String password) {

        boolean f = false;
        try {
            Connection con = ConnectionProvider.getConnection();

            String q = "SELECT * FROM atm.admin;";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(q);

            while (rs.next()) {
                String userId = rs.getString(1);
                String userPassword = rs.getString(2);
                if ((id.equals(userId)) && (password.equals(userPassword))) {
                    return true;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Problem in Admin.java->checkAdmin");
        }

        return f;
    }

// Allows the administrator to create a new user account.
// Return true if the account creation is successful; otherwise, false.
    public static boolean newAccount() {
        boolean f = false;
        System.out.println("\n    ...New Account Opening Form...");

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            System.out.print("Enter Name: ");
            String name = br.readLine();
            System.out.print("Address: ");
            String address = br.readLine();
            System.out.print("Phone Number: ");
            String phone = br.readLine();
//            System.out.print("Account Number: ");
//            String account = br.readLine();
            System.out.print("Password: ");
            String password = br.readLine();
            System.out.print("First Deposit Amount: ");
            int first = Integer.parseInt(br.readLine().trim());

            String time = getCurrentDateTime();

            Connection con = ConnectionProvider.getConnection();
            String q = "insert into atm.user(name,avl_bal,address,phone,time,password) Values(?,?,?,?,?,?);";
            PreparedStatement pstmt = con.prepareStatement(q);

            //set velues into query
            pstmt.setString(1, name);
            pstmt.setInt(2, first);
            pstmt.setString(3, address);
            pstmt.setString(4, phone);
            pstmt.setString(5, time);
            pstmt.setString(6, password);

            int flag = pstmt.executeUpdate();
            if (flag != 0) {
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("problem in Admin.java-> newAccount");
        }

        return f;
    }

//displays a list of all user accounts and their details to the admin.
    public static void showAccount() {
        System.out.println("\n\t\t\t\t......All Account Holders......");
        try {
            int i = 0;
            Connection con = ConnectionProvider.getConnection();
            String q = "SELECT * FROM atm.user;";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(q);

            System.out.println("S.N.\tAccount Number\tAccount Holder\tBalance\t Adrress\t Phone No.\tAccount Opening data&time");
            while (rs.next()) {
                i++;
                BigInteger id = getBigInteger(rs, 1);
                String name = rs.getString(2);
                int avl_bal = rs.getInt(3);
                String address = rs.getString(4);
                String phone = rs.getString(5);
                String dateTime = rs.getString(6);
                String password = rs.getString(7);

                System.out.println(" " + i + "\t" + id + "\t " + name + "\t " + avl_bal + "\t" + address + "\t" + phone + "\t " + dateTime);
            }
            System.out.println("");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("problem in Admin.java->showAccount");
        }

    }

    public static BigInteger getBigInteger(ResultSet resultSet, int columnIndex) throws SQLException {
        BigDecimal value = resultSet.getBigDecimal(columnIndex);
        return value == null ? null : value.toBigInteger();
    }

//Return A formatted string representing the current date and time.
    public static String getCurrentDateTime() {
        // Get the current date and time
        LocalDateTime currentDateTime = LocalDateTime.now();

        // Define a DateTimeFormatter for the desired date and time format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MMM-dd HH:mm:ss");

        // Format the current date and time as a string
        String formattedDateTime = currentDateTime.format(formatter);

        return formattedDateTime;
    }

}
