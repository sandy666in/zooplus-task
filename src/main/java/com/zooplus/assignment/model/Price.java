package com.zooplus.assignment.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Price {

    private String time;
    private String base;
    private String quote;
    private String rate;

}
