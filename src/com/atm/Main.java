package com.atm;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.*;

/**
 *
 * @author Ritik Mondal
 */
public class Main {

    public static void main(String[] args) {

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("\t\t....Capital Finance ATM....");

            System.out.println("1.Admin. ");
            System.out.println("2.User.");
            System.out.println("3.Exit.");
            System.out.print("Enter Choice: ");
            int ch = Integer.parseInt(br.readLine().trim());

            
            if (ch == 1) {
                
                Admin.adminOperations();

            } else if (ch == 2) {

                Account.UserOperations(args);
                
            } else if (ch == 3) {
                
                System.out.println("Exited Successfully...\n");
                System.exit(0);
                
            } else {

                System.out.println("Invalid Choice..");
            }
        } catch (Exception e) {
        }

    }

}
