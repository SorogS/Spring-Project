package com.sorog.carrent.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class FileDTO {
    private String base64;
    // just base64 string is enough. If you want, send additional details
    private String name;
    private String type;
    private String lastModified;
}
