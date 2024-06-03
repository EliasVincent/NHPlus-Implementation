package de.hitec.nhplus.datastorage;

import de.hitec.nhplus.model.Caregiver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * The CaregiverDAO class is a Data Access Object (DAO) class for the Caregiver model.
 * It provides CRUD operations for the Caregiver model.
 */
public class CaregiverDAO extends DaoImp<Caregiver> {


    /**
     * The constructor initializes the CaregiverDAO with a database connection.
     * @param connection the database connection
     */
    public CaregiverDAO(Connection connection) {
        super(connection);
    }

    /**
     * Returns a PreparedStatement to create a new Caregiver in the database.
     * @param caregiver the Caregiver to create
     * @return a PreparedStatement to create a new Caregiver in the database
     */
    @Override
    protected PreparedStatement getCreateStatement(Caregiver caregiver) {
        PreparedStatement preparedStatement = null;
        try {
            final String SQL = "INSERT INTO caregiver (firstname, surname, phonenumber, locked, datecreated) " +
                    "VALUES (?, ?, ?, ?, ?)";
            preparedStatement = this.connection.prepareStatement(SQL);
            preparedStatement.setString(1, caregiver.getFirstName());
            preparedStatement.setString(2, caregiver.getSurname());
            preparedStatement.setString(3, caregiver.getPhoneNumber());
            preparedStatement.setBoolean(4, caregiver.isLocked());
            preparedStatement.setString(5, caregiver.getDateCreated());
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return preparedStatement;
    }

    /**
     * Returns a PreparedStatement to read a Caregiver from the database by its ID.
     * @param cid the ID of the Caregiver
     * @return a PreparedStatement to read a Caregiver from the database by its ID
     */
    @Override
    protected PreparedStatement getReadByIDStatement(long cid) {
        PreparedStatement preparedStatement = null;
        try {
            final String SQL = "SELECT * FROM caregiver WHERE cid = ?";
            preparedStatement = this.connection.prepareStatement(SQL);
            preparedStatement.setLong(1, cid);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return preparedStatement;
    }

    /**
     * Returns a Caregiver instance from a ResultSet.
     * @param resultSet the ResultSet to get the Caregiver from
     * @return a Caregiver from a ResultSet
     */
    @Override
    protected Caregiver getInstanceFromResultSet(ResultSet resultSet) throws SQLException{
        return new Caregiver(
                resultSet.getInt(1),
                resultSet.getString(2),
                resultSet.getString(3),
                resultSet.getString(4),
                resultSet.getBoolean(5),
                resultSet.getString(6));
    }

    /**
     * Returns a PreparedStatement to read all Caregivers from the database.
     * @return a PreparedStatement to read all Caregivers from the database
     */
    @Override
    protected PreparedStatement getReadAllStatement() {
        PreparedStatement preparedStatement = null;
        try {
            final String SQL = "SELECT * FROM caregiver";
            preparedStatement = this.connection.prepareStatement(SQL);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return preparedStatement;
    }

    /**
     * Returns an ArrayList of Caregivers from a ResultSet.
     * @param result the ResultSet to get the Caregivers from
     * @return an ArrayList of Caregivers from a ResultSet
     */
    @Override
    protected ArrayList<Caregiver> getListFromResultSet(ResultSet result) throws SQLException {
        ArrayList<Caregiver> caregivers = new ArrayList<>();
        while (result.next()) {
            caregivers.add(new Caregiver(
                    result.getInt(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(4),
                    result.getBoolean(5),
                    result.getString(6)));
        }
        return caregivers;
    }

    /**
     * Returns a PreparedStatement to update a Caregiver in the database.
     * @param caregiver the Caregiver to update
     * @return a PreparedStatement to update a Caregiver in the database
     */
    @Override
    protected PreparedStatement getUpdateStatement(Caregiver caregiver) {
        PreparedStatement preparedStatement = null;
        try {
            final String SQL = "UPDATE caregiver SET firstname = ?, surname = ?, phonenumber = ?, locked = ?, datecreated = ? WHERE cid = ?";
            preparedStatement = this.connection.prepareStatement(SQL);
            preparedStatement.setString(1, caregiver.getFirstName());
            preparedStatement.setString(2, caregiver.getSurname());
            preparedStatement.setString(3, caregiver.getPhoneNumber());
            preparedStatement.setBoolean(4, caregiver.isLocked());
            preparedStatement.setString(5, caregiver.getDateCreated());
            preparedStatement.setLong(6, caregiver.getCid());
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return preparedStatement;
    }

    /**
     * Returns a PreparedStatement to delete a Caregiver from the database.
     * @param cid the ID of the Caregiver to delete
     * @return a PreparedStatement to delete a Caregiver from the database
     */
    @Override
    protected PreparedStatement getDeleteStatement(long cid) {
        PreparedStatement preparedStatement = null;
        try {
            final String SQL = "DELETE FROM caregiver WHERE cid = ?";
            preparedStatement = this.connection.prepareStatement(SQL);
            preparedStatement.setLong(1, cid);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return preparedStatement;
    }
}
