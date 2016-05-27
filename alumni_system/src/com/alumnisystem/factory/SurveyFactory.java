package com.alumnisystem.factory;

import com.alumnisystem.model.Survey;
import com.sun.istack.internal.NotNull;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SurveyFactory extends ModelFactory<Survey> {

    @Override
    public ArrayList<Survey> all() {
        try {
            statement.setStatement("SELECT * FROM survey");

            result = statement.executeQuery();

            ArrayList<Survey> surveys = new ArrayList<>();
            while (result.next()) {
                surveys.add(buildObject(new Survey(), result));
            }
            return surveys;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public Survey find(@NotNull int id) {
        try {
            statement.setStatement("SELECT * FROM survey WHERE id = ?")
                    .setInt(id);

            result = statement.executeQuery();
            if(result.next()) {
                return buildObject(new Survey(), result);
            } else {
                return null;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public Survey findBySchema(@NotNull String schema) {
        try {
            statement.setStatement("SELECT * FROM survey WHERE schemafile = ?")
                    .setString(schema);

            result = statement.executeQuery();
            if(result.next()) {
                return buildObject(new Survey(), result);
            } else {
                return null;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public Survey create(@NotNull Survey model) {
        return null;
    }

    @Override
    public Survey remove(@NotNull int id) {
        return null;
    }

    @Override
    public Survey update(@NotNull Survey model) {
        return null;
    }

    @Override
    Survey buildObject(@NotNull Survey model, ResultSet result) throws SQLException {
        model.id = result.getInt("survey.id");
        model.name = result.getString("survey.name");
        model.description = result.getString("survey.description");
        model.timecreate = result.getTimestamp("survey.datecreate");
        model.status = result.getBoolean("survey.status");
        model.schemafile = result.getString("survey.schemafile");

        return model;
    }

    public String generateSchemaString(String title) {
        MessageDigest md = null;
        try { md = MessageDigest.getInstance("MD5"); }
        catch (NoSuchAlgorithmException e) { e.printStackTrace();}

        assert md != null;
        md.update(title.getBytes());
        byte byteData[] = md.digest();
        StringBuilder sb = new StringBuilder();
        for (byte aByteData : byteData) { sb.append(Integer.toString((aByteData & 0xff) + 0x100, 16).substring(1)); }

        return "survey-" + new SimpleDateFormat("yyyyMMddHHmm").format(new Date()) + "_" + sb.toString();
    }

}
