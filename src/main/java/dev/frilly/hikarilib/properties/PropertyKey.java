package dev.frilly.hikarilib.properties;

import lombok.Getter;
import lombok.Value;

import java.util.List;

/**
 * Represents a key in a .properties file.
 */
@Value
@Getter
public class PropertyKey {

    String key;
    String value;
    List<String> comments;

}
