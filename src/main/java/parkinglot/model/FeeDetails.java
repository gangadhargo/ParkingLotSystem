package parkinglot.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/*
 * Class to define Parking Fee on hour basis
 * */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeeDetails {
	private int startHour;
	private int endHour;
	private Double price;
}
