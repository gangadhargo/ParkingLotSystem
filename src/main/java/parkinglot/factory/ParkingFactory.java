package parkinglot.factory;

import lombok.Data;
import lombok.experimental.SuperBuilder;
import parkinglot.model.ParkingFloor;
import parkinglot.service.Parking;

import java.util.List;


/**
 * Factory Pattern
 *Parking Factory is the base for creating multiple types of parking lots such as airport, mall, stadium parking etc.,
 *
 * **/
@Data
@SuperBuilder
public abstract class ParkingFactory {
	 protected Integer motorCycleSpotsCount = 10;
	 protected Integer carSpotsCount = 10;
	 protected Integer busSpotsCount = 10;

	public abstract Parking createParking();

	public abstract List<ParkingFloor> createParkingSpots();
}
