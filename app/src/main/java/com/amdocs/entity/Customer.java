package com.amdocs.entity;


public class Customer 
{
	private String name;
	private int number;	
	private int id;
	private String address;
	private String email;
	private int age;
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Customer()
	{
		
	}
	public int getid()
	{
		return id;
	}
	public void setid(int id)
	{
		this.id = id;
	}
	public int getNumber()
	{
		return number;
	}
	public void setNumber(int number)
	{
		this.number = number;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name) 
	{
		this.name = name;
	}
	@Override
	public String toString() {
		return "Customer [name=" + name + ", number=" + number + ", id=" + id + ", address=" + address + ", email="
				+ email + ", age=" + age + "]";
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public Customer(String name, int number, String address, String email, int age) {
		super();
		this.name = name;
		this.number = number;
		this.address = address;
		this.email = email;
		this.age = age;
	}


}
