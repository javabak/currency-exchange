package util.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

public class ToStringMapper {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @SneakyThrows
    public static String mapToString(Object o) {
        return objectMapper.writeValueAsString(o);
    }
}
