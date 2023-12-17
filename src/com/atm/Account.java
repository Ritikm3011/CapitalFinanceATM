package com.atm;

import static com.atm.Admin.getBigInteger;
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
public class Account {

//The main method of the application. It handles user interaction for ATM operations.
    public static void UserOperations(String[] args) {

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("\t\t....Capital Finance ATM....");
            System.out.print("Enter 12 digit Card Number: ");
            String id = br.readLine();
            BigInteger userId = new BigInteger(id);

            System.out.print("Enter Pin: ");
            String userPassword = br.readLine();

            User user = getUser(userId, userPassword);
            if (user != null) {
                System.out.println("Login Successfully...\n");

//        Account Holder's Informaion
                System.out.println("\t\t\t...Capital Finance ATM...");
                System.out.println("......................................................................................");
                System.out.println("Card Number: " + user.getId() + "\tCard Holder's Name: " + user.getName());
                System.out.println("Address: " + user.getAddress() + "\t\tPhone Number: " + user.getPhone());
                System.out.println("Card Type: Debit Card\t\tCard Activation Date&Time: " + user.getDate());
                System.out.println("Balance: INR." + getAmount(userId) + "/-");
                System.out.println("......................................................................................");

                while (true) {

                    System.out.println("\n1.Transaction History/Mini Statement\n2.Withdraw\n3.Deposit\n4.Transfer\n5.Exit\n");
                    System.out.print("Enter choice: ");
                    int ch = Integer.parseInt(br.readLine().trim());

                    // User Menu
                    if (ch == 1) {
//                     1.Transaction History/Mini Statement  
                        System.out.println("\t\t...Transaction History/Mini Statement...");
                        System.out.println("......................................................................................");
                        System.out.println("Card Number: " + user.getId() + "\tCard Holder's Name: " + user.getName());
                        System.out.println("Balance: INR." + getAmount(userId) + "/-" + "\t\tPhone Number: " + user.getPhone());
                        System.out.println("......................................................................................");
                        System.out.println("Transactions:-");

                        if (getTransaction(user)) {
                            System.out.println("......................................................................................");

                        } else {
                            System.out.println("");
                        }

                    } else if (ch == 2) {
//                    2.Withdraw
                        System.out.print("Enter Amount to be withdrawn: ");
                        int amount = Integer.parseInt(br.readLine());

                        boolean f = Withdraw(user, amount);
                        if (f == true) {
                            System.out.println("You have successfully withdrawn " + amount + " Rupees.");
                            printReceipt(user, new BigInteger("0"));
                        } else {
                            System.out.println("Transaction Failed...");
                        }

                    } else if (ch == 3) {
//                    3.Deposit    
                        System.out.print("Enter Amount to Deposit: ");
                        int amount = Integer.parseInt(br.readLine());

                        boolean f = Deposit(user, amount);
                        if (f == true) {
                            System.out.println("You have successfully Deposit " + amount + " Rupees.");
                            printReceipt(user, new BigInteger("0"));
                        } else {
                            System.out.println("Transaction Failed...");
                        }

                    } else if (ch == 4) {
//                    4.Transfer
                        System.out.print("Enter Recipient Account Number: ");
                        String ac = br.readLine();
                        BigInteger recipient = new BigInteger(ac);
                        System.out.print("Enter Amount to be Transfer: ");
                        int amount = Integer.parseInt(br.readLine().trim());

                        boolean f = Transfer(user, recipient, amount);
                        if (f == true) {
                            String str = getName(recipient);
                            System.out.println("Transfer to " + str + " Succesfully..");
                            printReceipt(user, recipient);
                        } else {
                            System.out.println("failed");
                        }

                    } else if (ch == 5) {
//                    5.Exit
                        return;
//                        System.exit(0);

                    } else {
                        System.out.println("Invalid Choice...");
                    }

                }

            } else {
                System.out.println("Invalid Card No and Pin");
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("problem in Account->main");

        }

    }

//Return a user from the database based on card number and PIN/password.
    public static User getUser(BigInteger userId, String userPassword) {
        User user = null;
        BigDecimal userid = new BigDecimal(userId);

        try {
            Connection con = ConnectionProvider.getConnection();

            String q = "SELECT * FROM atm.user where id=? AND password=?;";
            PreparedStatement stmt = con.prepareStatement(q);
            stmt.setBigDecimal(1, userid);
            stmt.setString(2, userPassword);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                BigInteger id = Admin.getBigInteger(rs, 1);
                String name = rs.getString(2);
                int avl_bal = rs.getInt(3);
                String address = rs.getString(4);
                String phone = rs.getString(5);
                String dateTime = rs.getString(6);
                String password = rs.getString(7);

                user = new User(id, name, avl_bal, address, phone, dateTime, password);

            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("problem in Account.java->getUser");
        }
        return user;

    }

//Handles the deposit operation, updates the user's account balance
//and call Transaction method which records transaction into atm.transaction table 
    public static boolean Deposit(User user, int amount) {

        BigDecimal userid = new BigDecimal(user.getId());
        boolean f = false;
        try {
            Connection con = ConnectionProvider.getConnection();
            String q = "UPDATE atm.user SET avl_bal = avl_bal + ? WHERE id = ?;";
            PreparedStatement stmt = con.prepareStatement(q);
            stmt.setInt(1, amount);
            stmt.setBigDecimal(2, userid);

            int rowUpdated = stmt.executeUpdate();
            if (rowUpdated > 0) {

                boolean success = Transaction(user, amount, "Deposit ", "Cash Deposit at ATM");
                if (success) {
                    return true;
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("problem in Account.java->Deposit");
        }

        return f;
    }

//Handles the withdrawal operation and updates the user's account balance
//and call Transaction method which records transaction into atm.transaction table 
    public static boolean Withdraw(User user, int amount) {
        BigDecimal userid = new BigDecimal(user.getId());
        boolean f = false;
        try {
            Connection con = ConnectionProvider.getConnection();
            String q = "UPDATE atm.user SET avl_bal = avl_bal - ? WHERE id = ?;";
            PreparedStatement stmt = con.prepareStatement(q);
            stmt.setInt(1, amount);
            stmt.setBigDecimal(2, userid);

            int rowUpdated = stmt.executeUpdate();
            if (rowUpdated > 0) {

                boolean success = Transaction(user, amount, "Withdraw", "Cash Withdraw at ATM");
                if (success) {
                    return true;
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("problem in Account.java->Deposit");
        }

        return f;
    }

// Records a transaction into atm.transaction table
    public static boolean Transaction(User user, int amount, String type, String note) {
        boolean f = false;
        String date = Admin.getCurrentDateTime();
        BigDecimal userId = new BigDecimal(user.getId());
        try {
            Connection con = ConnectionProvider.getConnection();
            String q = "insert into atm.transaction(user_id,amount,type,note,date) values (?,?,?,?,?);";
            PreparedStatement stmt = con.prepareStatement(q);

            stmt.setBigDecimal(1, userId);
            stmt.setInt(2, amount);
            stmt.setString(3, type);
            stmt.setString(4, note);
            stmt.setString(5, date);

            int flag = stmt.executeUpdate();
            if (flag != 0) {
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Problem in Account->Transaction\n");

        }
        return f;
    }

// Retrieves and displays the user's transaction history.
    public static boolean getTransaction(User user) {
        boolean f = false;
        int i = 0;
        String date = Admin.getCurrentDateTime();
        BigDecimal userId = new BigDecimal(user.getId());
        try {
            Connection con = ConnectionProvider.getConnection();
            String q = "SELECT * FROM atm.transaction where user_id = ?;";
            PreparedStatement stmt = con.prepareStatement(q);

            stmt.setBigDecimal(1, userId);
            ResultSet rs = stmt.executeQuery();
            System.out.println("S.N.\tTxn.ID\tAmount\tTxn. Type\t\tNote\t\t    Date & Time");
            while (rs.next()) {
                i++;
                int t_id = rs.getInt("t_id");
                int amount = rs.getInt("amount");
                String type = rs.getString("type");
                String note = rs.getString("note");
                String dateTime = rs.getString("date");

                System.out.println(" " + i + "\t " + t_id + "\t" + amount + "\t" + type + "\t" + note + "\t" + dateTime);
                f = true;
            }

        } catch (Exception e) {
            System.out.println("Problem in Account->Transaction");
            e.printStackTrace();

        }
        return f;
    }

//  Handles fund transfer between accounts, take recipient'a account and amount as input parameters
//    and update sender and recipienst's balance in database
    public static boolean Transfer(User user, BigInteger recipient, int amount) {
        String str = "Transfer to ****" + (recipient.toString().substring(7));

        boolean f = false;

        int sAmount = amount;
        BigDecimal sender = new BigDecimal(user.getId());
        BigDecimal receiver = new BigDecimal(recipient);

        try {
            Connection con = ConnectionProvider.getConnection();
//            Insert into sender's row
            String q = "UPDATE atm.user SET avl_bal = avl_bal - ? WHERE id = ?;";
            PreparedStatement stmt = con.prepareStatement(q);
            stmt.setInt(1, amount);
            stmt.setBigDecimal(2, sender);
            int s = stmt.executeUpdate();

//            Insert into recevier's row
            String p = " UPDATE atm.user SET avl_bal = avl_bal + ? WHERE id = ?;";
            PreparedStatement pstmt = con.prepareStatement(p);
            pstmt.setInt(1, sAmount);
            pstmt.setBigDecimal(2, receiver);
            int r = pstmt.executeUpdate();

            if ((s != 0) && (r != 0)) {
                if (Transaction(user, amount, "Transfer", str)) {
                    return true;
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Problem in Account.java->Transfer");
        }

        return f;
    }

//    Retrieves the name of a user based on their card number.
    public static String getName(BigInteger userId) {

        BigDecimal userid = new BigDecimal(userId);
        String name = "";

        try {
            Connection con = ConnectionProvider.getConnection();

            String q = "SELECT * FROM atm.user where id=?;";
            PreparedStatement stmt = con.prepareStatement(q);
            stmt.setBigDecimal(1, userid);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                name = rs.getString(2);

            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("problem in Account.java->getUser");
        }
        return name;

    }

// Retrieves the current available balance of a user.
    public static int getAmount(BigInteger id) {
        BigDecimal userId = new BigDecimal(id);
        int amount = 0;
        try {
            Connection con = ConnectionProvider.getConnection();
            String q = "SELECT avl_bal FROM atm.user where id = ?;";
            PreparedStatement stmt = con.prepareStatement(q);
            stmt.setBigDecimal(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                amount = rs.getInt("avl_bal");

            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Problem in Account.java->getAmount\n");
        }

        return amount;
    }

//    Prints a transaction receipt for the last transaction.
    public static void printReceipt(User user, BigInteger recipient) {
        int t_id = 0;
        int amount = 0;
        String date = "";
        String type = "";
        BigDecimal sAc = new BigDecimal(user.getId());       // sAc = Sender account
        String sName = getName(user.getId());

        try {
            //Get Last Transection from database
            Connection con = ConnectionProvider.getConnection();
            String q = "SELECT * FROM atm.transaction WHERE user_id = ? ORDER BY t_id DESC LIMIT 1;";
            PreparedStatement stmt = con.prepareStatement(q);
            stmt.setBigDecimal(1, sAc);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                t_id = rs.getInt("t_id");
                amount = rs.getInt("amount");
                type = (rs.getString("type")).trim();
                date = rs.getString("date");

            }

            System.out.println("...............................................");
            System.out.println("\t....Capital Finance ATM....\n\t    Transaction Receipt");
            System.out.println("...............................................");

            System.out.println("Transaction ID\t:\t" + t_id);
            System.out.println("Date & Time\t:\t" + date);
            System.out.println("Card Number\t:\t" + user.getId());
            System.out.println("Account Holder\t:\t" + user.getName());

            if (type.equals("Deposit")) {
                System.out.println("Transaction Type:\t" + type);
                System.out.println("Deposit Amount\t:\tINR." + amount + "/-");

            } else if (type.equals("Withdraw")) {
                System.out.println("Transaction Type:\tWithdrawal");
                System.out.println("Withdrawal Amount:\tINR." + amount + "/-");

            } else if (type.equals("Transfer")) {
                BigDecimal rAc = new BigDecimal(recipient);
                String rName = getName(recipient);
                System.out.println("Transaction Type:\t" + type);
                System.out.println("Recipient Account:\t" + rAc);
                System.out.println("Recipient Name\t:\t" + rName);
                System.out.println("Transfer Amount\t:\tINR." + amount + "/-");
            }
            System.out.println("Account Balance\t:\tINR." + getAmount(user.getId()) + "/-");
            System.out.println("Status\t\t:\tSuccess");
            System.out.println("...............................................");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Problem in Account.java->printReceipt");
        }

    }

//end
}

// Ritik Mondal
// 25.September.2023
