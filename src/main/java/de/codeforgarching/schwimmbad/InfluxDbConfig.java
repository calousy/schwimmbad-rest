package de.codeforgarching.schwimmbad;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InfluxDbConfig
{

    @Value("${InfluxDB.URL}")
    private String _serverURL;

    @Value("${InfluxDB.username}")
    private String _username;

    @Value("${InfluxDB.password}")
    private String _password;


    @Bean
    public InfluxDB influxDb()
    {
        InfluxDB influxDB = InfluxDBFactory.connect(_serverURL, _username, _password);
        return influxDB;
    }
}
