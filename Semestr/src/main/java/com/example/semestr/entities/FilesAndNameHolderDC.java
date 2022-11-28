package com.example.semestr.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FilesAndNameHolderDC {
    private Long id;
    private String title;
    private String description;
    private Long holderId;
    private String nameHolder;
    private String nameFile;
    private boolean publicAccess;
}
