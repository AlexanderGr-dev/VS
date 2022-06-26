package service;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IAtomicLong;
import com.hazelcast.core.IMap;

import jakarta.ws.rs.*;

import java.sql.*;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import de.othr.vs.xml.*;

@Path("studentaffairs")
public class StudentService {

    private static AtomicInteger nextStudentId = new AtomicInteger(1);

    private static ConcurrentMap<Integer, Student> studentDb = new ConcurrentHashMap<>();  // kann als interne Datenbank verwendet werden

    private static HazelcastInstance hazelcastInstance = Hazelcast.newHazelcastInstance();

    private static IAtomicLong nextStudenId = hazelcastInstance.getAtomicLong("student_id");
    private static IMap<Integer, Student> students = hazelcastInstance.getMap( "students" );


    @POST
    @Path("students")
    public Student matriculate(Student s) {
        s.setMatrikelNr(nextStudentId.incrementAndGet());
        students.put(s.getMatrikelNr(),s,5, TimeUnit.MINUTES);
        //studentDb.put(s.getMatrikelNr(),s);

       return s;
    }

    @DELETE
    @Path("students/{studentId}")
    public Student exmatriculate(@PathParam("studentId") int studentId) {
        Student old;
        old = studentDb.get(studentId);
        studentDb.remove(studentId,old);

        return old;
    }

    @GET
    @Path("students/{studentId}")
    public Student getStudentById(@PathParam("studentId") int studentId) throws SQLException {
        Connection c = DriverManager.getConnection("jdbc:mysql://im-vm-011:3306/vs-08?enabledTLSProtocols=TLSv1.2","vs-08","vs-08-pw");
        Statement stmt = c.createStatement();
        String query = "SELECT * FROM Student WHERE matrikelNR = " + studentId;
        ResultSet rs = stmt.executeQuery(query);
        Student s = new Student();

        while(rs.next()){

            int matrikelNr = rs.getInt("matrikelNr");
            String vorname = rs.getString("vorname");
            String nachname = rs.getString("nachname");
            int ects = rs.getInt("ects");
            String strasse = rs.getString("strasse");
            String ort = rs.getString("ort");

            s.setMatrikelNr(matrikelNr);
            s.setVorname(vorname);
            s.setNachname(nachname);
            s.setEcts(ects);
            s.setAnschrift(new Adresse(strasse,null,ort));
        }

        c.close();

        //Student s = studentDb.get(studentId);

       return s;
    }

    @PUT
    @Path("students/{studentId}")
    public Student updateStudentAccount(@PathParam("studentId") int studentId, Student newData) {
        Student updated;
        studentDb.replace(studentId,newData);
        updated = studentDb.get(studentId);

        return updated;

    }

    //@GET
    //@Path("students")
    public Collection<Student> getAllStudents() {

        return studentDb.values();

    }

    @GET
    @Path("students")
    public Collection<Student> getStudentsByRange(@QueryParam("from") int fromStudentId,@QueryParam("to") int toStudentId) {

       /* Collection<Student> list = new ArrayList<>();
        boolean found = false;
        for (Integer id: studentDb.keySet()){
                if (id == fromStudentId) {
                    list.add(studentDb.get(id));
                    found = true;
                    break;
                }
                if (found){
                    if(id == toStudentId){
                        list.add(studentDb.get(id));
                        found = false;
                        break;
                    }
                    list.add(studentDb.get(id));
                }
        }

        return list;*/

        if(fromStudentId == 0 && toStudentId == 0)
            return getAllStudents();
        else if(toStudentId == 0 && fromStudentId > 0)
            return studentDb.values()
                    .stream()
                    .filter( student ->
                            student.getMatrikelNr() >= fromStudentId)
                    .collect(Collectors.toSet());
        else
            return studentDb.values()
                    .stream()
                    .filter( student ->
                            student.getMatrikelNr() >= fromStudentId
                                    && student.getMatrikelNr() <= toStudentId)
                    .collect(Collectors.toSet());


    }
}
