package com.newsroom.newsroomservice.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AiResults {

    private String title;

    private String content;

    private Boolean isInteresting;

    private Integer importance;

    private List<String> tags;
}
