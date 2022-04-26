package com.zooplus.assignment.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Currency {

    private String code;
    private String name;
    private String symbol;

    public boolean isSelected(String name){
        if (code != null) {
            return this.name.equals(name);
        }
        return false;
    }
}
