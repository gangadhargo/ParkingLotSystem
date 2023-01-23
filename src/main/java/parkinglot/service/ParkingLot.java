package parkinglot.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import parkinglot.exception.ParkingLotException;
import parkinglot.factory.StadiumParking;
import parkinglot.model.FeeDetails;
import parkinglot.model.ParkingFloor;
import parkinglot.model.Receipt;
import parkinglot.model.ParkingSpot;
import parkinglot.model.Ticket;
import parkinglot.model.Vehicle;
import parkinglot.model.VehicleType;
import parkinglot.pricing.AirportParkingPricingModel;
import parkinglot.pricing.MallParkingPricingModel;
import parkinglot.pricing.PricingModel;
import parkinglot.utils.ParkingLotUtils;
/*
 * Parking Lot Implements parking operations such as park vehicle, unpark vehicle, generate ticket, generate receipt
 * */
public class ParkingLot implements Parking {
	private String name;
	private String address;

	//A parking lot can have Multiple floors or single floor with wide open area
	private List<ParkingFloor> floors;

	private PricingModel pricingModel;

	ParkingLotUtils parkingLotUtils = new ParkingLotUtils();

	public ParkingLot() {
		this.floors = new ArrayList<>();
	}

	public ParkingLot(List<ParkingFloor> parkingFloors,
					  PricingModel pricingModel) {
		this.floors = parkingFloors;
		this.pricingModel = pricingModel;
	}

	/*
	* Park vehicle based on vehicle type , vehicle
	* */
	@Override
	public ParkingSpot parkVehicle(VehicleType vehicleType, Vehicle vehicle) throws ParkingLotException {
		// TODO Auto-generated method stub
		boolean isParkingSpotAssignedToVehicle = false;
		for (ParkingFloor floor : floors) {
			if (!floor.verifyIfFloorCapacityforGivenVehicleTypeIsFull(vehicleType)) {
				ParkingSpot parkingSpot = floor.parkVehicle(vehicleType, vehicle);
				if (parkingSpot != null) {
					isParkingSpotAssignedToVehicle = true;
					return parkingSpot;
				}
			}
		}
		if (!isParkingSpotAssignedToVehicle) {
			throw new ParkingLotException("No Space available");
		}
		return null;
	}
	/*
	 * Create Entry Ticket on parking a vehicle
	 * */
	@Override
	public Ticket createEntryTicket(ParkingSpot parkingSpot) throws ParkingLotException{
		Ticket ticket = new Ticket();
		ticket.setParkingSpot(parkingSpot);
		ticket.setTicketNumber(getRandomTicketNumber());
		ticket.setEntryDateTime(new Date());
		return ticket;
	}
	private Integer getRandomTicketNumber(){
		Integer min = 1;
		Integer max = Integer.MAX_VALUE;
		Integer generatedLong = min + (int) (Math.random() * (max - min));
		return generatedLong;
	}

	@Override
	public Receipt createRecepitOnExit(VehicleType vehicleType, Ticket ticket) throws ParkingLotException{
		// TODO Auto-generated method stub
		Receipt receipt = new Receipt();
		receipt.setEntryDateTime(ticket.getEntryDateTime());
		Date endTime = new Date();
		receipt.setExitDateTime(endTime);
		Double price = pricingModel.calculatePrice(vehicleType, ticket.getEntryDateTime(), endTime);
		receipt.setFees(price);
		return receipt;
	}

	@Override
	public Boolean unParkVehicle(VehicleType vehicleType, ParkingSpot parkingSpot) throws ParkingLotException{
		for (ParkingFloor floor : floors) {
			if (floor.unparkVehicle(vehicleType, parkingSpot)) {
				return true;
			}
		}
		return false;
	}

}
