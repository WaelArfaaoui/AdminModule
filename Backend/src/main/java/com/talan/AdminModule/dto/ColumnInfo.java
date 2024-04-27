package com.talan.AdminModule.dto;

import lombok.*;


@Setter
@NonNull
public class ColumnInfo {

    private String name;
    private String type;

    public ColumnInfo(String name, String type) {
        this.name = name;
        this.type = type;
    }
public  ColumnInfo(){}

    public String getName() {
        return name;
    }
    public String getType(){
        return type;
    }
}

