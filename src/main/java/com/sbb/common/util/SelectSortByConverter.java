package com.sbb.common.util;

import org.springframework.core.convert.converter.Converter;

import com.sbb.question.domain.SelectSortBy;

public class SelectSortByConverter implements Converter<String, SelectSortBy> {

    @Override
    public SelectSortBy convert(String source) {
        try {
            return SelectSortBy.valueOf(source);
        } catch (IllegalArgumentException e) {
            return SelectSortBy.CREATED_AT;
        }
    }
}
