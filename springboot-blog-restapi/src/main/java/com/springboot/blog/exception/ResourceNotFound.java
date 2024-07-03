package com.springboot.blog.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
@Getter
public class ResourceNotFound extends RuntimeException{
    private String resource;
    private String fieldValue;
    private String getFieldName;

    public ResourceNotFound(String resource, String fieldValue, String getFieldName) {
        super(String.format("%s not found with %s : %s",resource,fieldValue,getFieldName));
        this.resource = resource;
        this.fieldValue = fieldValue;
        this.getFieldName = getFieldName;
    }

}
