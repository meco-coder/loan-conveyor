package code.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum ApplicationStatus {
    @JsonProperty("preapproval")
    PREAPPROVAL,
    @JsonProperty("approved")
    APPROVED,
    @JsonProperty("ccDenied")
    CC_DENIED,
    @JsonProperty("ccApproved")
    CC_APPROVED,
    @JsonProperty("prepareDocuments")
    PREPARE_DOCUMENTS,
    @JsonProperty("documentCreated")
    DOCUMENT_CREATED,
    @JsonProperty("clientDenied")
    CLIENT_DENIED,
    @JsonProperty("documentSigned")
    DOCUMENT_SIGNED,
    @JsonProperty("creditIssued")
    CREDIT_ISSUED


}
