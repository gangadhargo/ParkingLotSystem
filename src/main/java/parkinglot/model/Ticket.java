package parkinglot.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
/**
 * Class to define ticket on user parking the vehicle
 * **/

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ticket {
	private Integer ticketNumber;
	private ParkingSpot parkingSpot;
	private Date entryDateTime;
}
