package parkinglot.model;

import lombok.Data;

import java.util.Date;
/**
 * Class to user receipt and fees
 * **/
@Data
public class Receipt {
	private String receiptNumber;
	private Date entryDateTime;
	private Date exitDateTime;
	private Double fees;

}
