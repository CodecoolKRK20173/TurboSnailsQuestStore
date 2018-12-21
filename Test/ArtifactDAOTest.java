import com.codecool.quest_store.dao.ArtifactDAO;
import com.codecool.quest_store.model.Item;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import com.codecool.quest_store.dao.DBConnector;

import java.security.spec.ECField;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;


public class ArtifactDAOTest {

    @Mock
    DBConnector connectorMock;
    Connection connectionMock = mock(Connection.class);
    PreparedStatement statementMock = mock(PreparedStatement.class);
    ResultSet resultMock = mock(ResultSet.class);


    @BeforeEach
    public void init() {
        connectorMock = mock(DBConnector.class);
    }



    @Test
    public void addMethodTestIfItemtWillBeNull() throws SQLException{
        String query = "INSERT INTO quest ( ID,access_level,title,description,artifact_price,artifact_type ) " +
                "VALUES(?,?,?,?,?,?)";
        when(connectorMock.getConnection()).thenReturn(connectionMock);
        when(connectionMock.prepareStatement(query)).thenReturn(statementMock);
        when(statementMock.executeQuery()).thenReturn(resultMock);
        ArtifactDAO artifactDAO = new ArtifactDAO(connectorMock);

        assertThrows(NullPointerException.class,()->artifactDAO.add(null));
    }






}
