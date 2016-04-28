package com.alumnisystem.factory;

import com.alumnisystem.database.Database;
import com.sun.istack.internal.NotNull;

import java.sql.*;
import java.util.*;

/**
 * Model factory
 * Author: @meranotemajimo
 * Inspire by: x7castitle
 */
public abstract class ModelFactory<T> {

    protected Connection connection;
    protected String sql;
    protected PreparedStatement statement;
    protected ResultSet result;

    private String baseTable;

    /**
     * Foreign mapping<br>
     * Key: (String)foreignTable, Value: Object[(String)baseTable, (String)baseKey, (String)foreignKey]
     */
    private LinkedHashMap<String, Object[]> tableForeignTableMapping = new LinkedHashMap<>();

    /**
     * Key prefix mapping (for foreign that needresult = true)<br>
     * Key: (String)foreignTable, Value: (String)prefix
     */
    private HashMap<String, String> tableKeyPrefixMapping = new HashMap<>();

    /**
     * Skip result column mapping (for foreign that needresult = true or baseTable that need to skip some column)<br>
     * Key: (String)foreignTable, Value: (String)[columnNameToSkip...]
     */
    private HashMap<String, String[]> tableSkipColumnMapping = new HashMap<>();

    /**
     * Meta table (for foreign that needresult = true)<br>
     * Internal used
     */
    private HashMap<String, ArrayList<Database.Column>> metaTableModel = new HashMap<>();

    /**
     * Model Factory Constructor
     * @param connection
     */
    protected ModelFactory(Connection connection) {
        this.connection = connection;
    }

    /**
     * Set model data (table)
     * @param table modelTable
     * @param skipcolumns skip column name list
     */
    private final void setFactory(@NotNull String table, String... skipcolumns) {
        this.baseTable = table;

        if(skipcolumns.length > 0) tableSkipColumnMapping.put(table, skipcolumns);
    }

    /**
     * Set foreign table to be fetch of this model<br>
     * @param baseTable base table of the foreign
     * @param foreignTable target table
     * @param baseKey base table foreign key (as Array)
     * @param foreignKey target foreign key (as Array)
     * @param needResult is need result from this target table
     * @param keyPrefix key prefix when build tuple for target table
     * @param skipcolumns skip column name of target table list
     */
    protected final void setForeignFactory(@NotNull String baseTable, @NotNull String foreignTable, @NotNull String[] baseKey, @NotNull String[] foreignKey, boolean needResult, String keyPrefix, String... skipcolumns) {
        tableForeignTableMapping.put(foreignTable, new Object[] {baseTable, baseKey, foreignKey});

        if(needResult) {
            if(!keyPrefix.equals("")) tableKeyPrefixMapping.put(foreignTable, keyPrefix);
            tableSkipColumnMapping.put(foreignTable, skipcolumns);
        }
    }

    /**
     * Set foreign table to be fetch of this model and not set to get the result<br>
     * @param baseTable base table of the foreign
     * @param foreignTable target table
     * @param baseKey base table foreign key (as Array)
     * @param foreignKey target foreign key (as Array)
     */
    protected final void setForeignFactory(@NotNull String baseTable, @NotNull String foreignTable, @NotNull String[] baseKey, @NotNull String[] foreignKey) {
        setForeignFactory(baseTable, foreignTable, baseKey, foreignKey, false, "");
    }

    /**
     * Set foreign table to be fetch of this model and set to get the result<br>
     * @param baseTable base table of the foreign
     * @param foreignTable target table
     * @param baseKey base table foreign key (as Array)
     * @param foreignKey target foreign key (as Array)
     * @param keyPrefix key prefix when build tuple for target table
     * @param skipcolumns skip column name of target table list
     */
    protected final void setForeignFactory(@NotNull String baseTable, @NotNull String foreignTable, @NotNull String[] baseKey, @NotNull String[] foreignKey, String keyPrefix, String... skipcolumns) {
        setForeignFactory(baseTable, foreignTable, baseKey, foreignKey, true, keyPrefix, skipcolumns);
    }

    /**
     * Initialize factory
     */
    protected final void initFactory(@NotNull String table, String... skipColumns) {
        setFactory(table, skipColumns);

        /**
         * Build SQL script
         */
        sql = "SELECT * FROM " + baseTable + " ";
        for(Map.Entry<String, Object[]> entry : tableForeignTableMapping.entrySet()) {
            if(((String[])entry.getValue()[1]).length != ((String[])entry.getValue()[2]).length) throw new RuntimeException("Base key and Foreign key length not match.");

            sql += "LEFT JOIN " + entry.getKey() + " ON (";
            for(int i = 0; i < ((String[])entry.getValue()[1]).length; i++) {
                if(i != 0) sql += " AND ";
                sql += entry.getValue()[0] + "." + ((String[])entry.getValue()[1])[i] + " = " + entry.getKey() + "." + ((String[])entry.getValue()[2])[i];
            }
            sql += ") ";
        }

        /**
         * Get column info of base table and each foreign table
         */
        metaTableModel.put(baseTable, Database.getTableColumnNameList(connection, baseTable));
        for(String foreign : tableForeignTableMapping.keySet()) {
            metaTableModel.put(foreign, Database.getTableColumnNameList(connection, foreign));
        }
    }

    /**
     * Get result from meta table
     * @param metaTableName target table name
     * @return HashMap&lt;String tablename, Object&gt;
     * @throws SQLException
     */
    protected final HashMap<String, Object> getResultDataByMetaTable(String metaTableName) throws SQLException {
        if(metaTableModel.get(metaTableName) == null) throw new RuntimeException("No meta table found, add table list first");
        HashMap<String, Object> data = new HashMap<>();

        String keyprefix = tableKeyPrefixMapping.getOrDefault(metaTableName, "");
        String columnprefix = metaTableName;

        for(Database.Column column : metaTableModel.get(metaTableName)) {
            String keyname = !keyprefix.equals("") ? keyprefix + "." + column.name : column.name;
            String columnname = !columnprefix.equals("") ? columnprefix + "." + column.name : column.name;

            boolean flag = false;
            for(String skip : tableSkipColumnMapping.getOrDefault(metaTableName, new String[] {})) {
                String skipcolumnname = !columnprefix.equals("") ? columnprefix + "." + skip : skip;
                if(skipcolumnname.equals(columnname)) {
                    flag = true;
                    break;
                }
            }
            if(flag) continue;

//            System.out.println(column.name + " " + column.type);
            data.put(keyname, getResultObject(result, columnname, column.type));
        }

        return data;
    }

    /**
     * Get result object, get from database and return it as Object.
     * @param result - ResultSet of query
     * @param columnName - column name to fetch
     * @param columnType - column type to fetch
     * @return Object
     * @throws SQLException
     */
    private final Object getResultObject(ResultSet result, String columnName, int columnType) throws SQLException {
        if(Arrays.asList(Types.CHAR, Types.VARCHAR, Types.LONGVARCHAR).contains(columnType)) {
            return result.getString(columnName);
        } else if(Arrays.asList(Types.TINYINT, Types.INTEGER).contains(columnType)) {
            return result.getInt(columnName);
        } else if(Arrays.asList(Types.FLOAT, Types.DOUBLE).contains(columnType)) {
            return result.getDouble(columnName);
        } else if(Arrays.asList(Types.BIT).contains(columnType)) {
            return result.getBoolean(columnName);
        } else if(Arrays.asList(Types.DATE).contains(columnType)) {
            return result.getDate(columnName);
        } else if(Arrays.asList(Types.TIME).contains(columnType)) {
            return result.getTime(columnName);
        } else {
            throw new RuntimeException("Can't get result for this column type (No case detected)");
        }
    }

    public final ArrayList<T> all() {
        try {
            statement = connection.prepareStatement(sql);
            result = statement.executeQuery();

            return all(result);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public final ArrayList<T> find(Object[]... key) {
        try {
            statement = connection.prepareStatement(buildSQLScript(key));
            result = statement.executeQuery();

            return find(result);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public final T findOne(Object[]... key) {
        try {
            statement = connection.prepareStatement(buildSQLScript(key));
            result = statement.executeQuery();

            return findOne(result);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public final T findById(int id) {
        try {
            String sqlst = sql + "WHERE " + baseTable + ".id = " + id + " ";
            statement = connection.prepareStatement(sqlst);
            result = statement.executeQuery();

            return findOne(result);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    protected abstract ArrayList<T> all(ResultSet result) throws SQLException;

    protected abstract ArrayList<T> find(ResultSet result) throws SQLException;

    protected abstract T findOne(ResultSet result) throws SQLException;

    protected abstract T findById(ResultSet result) throws SQLException;

    private String buildSQLScript(Object[]... key) {
        String sqlst = sql;

        int i = 0;
        for(Object[] o : key) {
            if(i++ != 0) {
                if(o.length == 4 && !((String)o[1]).toUpperCase().equals("BETWEEN")) sqlst += o[3] + " ";
                else if(o.length == 5 && o[1] == "BETWEEN") sqlst += o[4];
                else sqlst += "AND ";
            } else {
                sqlst += "WHERE ";
            }

            if(o.length == 2) {
                sqlst += o[0] + " = " + o[1] + " ";
            } else if(o.length == 3 || (o.length == 4 && !((String)o[1]).toUpperCase().equals("BETWEEN"))) {
                sqlst += o[0] + " " + o[1] + " " + o[2] + " ";
            } else if((o.length == 4 && ((String)o[1]).toUpperCase().equals("BETWEEN")) || o.length == 5) {
                sqlst += o[0] + " " + o[1] + " " + o[2] + " AND " + o[3] + " ";
            }
        }

//        System.out.println(sqlst);

        return sqlst;
    }

}
