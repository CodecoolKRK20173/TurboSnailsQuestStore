package com.codecool.quest_store.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LevelsDAOImpl implements LevelsDAO {
    private Connection connection;

    public LevelsDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public int getStudentLevel(int studentId) {
        int level = 0;
        String query = "SELECT level_id FROM access_level " +
                "INNER JOIN wallet " +
                "ON wallet.lifetime_coins <= access_level.max_lifetime_coins " +
                "AND wallet.lifetime_coins >= access_level.min_lifetime_coins " +
                "WHERE wallet.student_id = ?;";

        PreparedStatement preparedStatement;
        ResultSet resultSet;

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, studentId);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                level = resultSet.getInt("level_id");
            }

            preparedStatement.close();
            resultSet.close();
        } catch (SQLException e) {
            System.err.println("Can't get student's level!");
            e.printStackTrace();
        }
        return level;
    }

    @Override
    public void updateLevel(int levelId, int minCoolcoinsAmount, int maxCoolcoinsAmount) {
        String query = "UPDATE access_level " +
                "SET min_lifetime_coins = ?, " +
                "max_lifetime_coins = ? " +
                "WHERE level_id =?;";
        PreparedStatement preparedStatement;

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, minCoolcoinsAmount);
            preparedStatement.setInt(2, maxCoolcoinsAmount);
            preparedStatement.setInt(3, levelId);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            System.err.println("Can't update access level!");
            e.printStackTrace();

        }
    }
}