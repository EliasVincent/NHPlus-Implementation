package de.hitec.nhplus.datastorage;

import java.sql.SQLException;
import java.util.List;

/**
 * The Data Access Object (DAO) interface.
 * @param <T> Object to be persisted
 */
public interface Dao<T> {
    /**
     * Creates a new object in the database.
     * @param t the object to create
     * @throws SQLException if an error occurs while creating the object
     */
    void create(T t) throws SQLException;

    /**
     * Reads an object from the database by its ID (Primary Key).
     * @param key the ID of the object
     * @return the object with the given ID
     * @throws SQLException if an error occurs while reading the object
     */
    T read(long key) throws SQLException;

    /**
     * Reads all objects from the database.
     * @return a list of all objects
     * @throws SQLException if an error occurs while reading the objects
     */
    List<T> readAll() throws SQLException;

    /**
     * Updates an object in the database.
     * @param t the object to update
     * @throws SQLException if an error occurs while updating the object
     */
    void update(T t) throws SQLException;

    /**
     * Deletes an object from the database by its ID (Primary Key).
     * @param key the ID of the object
     * @throws SQLException if an error occurs while deleting the object
     */
    void deleteById(long key) throws SQLException;
}
