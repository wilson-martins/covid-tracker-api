package com.mc855.tracker.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class StatusHistoryTestDataDto {

    @JsonProperty("case_group")
    private Integer caseGroup;
    @JsonProperty("state")
    private String state;
    @JsonProperty("pos")
    private Integer position;
}
