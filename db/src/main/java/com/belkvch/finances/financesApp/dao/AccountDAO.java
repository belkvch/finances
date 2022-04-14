package com.belkvch.finances.financesApp.dao;

import com.belkvch.finances.financesApp.entyti.Accounts;

import java.util.List;

public interface AccountDAO {
    List<Accounts> showAllAccountsForUser(int userId);

    Accounts addNewAccount(Accounts accounts);

    Accounts getAccountById(int id);

    Accounts changeOperationAmount(Accounts account);
}
