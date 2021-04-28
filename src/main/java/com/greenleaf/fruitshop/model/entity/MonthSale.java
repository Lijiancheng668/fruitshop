package com.greenleaf.fruitshop.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MonthSale implements Comparable<MonthSale>{
    private Integer name;
    private Integer value;

    @Override
    public int compareTo(MonthSale o) {
        return name.compareTo(o.name);
    }
}
