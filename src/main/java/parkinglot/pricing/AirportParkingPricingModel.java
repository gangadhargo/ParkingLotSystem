package parkinglot.pricing;

import java.util.*;
import java.util.stream.Collectors;

import parkinglot.model.FeeDetails;
import parkinglot.model.Ticket;
import parkinglot.model.VehicleType;
import parkinglot.utils.ParkingLotUtils;

/*
* class to define airport parking pricing
* */
public class AirportParkingPricingModel implements PricingModel{
	private static Map<VehicleType, List<FeeDetails>> feeDetailsBaseOnHourAndVehicle = new HashMap<>();

	public AirportParkingPricingModel() {
		List<FeeDetails> motorVehicleFees = new ArrayList<>();
		motorVehicleFees.add(new FeeDetails(0, 1, 0d));
		motorVehicleFees.add(new FeeDetails(1, 8, 40d));
		motorVehicleFees.add(new FeeDetails(8, 24, 60d));
		motorVehicleFees.add(new FeeDetails(24, 24, 80d));

		List<FeeDetails> carFees = new ArrayList<>();
		carFees.add(new FeeDetails(0, 12, 60d));
		carFees.add(new FeeDetails(12, 24, 80d));
		carFees.add(new FeeDetails(24, 24, 100d));
		this.feeDetailsBaseOnHourAndVehicle.put(VehicleType.MOTORCYCLE, motorVehicleFees);
		this.feeDetailsBaseOnHourAndVehicle.put(VehicleType.CAR, carFees);
	}
	
	@Override
	public void addHourlyPricing(VehicleType vehicleType, Ticket ticket) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map<VehicleType, List<FeeDetails>> getAllPricing() {
		// TODO Auto-generated method stub
		return feeDetailsBaseOnHourAndVehicle;
	}

	@Override
	public List<FeeDetails> getHourlyPricingByVehicleType(VehicleType vehicleType) {
		// TODO Auto-generated method stub
		return feeDetailsBaseOnHourAndVehicle.get(vehicleType);
	}

	/*
	* calculate price based on params vehicle type, start time, end time
	* */
	@Override
	public Double calculatePrice(VehicleType vehicleType, Date startTime, Date endTime){
		Integer hours = ParkingLotUtils.getHoursBetweenTwoDates(startTime, endTime);
		List<FeeDetails> feeDetailsList = feeDetailsBaseOnHourAndVehicle.get(vehicleType);
		Double price = 0.0;
		Double oneDayPrice = 0.0;
		if(hours.intValue() >= 24) {
			for (FeeDetails feeDetails : feeDetailsList) {
				if (feeDetails.getStartHour() >= 24 && feeDetails.getEndHour() >= 24) {
					oneDayPrice = feeDetails.getPrice();
					break;
				}
			}
			while(hours >= 0){
				price+= oneDayPrice;
				hours-= 24;
			}
		}else {
			for (FeeDetails feeDetails : feeDetailsList) {
				if (hours >= feeDetails.getStartHour() && hours < feeDetails.getEndHour()) {
					price += feeDetails.getPrice();
					break;
				}
			}
		}
		return price;
	}

}
