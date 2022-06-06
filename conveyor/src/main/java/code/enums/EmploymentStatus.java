package code.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum EmploymentStatus {
    @JsonProperty("unemployed")
    UNEMPLOYED,
    @JsonProperty("selfEmployed")
    SELF_EMPLOYED,
    @JsonProperty("businessOwner")
    BUSINESS_OWNER
}
