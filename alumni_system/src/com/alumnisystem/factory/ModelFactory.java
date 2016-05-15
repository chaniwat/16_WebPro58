package com.alumnisystem.factory;

import com.alumnisystem.utility.database.Database;
import com.alumnisystem.utility.database.SuperStatement;
import com.sun.istack.internal.NotNull;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Base Model Factory
 */
public abstract class ModelFactory<T> {

    protected Connection connection;
    protected SuperStatement statement;
    protected ResultSet result;

    public ModelFactory() {
        this.connection = Database.getConnection();
        statement = new SuperStatement(connection);
    }

    public abstract ArrayList<T> all();
    public abstract T find(@NotNull int id);
    public abstract T create(@NotNull T model);
    public abstract T remove(@NotNull int id);
    public abstract T update(@NotNull T model);
    abstract T buildObject(@NotNull T model, ResultSet result) throws SQLException;

}
