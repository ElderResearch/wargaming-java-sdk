package com.elderresearch.wargaming.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.experimental.Accessors;

@Data @Accessors(chain = true)
public class PlayerVehicle {
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	private MarkOfMastery markOfMastery;
	
	private int tankId;
	
	private PlayerVehicleStats statistics;
	
	@Data @Accessors(chain = true)
	public static class PlayerVehicleStats {
		private int battles, wins;
	}
}
