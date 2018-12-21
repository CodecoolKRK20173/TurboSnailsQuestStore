import com.codecool.quest_store.dao.ArtifactDAO;
import com.codecool.quest_store.dao.InventoryDAO;
import com.codecool.quest_store.dao.InventoryDAOimpl;
import com.codecool.quest_store.model.Artifact;
import com.codecool.quest_store.model.Item;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import com.codecool.quest_store.dao.DBConnector;

import java.security.spec.ECField;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.jooq.tools.jdbc.*;


public class InventoryDAOtest {

   @Mock
   DBConnector connectorMock = mock(DBConnector.class);
   Connection connectionMock = mock(Connection.class);
   PreparedStatement statementMock = mock(PreparedStatement.class);
   ResultSet resultMock = mock(ResultSet.class);



   @Test
    public void testIfSetOfIdToQueryWillBeInvoke() throws SQLException{
       String query = "SELECT * FROM artifact INNER JOIN student_artifact ON" +
               " artifact.id = student_artifact.artifact_id WHERE student_artifact.student_id = ?";
      when(connectorMock.getConnection()).thenReturn(connectionMock);
      when(connectionMock.prepareStatement(query)).thenReturn(statementMock);
      when(statementMock.executeQuery()).thenReturn(resultMock);

       int existingId = 5;

       InventoryDAOimpl dao = new InventoryDAOimpl(connectorMock);

       dao.getListOfItemsByUserId(existingId);
       verify(statementMock).setInt(1,existingId);
   }


   @Test
    public void checkIfGetListOfItemsByUserIdReturnCorrectCountOfItemsInList() throws SQLException {
       String query = "SELECT * FROM artifact INNER JOIN student_artifact ON" +
               " artifact.id = student_artifact.artifact_id WHERE student_artifact.student_id = ?";
       when(connectorMock.getConnection()).thenReturn(connectionMock);
       when(connectionMock.prepareStatement(query)).thenReturn(statementMock);
       when(statementMock.executeQuery()).thenReturn(resultMock);
      when(resultMock.next()).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false);

       String nameExample = "ArtifactNameExample";
       String descriptionExample = "DescriptionExample";
       String typeExample = "artifactTypeExample";
       int accessLevelExample = 5;
       int priceExample = 50;
       int artifactExampleId = 99;

       int exampleOfExistingId = 5;
       int countOfStudentArtifacts = 4;

      when(resultMock.getInt("id")).thenReturn(artifactExampleId);
      when(resultMock.getInt("access_level")).thenReturn(accessLevelExample);
      when(resultMock.getString("title")).thenReturn(nameExample);
      when(resultMock.getString("description")).thenReturn(descriptionExample);
      when(resultMock.getInt("artifact_price")).thenReturn(priceExample);
      when(resultMock.getString("artifact_type")).thenReturn(typeExample);

      List<Item> expectedInventory = new ArrayList<>();
      Artifact stubArtifact = new Artifact(artifactExampleId,accessLevelExample,nameExample,descriptionExample,priceExample,typeExample);
      for (int i = 0; i < countOfStudentArtifacts ; i++) {
          expectedInventory.add(stubArtifact);
      }

       InventoryDAOimpl dao = new InventoryDAOimpl(connectorMock);
      assertEquals(expectedInventory.size(), dao.getListOfItemsByUserId(exampleOfExistingId).size());
   }



}
