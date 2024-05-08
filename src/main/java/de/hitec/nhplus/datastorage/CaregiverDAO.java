package de.hitec.nhplus.datastorage;

import de.hitec.nhplus.model.Caregiver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CaregiverDAO extends DaoImp<Caregiver> {


    public CaregiverDAO(Connection connection) {
        super(connection);
    }

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
            preparedStatement.setLong(5, caregiver.getCid());
            preparedStatement.setString(6, caregiver.getDateCreated());
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return preparedStatement;
    }

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
