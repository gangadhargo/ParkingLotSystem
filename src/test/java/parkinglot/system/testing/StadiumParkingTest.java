package parkinglot.system.testing;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import parkinglot.exception.ParkingLotException;
import parkinglot.factory.AirportParking;
import parkinglot.factory.ParkingFactory;
import parkinglot.factory.StadiumParking;
import parkinglot.model.*;
import parkinglot.service.Parking;

import java.util.Date;

public class StadiumParkingTest {

	ParkingFactory parkingFactory;
	Parking parking;

	Vehicle vehicle;

	@Before
	public void init(){
		parkingFactory = StadiumParking.builder().motorCycleSpotsCount(1000).carSpotsCount(1500).build();
		parking = parkingFactory.createParking();
		vehicle = new Vehicle();
		vehicle.setRegisterNumber("123");
	}

	@Test
	public void parkAndUnParkVehicle() throws ParkingLotException {
		ParkingSpot parkingSpot = parking.parkVehicle(VehicleType.MOTORCYCLE, vehicle);
		Assert.assertTrue(parkingSpot.getSpotNumber()!= null);
		Ticket ticket = parking.createEntryTicket(parkingSpot);
		Assert.assertTrue(ticket.getTicketNumber()!= null);
		Boolean isUnParked = parking.unParkVehicle(VehicleType.MOTORCYCLE, parkingSpot);
		Assert.assertTrue(isUnParked);
	}
	@Test
	public void calculateMotorCycleParkingPriceWithThreeHoursFourtyMinutes() throws ParkingLotException {
		ParkingSpot parkingSpot = parking.parkVehicle(VehicleType.MOTORCYCLE, vehicle);
		Ticket ticket = parking.createEntryTicket(parkingSpot);
		parking.unParkVehicle(VehicleType.MOTORCYCLE, parkingSpot);
		Date currentDate = new Date(System.currentTimeMillis() - 10840 * 1000);
		ticket.setEntryDateTime(currentDate);
		Receipt receipt = parking.createRecepitOnExit(VehicleType.MOTORCYCLE, ticket);
		Double price = receipt.getFees();
		Assert.assertEquals("30.0", String.valueOf(price));
	}

	@Test
	public void calculateMotorCycleParkingPriceWithFourteenHoursFiftyNineMinutes() throws ParkingLotException {
		ParkingSpot parkingSpot = parking.parkVehicle(VehicleType.MOTORCYCLE, vehicle);
		Ticket ticket = parking.createEntryTicket(parkingSpot);
		parking.unParkVehicle(VehicleType.MOTORCYCLE, parkingSpot);
		Date currentDate = new Date(System.currentTimeMillis() - 53940 * 1000);
		ticket.setEntryDateTime(currentDate);
		Receipt receipt = parking.createRecepitOnExit(VehicleType.MOTORCYCLE, ticket);
		Double price = receipt.getFees();
		Assert.assertEquals("390.0", String.valueOf(price));
	}

	@Test
	public void calculateCarOrSUVParkingPriceElevenHourThirtyMinutes() throws ParkingLotException {
		ParkingSpot parkingSpot = parking.parkVehicle(VehicleType.CAR, vehicle);
		Ticket ticket = parking.createEntryTicket(parkingSpot);
		Boolean isUnParked = parking.unParkVehicle(VehicleType.CAR, parkingSpot);
		if(isUnParked){
			Date currentDate = new Date(System.currentTimeMillis() - 39630 * 1000);
			ticket.setEntryDateTime(currentDate);
			Receipt receipt = parking.createRecepitOnExit(VehicleType.CAR, ticket);
			Double price = receipt.getFees();
			Assert.assertEquals("180.0", String.valueOf(price));
		}
	}
	@Test
	public void calculateCarOrSUVParkingPriceThirteenHoursFiveMinutes() throws ParkingLotException {
		ParkingSpot parkingSpot = parking.parkVehicle(VehicleType.CAR, vehicle);
		Ticket ticket = parking.createEntryTicket(parkingSpot);
		Boolean isUnParked = parking.unParkVehicle(VehicleType.CAR, parkingSpot);
		if(isUnParked){
			Date currentDate = new Date(System.currentTimeMillis() - 46805 * 1000);
			ticket.setEntryDateTime(currentDate);
			Receipt receipt = parking.createRecepitOnExit(VehicleType.CAR, ticket);
			Double price = receipt.getFees();
			Assert.assertEquals("580.0", String.valueOf(price));
		}
	}
}
