package code.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Position {
    @JsonProperty("middleManager")
    MIDDLE_MANAGER,
    @JsonProperty("topManager")
    TOP_MANAGER
}
