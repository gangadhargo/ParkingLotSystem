package parkinglot.system.testing;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import parkinglot.exception.ParkingLotException;
import parkinglot.factory.AirportParking;
import parkinglot.factory.MallParking;
import parkinglot.factory.ParkingFactory;
import parkinglot.factory.StadiumParking;
import parkinglot.model.*;
import parkinglot.pricing.MallParkingPricingModel;
import parkinglot.pricing.PricingModel;
import parkinglot.service.Parking;
import parkinglot.service.ParkingLot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MallParkingTest {

	ParkingFactory parkingFactory;
	Parking parking;

	Vehicle vehicle;

	PricingModel pricingModel;

	@Before
	public void init(){
		parkingFactory = MallParking.builder().motorCycleSpotsCount(100).carSpotsCount(80).busSpotsCount(10).build();
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
	public void calculateMotorCycleParkingPriceWithThreeHoursThirtyMinutes() throws ParkingLotException {
		ParkingSpot parkingSpot = parking.parkVehicle(VehicleType.MOTORCYCLE, vehicle);
		Ticket ticket = parking.createEntryTicket(parkingSpot);
		parking.unParkVehicle(VehicleType.MOTORCYCLE, parkingSpot);
		Date currentDate = new Date(System.currentTimeMillis() - 10830 * 1000);
		ticket.setEntryDateTime(currentDate);
		Receipt receipt = parking.createRecepitOnExit(VehicleType.MOTORCYCLE, ticket);
		Double price = receipt.getFees();
		Assert.assertEquals("40.0", String.valueOf(price));
	}

	@Test
	public void calculateCarOrSUVParkingPriceWithSixHoursOneMinute() throws ParkingLotException {
		ParkingSpot parkingSpot = parking.parkVehicle(VehicleType.CAR, vehicle);
		Ticket ticket = parking.createEntryTicket(parkingSpot);
		parking.unParkVehicle(VehicleType.CAR, parkingSpot);
		Date currentDate = new Date(System.currentTimeMillis() - 21601 * 1000);
		ticket.setEntryDateTime(currentDate);
		Receipt receipt = parking.createRecepitOnExit(VehicleType.CAR, ticket);
		Double price = receipt.getFees();
		Assert.assertEquals("140.0", String.valueOf(price));
	}

	@Test
	public void calculateBusOrTruckParkingPriceOneHourFiftyNineMinutes() throws ParkingLotException {
		ParkingSpot parkingSpot = parking.parkVehicle(VehicleType.BUS, vehicle);
		Ticket ticket = parking.createEntryTicket(parkingSpot);
		Boolean isUnParked = parking.unParkVehicle(VehicleType.BUS, parkingSpot);
		if(isUnParked){
			Date currentDate = new Date(System.currentTimeMillis() - 3659 * 1000);
			ticket.setEntryDateTime(currentDate);
			Receipt receipt = parking.createRecepitOnExit(VehicleType.BUS, ticket);
			Double price = receipt.getFees();
			Assert.assertEquals("100.0", String.valueOf(price));
		}
	}
	@Test
	public void verifyNoSpaceAvailableMessage() {
		try {
			ParkingFactory parkingFactory = MallParking.builder().motorCycleSpotsCount(2).build();
			Parking parking = parkingFactory.createParking();
			//park first vehicle
			ParkingSpot parkingMotorCycle1 = parking.parkVehicle(VehicleType.MOTORCYCLE, vehicle);
			Ticket ticket1 = parking.createEntryTicket(parkingMotorCycle1);
			Assert.assertTrue(parkingMotorCycle1.getSpotNumber() != null);
			Assert.assertTrue(ticket1.getTicketNumber() != null);
			Assert.assertTrue(ticket1.getEntryDateTime() != null);
			//park second vehicle
			Vehicle vehicle2 = new Vehicle();
			vehicle2.setRegisterNumber("1234");
			ParkingSpot parkingMotorCycle2 = parking.parkVehicle(VehicleType.MOTORCYCLE, vehicle2);
			Assert.assertTrue(parkingMotorCycle2.getSpotNumber() != null);
			//try parking 3rd vehicle then check error message
			Vehicle vehicle3 = new Vehicle();
			vehicle3.setRegisterNumber("1234");
			parking.parkVehicle(VehicleType.MOTORCYCLE, vehicle3);
		} catch (ParkingLotException e) {
			String message = "No Space available";
			Assert.assertEquals(message, e.getMessage());
		}
	}
	@Test
	public void unParkFromSecondVehicleAfterParkingTwoVehicle() throws ParkingLotException, ParseException {

			ParkingFactory parkingFactory = MallParking.builder().motorCycleSpotsCount(2).build();
			Parking parking = parkingFactory.createParking();

			//park first vehicle
			ParkingSpot parkingMotorCycle1 = parking.parkVehicle(VehicleType.MOTORCYCLE, vehicle);
			Ticket ticket1 = parking.createEntryTicket(parkingMotorCycle1);
			Assert.assertTrue(parkingMotorCycle1.getSpotNumber() != null);
			Assert.assertTrue(ticket1.getTicketNumber() != null);
			Assert.assertTrue(ticket1.getEntryDateTime() != null);

			//park second vehicle
			Vehicle vehicle2 = new Vehicle();
			vehicle2.setRegisterNumber("1234");
			ParkingSpot parkingMotorCycle2  = parking.parkVehicle(VehicleType.MOTORCYCLE, vehicle2);
			Assert.assertTrue(parkingMotorCycle2.getSpotNumber() == 2);
			Ticket ticket = parking.createEntryTicket(parkingMotorCycle2);
			Assert.assertTrue(ticket.getParkingSpot().getSpotNumber().equals(2));

			//unpark second vehicle and calculate price
			Boolean isTrue = parking.unParkVehicle(VehicleType.MOTORCYCLE, parkingMotorCycle2);
			Assert.assertTrue(isTrue);
			String pattern = "dd-MM-yyyy HH:mm:ss";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
			Date entryDate = simpleDateFormat.parse("29-05-2022 14:44:07");
			Date exitDate = simpleDateFormat.parse("29-05-2022 15:40:07");
			pricingModel = new MallParkingPricingModel();
			Double price = pricingModel.calculatePrice(VehicleType.MOTORCYCLE,entryDate, exitDate);
			Assert.assertEquals( "10.0", String.valueOf(price));

			//park vehicle in spot 2 where unparked earlier
			Vehicle vehicle3 = new Vehicle();
			vehicle3.setRegisterNumber("12345");
			ParkingSpot parkingMotorCycle3  = parking.parkVehicle(VehicleType.MOTORCYCLE, vehicle3);
			Assert.assertTrue(parkingMotorCycle3.getSpotNumber() == 2);
			Ticket ticket3 = parking.createEntryTicket(parkingMotorCycle3);
			Assert.assertTrue(ticket.getParkingSpot().getSpotNumber().equals(2));

			//unpark first vehicle and calculate fee to pay
			parking.unParkVehicle(VehicleType.MOTORCYCLE, parkingMotorCycle1);
			Date firstMotorCycleEntryDate = simpleDateFormat.parse("29-05-2022 14:04:07");
			Date firstMotorCycleExitDate = simpleDateFormat.parse("29-05-2022 17:44:07");
			Double firstVehicleParkingFee = pricingModel.calculatePrice(VehicleType.MOTORCYCLE,firstMotorCycleEntryDate, firstMotorCycleExitDate);
			Assert.assertEquals( "40.0", String.valueOf(firstVehicleParkingFee));

	}

}
