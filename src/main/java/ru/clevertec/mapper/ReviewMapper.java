package ru.clevertec.mapper;

import org.mapstruct.Mapper;
import ru.clevertec.dto.ReviewDto;
import ru.clevertec.entity.Review;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CarMapper.class, ClientMapper.class})
public interface ReviewMapper {
    ReviewDto toReviewDto(Review review);
    Review toReview(ReviewDto dto);
    List<ReviewDto> toReviewDtoList(List<Review> reviews);
}
