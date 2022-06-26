package mapreduce;

import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;
import de.othr.vs.xml.Veranstaltung;

public class TippMapper implements Mapper<String, Veranstaltung, String, Veranstaltung> {

    public TippMapper(String[] suchwoerter){

    }

    @Override
    public void map(String s, Veranstaltung veranstaltung, Context<String, Veranstaltung> context) {

    }
}
