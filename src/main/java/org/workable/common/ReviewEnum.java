package org.workable.common;

public enum ReviewEnum {

    LIKE("like"),
    HATE("hate");

    public String userReview;

    ReviewEnum(String userReview) {
        this.userReview = userReview;
    }
}
