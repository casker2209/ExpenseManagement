package com.mobile.expensemanagement.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ExpenseDAO {
    @Query("SELECT * FROM expense WHERE name LIKE  '%' || :name_query || '%'")
    List<Expense> getExpenseByName(String name_query);

    @Query("SELECT * FROM expense WHERE destination LIKE '%' ||:destination_query|| '%'")
    List<Expense> getExpenseByDestination(String destination_query);

    @Query("SELECT * FROM expense WHERE destination LIKE '%' ||:destination_query|| '%' AND name LIKE '%' ||:name_query|| '%'")
    List<Expense> getExpenseByNameAndDestination(String destination_query,String name_query);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Expense expense);

    @Update
    void update(Expense expense);

    @Delete
    void delete(Expense expense);



}
