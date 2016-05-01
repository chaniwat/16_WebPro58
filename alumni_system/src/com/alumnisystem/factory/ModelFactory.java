package com.alumnisystem.factory;

import com.alumnisystem.annotation.Model;
import com.alumnisystem.database.SuperStatement;
import com.alumnisystem.model.BaseModel;
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

    public ModelFactory(@NotNull Connection connection) {
        this.connection = connection;
        statement = new SuperStatement(connection);
    }

    public static ModelFactory getFactory(Connection connection, Class<? extends BaseModel> modelClass) {
        Model annoModel = modelClass.getAnnotation(Model.class);
        if(annoModel == null) throw new RuntimeException("Not a model");
        try {
            return (ModelFactory) Class.forName("com.alumnisystem.factory." + annoModel.factory())
                    .getConstructor(Connection.class).newInstance(connection);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public abstract ArrayList<T> all();
    public abstract T find(@NotNull int id);
    public abstract T create(@NotNull T model);
    public abstract T remove(@NotNull int id);
    public abstract T update(@NotNull T model);
    abstract T setObject(@NotNull T model, ResultSet result) throws SQLException;

}
