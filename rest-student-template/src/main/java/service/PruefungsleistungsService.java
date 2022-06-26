package service;


import entity.Prüfungslesitung;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.sql.*;

@Path("leistung")
public class PruefungsleistungsService {

    @POST
    @Path("pruefungsleistungen")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Prüfungslesitung getStudentById(Prüfungslesitung p) throws SQLException {
        Connection c = DriverManager.getConnection("jdbc:mysql://im-vm-011:3306/vs-08?enabledTLSProtocols=TLSv1.2","vs-08","vs-08-pw");
        Statement stmt = c.createStatement();
        c.setAutoCommit(false);
        String query1 = "INSERT INTO Pruefungsleistung VALUES ('" + p.getId() + "','" + p.getPruefungId() +"','" + p.getMatrikelNr() + "','" + p.getVersuch() + "','" + p.getNote() +"')";
        stmt.executeUpdate(query1);

        int ects =0;
        String query2 = "SELECT ects FROM Pruefung WHERE pruefungId = '" + p.getPruefungId()+"'";
        ResultSet rs = stmt.executeQuery(query2);
        while(rs.next()) {
            ects = rs.getInt("ects");
        }

        String query3 = "UPDATE Student SET ects = ects + " +ects+" WHERE matrikelNr = " + p.getMatrikelNr();
        stmt.executeUpdate(query3);
        c.commit();

        return p;
    }
}
