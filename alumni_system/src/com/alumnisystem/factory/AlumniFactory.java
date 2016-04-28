package com.alumnisystem.factory;

import com.alumnisystem.database.Database;
import com.alumnisystem.model.Alumni;
import exception.NoAlumniFoundException;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by meranote on 4/28/2016 AD.
 */
class AlumniFactory extends ModelFactory<Alumni> {

    public AlumniFactory(Connection connection) {
        super(connection);

        setForeignFactory("alumni", "alumni_user_pivot", new String[]{"id"}, new String[]{"alumni_id"});
        setForeignFactory("alumni_user_pivot", "user", new String[]{"user_id"}, new String[]{"id"});
        setForeignFactory("user", "user_detail", new String[]{"user_detail_id"}, new String[]{"id"}, "", "id");
        setForeignFactory("alumni", "alumni_address", new String[]{"id"}, new String[]{"alumni_id"}, "address", "alumni_id");
        setForeignFactory("alumni_address", "province", new String[]{"province_id"}, new String[]{"id"});
        setForeignFactory("alumni", "alumni_track", new String[]{"id"}, new String[]{"alumni_id"}, "track", "alumni_id");
        setForeignFactory("alumni_track", "track", new String[]{"track_id"}, new String[]{"id"});
        setForeignFactory("track", "curriculum", new String[]{"curriculum_id"}, new String[]{"id"});
        initFactory("alumni");
    }

    @Override
    public ArrayList<Alumni> all(ResultSet result) throws SQLException {
        ArrayList<Alumni> alumnis = new ArrayList<>();
        while (result.next()) {
            alumnis.add(buildAlumniObject(result));
        }
        return alumnis;
    }

    @Override
    public ArrayList<Alumni> find(ResultSet result) throws SQLException {
        ArrayList<Alumni> alumnis = new ArrayList<>();
        while (result.next()) {
            alumnis.add(buildAlumniObject(result));
        }
        return alumnis;
    }

    @Override
    public Alumni findOne(ResultSet result) throws SQLException {
        if(result.next()) return buildAlumniObject(result, true);
        else throw new NoAlumniFoundException();
    }

    @Override
    public Alumni findById(ResultSet result) throws SQLException {
        if(result.next()) return buildAlumniObject(result, true);
        else throw new NoAlumniFoundException();
    }

    private Alumni buildAlumniObject(ResultSet result) throws SQLException {
        return buildAlumniObject(result, false);
    }

    private Alumni buildAlumniObject(ResultSet result, boolean ejectWhenIdNotMatch) throws SQLException {
        Alumni alumni = new Alumni();

        alumni.tuples.putAll(getResultDataByMetaTable("alumni"));
        alumni.tuples.putAll(getResultDataByMetaTable("user_detail"));
        alumni.tuples.putAll(getResultDataByMetaTable("alumni_address"));

        alumni.tuples.put("track", new ArrayList<HashMap<String, Object>>());
        ((ArrayList<HashMap<String, Object>>) alumni.tuples.get("track")).add(getResultDataByMetaTable("alumni_track"));
        while (true) {
            if(result.next()) {
                if((int)getResultDataByMetaTable("alumni").get("id") != (int)alumni.tuples.get("id")) {
                    if(ejectWhenIdNotMatch) throw new RuntimeException("result return more than 1 model");
                    result.previous();
                    break;
                } else {
                    ((ArrayList<HashMap<String, Object>>) alumni.tuples.get("track")).add(getResultDataByMetaTable("alumni_track"));
                }
            } else {
                break;
            }
        }

        return alumni;
    }

    public static void main(String[] args) throws SQLException {
//        Connection connection = Database.getDatabaseConnectionForTest();
//
//        AlumniFactory alumniFactory = new AlumniFactory(connection);
//
//        int i = 0;
//        Object[] cn1 = {"alumni_track.generation", 2};
//        Object[] cn2 = {"alumni_track.generation", "=", 4, "OR"};
//        for (Alumni alumni : alumniFactory.find(cn1, cn2)) {
//            System.out.println(alumni.tuples);
//            i++;
//        }
//        System.out.println("total rows: " + i);
//
//        Object[] cn1 = {"alumni_track.student_id", 54070013};
//        System.out.print(alumniFactory.findOne(cn1).tuples);
//
//        connection.close();
    }

}
