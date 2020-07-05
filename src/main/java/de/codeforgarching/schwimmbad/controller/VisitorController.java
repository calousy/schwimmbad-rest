package de.codeforgarching.schwimmbad.controller;

import de.codeforgarching.schwimmbad.backend.entity.VisitorInformation;
import org.influxdb.InfluxDB;
import org.influxdb.dto.QueryResult;
import org.influxdb.querybuilder.SelectQueryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import static org.influxdb.querybuilder.BuiltQuery.QueryBuilder.desc;
import static org.influxdb.querybuilder.BuiltQuery.QueryBuilder.select;

@RestController
public class VisitorController {

    @Autowired
    private InfluxDB _influxDB;

    @Value("${InfluxDB.databaseName}")
    private String databaseName;


    @GetMapping("/visitor")
    public VisitorInformation visitor() {
        SelectQueryImpl query = select().from(databaseName, "Besucheranzahl").limit(1).orderBy(desc());

        List<QueryResult.Result> results = _influxDB.query(query).getResults();

        List<List<Object>> values = results.get(0).getSeries().get(0).getValues();
        List<Object> value = values.get(0);
        String timestamp = value.get(0).toString();

        ZonedDateTime utcZonedDateTIme = ZonedDateTime.parse(timestamp);
        ZonedDateTime local = utcZonedDateTIme.withZoneSameInstant(ZoneId.of("Europe/Berlin"));

        int visitors = (int)Float.parseFloat(value.get(2).toString());
        return new VisitorInformation(local.toLocalDateTime(), visitors);
    }
}
