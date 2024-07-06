package com.ebay.shipping.Payloads;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class customerDto {

    private long id;

    private String name;

    private String email;

    private String address;


}
