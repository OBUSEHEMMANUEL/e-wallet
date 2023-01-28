package com.example.ewallet.data.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CardData {
   private String bin;
   private String brand;
   private String sub_brand;
   private String country_code;
   private String country_name;
   private  String card_type;
   private String bank;
}
