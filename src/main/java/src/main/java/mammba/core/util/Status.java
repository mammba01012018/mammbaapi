package src.main.java.mammba.core.util;

public enum Status {
	BOOKED("Booked"), APPROVED("Approved"), FORPAYMENT("For Payment"), CANCELLED("Cancelled"), PAID("Paid");

	private String strStatus;

	Status(String status) {
		this.strStatus = status;
	}

	@Override
	public String toString() {
		return this.strStatus;
	}
}