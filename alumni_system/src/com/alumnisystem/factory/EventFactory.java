package com.alumnisystem.factory;

import com.alumnisystem.exception.EventNotFound;
import com.alumnisystem.model.Event;
import com.sun.istack.internal.NotNull;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class EventFactory extends ModelFactory<Event> {

    @Override
    public ArrayList<Event> all() {
        try {
            statement.setStatement("SELECT * FROM event");

            ResultSet result = statement.executeQuery();

            ArrayList<Event> curricula = new ArrayList<>();
            while (result.next()) {
                curricula.add(buildObject(new Event(), result));
            }
            return curricula;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public Event find(@NotNull int id) throws EventNotFound {
        try {
            statement.setStatement("SELECT * FROM event WHERE id = ?")
                    .setInt(id);

            ResultSet result = statement.executeQuery();

            if(result.next()) {
                return buildObject(new Event(), result);
            } else {
                throw new EventNotFound();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public Event create(@NotNull Event model) {
        try {
            long currentTime = new java.util.Date().getTime();

            statement.setStatement("INSERT INTO event VALUES (0, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)
                    .setString(model.getTitle())
                    .setString(model.getDetail())
                    .setString(model.getDescription())
                    .setDate(new Date(currentTime))
                    .setTime(new Time(currentTime));

            statement.executeUpdate();

            result = statement.getStatement().getGeneratedKeys();

            if(result.next()) {
                model.setId(result.getInt(1));
            }

            return model;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public Event remove(@NotNull int id) throws EventNotFound {
        Event event = find(id);

        try {
            statement.setStatement("DELETE FROM event WHERE id = ?")
                    .setInt(id);

            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return event;
    }

    @Override
    public Event update(@NotNull Event model) {
        try {
            statement.setStatement("UPDATE event " +
                    "SET title = ?, description = ?, " +
                    "detail = ? " +
                    "WHERE id = ?")
                    .setString(model.getTitle())
                    .setString(model.getDetail())
                    .setString(model.getDescription())
                    .setInt(model.getId());

            statement.executeUpdate();

            return model;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    Event buildObject(@NotNull Event model, ResultSet result) throws SQLException {
        model.setId(result.getInt("event.id"));
        model.setTitle(result.getString("event.title"));
        model.setDetail(result.getString("event.detail"));
        model.setDescription(result.getString("event.description"));
        model.setDate(result.getDate("event.date"));
        model.setTime(result.getTime("event.time"));

        return model;
    }

}
