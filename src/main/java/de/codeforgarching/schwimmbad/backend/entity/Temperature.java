package de.codeforgarching.schwimmbad.backend.entity;

import java.time.LocalDateTime;

public class Temperature
{
    private String _poolName;
    private LocalDateTime _timestamp;
    private float _temperature;

    public Temperature()
    {
    }

    public String getPoolName()
    {
        return _poolName;
    }

    public void setPoolName(String poolName)
    {
        _poolName = poolName;
    }

    public LocalDateTime getTimestamp()
    {
        return _timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp)
    {
        _timestamp = timestamp;
    }

    public float getTemperature()
    {
        return _temperature;
    }

    public void setTemperature(float temperature)
    {
        _temperature = temperature;
    }
}
