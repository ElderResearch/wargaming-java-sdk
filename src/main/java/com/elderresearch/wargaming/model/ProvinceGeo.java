package com.elderresearch.wargaming.model;

import java.util.Map;

import org.geojson.GeoJsonObject;
import org.geojson.LngLatAlt;

import lombok.Data;

@Data
public class ProvinceGeo {
	private String alias;
	private GeoJsonObject geom;
	private Map<String, GeoJsonObject> borders;
	private LngLatAlt center;
	
	// Don't need junctions and GeoJson point doesn't have azimuth
}
