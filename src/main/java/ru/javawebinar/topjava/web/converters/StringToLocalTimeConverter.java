package ru.javawebinar.topjava.web.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class StringToLocalTimeConverter implements Converter<String, LocalTime> {

    @Override
    public LocalTime convert(@Nullable String time) {
        return (time == null) ? null : LocalTime.parse(time, DateTimeFormatter.ISO_LOCAL_TIME);
    }

}
