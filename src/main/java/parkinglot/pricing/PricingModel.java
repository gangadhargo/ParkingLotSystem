package parkinglot.pricing;

import java.util.Date;
import java.util.List;
import java.util.Map;

import parkinglot.model.FeeDetails;
import parkinglot.model.Ticket;
import parkinglot.model.VehicleType;

/*
* Interface defines to implement various parking pricing models such as hourly daily, pricing calculations
* */
public interface PricingModel {
	
	void addHourlyPricing(VehicleType vehicleType, Ticket ticket);
	Map<VehicleType, List<FeeDetails>> getAllPricing();
	List<FeeDetails> getHourlyPricingByVehicleType(VehicleType vehicleType);

	Double calculatePrice(VehicleType vehicleType, Date startTime, Date endTime);

}
