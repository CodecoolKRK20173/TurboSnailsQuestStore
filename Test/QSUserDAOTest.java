import com.codecool.quest_store.dao.ArtifactDAO;
import com.codecool.quest_store.dao.DBConnector;
import com.codecool.quest_store.dao.QSUserDAO;
import com.codecool.quest_store.model.Person;
import com.codecool.quest_store.model.QSUser;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class QSUserDAOTest {

    @Mock
    DBConnector connectorMock;
    Connection connectionMock;
    PreparedStatement statementMock;
    ResultSet resultMock;


    @BeforeEach
    public void init() {
        connectorMock = mock(DBConnector.class);
        connectionMock = mock(Connection.class);
        statementMock = mock(PreparedStatement.class);
        resultMock = mock(ResultSet.class);

    }

    @Test
    public void insertMethodTestIfPersonWillBeNullThrowNullPointerExc() {

        QSUserDAO dao = new QSUserDAO(connectorMock);

        assertThrows(NullPointerException.class, ()->dao.insert(null));
    }

    @Test
    public void checkIfDeleteUserByIdInvokeExecuteUpdate() throws SQLException {
        when(connectorMock.getConnection()).thenReturn(connectionMock);
        String query = "DELETE FROM qs_user WHERE id=?";
        when(connectionMock.prepareStatement(query)).thenReturn(statementMock);

        QSUserDAO dao = new QSUserDAO(connectorMock);
        dao.delete(2);
        verify(statementMock).executeUpdate();
    }



    @Test
    public void checkIfGetPersonByExistingIdReturnPerson() throws SQLException, AssertionError {

        int exampleId = 1;
        String exampleName = "John";
        String exampleSurname = "Terry";
        String exampleMail = "d@com";
        String exampleClassName = "1D";
        String exampleuserType = "Mentor";
        String examplestatus = "status";

        String query = "SELECT qs_user.id, first_name, last_name, email, class_.name AS class_name, " +
                "user_type.user_type_name AS user_type, " +
                "user_status.user_status_name AS STATUS " +
                "FROM qs_user " +
                "INNER JOIN class_ ON qs_user.class_id = class_.class_id " +
                "INNER JOIN user_type ON qs_user.user_type = user_type.user_type_id " +
                "INNER JOIN user_status ON qs_user.STATUS = user_status.user_status_id " +
                "WHERE qs_user.id = ?";

        when(connectorMock.getConnection()).thenReturn(connectionMock);
        when(connectionMock.prepareStatement(query)).thenReturn(statementMock);
        when(statementMock.executeQuery()).thenReturn(resultMock);
        when(resultMock.next()).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false);


        when(resultMock.getInt("id")).thenReturn(exampleId);
        when(resultMock.getString("first_name")).thenReturn(exampleName);
        when(resultMock.getString("last_name")).thenReturn(exampleSurname);
        when(resultMock.getString("email")).thenReturn(exampleMail);
        when(resultMock.getString("class_name")).thenReturn(exampleClassName);
        when(resultMock.getString("user_type")).thenReturn(exampleuserType);
        when(resultMock.getString("status")).thenReturn(examplestatus);

        QSUser expectedUser = new QSUser(exampleId, exampleName, exampleSurname, exampleMail, exampleClassName, exampleuserType, examplestatus);

        QSUserDAO dao = new QSUserDAO(connectorMock);


//        Assert.assertTrue(EqualsBuilder.reflectionEquals(expectedUser,dao.getPersonById(exampleId)));
        Assert.assertTrue(expectedUser.equals(dao.getPersonById(exampleId)));
    }

}
