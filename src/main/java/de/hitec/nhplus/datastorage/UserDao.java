package de.hitec.nhplus.datastorage;

import de.hitec.nhplus.model.User;

import java.sql.*;
import java.util.ArrayList;

/**
 * A data access object for interacting with the `User` entity.
 * This class provides CRUD operations using prepared SQL statements.
 */
public class UserDao extends DaoImp<User> {

    /**
     * The constructor initializes an instance of UserDao with the given database connection.
     *
     * @param connection the database connection used to execute SQL statements.
     */
    public UserDao(Connection connection) {
        super(connection);
    }

    /**
     * Generates a PreparedStatement to insert a new user into the database.
     *
     * @param user the user object to be persisted.
     * @return the prepared statement to insert the user.
     */
    @Override
    protected PreparedStatement getCreateStatement(User user) {
        String sql = "INSERT INTO user (email, password, status) VALUES (?, ?, ?)";
        try {
            PreparedStatement statement = this.connection.prepareStatement(sql);
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getPassword());
            statement.setInt(3, user.getStatus());
            return statement;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    /**
     * Generates a PreparedStatement to query a user by their unique identifier.
     *
     * @param id the unique identifier of the user.
     * @return the prepared statement to retrieve the user by ID.
     */
    @Override
    protected PreparedStatement getReadByIDStatement(long id) {
        String sql = "SELECT * FROM user WHERE id = ?";
        try {
            PreparedStatement statement = this.connection.prepareStatement(sql);
            statement.setLong(1, id);
            return statement;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    /**
     * Maps a ResultSet to a User object.
     *
     * @param result the result set containing user data.
     * @return a user object.
     * @throws SQLException if there's an error during the result set mapping.
     */
    @Override
    protected User getInstanceFromResultSet(ResultSet result) throws SQLException {
        return new User(
                result.getInt("id"),
                result.getString("email"),
                result.getString("password"),
                result.getInt("status")
        );
    }

    /**
     * Generates a PreparedStatement to retrieve all users.
     *
     * @return a prepared statement to query all users.
     */
    @Override
    protected PreparedStatement getReadAllStatement() {
        String sql = "SELECT * FROM user";
        try {
            return this.connection.prepareStatement(sql);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    /**
     * Maps a ResultSet to a list of User objects.
     *
     * @param result the result set containing user data.
     * @return a list of user objects.
     * @throws SQLException if there's an error during the result set mapping.
     */
    @Override
    protected ArrayList<User> getListFromResultSet(ResultSet result) throws SQLException {
        ArrayList<User> list = new ArrayList<>();
        while (result.next()) {
            User user = new User(
                    result.getInt("id"),
                    result.getString("email"),
                    result.getString("password"),
                    result.getInt("status")
            );
            list.add(user);
        }
        return list;
    }

    /**
     * Generates a PreparedStatement to update an existing user by their unique identifier.
     *
     * @param user the user object with updated data.
     * @return a prepared statement to update the user.
     */
    @Override
    protected PreparedStatement getUpdateStatement(User user) {
        String sql = "UPDATE user SET email = ?, password = ?, status = ? WHERE id = ?";
        try {
            PreparedStatement statement = this.connection.prepareStatement(sql);
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getPassword());
            statement.setInt(3, user.getStatus());
            statement.setLong(4, user.getId());
            return statement;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    /**
     * Generates a PreparedStatement to delete a user by their unique identifier.
     *
     * @param id the unique identifier of the user to delete.
     * @return a prepared statement to delete the user by ID.
     */
    @Override
    protected PreparedStatement getDeleteStatement(long id) {
        String sql = "DELETE FROM user WHERE id = ?";
        try {
            PreparedStatement statement = this.connection.prepareStatement(sql);
            statement.setLong(1, id);
            return statement;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }
}
