package com.codecool.quest_store.dao;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CreepyGuyDAOimplTest {

    @Mock
    private DBConnector dbConnector = mock(DBConnector.class);
    private Connection mockConnection = mock(Connection.class);
    private PreparedStatement mockStatement = mock(PreparedStatement.class);
    private ResultSet resultSetMock = mock(ResultSet.class);
    CreepyGuyDAOimpl creepyGuyDAOimpl = new CreepyGuyDAOimpl(dbConnector);

    @Test
    public void testIfCreateClassThrowsException() {
        when(dbConnector.getConnection()).thenReturn(mockConnection);
        assertThrows(IllegalArgumentException.class, ()->{creepyGuyDAOimpl.createClass("");
        });

    }

}