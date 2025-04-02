package Service;

import Model.Account;
import DAO.AccountDAO;

public class AccountService {
    private AccountDAO accountDAO;

    // Constructor for Account Service without provided account
    public AccountService(){
        accountDAO = new AccountDAO();
    }

    // Constructor with provided account
    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }

    public Account registerAccount(Account account){
        if(account.getUsername().isEmpty()){
            return null;
        }
        else if(account.getPassword().length() < 4){
            return null;
        }
        else if(accountDAO.getAccountByUsername(account.getUsername())){
            return null;
        }
        else{
            account = accountDAO.registerAccount(account);
        }
        return account;
    }

    public Account loginAccount(Account account){
        return accountDAO.login(account);
    }
}
