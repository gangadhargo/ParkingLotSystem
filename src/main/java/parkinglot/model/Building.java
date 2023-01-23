package parkinglot.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/*
* Class to define Parking Building with floors
* */
@Data
public class Building {
	private Integer buildingNo;
	private String buildingName;
	private List<ParkingFloor> floors;
}
