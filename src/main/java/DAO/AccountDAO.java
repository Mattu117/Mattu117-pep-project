package DAO;
import Util.ConnectionUtil;
import Model.Account;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {

    // Class to register Account
    public Account registerAccount(Account account){
        Connection connection = ConnectionUtil.getConnection();

        try {
            // SQL Logic to add account into database
            String sql = "INSERT INTO account (username, password) VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);

            // Write prepared Statement
            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());
            preparedStatement.executeUpdate();

            // Get id of account and return with it

            ResultSet pk = preparedStatement.getGeneratedKeys();
            if(pk.next()){
                int account_id = (int) pk.getLong(1);
                return new Account(account_id, account.getUsername(), account.getPassword());
            }

        } catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return null;
    }

    // Class to check if username is in database;
    public boolean getAccountByUsername(String username){
        Connection connection = ConnectionUtil.getConnection();
        try {
            // SQL Logic to get account with a username
            String sql = "SELECT * FROM account WHERE username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            // Write prepared Statement
            preparedStatement.setString(1, username);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                return true;
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return false;
    }

    // Check if username and password are in database
    public Account login(Account account){
        Connection connection = ConnectionUtil.getConnection();
        try {
            // SQL Logic to get account with a username
            String sql = "SELECT * FROM account WHERE username = ? AND password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);

            // Write prepared Statement
            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Account loginAccount = new Account(rs.getInt("account_id"),rs.getString("username"),rs.getString("password"));
                return loginAccount;
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    
}
