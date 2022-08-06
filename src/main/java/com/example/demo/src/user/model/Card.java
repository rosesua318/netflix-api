package com.example.demo.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Card {
    private String cardNumber;
    private String name;
    private String yymm;
    private String birthYear;
    private String birthMonth;
    private String birthDay;
    private String phone;
}
