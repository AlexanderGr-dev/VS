package service;

import com.hazelcast.core.*;
import com.hazelcast.mapreduce.Job;
import com.hazelcast.mapreduce.JobTracker;
import com.hazelcast.mapreduce.KeyValueSource;
import de.othr.vs.xml.Student;
import de.othr.vs.xml.Veranstaltung;
import jakarta.ws.rs.*;
import mapreduce.TippCollator;
import mapreduce.TippMapper;
import mapreduce.TippReducerFactory;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Path("studentaffairs")
public class VeranstaltungsService {

    private static HazelcastInstance hazelcastInstance = Hazelcast.newHazelcastInstance();

    private static IAtomicLong nextVeranstaltungId = hazelcastInstance.getAtomicLong("veranstaltung_id");
    private static IMap<String, Veranstaltung> veranstaltungen = hazelcastInstance.getMap( "veranstaltungen" );

    @POST
    @Path("events")
    public Veranstaltung veranstalten(Veranstaltung v) {
        v.setId(UUID.randomUUID().toString());
        veranstaltungen.put(v.getId(),v);
        //studentDb.put(s.getMatrikelNr(),s);

        return v;
    }

    @GET
    @Path("events/{id}")
    public Veranstaltung getStudentById(@PathParam("id") String veranstaltungId){
        Veranstaltung v = veranstaltungen.get(veranstaltungId);
        return v;
    }

    @GET
    @Path("events")
    public List<Veranstaltung> getStudentById(@QueryParam("search")String[] suchwoerter){
        JobTracker jobTracker = hazelcastInstance.getJobTracker( "default" );

        KeyValueSource<String, String> source = KeyValueSource.fromMap( veranstaltungen );
        Job<String, String> job = jobTracker.newJob( source );

        ICompletableFuture<Map<String, Long>> future = job
                .mapper( new TippMapper(suchwoerter) )
                .combiner( new TippCollator() )
                .reducer( new TippReducerFactory() )
                .submit();
    }
}
