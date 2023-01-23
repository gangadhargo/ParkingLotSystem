package parkinglot.pricing;

import java.util.*;
import java.util.stream.Collectors;

import parkinglot.model.FeeDetails;
import parkinglot.model.Ticket;
import parkinglot.model.VehicleType;
import parkinglot.utils.ParkingLotUtils;
/*
 * class to define mall parking pricing
 * */
public class MallParkingPricingModel implements PricingModel{
	private static Map<VehicleType, List<FeeDetails>> feeDetailsBaseOnHourAndVehicle = new HashMap<>();

	public MallParkingPricingModel(){
		List<FeeDetails> motorVehicleFees = new ArrayList<>();
		motorVehicleFees
				.add(new FeeDetails(0, 1, 10d));

		List<FeeDetails> carFees = new ArrayList<>();
		carFees.add(new FeeDetails(0, 1, 20d));

		List<FeeDetails> busFees = new ArrayList<>();
		busFees.add(new FeeDetails(1, 1, 50d));

		feeDetailsBaseOnHourAndVehicle.put(VehicleType.MOTORCYCLE, motorVehicleFees);
		feeDetailsBaseOnHourAndVehicle.put(VehicleType.CAR, carFees);
		feeDetailsBaseOnHourAndVehicle.put(VehicleType.BUS, busFees);
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
		while(hours>=0) {
			for (FeeDetails feeDetails : feeDetailsList) {
				price += feeDetails.getPrice();
				hours-= feeDetails.getEndHour();
				break;
			}
		}
		return price;
	}
}
