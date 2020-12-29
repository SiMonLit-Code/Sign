package com.sign.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Add {
    private String did ;
    private String pay ;
    private String exam;
    private String card;
    private String soldier;
    private Collect collect ;
}
