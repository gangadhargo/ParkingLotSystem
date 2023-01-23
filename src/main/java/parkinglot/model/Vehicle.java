package parkinglot.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * Class to define user vehicle details
 * **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Vehicle {
	private String registerNumber;
	private String color;
	private String model;
}
