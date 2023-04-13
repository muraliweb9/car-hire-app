package com.interview.carhire.services;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RestErrorMessage {
    private String errorMessage;
}
