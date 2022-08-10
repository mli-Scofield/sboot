package com.dtc233.eslog;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author dtc
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    private Integer id;
    private String title;
}
