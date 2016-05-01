package com.alumnisystem.factory;

import com.alumnisystem.exception.ModelNotFound;
import com.alumnisystem.model.Model;

import java.sql.Connection;

/**
 * Created by meranote on 4/28/2016 AD.
 */
public class ModelFactoryUtility {

    private Connection connection;

    public ModelFactoryUtility(Connection connection) {
        this.connection = connection;
    }

    public ModelFactory getModelFactory(Class<? extends Model> modelClass) throws ModelNotFound {
        switch (modelClass.getName()) {
            case "com.alumnisystem.model.Alumni": return new AlumniFactory(connection);
            default: throw new ModelNotFound();
        }
    }

}
