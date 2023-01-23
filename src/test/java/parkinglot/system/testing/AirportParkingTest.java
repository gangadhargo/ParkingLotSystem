package parkinglot.system.testing;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import parkinglot.exception.ParkingLotException;
import parkinglot.factory.AirportParking;
import parkinglot.factory.ParkingFactory;
import parkinglot.model.*;
import parkinglot.service.Parking;

import java.util.Date;

public class AirportParkingTest {

	ParkingFactory parkingFactory;
	Parking parking;

	Vehicle vehicle;

	@Before
	public void init(){
		parkingFactory = AirportParking.builder().motorCycleSpotsCount(200).carSpotsCount(500).busSpotsCount(100).build();
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
	public void calculateMotorCycleParkingPriceWithFiftyFiveMinutes() throws ParkingLotException {
		ParkingSpot parkingSpot = parking.parkVehicle(VehicleType.MOTORCYCLE, vehicle);
		Ticket ticket = parking.createEntryTicket(parkingSpot);
		parking.unParkVehicle(VehicleType.MOTORCYCLE, parkingSpot);
		Date currentDate = new Date(System.currentTimeMillis() - 3300 * 1000);
		ticket.setEntryDateTime(currentDate);
		Receipt receipt = parking.createRecepitOnExit(VehicleType.MOTORCYCLE, ticket);
		Double price = receipt.getFees();
		Assert.assertEquals("0.0", String.valueOf(price));
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
		Assert.assertEquals("60.0", String.valueOf(price));
	}

	@Test
	public void calculateMotorCycleParkingPriceOneDayTwelveHours() throws ParkingLotException {
		ParkingSpot parkingSpot = parking.parkVehicle(VehicleType.MOTORCYCLE, vehicle);
		Ticket ticket = parking.createEntryTicket(parkingSpot);
		parking.unParkVehicle(VehicleType.MOTORCYCLE, parkingSpot);
		Date currentDate = new Date(System.currentTimeMillis() - 129600 * 1000);
		ticket.setEntryDateTime(currentDate);
		Receipt receipt = parking.createRecepitOnExit(VehicleType.MOTORCYCLE, ticket);
		Double price = receipt.getFees();
		Assert.assertEquals("160.0", String.valueOf(price));
	}
	@Test
	public void calculateCarParkingPriceThreeDaysOneHour() throws ParkingLotException {
		ParkingSpot parkingSpot = parking.parkVehicle(VehicleType.CAR, vehicle);
		Ticket ticket = parking.createEntryTicket(parkingSpot);
		Boolean isUnParked = parking.unParkVehicle(VehicleType.CAR, parkingSpot);
		if(isUnParked){
			Date currentDate = new Date(System.currentTimeMillis() - 259260 * 1000);
			ticket.setEntryDateTime(currentDate);
			Receipt receipt = parking.createRecepitOnExit(VehicleType.CAR, ticket);
			Double price = receipt.getFees();
			Assert.assertEquals("400.0", String.valueOf(price));
		}
	}
	@Test
	public void calculateCarOrSUVParkingPriceFiftyMinutes() throws ParkingLotException {
		ParkingSpot parkingSpot = parking.parkVehicle(VehicleType.CAR, vehicle);
		Ticket ticket = parking.createEntryTicket(parkingSpot);
		Boolean isUnParked = parking.unParkVehicle(VehicleType.CAR, parkingSpot);
		if(isUnParked){
			Date currentDate = new Date(System.currentTimeMillis() - 3000 * 1000);
			ticket.setEntryDateTime(currentDate);
			Receipt receipt = parking.createRecepitOnExit(VehicleType.CAR, ticket);
			Double price = receipt.getFees();
			Assert.assertEquals("60.0", String.valueOf(price));
		}
	}

	@Test
	public void calculateCarOrSUVParkingPriceTwentyThreeHoursFiftyNineMinutes() throws ParkingLotException {
		ParkingSpot parkingSpot = parking.parkVehicle(VehicleType.CAR, vehicle);
		Ticket ticket = parking.createEntryTicket(parkingSpot);
		Boolean isUnParked = parking.unParkVehicle(VehicleType.CAR, parkingSpot);
		if(isUnParked){
			Date currentDate = new Date(System.currentTimeMillis() - 86340 * 1000);
			ticket.setEntryDateTime(currentDate);
			Receipt receipt = parking.createRecepitOnExit(VehicleType.CAR, ticket);
			Double price = receipt.getFees();
			Assert.assertEquals("80.0", String.valueOf(price));
		}
	}
}
