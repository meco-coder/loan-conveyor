package code.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum MaritalStatus {
    @JsonProperty("married")
    MARRIED,
    @JsonProperty("notMarried")
    NOT_MARRIED
}
