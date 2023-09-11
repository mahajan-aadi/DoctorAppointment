package com.amdocs.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import com.amdocs.dao.CustomerDao;
import com.amdocs.entity.Customer;
import com.amdocs.exceptions.CustomerNotFoundException;
import com.amdocs.util.DBUtil;

public class CustomerImpl implements CustomerDao
{
	private static final String INSERT_QUERY="INSERT INTO CUSTOMER(name, number, age, email, address) VALUES (?,?,?,?,?)";
	private static final String MODIFY_NUMBER_QUERY ="UPDATE CUSTOMER SET number=? WHERE id=?";
	private static final String MODIFY_NAME_QUERY ="UPDATE CUSTOMER SET name=? WHERE id=?";
	private static final String MODIFY_AGE_QUERY ="UPDATE CUSTOMER SET age=? WHERE id=?";
	private static final String MODIFY_ADDREDSS_QUERY ="UPDATE CUSTOMER SET address=? WHERE id=?";
	private static final String MODIFY_EMAIL_QUERY ="UPDATE CUSTOMER SET email=? WHERE id=?";
	private static final String DELETE_QUERY ="DELETE FROM CUSTOMER WHERE id=?";
	private static final String DISPLAY_ALL_QUERY="SELECT *  FROM CUSTOMER";
	private static final String DISPLAY_SINGLE_QUERY="SELECT *  FROM CUSTOMER WHERE ID=?";
	private  Connection connection=DBUtil.getConnection();
	
	Scanner scanner = new Scanner(System.in);

	@Override
	public boolean register() throws SQLException, NumberFormatException
	{
		int number,age;
		boolean result = false;
		System.out.println("\nEnter Name:");
		String name = scanner.nextLine();
		System.out.println("\nEnter Address:");
		String address = scanner.nextLine();
		System.out.println("\nEnter Email:");
		String email = scanner.nextLine();
		System.out.println("\nEnter Number:");
		try
		{
			number = Integer.parseInt(scanner.nextLine());
		}
		catch(NumberFormatException e)
		{
			throw new NumberFormatException("Enter a valid number");
		}
		
		System.out.println("Enter Age:");
		
		try
		{
			age = Integer.parseInt(scanner.nextLine());
		}
		catch(NumberFormatException e)
		{
			throw new NumberFormatException("Enter a valid number");
		}

		PreparedStatement ps=connection.prepareStatement(INSERT_QUERY);
		
		ps.setString(1,name);
		ps.setInt(2,number);
		ps.setInt(3,age);
		ps.setString(4,email);
		ps.setString(5,address);
		
		if(ps.executeUpdate()>1)
			result=true;
		ps.close();
		return result;
	
	}
	public Customer modify() throws CustomerNotFoundException, SQLException, NumberFormatException
	{
		System.out.println("Enter ID you want to modify");
		int id;
		try {
			id=Integer.parseInt(scanner.nextLine());}
		catch(NumberFormatException e) {
			throw new NumberFormatException("Enter a valid number");}
		
		PreparedStatement stmt = connection.prepareStatement(DISPLAY_SINGLE_QUERY);
		stmt.setInt(1, id);
		ResultSet rs = stmt.executeQuery();
		
		if(!rs.next()) 
			throw new CustomerNotFoundException("Customer Not Found With Id: " + id);
		rs.close();
		stmt.close();
		System.out.println("What Do You Want TO Modify?\n1. Name \n2. Number \n3. Age \n4. Email \n5. Address");
		int option;
		try {
			option=Integer.parseInt(scanner.nextLine());
		}
		catch(NumberFormatException e )
		{
			throw new NumberFormatException("Enter a valid number");
		}
		if(option ==1)
		{
			System.out.println("Enter Name:");
			String name=scanner.nextLine();
			modifyByName(id, name);
		}
		else if(option==2)
		{
			System.out.println("Enter number:");
			try {
				int number=Integer.parseInt(scanner.nextLine());
				modifyByNumber(id, number);
			}
			catch(NumberFormatException e)
			{
				throw new NumberFormatException("Enter a valid number");
			}
		}
		else if(option==3)
		{
			System.out.println("Enter age:");
			try {
				int age=Integer.parseInt(scanner.nextLine());
				modifyByAge(id, age);
			}
			catch(NumberFormatException e)
			{
				throw new NumberFormatException("Enter a valid number");
			}
		}
		else if(option ==4)
		{
			System.out.println("Enter Email:");
			String email=scanner.nextLine();
			modifyByEmail(id, email);
		}
		else if(option ==5)
		{
			System.out.println("Enter Address:");
			String address=scanner.nextLine();
			modifyByAddress(id, address);
		}
		else
		{
			throw new NumberFormatException("Enter a valid number");
		}
		
		stmt = connection.prepareStatement(DISPLAY_SINGLE_QUERY);
		stmt.setInt(1, id);
		rs = stmt.executeQuery();
		rs.next();
		Customer customer = new Customer();
		customer.setid(rs.getInt("ID"));
		customer.setName(rs.getString("name"));
		customer.setNumber(rs.getInt("number"));
		customer.setAge(rs.getInt("age"));
		customer.setAddress(rs.getString("address"));
		customer.setEmail(rs.getString("email"));
		rs.close();
		stmt.close();
		
		return customer;
		

	}
	void modifyByAddress(int id,String address) throws CustomerNotFoundException, SQLException 
	{
		String query = MODIFY_ADDREDSS_QUERY;
		PreparedStatement stmt = connection.prepareStatement(query);
		stmt.setString(1, address);
		stmt.setInt(2, id);
		stmt.execute();		
		stmt.close();
	}
	void modifyByEmail(int id,String email) throws CustomerNotFoundException, SQLException 
	{
		String query = MODIFY_EMAIL_QUERY;
		PreparedStatement stmt = connection.prepareStatement(query);
		stmt.setString(1, email);
		stmt.setInt(2, id);
		stmt.execute();		
		stmt.close();
	}
	void modifyByName(int id,String name) throws CustomerNotFoundException, SQLException 
	{
		String query = MODIFY_NAME_QUERY;
		PreparedStatement stmt = connection.prepareStatement(query);
		stmt.setString(1, name);
		stmt.setInt(2, id);
		stmt.execute();		
		stmt.close();
	}
	void modifyByNumber(int id,int number) throws CustomerNotFoundException, SQLException 
	{
		String query = MODIFY_NUMBER_QUERY;
		PreparedStatement stmt = connection.prepareStatement(query);
		stmt.setInt(1, number);
		stmt.setInt(2, id);
		stmt.execute();
		stmt.close();
	}
	void modifyByAge(int id,int age) throws CustomerNotFoundException, SQLException 
	{
		String query = MODIFY_AGE_QUERY;
		PreparedStatement stmt = connection.prepareStatement(query);
		stmt.setInt(1, age);
		stmt.setInt(2, id);
		stmt.execute();
		stmt.close();
	}
	public void delete() throws CustomerNotFoundException, SQLException, NumberFormatException
	{
		System.out.println("Enter ID:");
		int id;
		try {
			id= Integer.parseInt(scanner.nextLine());
		}
		catch(NumberFormatException e)
		{
			throw new NumberFormatException("Enter valid number");
		}
				
		System.out.println("****Deleting Started***");
		
		PreparedStatement stmt = connection.prepareStatement(DISPLAY_SINGLE_QUERY);
		stmt.setInt(1, id);
		ResultSet rs = stmt.executeQuery();
		
		if(!rs.next()) 
			throw new CustomerNotFoundException("Customer Not Found With Id: " + id);
		
		stmt = connection.prepareStatement(DELETE_QUERY);
		stmt.setInt(1, id);
		stmt.execute();
		
		rs.close();
		stmt.close();
		System.out.println("****Deleting Ended****");
	}
	@Override
	public Customer singleDisplay() throws SQLException,CustomerNotFoundException, NumberFormatException
	{
		Customer customer = new Customer();
		System.out.println("Enter ID:");
		int id;
		try {
			id= Integer.parseInt(scanner.nextLine());
		}
		catch(NumberFormatException e)
		{
			throw new NumberFormatException("Enter valid number");
		}

		PreparedStatement stmt = connection.prepareStatement(DISPLAY_SINGLE_QUERY);
		stmt.setInt(1, id);
		ResultSet rs = stmt.executeQuery();
		
		if(rs.next()) 
		{
		customer.setid(rs.getInt("ID"));
		customer.setName(rs.getString("name"));
		customer.setNumber(rs.getInt("number"));
		customer.setAge(rs.getInt("age"));
		customer.setAddress(rs.getString("address"));
		customer.setEmail(rs.getString("email"));
		}
		else
		{
			throw new CustomerNotFoundException("Customer Not Found With Id: " + id);
		}
		
		rs.close();
		stmt.close();
		return customer;
	}
	@Override
	public List<Customer> displayAll() throws SQLException
	{
		List<Customer> customers = new ArrayList<>();
		Statement stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery(DISPLAY_ALL_QUERY);
		while (rs.next()) {
			Customer customer = new Customer();
			customer.setid(rs.getInt("ID"));
			customer.setName(rs.getString("name"));
			customer.setNumber(rs.getInt("number"));
			customer.setAge(rs.getInt("age"));
			customer.setAddress(rs.getString("address"));
			customer.setEmail(rs.getString("email"));
			customers.add(customer);
		}
		rs.close();
		stmt.close();
		return customers;
	}
}
