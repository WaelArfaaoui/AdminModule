package com.talan.AdminModule.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ColumnInfo {
    @NonNull
    private String name;
    private String type;


}

