package com.elderresearch.wargaming.model;

import com.fasterxml.jackson.annotation.JsonCreator;

import lombok.Data;
import lombok.experimental.Accessors;

@Data @Accessors(chain = true)
public class DistributionSummary {
	private int min, average, max;
	
	@JsonCreator
	public static DistributionSummary from(int... values) {
		assert values.length == 3;
		return new DistributionSummary()
			.setMin(values[0])
			.setAverage(values[1])
			.setMax(values[2]);
	}
}
