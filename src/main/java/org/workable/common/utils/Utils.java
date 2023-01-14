package org.workable.common.utils;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.workable.controller.DTO.MovieDTO;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class Utils {

    public static final String LIKE = "likes";
    public static final String HATE = "hates";
    public static final String DATE = "date";

    public List<MovieDTO> sortMovieList(List<MovieDTO> movieDTOs, String sortBy){
        if (StringUtils.hasText(sortBy)){
            switch(sortBy){
                case LIKE: //First case
                    movieDTOs.sort(Comparator.comparingInt(MovieDTO::getLikes));
                    break;
                case HATE: //Second case
                    movieDTOs.sort(Comparator.comparingInt(MovieDTO::getHates));
                    break;
                case DATE: //Third case
                    Collections.sort(movieDTOs);
                    break;
                default: //Default case
                    break;
            }
        }
        return movieDTOs;
    }
}
