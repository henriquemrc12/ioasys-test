package com.ioasys.api.movies._forms;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MovieCreateForm {

    private String name;

    private String genre;

    private String directedBy;

}
