package parkinglot.factory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import lombok.Data;
import lombok.experimental.SuperBuilder;
import parkinglot.model.*;
import parkinglot.pricing.AirportParkingPricingModel;
import parkinglot.pricing.PricingModel;
import parkinglot.service.Parking;
import parkinglot.service.ParkingLot;


/**
 * Class to initialize to Airport Parking with parking floors, parking spots and pricing model
 * */
@SuperBuilder
@Data
public class AirportParking extends ParkingFactory {
	@Override
	public Parking createParking() {
		List<ParkingFloor> parkingFloors = createParkingSpots();
		PricingModel pricingModel = new AirportParkingPricingModel();
		return new ParkingLot(parkingFloors, pricingModel);
	}

	@Override
	public List<ParkingFloor> createParkingSpots() {
		List<ParkingSpot> motorCycleParkingSpots = new ArrayList<>();
		if(this.motorCycleSpotsCount!= null){
			motorCycleParkingSpots.addAll(IntStream.range(0, this.motorCycleSpotsCount)
					.mapToObj(i -> new ParkingSpot(i+1, VehicleType.MOTORCYCLE, ParkingStatus.UNPARKED))
					.collect(Collectors.toList()));
		}
		List<ParkingSpot> carParkingSpots = new ArrayList<>();
		if(this.carSpotsCount!= null){
			carParkingSpots.addAll(IntStream.range(0, this.carSpotsCount)
					.mapToObj(i -> new ParkingSpot(i+1, VehicleType.CAR, ParkingStatus.UNPARKED)).collect(Collectors.toList()));
		}
		List<ParkingSpot> busParkingSpots = new ArrayList<>();

		if(this.busSpotsCount!= null) {
			busParkingSpots.addAll(IntStream.range(0, this.busSpotsCount)
					.mapToObj(i -> new ParkingSpot(i+1, VehicleType.BUS, ParkingStatus.UNPARKED)).collect(Collectors.toList()));
		}
		Map<VehicleType, List<ParkingSpot>> vehiclesWithSpots = new HashMap<>();
		vehiclesWithSpots.put(VehicleType.MOTORCYCLE, motorCycleParkingSpots);
		vehiclesWithSpots.put(VehicleType.CAR, carParkingSpots);
		vehiclesWithSpots.put(VehicleType.BUS, busParkingSpots);

		Map<VehicleType, Integer> vehiclesWithSpotsCapacity = new HashMap<>();
		vehiclesWithSpotsCapacity.put(VehicleType.MOTORCYCLE, motorCycleParkingSpots.size());
		vehiclesWithSpotsCapacity.put(VehicleType.CAR, carParkingSpots.size());
		vehiclesWithSpotsCapacity.put(VehicleType.BUS, busParkingSpots.size());

		List<ParkingFloor>  parkingFloors = new ArrayList<>();
		ParkingFloor parkingFloor = new ParkingFloor();
		parkingFloor.setAvailableSpotsByVehicleType(vehiclesWithSpots);
		parkingFloor.setVehicleTypeSpotsCapacity(vehiclesWithSpotsCapacity);
		parkingFloors.add(parkingFloor);
		return parkingFloors;
	}
}