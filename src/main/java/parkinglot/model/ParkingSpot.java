package parkinglot.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * Class to define Parking Spots with vehicle type
 * **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParkingSpot {
	
	private Integer spotNumber;
	private Vehicle vehicle;
	private ParkingStatus parkingStatus;
	private VehicleType vehicleType;

	public ParkingSpot(VehicleType vehicleType, ParkingStatus parkingStatus) {
		this.vehicleType = vehicleType;
		this.parkingStatus = parkingStatus;
	}
	public ParkingSpot(Integer spotNumber, VehicleType vehicleType, ParkingStatus parkingStatus) {
		this.vehicleType = vehicleType;
		this.parkingStatus = parkingStatus;
		this.spotNumber = spotNumber;
	}
}
