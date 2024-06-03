package de.hitec.nhplus.datastorage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The DaoImp class is an abstract class for Data Access Object (DAO) classes.
 * It provides CRUD operations for the given object.
 * @param <T> Object to be persisted
 */
public abstract class DaoImp<T> implements Dao<T> {
    protected Connection connection;

    /**
     * The constructor initializes the DaoImp with a database connection.
     * @param connection the database connection
     */
    public DaoImp(Connection connection) {
        this.connection = connection;
    }

    /**
     * Creates a new object in the database.
     * @param t the object to create
     * @throws SQLException if an error occurs while creating the object
     */
    @Override
    public void create(T t) throws SQLException {
        getCreateStatement(t).executeUpdate();
    }

    /**
     * Reads an object from the database by its ID (Primary Key).
     * @param key the ID of the object
     * @return the object with the given ID
     * @throws SQLException if an error occurs while reading the object
     */
    @Override
    public T read(long key) throws SQLException {
        T object = null;
        ResultSet result = getReadByIDStatement(key).executeQuery();
        if (result.next()) {
            object = getInstanceFromResultSet(result);
        }
        return object;
    }

    /**
     * Reads all objects from the database.
     * @return a list of all objects
     * @throws SQLException if an error occurs while reading the objects
     */
    @Override
    public List<T> readAll() throws SQLException {
        return getListFromResultSet(getReadAllStatement().executeQuery());
    }

    /**
     * Updates an object in the database.
     * @param t the object to update
     * @throws SQLException if an error occurs while updating the object
     */
    @Override
    public void update(T t) throws SQLException {
        getUpdateStatement(t).executeUpdate();
    }

    /**
     * Deletes an object from the database by its ID (Primary Key).
     * @param key the ID of the object
     * @throws SQLException if an error occurs while deleting the object
     */
    @Override
    public void deleteById(long key) throws SQLException {
        getDeleteStatement(key).executeUpdate();
    }

    /**
     * Returns an object from a ResultSet.
     * @param set the ResultSet to get the object from
     * @return an object from a ResultSet
     * @throws SQLException if an error occurs while getting the object from the ResultSet
     */
    protected abstract T getInstanceFromResultSet(ResultSet set) throws SQLException;

    /**
     * Returns a list of objects from a ResultSet.
     * @param set the ResultSet to get the objects from
     * @return a list of objects from a ResultSet
     * @throws SQLException if an error occurs while getting the objects from the ResultSet
     */
    protected abstract ArrayList<T> getListFromResultSet(ResultSet set) throws SQLException;

    /**
     * Returns a PreparedStatement to create a new object in the database.
     * @param t the object to create
     * @return a PreparedStatement to create a new object in the database
     */
    protected abstract PreparedStatement getCreateStatement(T t);

    /**
     * Returns a PreparedStatement to read an object from the database by its ID (Primary Key).
     * @param key the ID of the object
     * @return a PreparedStatement to read an object from the database by its ID (Primary Key)
     */
    protected abstract PreparedStatement getReadByIDStatement(long key);

    /**
     * Returns a PreparedStatement to read all objects from the database.
     * @return a PreparedStatement to read all objects from the database
     */
    protected abstract PreparedStatement getReadAllStatement();

    /**
     * Returns a PreparedStatement to update an object in the database.
     * @param t the object to update
     * @return a PreparedStatement to update an object in the database
     */
    protected abstract PreparedStatement getUpdateStatement(T t);

    /**
     * Returns a PreparedStatement to delete an object from the database by its ID (Primary Key).
     * @param key the ID of the object
     * @return a PreparedStatement to delete an object from the database by its ID (Primary Key)
     */
    protected abstract PreparedStatement getDeleteStatement(long key);
}
