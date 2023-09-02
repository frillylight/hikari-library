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

    public String key;
    public String value;
    public List<String> comments;

}
