package parkinglot.model;

import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Class to define Parking Floors with parking spots
 * **/
@Data
public class ParkingFloor {
	private String floorNumber;
	private String floorName;
	private Integer id;
	private Map<VehicleType, List<ParkingSpot>> availableSpotsByVehicleType;
	private Map<VehicleType, Integer> vehicleTypeSpotsCapacity;
	
	public ParkingFloor() {
		this.availableSpotsByVehicleType = new HashMap<>();
		this.vehicleTypeSpotsCapacity = new HashMap<>();
	}

	public ParkingSpot parkVehicle(VehicleType vehicleType, Vehicle vehicle) {
		ParkingSpot parkingSpot = getAvailableSpotNumberFromListOfSpotsByVehicle(vehicleType);
		if(parkingSpot != null) {
			availableSpotsByVehicleType.get(vehicleType).remove(parkingSpot);
			parkingSpot.setVehicle(vehicle);
			parkingSpot.setParkingStatus(ParkingStatus.PARKED);
			availableSpotsByVehicleType.get(vehicleType).add(parkingSpot);
			return parkingSpot;
		}else {
			System.out.println("Parking Space is full for "+ vehicleType);
		}
		return null;
	}
	
	public Boolean unparkVehicle(VehicleType vehicleType, ParkingSpot vehicleParkingSpot) {
		for(ParkingSpot parkingSpot : availableSpotsByVehicleType.get(vehicleType)) {
			if(parkingSpot.getParkingStatus().equals(ParkingStatus.PARKED)
					&& parkingSpot.getVehicle().getRegisterNumber().equals(vehicleParkingSpot.getVehicle().getRegisterNumber())
			&& parkingSpot.getSpotNumber().equals(vehicleParkingSpot.getSpotNumber())) {
				parkingSpot.setParkingStatus(ParkingStatus.UNPARKED);
				parkingSpot.setVehicle(null);
				return true;
			}
		}
		return false;
	}
	
	private ParkingSpot getAvailableSpotNumberFromListOfSpotsByVehicle(VehicleType type) {
		for(ParkingSpot parkingSpot : availableSpotsByVehicleType.get(type)) {
			if(parkingSpot.getParkingStatus().equals(ParkingStatus.UNPARKED)) {
				return parkingSpot;
			}
		}
		return null;
	}

	public boolean verifyIfFloorCapacityforGivenVehicleTypeIsFull(VehicleType vehicleType) {
		if( availableSpotsByVehicleType.get(vehicleType).size()!= 0) {
			List<ParkingSpot> parkedParkingSpots = availableSpotsByVehicleType.get(vehicleType).
					stream().
					filter(spot -> spot.getParkingStatus().equals(ParkingStatus.PARKED)).
					collect(Collectors.toList());
			if (parkedParkingSpots.size() == vehicleTypeSpotsCapacity.get(vehicleType)) {
				return true;
			}
			return false;
		}
		return true;
	}
	
}
