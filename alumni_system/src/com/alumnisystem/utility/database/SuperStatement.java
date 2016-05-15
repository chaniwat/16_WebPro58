package com.alumnisystem.utility.database;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.util.Calendar;

/**
 * Superior for chaining of {@link PreparedStatement}
 */
public class SuperStatement {

    private Connection connection;
    private PreparedStatement statement;

    private int currentPosition = 1;

    public SuperStatement(Connection connection) {
        this.connection = connection;
    }

    public SuperStatement setStatement(String sql, int autoGenerateKey) throws SQLException {
        currentPosition = 1;
        statement = connection.prepareStatement(sql, autoGenerateKey);
        return this;
    }

    public SuperStatement setStatement(String sql) throws SQLException {
        currentPosition = 1;
        statement = connection.prepareStatement(sql);
        return this;
    }

    public ResultSet executeQuery() throws SQLException {
        currentPosition = 1;
        return statement.executeQuery();
    }

    public int executeUpdate() throws SQLException {
        currentPosition = 1;
        return statement.executeUpdate();
    }

    public SuperStatement setNull(int sqlType) throws SQLException {
        statement.setNull(currentPosition++, sqlType);
        return this;
    }

    public SuperStatement setBoolean(boolean x) throws SQLException {
        statement.setBoolean(currentPosition++, x);
        return this;
    }

    public SuperStatement setByte(byte x) throws SQLException {
        statement.setByte(currentPosition++, x);
        return this;
    }

    public SuperStatement setShort(short x) throws SQLException {
        statement.setShort(currentPosition++, x);
        return this;
    }

    public SuperStatement setInt(int x) throws SQLException {
        statement.setInt(currentPosition++, x);
        return this;
    }

    public SuperStatement setLong(long x) throws SQLException {
        statement.setLong(currentPosition++, x);
        return this;
    }

    public SuperStatement setFloat(float x) throws SQLException {
        statement.setFloat(currentPosition++, x);
        return this;
    }

    public SuperStatement setDouble(double x) throws SQLException {
        statement.setDouble(currentPosition++, x);
        return this;
    }

    public SuperStatement setBigDecimal(BigDecimal x) throws SQLException {
        statement.setBigDecimal(currentPosition++, x);
        return this;
    }

    public SuperStatement setString(String x) throws SQLException {
        statement.setString(currentPosition++, x);
        return this;
    }

    public SuperStatement setBytes(byte[] x) throws SQLException {
        statement.setBytes(currentPosition++, x);
        return this;
    }

    public SuperStatement setDate(Date x) throws SQLException {
        statement.setDate(currentPosition++, x);
        return this;
    }

    public SuperStatement setTime(Time x) throws SQLException {
        statement.setTime(currentPosition++, x);
        return this;
    }

    public SuperStatement setTimestamp(Timestamp x) throws SQLException {
        statement.setTimestamp(currentPosition++, x);
        return this;
    }

    public SuperStatement setAsciiStream(InputStream x, int length) throws SQLException {
        statement.setAsciiStream(currentPosition++, x, length);
        return this;
    }

    public SuperStatement setUnicodeStream(InputStream x, int length) throws SQLException {
        statement.setUnicodeStream(currentPosition++, x, length);
        return this;
    }

    public SuperStatement setBinaryStream(InputStream x, int length) throws SQLException {
        statement.setBinaryStream(currentPosition++, x, length);
        return this;
    }

    public void clearParameters() throws SQLException {
        currentPosition = 1;
        statement.clearParameters();
    }

    public SuperStatement setObject(Object x, int targetSqlType) throws SQLException {
        statement.setObject(currentPosition++, x, targetSqlType);
        return this;
    }

    public SuperStatement setObject(Object x) throws SQLException {
        statement.setObject(currentPosition++, x);
        return this;
    }

    public SuperStatement setCharacterStream(Reader reader, int length) throws SQLException {
        statement.setCharacterStream(currentPosition++, reader, length);
        return this;
    }

    public SuperStatement setRef(Ref x) throws SQLException {
        statement.setRef(currentPosition++, x);
        return this;
    }

    public SuperStatement setBlob(Blob x) throws SQLException {
        statement.setBlob(currentPosition++, x);
        return this;
    }

    public SuperStatement setClob(Clob x) throws SQLException {
        statement.setClob(currentPosition++, x);
        return this;
    }

    public SuperStatement setArray(Array x) throws SQLException {
        statement.setArray(currentPosition++, x);
        return this;
    }

    public ResultSetMetaData getMetaData() throws SQLException {
        return statement.getMetaData();
    }

    public SuperStatement setDate(Date x, Calendar cal) throws SQLException {
        statement.setDate(currentPosition++, x, cal);
        return this;
    }

    public SuperStatement setTime(Time x, Calendar cal) throws SQLException {
        statement.setTime(currentPosition++, x, cal);
        return this;
    }

    public SuperStatement setTimestamp(Timestamp x, Calendar cal) throws SQLException {
        statement.setTimestamp(currentPosition++, x, cal);
        return this;
    }

    public SuperStatement setNull(int sqlType, String typeName) throws SQLException {
        statement.setNull(currentPosition++, sqlType, typeName);
        return this;
    }

    public SuperStatement setURL(URL x) throws SQLException {
        statement.setURL(currentPosition++, x);
        return this;
    }

    public ParameterMetaData getParameterMetaData() throws SQLException {
        return statement.getParameterMetaData();
    }

    public SuperStatement setRowId(RowId x) throws SQLException {
        statement.setRowId(currentPosition++, x);
        return this;
    }

    public SuperStatement setNString(String value) throws SQLException {
        statement.setNString(currentPosition++, value);
        return this;
    }

    public SuperStatement setNCharacterStream(Reader value, long length) throws SQLException {
        statement.setNCharacterStream(currentPosition++, value, length);
        return this;
    }

    public SuperStatement setNClob(NClob value) throws SQLException {
        statement.setNClob(currentPosition++, value);
        return this;
    }

    public SuperStatement setClob(Reader reader, long length) throws SQLException {
        statement.setClob(currentPosition++, reader, length);
        return this;
    }

    public SuperStatement setBlob(InputStream inputStream, long length) throws SQLException {
        statement.setBlob(currentPosition++, inputStream, length);
        return this;
    }

    public SuperStatement setNClob(Reader reader, long length) throws SQLException {
        statement.setNClob(currentPosition++, reader, length);
        return this;
    }

    public SuperStatement setSQLXML(SQLXML xmlObject) throws SQLException {
        statement.setSQLXML(currentPosition++, xmlObject);
        return this;
    }

    public SuperStatement setObject(Object x, int targetSqlType, int scaleOrLength) throws SQLException {
        statement.setObject(currentPosition++, targetSqlType, scaleOrLength);
        return this;
    }

    public SuperStatement setAsciiStream(InputStream x, long length) throws SQLException {
        statement.setAsciiStream(currentPosition++, x, length);
        return this;
    }

    public SuperStatement setBinaryStream(InputStream x, long length) throws SQLException {
        statement.setBinaryStream(currentPosition++, x, length);
        return this;
    }

    public SuperStatement setCharacterStream(Reader reader, long length) throws SQLException {
        statement.setCharacterStream(currentPosition++, reader, length);
        return this;
    }

    public SuperStatement setAsciiStream(InputStream x) throws SQLException {
        statement.setAsciiStream(currentPosition++, x);
        return this;
    }

    public SuperStatement setBinaryStream(InputStream x) throws SQLException {
        statement.setBinaryStream(currentPosition++, x);
        return this;
    }

    public SuperStatement setCharacterStream(Reader reader) throws SQLException {
        statement.setCharacterStream(currentPosition++, reader);
        return this;
    }

    public SuperStatement setNCharacterStream(Reader value) throws SQLException {
        statement.setNCharacterStream(currentPosition++, value);
        return this;
    }

    public SuperStatement setClob(Reader reader) throws SQLException {
        statement.setClob(currentPosition++, reader);
        return this;
    }

    public SuperStatement setBlob(InputStream inputStream) throws SQLException {
        statement.setBlob(currentPosition++, inputStream);
        return this;
    }

    public SuperStatement setNClob(Reader reader) throws SQLException {
        statement.setNClob(currentPosition++, reader);
        return this;
    }

    public void close() throws SQLException {
        statement.close();
    }

    public ResultSet getResultSet() throws SQLException {
        return statement.getResultSet();
    }


    public Connection getConnection() throws SQLException {
        return statement.getConnection();
    }

    public boolean isClosed() throws SQLException {
        return statement.isClosed();
    }

    public PreparedStatement getStatement() {
        return statement;
    }

}
