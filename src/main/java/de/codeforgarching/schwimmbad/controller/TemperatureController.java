package de.codeforgarching.schwimmbad.controller;

import de.codeforgarching.schwimmbad.backend.entity.Temperature;
import org.influxdb.InfluxDB;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.influxdb.querybuilder.Ordering;
import org.influxdb.querybuilder.SelectQueryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.List;

import static org.influxdb.querybuilder.BuiltQuery.QueryBuilder.desc;
import static org.influxdb.querybuilder.BuiltQuery.QueryBuilder.select;

@RestController
public class TemperatureController
{

    @Autowired
    private InfluxDB _influxDB;

    @Value("${InfluxDB.databaseName}")
    private String databaseName;


    @GetMapping("/temperature")
    public Temperature temperature(@RequestParam(value = "name") String poolName) {
        Temperature temperature = new Temperature();

        SelectQueryImpl query = select().from(databaseName, poolName).limit(1).orderBy(desc());

        List<QueryResult.Result> results = _influxDB.query(query).getResults();

        List<List<Object>> values = results.get(0).getSeries().get(0).getValues();
        temperature.setPoolName(poolName);
        List<Object> value = values.get(0);
        String timestamp = value.get(0).toString();

        ZonedDateTime utcZonedDateTIme = ZonedDateTime.parse(timestamp);
        ZonedDateTime local = utcZonedDateTIme.withZoneSameInstant(ZoneId.of("Europe/Berlin"));

        temperature.setTimestamp(local.toLocalDateTime());
        temperature.setTemperature(Float.parseFloat(value.get(3).toString()));

        return temperature;
    }
}
