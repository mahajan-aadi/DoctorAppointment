package com.amdocs.entity;

public class Appointments 
{
	private String customerName;
	private int customerNumber;	
	private int customerAge;
	private String customerAddress;
	private String customerEmail;
	public int getCustomerAge() {
		return customerAge;
	}
	public void setCustomerAge(int customerAge) {
		this.customerAge = customerAge;
	}
	public String getCustomerAddress() {
		return customerAddress;
	}
	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}
	public String getCustomerEmail() {
		return customerEmail;
	}
	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}
	private String doctorName;
	private int doctorNumber;	
	public Appointments()
	{
		
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public int getCustomerNumber() {
		return customerNumber;
	}
	public void setCustomerNumber(int customerNumber) {
		this.customerNumber = customerNumber;
	}
	public String getDoctorName() {
		return doctorName;
	}
	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}
	public int getDoctorNumber() {
		return doctorNumber;
	}
	public void setDoctorNumber(int doctorNumber) {
		this.doctorNumber = doctorNumber;
	}
	@Override
	public String toString() {
		return "Appointments [customerName=" + customerName + ", customerNumber=" + customerNumber + ", customerAge="
				+ customerAge + ", customerAddress=" + customerAddress + ", customerEmail=" + customerEmail
				+ ", doctorName=" + doctorName + ", doctorNumber=" + doctorNumber + "]";
	}

}
