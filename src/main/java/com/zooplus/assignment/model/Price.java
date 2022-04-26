package com.zooplus.assignment.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Price {

    private String base;
    private String currency;
    private String amount;
}
