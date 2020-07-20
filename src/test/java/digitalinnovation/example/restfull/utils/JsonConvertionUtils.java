package digitalinnovation.example.restfull.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class JsonConvertionUtils {

  public static String asJsonString(Object SoldierResource) {
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
      objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
      objectMapper.registerModules(new JavaTimeModule());

      String writeValueAsString = objectMapper.writeValueAsString(SoldierResource);

      return writeValueAsString;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
