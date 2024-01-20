package com.panneer.springlearn.batch.configuration;

import com.panneer.springlearn.batch.entity.Coffee;
import org.springframework.batch.item.ItemProcessor;

public class CoffeeCustomProcessor implements ItemProcessor<Coffee,Coffee> {
    @Override
    public Coffee process(Coffee item) {
        item.setTimeStamp(String.valueOf(System.currentTimeMillis()));
        return item;
    }
}
