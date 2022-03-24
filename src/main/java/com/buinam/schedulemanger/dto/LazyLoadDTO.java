package com.buinam.schedulemanger.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class LazyLoadDTO {
    private BigDecimal count;
    private BigDecimal countOk;
	private BigDecimal countError;
	private BigDecimal countDuplicate;
	private List<?> listObject;
}
