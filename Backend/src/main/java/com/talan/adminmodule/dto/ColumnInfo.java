package com.talan.adminmodule.dto;

import lombok.*;


@Setter

public class ColumnInfo {
@NonNull
    private String name;
    private String type;

    public ColumnInfo(@NonNull String name, String type) {
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

