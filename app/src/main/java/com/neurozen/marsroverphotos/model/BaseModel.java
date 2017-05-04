package com.neurozen.marsroverphotos.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

public abstract class BaseModel {

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
