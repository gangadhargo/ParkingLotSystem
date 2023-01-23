package parkinglot.service;

import parkinglot.exception.ParkingLotException;
import parkinglot.model.Receipt;
import parkinglot.model.ParkingSpot;
import parkinglot.model.Ticket;
import parkinglot.model.Vehicle;
import parkinglot.model.VehicleType;

/*
* Interface to implement major parking operations
* */
public interface Parking {
	ParkingSpot parkVehicle(VehicleType vehicleType, Vehicle vehicle) throws ParkingLotException;
	Boolean unParkVehicle(VehicleType vehicleType, ParkingSpot parkingSpot) throws ParkingLotException;;
	Ticket createEntryTicket(ParkingSpot parkingSpot) throws ParkingLotException;;
	Receipt createRecepitOnExit(VehicleType vehicleType, Ticket ticket) throws ParkingLotException;;

}
