package com.mc855.tracker.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Component
public class CsvUtil {

    public static <T> List<T> getObject(Class<T> objectClass, String filePath) {

        File file = new File(filePath);

        // Bind schema with file header
        CsvSchema bootstrapSchema = CsvSchema.emptySchema().withHeader();
        ObjectMapper mapper = new CsvMapper();
        try {
            List<Object> list = mapper.readerFor(objectClass).with(bootstrapSchema).readValues(file).readAll();
            log.debug("Parsed [{}] {}", list.size(), objectClass.getSimpleName());
            return list.stream()
                    .filter(Objects::nonNull)
                    .map(element->(T) element)
                    .collect(Collectors.toList());

        } catch (Exception e) {
            log.error("Failed to parse customer file [{}]", filePath, e);
            return new ArrayList<>();
        }
    }
}
