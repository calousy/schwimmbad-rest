import de.codeforgarching.schwimmbad.SchwimmbadRestApp;
import org.influxdb.InfluxDB;
import org.influxdb.dto.QueryResult;
import org.influxdb.querybuilder.SelectQueryImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.influxdb.querybuilder.BuiltQuery.QueryBuilder.desc;
import static org.influxdb.querybuilder.BuiltQuery.QueryBuilder.select;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SchwimmbadRestApp.class)
public class EnvTest {

    @Autowired
    private InfluxDB _influxDB;

    @Test
    public void test()
    {
        SelectQueryImpl query = select().from("garching_schwimmbad", "Springerbecken").limit(1).orderBy(desc());

//        List<QueryResult.Result> results = _influxDB.query(query).getResults();

        SelectQueryImpl selectQuery = select("time", "Springerbecken").into("Springerbecken.temperature").from("garching_schwimmbad", "schwimmbad");

        System.out.println(selectQuery.buildQueryString());

        List<QueryResult.Result> results = _influxDB.query(selectQuery).getResults();

        for (QueryResult.Result result : results) {
            System.out.println(result.toString());

        }
    }
}
