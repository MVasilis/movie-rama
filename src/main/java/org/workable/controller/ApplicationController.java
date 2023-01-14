package org.workable.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.workable.controller.DTO.MovieDTO;
import org.workable.service.MovieService;

import java.util.List;

import static org.workable.common.Constant.APPLICATION_ENTRY_POINT;
import static org.workable.common.Constant.MOVIES_LIST_ATTRIBUTE;

/**
 *  This is the Entry to application controller
 */
@Controller
public class ApplicationController {

    @Autowired
    private MovieService movieService;

    @GetMapping("")
    public String viewHomePage(Model model,
                               @RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "100") int size) {
        List<MovieDTO> movies = movieService.retrieveAll(page, size, "", "");
        model.addAttribute(MOVIES_LIST_ATTRIBUTE, movies);
        return APPLICATION_ENTRY_POINT;
    }

}
