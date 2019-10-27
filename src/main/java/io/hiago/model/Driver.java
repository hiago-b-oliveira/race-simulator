package io.hiago.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Driver {
    private String code;
    private String name;
}
