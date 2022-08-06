package com.example.demo.src.profile.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetProfileRes {
    private int profildIdx;
    private String name;
    private String image;
    private boolean isPasswordEmpty;
}
