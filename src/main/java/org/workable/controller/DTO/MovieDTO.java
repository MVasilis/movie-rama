package org.workable.controller.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovieDTO implements Comparable<MovieDTO>{

    private Long id;
    private String movieTitle;
    private String movieDescription;
    private Date publicationDate;
    private String nameOfUser;
    private Integer likes;
    private Integer hates;
    private List<ReviewDTO> reviews;
    private UserDTO addedBy;

    @Override
    public int compareTo(MovieDTO o) {
        return getPublicationDate().compareTo(o.getPublicationDate());
    }
}
