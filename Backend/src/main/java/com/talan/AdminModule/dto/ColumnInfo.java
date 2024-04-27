package com.talan.AdminModule.dto;

import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
public class ColumnInfo {
    @NonNull
    private String name;
    private String type;

    public ColumnInfo(@NonNull String name, String type) {
        this.name = name;
        this.type = type;
    }
}

