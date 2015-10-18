package ro.pub.cs.aipi.lab07.dataaccess;

import java.io.BufferedReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import ro.pub.cs.aipi.lab07.general.Constants;
import ro.pub.cs.aipi.lab07.general.Utilities;
import ro.pub.cs.aipi.lab07.entities.Referrence;

public class DataBaseWrapperImplementation implements DataBaseWrapper {

    private Connection dbConnection;
    private DatabaseMetaData dbMetaData;
    private String defaultDatabase;

    private static DataBaseWrapperImplementation instance;

    private DataBaseWrapperImplementation() {
    }

    @Override
    public void releaseResources() {
        try {
            closeConnection();
        } catch (SQLException exception) {
            System.out.println("An exception has occured: " + exception.getMessage());
            if (Constants.DEBUG) {
                exception.printStackTrace();
            }
        }
    }

    public static DataBaseWrapperImplementation getInstance() {
        if (instance == null) {
            try {
                Class.forName("com.mysql.jdbc.Driver"); 
            } catch (ClassNotFoundException exception) {
                System.out.println ("An exception has occurred: "+exception.getMessage());
                if (Constants.DEBUG)
                    exception.printStackTrace();
            }        	
            instance = new DataBaseWrapperImplementation();
        }
        return instance;
    }

    private void openConnection() throws SQLException {
        if (dbConnection == null || dbConnection.isClosed()) {
            dbConnection = DriverManager.getConnection(
                    Constants.DATABASE_CONNECTION + (defaultDatabase != null ? defaultDatabase : ""),
                    Constants.DATABASE_USERNAME,
                    Constants.DATABASE_PASSWORD);
            dbMetaData = dbConnection.getMetaData();
        }
    }

    private void closeConnection() throws SQLException {
        if (dbConnection != null && !dbConnection.isClosed()) {
            dbConnection.close();
        }
    }

    private Statement createStatement() throws SQLException {
        openConnection();
        return dbConnection.createStatement(
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
    }

    private void destroyStatement(Statement statement) throws SQLException {
        if (statement != null && !statement.isClosed()) {
            statement.close();
        }
    }

    @Override
    public void setDefaultDatabase(String defaultDatabase) {
        this.defaultDatabase = defaultDatabase;
    }

    @Override
    public ArrayList<String> getTableNames() throws SQLException {
        openConnection();
        ArrayList<String> tableNames = new ArrayList<>();
        ResultSet result = dbMetaData.getTables(Constants.DATABASE_NAME, null, null, null);
        while (result.next()) {
            tableNames.add(result.getString("TABLE_NAME"));
        }
        return tableNames;
    }

    @Override
    public int getTableNumberOfRows(String tableName) throws SQLException {
        openConnection();
        Statement statement = createStatement();
        String query = "SELECT COUNT(*) FROM " + tableName;
        ResultSet result = statement.executeQuery(query);
        result.next();
        int numberOfRows = result.getInt(1);
        destroyStatement(statement);
        return numberOfRows;
    }

    @Override
    public int getTableNumberOfColumns(String tableName) throws SQLException {
        int numberOfColumns = 0;
        openConnection();
        ResultSet result = dbMetaData.getColumns(Constants.DATABASE_NAME, null, tableName, null);
        while (result.next()) {
            numberOfColumns++;
        }
        return numberOfColumns;
    }

    @Override
    public String getTablePrimaryKey(String tableName) throws SQLException {
        ArrayList<String> result = getTablePrimaryKeys(tableName);
        if (result.size() != 1) {
            return null;
        }
        return result.get(0);
    }

    @Override
    public ArrayList<String> getTablePrimaryKeys(String tableName) throws SQLException {
        ArrayList<String> primaryKeys = new ArrayList<>();
        openConnection();
        ResultSet result = dbMetaData.getPrimaryKeys(Constants.DATABASE_NAME, null, tableName);
        while (result.next()) {
            primaryKeys.add(result.getString("COLUMN_NAME"));
        }
        return primaryKeys;
    }

    @Override
    public int getTablePrimaryKeyMaximumValue(String tableName) throws SQLException {
        String primaryKey = getTablePrimaryKey(tableName);
        String query = "SELECT MAX(" + primaryKey + ") FROM " + tableName;
        openConnection();
        Statement statement = createStatement();
        ResultSet result = statement.executeQuery(query);
        result.next();
        int primaryKeyMaximumValue = result.getInt(1);
        destroyStatement(statement);
        return primaryKeyMaximumValue;
    }

    @Override
    public boolean isAutoGeneratedPrimaryKey(String tableName) throws SQLException {
        String primaryKey = getTablePrimaryKey(tableName);
        ResultSet result = dbMetaData.getColumns(null, Constants.DATABASE_NAME, tableName, primaryKey);
        if (result.next()) {
            return result.getBoolean("IS_AUTOINCREMENT");
        }
        return false;
    }

    @Override
    public ArrayList<String> getTableColumns(String tableName) throws SQLException {
        ArrayList<String> tableColumns = new ArrayList<>();
        openConnection();
        ResultSet result = dbMetaData.getColumns(Constants.DATABASE_NAME, null, tableName, null);
        while (result.next()) {
            tableColumns.add(result.getString("COLUMN_NAME"));
        }
        return tableColumns;
    }

    @Override
    public ArrayList<ArrayList<String>> getTableContent(String tableName, ArrayList<String> attributes, String whereClause, String orderByClause, String groupByClause) throws SQLException {
        openConnection();
        Statement statement = createStatement();
        String query = "SELECT ";
        int numberOfColumns = -1;
        if (attributes == null) {
            numberOfColumns = getTableNumberOfColumns(tableName);
            query += "*";
        } else {
            numberOfColumns = attributes.size();
            query = attributes.stream().map((attribute) -> attribute + ", ").reduce(query, String::concat);
            query = query.substring(0, query.length() - 2);
        }
        query += " FROM " + tableName;
        if (whereClause != null) {
            query += " WHERE " + whereClause;
        }
        if (groupByClause != null) {
            query += " GROUP BY " + groupByClause;
        }
        if (orderByClause != null) {
            query += " ORDER BY " + orderByClause;
        }
        if (Constants.DEBUG) {
            System.out.println("query: " + query);
        }
        if (numberOfColumns == -1) {
            return null;
        }
        ArrayList<ArrayList<String>> dataBaseContent = new ArrayList<>();
        ResultSet result = statement.executeQuery(query);
        int currentRow = 0;
        while (result.next()) {
            dataBaseContent.add(new ArrayList<>());
            for (int currentColumn = 0; currentColumn < numberOfColumns; currentColumn++) {
                dataBaseContent.get(currentRow).add(result.getString(currentColumn + 1));
            }
            currentRow++;
        }
        destroyStatement(statement);
        return dataBaseContent;
    }

    @Override
    public int insertValuesIntoTable(String tableName, ArrayList<String> attributes, ArrayList<String> values, boolean skipPrimaryKey) throws SQLException, DataBaseException {
        int result = -1;
        openConnection();
        Statement statement = createStatement();
        String query = "INSERT INTO " + tableName + " (";
        if (attributes == null) {
            attributes = getTableColumns(tableName);
            if (skipPrimaryKey) {
                attributes.remove(0);
            }
        }
        if (attributes.size() != values.size()) {
            throw new DataBaseException("Numarul de atribute (" + attributes.size() + ") nu corespunde cu numarul de valori (" + values.size() + ") precizate");
        }
        for (String attribute : attributes) {
            query += attribute + ", ";
        }
        query = query.substring(0, query.length() - 2);
        query += ") VALUES (";
        query = values.stream().map((currentValue) -> (Utilities.isSystemFunction(currentValue)?currentValue:("\'" + currentValue + "\'")) + ",").reduce(query, String::concat);
        query = query.substring(0, query.length() - 1);
        query += ")";
        if (Constants.DEBUG) {
            System.out.println("query: " + query);
        }
        result = statement.executeUpdate(query);
        destroyStatement(statement);
        return result;
    }

    @Override
    public int updateRecordsIntoTable(String tableName, ArrayList<String> attributes, ArrayList<String> values, String whereClause) throws SQLException, DataBaseException {
        int result = -1;
        openConnection();
        Statement statement = createStatement();
        String query = "UPDATE " + tableName + " SET ";
        if (attributes == null) {
            attributes = getTableColumns(tableName);
        }
        if (attributes.size() != values.size()) {
            throw new DataBaseException("Numarul de atribute (" + attributes.size() + ") nu corespunde cu numarul de valori (" + values.size() + ") precizate");
        }
        for (int currentIndex = 0; currentIndex < values.size(); currentIndex++) {
            query += attributes.get(currentIndex) + "=\'" + values.get(currentIndex) + "\', ";
        }
        query = query.substring(0, query.length() - 2);
        query += " WHERE ";
        if (whereClause != null) {
            query += whereClause;
        } else {
            query += getTablePrimaryKey(tableName) + "=\'" + values.get(0) + "\'";
        }
        if (Constants.DEBUG) {
            System.out.println("query: " + query);
        }
        result = statement.executeUpdate(query);
        destroyStatement(statement);
        return result;
    }

    @Override
    public int deleteRecordsFromTable(String tableName, ArrayList<String> attributes, ArrayList<String> values, String whereClause) throws SQLException, DataBaseException {
        int result = -1;
        openConnection();
        Statement statement = createStatement();
        String query = "DELETE FROM " + tableName + " WHERE ";
        if (whereClause != null) {
            query += whereClause;
        } else {
            if (attributes.size() != values.size()) {
                throw new DataBaseException("Numarul de atribute (" + attributes.size() + ") nu corespunde cu numarul de valori (" + values.size() + ") precizate");
            }
            for (int currentIndex = 0; currentIndex < values.size(); currentIndex++) {
                query += attributes.get(currentIndex) + "=\'" + values.get(currentIndex) + "\' AND ";
            }
            query = query.substring(0, query.length() - 4);
        }
        if (Constants.DEBUG) {
            System.out.println("query: " + query);
        }
        result = statement.executeUpdate(query);
        destroyStatement(statement);
        return result;
    }

    @Override
    public String executeProcedure(String procedureName, ArrayList<String> parameterTypes, ArrayList<String> parameterValues, ArrayList<Integer> parameterDataTypes) throws SQLException {
        String result = new String();
        int resultIndex = -1;
        openConnection();
        String query = "{CALL " + procedureName + "(";
        int parameterNumber = parameterTypes.size();
        for (int count = 1; count <= parameterNumber; count++) {
            query += "?, ";
        }
        if (parameterNumber != 0) {
            query = query.substring(0, query.length() - 2);
        }
        query += ")}";
        CallableStatement statement = dbConnection.prepareCall(query);
        int i = 0, j = 0, k = 1;
        for (String parameterType : parameterTypes) {
            switch (parameterType) {
                case "IN":
                    statement.setString(k, parameterValues.get(i++));
                    break;
                case "OUT":
                    statement.registerOutParameter(k, parameterDataTypes.get(j++).intValue());
                    resultIndex = k;
                    break;
                case "INOUT":
                    statement.setString(k, parameterValues.get(i++));
                    statement.registerOutParameter(k, parameterDataTypes.get(j++).intValue());
                    resultIndex = k;
                    break;
            }
            k++;
        }
        statement.execute();
        result = statement.getString(resultIndex);
        statement.close();
        return result;
    }

    @Override
    public ArrayList<Referrence> getReferrences(String tableName) throws SQLException {
        ArrayList<Referrence> referrences = new ArrayList<>();
        openConnection();
        ResultSet result = dbMetaData.getImportedKeys(Constants.DATABASE_NAME, null, tableName);
        while (result.next()) {
            referrences.add(new Referrence(result.getString("PKTABLE_NAME"), result.getString("FKTABLE_NAME"), result.getString("PKCOLUMN_NAME"), result.getString("FKCOLUMN_NAME")));
        }
        return referrences;
    }

    @Override
    public String getForeignKeyParentTable(String childTableName, String childAttribute) throws SQLException {
        ArrayList<Referrence> referrences = getReferrences(childTableName);
        for (Referrence referrence : referrences) {
            if (childAttribute.equals(referrence.getChildAttributeName())) {
                return referrence.getParentTable();
            }
        }
        return null;
    }

    @Override
    public String getForeignKeyParentAttribute(String childTableName, String childAttribute) throws SQLException {
        ArrayList<Referrence> referrences = getReferrences(childTableName);
        for (Referrence referrence : referrences) {
            if (childAttribute.equals(referrence.getChildAttributeName())) {
                return referrence.getParentAttributeName();
            }
        }
        return null;
    }

    @Override
    public String getForeignKeyValue(String childTableName, String childTableAttribute, ArrayList<String> parentTableValues) throws SQLException {
        String parentTable = getForeignKeyParentTable(childTableName, childTableAttribute);
        if (parentTable != null) {
            ArrayList<String> parentTableAttributes = getTableColumns(parentTable);
            int numberOfParentTableAttributes = parentTableAttributes.size();
            String whereClause = parentTableAttributes.get(0) + "=\'" + parentTableValues.get(0) + "\'";
            for (int position = 1; position < numberOfParentTableAttributes; position++) {
                whereClause += " AND " + parentTableAttributes.get(position) + "=\'" + parentTableValues.get(position).trim() + "\'";
            }
            ArrayList<ArrayList<String>> parentTablePrimaryKey = getTableContent(parentTable,
                    getTablePrimaryKeys(parentTable),
                    whereClause,
                    null,
                    null);
            return parentTablePrimaryKey.get(0).get(0);
        }
        return null;
    }

    @Override
    public void runScript(String fileName) throws SQLException {
        openConnection();
        Charset charset = Charset.forName("UTF-8");
        try (BufferedReader bufferedReader = Files.newBufferedReader(Paths.get(fileName), charset)) {
            String currentLine;
            StringBuffer command = new StringBuffer();
            boolean insideStoredProcedure = false, insideComment = false;
            while ((currentLine = bufferedReader.readLine()) != null) {
                if ((!insideComment && currentLine.startsWith("/*")) || (insideComment && currentLine.endsWith("*/"))) {
                    insideComment = !insideComment;
                    continue;
                }
                if (currentLine == null || currentLine.isEmpty() || currentLine.startsWith("--")) {
                    continue;
                }
                if (currentLine.endsWith("//")) {
                    insideStoredProcedure = !insideStoredProcedure;
                    if (!insideStoredProcedure) {
                        command.append(currentLine.substring(0, currentLine.indexOf("//")).trim()).append(" ");
                    }
                } else {
                    command.append(currentLine.trim()).append(" ");
                }
                if ((currentLine.endsWith(";") || currentLine.endsWith("//")) && !insideStoredProcedure) {
                    if (Constants.DEBUG) {
                        System.out.println("Executing command: " + command.toString());
                    }
                    Statement statement = null;
                    try {
                        statement = createStatement();
                        statement.execute(command.toString());
                        if (Constants.DEBUG) {
                            System.out.println("Command has been executed successfully");
                        }
                    } catch (SQLException sqlException) {
                        System.out.println("Command has not been executed successfully: " + sqlException.getMessage());
                        if (Constants.DEBUG) {
                            sqlException.printStackTrace();
                        }
                    } finally {
                        if (statement != null) {
                            destroyStatement(statement);
                        }
                    }
                    command = new StringBuffer();
                }
            }
        } catch (Exception exception) {
            System.out.println("An exception has occured: " + exception.getMessage());
            if (Constants.DEBUG) {
                exception.printStackTrace();
            }
        }
    }

}
