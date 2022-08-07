package com.example.restaurant.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class AdminReviewAction {
    @Getter @Setter private Boolean accept;

    public AdminReviewAction(Boolean accept) {
        this.accept = accept;
    }

}
