package com.example.semestr.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileDC {
    private Long idFile;
    private String title;
    private String description;
    private Long holderId;
    private String nameFile;
    private boolean publicAccess; // false - только мне, true -  всем
//    private Integer access; // 1 - только мне, 2 - Всем
//    private List<String> accessLogin;
}
