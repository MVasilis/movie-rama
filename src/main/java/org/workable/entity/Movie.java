package org.workable.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="MOVIE_ID")
    private Long id;

    @Column(name = "MOVIE_TITLE")
    private String movieTitle;


    @Column(name = "MOVIE_DESCRIPTION")
    private String movieDescription;

    @Column(name = "PUBLICATION_DATE")
    private Date publicationDate;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User addedBy;

    @OneToMany(mappedBy="movie")
    private List<Review> reviews;

}
