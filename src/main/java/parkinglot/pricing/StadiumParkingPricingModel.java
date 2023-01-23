package parkinglot.pricing;


import parkinglot.model.FeeDetails;
import parkinglot.model.Ticket;
import parkinglot.model.VehicleType;
import parkinglot.utils.ParkingLotUtils;

import java.util.*;
/*
 * class to define stadium parking pricing
 * */

public class StadiumParkingPricingModel implements PricingModel{
    private static Map<VehicleType, List<FeeDetails>> feeDetailsBaseOnHourAndVehicle = new HashMap<>();
   public StadiumParkingPricingModel(){
       List<FeeDetails> motorVehicleFees = new ArrayList<>();
       motorVehicleFees.add(new FeeDetails(0, 4, 30d));
       motorVehicleFees.add(new FeeDetails(4, 12, 60d));
       motorVehicleFees.add(new FeeDetails(12, Integer.MAX_VALUE, 100d));

       List<FeeDetails> carFees = new ArrayList<>();
       carFees.add(new FeeDetails(0, 4, 60d));
       carFees.add(new FeeDetails(4, 12, 120d));
       carFees.add(new FeeDetails(12, Integer.MAX_VALUE, 200d));
       feeDetailsBaseOnHourAndVehicle.put(VehicleType.MOTORCYCLE, motorVehicleFees);
       feeDetailsBaseOnHourAndVehicle.put(VehicleType.CAR, carFees);
   }
    @Override
    public void addHourlyPricing(VehicleType vehicleType, Ticket ticket) {
    }

    @Override
    public Map<VehicleType, List<FeeDetails>> getAllPricing() {
        return feeDetailsBaseOnHourAndVehicle;
    }

    @Override
    public List<FeeDetails> getHourlyPricingByVehicleType(VehicleType vehicleType) {
        return null;
    }
    /*
     * calculate price based on params vehicle type, start time, end time
     * */
    @Override
    public Double calculatePrice(VehicleType vehicleType, Date startTime, Date endTime){
        Integer hours = ParkingLotUtils.getHoursBetweenTwoDates(startTime, endTime);
        List<FeeDetails> feeDetailsList = feeDetailsBaseOnHourAndVehicle.get(vehicleType);
        Double price = 0.0;
        if(feeDetailsList!= null) {
            int j = 0;
            while(j <= hours){
                for (FeeDetails feeDetails : feeDetailsList) {
                    if(j <= hours && j >= feeDetails.getStartHour() && j < feeDetails.getEndHour()){
                        price+= feeDetails.getPrice();
                        if(feeDetails.getEndHour()!= Integer.MAX_VALUE) {
                            j = feeDetails.getEndHour();
                        }else{
                            j += 1;
                        }
                    }
                }
            }
        }
        return price;
    }
}
