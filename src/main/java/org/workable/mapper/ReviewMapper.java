package org.workable.mapper;

import org.mapstruct.Mapper;
import org.workable.controller.DTO.ReviewDTO;
import org.workable.entity.Review;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    List<ReviewDTO> reviewsEntityToReviewDTOs(List<Review> source);

    ReviewDTO reviewEntityToReviewDTO(Review source);

    Review reviewDTOtoReviewEntity(ReviewDTO source);
}
