package com.mc855.tracker.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateGoogleIdTokenDto {

    private Long personId;
    private String googleIdToken;
}
