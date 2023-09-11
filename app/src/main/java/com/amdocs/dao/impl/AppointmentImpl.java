package com.amdocs.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import com.amdocs.dao.AppointmentDao;
import com.amdocs.entity.Appointments;
import com.amdocs.exceptions.AppointmentAlreadyThereException;
import com.amdocs.exceptions.AppointmentNotFoundException;
import com.amdocs.exceptions.CustomerNotFoundException;
import com.amdocs.exceptions.DoctorAlreadyBookedException;
import com.amdocs.exceptions.DoctorNotFoundException;
import com.amdocs.util.DBUtil;

public class AppointmentImpl implements AppointmentDao
{
	private static final String RANDOM_ID="SELECT ID FROM DOCTORS WHERE CUSTOMERID IS NULL ORDER BY RAND() LIMIT 1";
	private static final String INSERT_QUERY_DOCTOR="UPDATE DOCTORS SET CUSTOMERID=? WHERE ID=?";
	private static final String INSERT_QUERY_CUSTOMER="UPDATE CUSTOMER SET DOCTORID=? WHERE ID=?";
	private static final String DELETE_QUERY_DOCTOR="UPDATE DOCTORS SET CUSTOMERID=NULL WHERE ID=?";
	private static final String DELETE_QUERY_CUSTOMER="UPDATE CUSTOMER SET DOCTORID=NULL WHERE ID=?";
	private static final String FETCH_DATA="SELECT C.*,D.* FROM CUSTOMER C, DOCTORS D WHERE C.ID=D.CUSTOMERID AND C.ID=?";
	private static final String CHECK_CUSTOMER_ID="SELECT *  FROM CUSTOMER WHERE ID=?";
	private static final String CHECK_DOCTOR_ID="SELECT *  FROM DOCTORS WHERE ID=?";
	private static final String DISPLAY_ALL_QUERY="SELECT C.*,D.* FROM CUSTOMER C, DOCTORS D WHERE C.ID=D.CUSTOMERID";
	

	private  Connection connection=DBUtil.getConnection();
	private Scanner scanner=new Scanner(System.in);
	@Override
	public Appointments booking() throws SQLException,CustomerNotFoundException, AppointmentAlreadyThereException, NumberFormatException
	{
		System.out.println("Enter ID of which appointment you want to add");
		int customerID;
		try {
			customerID= Integer.parseInt(scanner.nextLine());
		}
		catch(NumberFormatException e)
		{
			throw new NumberFormatException("Enter valid number");
		}
		
		PreparedStatement ps = connection.prepareStatement(CHECK_CUSTOMER_ID);
		ps.setInt(1,customerID);
		ResultSet rs = ps.executeQuery();
		if(!rs.next())
		{
			throw new CustomerNotFoundException("Customer Not Found With Id: " + customerID);
		}
		int doctorID=rs.getInt("DOCTORID");
		if(!rs.wasNull())
			throw new AppointmentAlreadyThereException("Appointment already there of Id: " + customerID);
		
		ps=connection.prepareStatement(RANDOM_ID);
		rs=ps.executeQuery();
		rs.next();
		doctorID=rs.getInt("id");
		
		ps=connection.prepareStatement(INSERT_QUERY_CUSTOMER);
		ps.setInt(1, doctorID);
		ps.setInt(2, customerID);
		ps.executeUpdate();
		
		ps=connection.prepareStatement(INSERT_QUERY_DOCTOR);
		ps.setInt(1, customerID);
		ps.setInt(2, doctorID);
		ps.executeUpdate();
		
		ps=connection.prepareStatement(FETCH_DATA);
		ps.setInt(1, customerID);
		rs=ps.executeQuery();
		rs.next();
		Appointments appointments=new Appointments();
		appointments.setCustomerName(rs.getString("c.name"));
		appointments.setCustomerAddress(rs.getString("c.address"));
		appointments.setCustomerEmail(rs.getString("c.email"));
		appointments.setCustomerNumber(rs.getInt("c.number"));
		appointments.setCustomerAge(rs.getInt("c.age"));
		appointments.setDoctorName(rs.getString("d.name"));
		appointments.setDoctorNumber(rs.getInt("c.number"));

		ps.close();
		rs.close();
		return appointments;
	}
	@Override
	public Appointments displaySingle() throws SQLException, AppointmentNotFoundException,CustomerNotFoundException, NumberFormatException
	{
		System.out.println("Enter ID of which appointment you want to see");
		int customerID;
		try {
			customerID= Integer.parseInt(scanner.nextLine());
		}
		catch(NumberFormatException e)
		{
			throw new NumberFormatException("Enter valid number");
		}
	
		PreparedStatement ps = connection.prepareStatement(CHECK_CUSTOMER_ID);
		ps.setInt(1,customerID);
		ResultSet rs = ps.executeQuery();
		if(!rs.next())
		{
			throw new CustomerNotFoundException("Customer Not Found With Id: " + customerID);
		}
		
		ps=connection.prepareStatement(FETCH_DATA);
		ps.setInt(1, customerID);
		rs=ps.executeQuery();
		Appointments appointments=new Appointments();		
		if(rs.next()) 
		{
			appointments.setCustomerName(rs.getString("c.name"));
			appointments.setCustomerAddress(rs.getString("c.address"));
			appointments.setCustomerEmail(rs.getString("c.email"));
			appointments.setCustomerNumber(rs.getInt("c.number"));
			appointments.setCustomerAge(rs.getInt("c.age"));
			appointments.setDoctorName(rs.getString("d.name"));
			appointments.setDoctorNumber(rs.getInt("c.number"));
		}
		else
		{
			throw new AppointmentNotFoundException("No Appointments with ID:"+customerID);
		}
		ps.close();
		rs.close();
		return appointments;

	}
	@Override
	public List<Appointments> displayAll() throws SQLException{
		List<Appointments> appointmentsList = new ArrayList<Appointments>();
		Statement stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery(DISPLAY_ALL_QUERY);
		while (rs.next()) {
			Appointments appointments=new Appointments();
			appointments.setCustomerName(rs.getString("c.name"));
			appointments.setCustomerAddress(rs.getString("c.address"));
			appointments.setCustomerEmail(rs.getString("c.email"));
			appointments.setCustomerNumber(rs.getInt("c.number"));
			appointments.setCustomerAge(rs.getInt("c.age"));
			appointments.setDoctorName(rs.getString("d.name"));
			appointments.setDoctorNumber(rs.getInt("c.number"));
			appointmentsList.add(appointments);
		}
		rs.close();
		stmt.close();
		return appointmentsList;
	}
	@Override
	public void delete() throws CustomerNotFoundException, SQLException, AppointmentNotFoundException, NumberFormatException
	{
		System.out.println("Enter ID of which appointment you want to modify");
		int customerID;
		try {
			customerID= Integer.parseInt(scanner.nextLine());
		}
		catch(NumberFormatException e)
		{
			throw new NumberFormatException("Enter valid number");
		}
		
		PreparedStatement ps = connection.prepareStatement(CHECK_CUSTOMER_ID);
		ps.setInt(1,customerID);
		ResultSet rs = ps.executeQuery();
		if(!rs.next())
		{
			throw new CustomerNotFoundException("Customer Not Found With Id: " + customerID);
		}
		int doctorID=rs.getInt("doctorID");
		if(rs.wasNull())
		{
			throw new AppointmentNotFoundException("No Appointments with ID:"+customerID);
		}
		ps=connection.prepareStatement(DELETE_QUERY_DOCTOR);
		ps.setInt(1, doctorID);
		ps.executeUpdate();
		
		ps=connection.prepareStatement(DELETE_QUERY_CUSTOMER);
		ps.setInt(1, customerID);
		ps.executeUpdate();
		
		ps.close();
		rs.close();
	}
	@Override
	public Appointments modify() throws SQLException,CustomerNotFoundException, AppointmentNotFoundException, DoctorNotFoundException, DoctorAlreadyBookedException, NumberFormatException
	{
		System.out.println("Enter ID of which appointment you want to modify");
		int customerID;
		try {
			customerID= Integer.parseInt(scanner.nextLine());
		}
		catch(NumberFormatException e)
		{
			throw new NumberFormatException("Enter valid number");
		}

		
		PreparedStatement ps = connection.prepareStatement(CHECK_CUSTOMER_ID);
		ps.setInt(1,customerID);
		ResultSet rs = ps.executeQuery();
		if(!rs.next())
		{
			throw new CustomerNotFoundException("Customer Not Found With Id: " + customerID);
		}
		int doctorID=rs.getInt("doctorID");
		if(rs.wasNull())
		{
			throw new AppointmentNotFoundException("No Appointments with ID:"+customerID);
		}
		
		System.out.println("Enter the new Doctor ID:");
		int newDoctorID;
		try {
			newDoctorID= Integer.parseInt(scanner.nextLine());
		}
		catch(NumberFormatException e)
		{
			throw new NumberFormatException("Enter valid number");
		}


		ps = connection.prepareStatement(CHECK_DOCTOR_ID);
		ps.setInt(1,newDoctorID);
		rs = ps.executeQuery();
		if(!rs.next())
		{
			throw new DoctorNotFoundException("Doctor Not Found With Id: " + newDoctorID);
		}
		int tempCustomerID=rs.getInt("CUSTOMERID");
		if(!rs.wasNull())
			throw new DoctorAlreadyBookedException("Doctor Already Booked");

		ps=connection.prepareStatement(INSERT_QUERY_CUSTOMER);
		ps.setInt(1, newDoctorID);
		ps.setInt(2, customerID);
		ps.executeUpdate();
		
		ps=connection.prepareStatement(DELETE_QUERY_DOCTOR);
		ps.setInt(1, doctorID);
		ps.executeUpdate();

		
		ps=connection.prepareStatement(INSERT_QUERY_DOCTOR);
		ps.setInt(1, customerID);
		ps.setInt(2, newDoctorID);
		ps.executeUpdate();
		
		ps=connection.prepareStatement(FETCH_DATA);
		ps.setInt(1, customerID);
		rs=ps.executeQuery();
		rs.next();
		Appointments appointments=new Appointments();
		appointments.setCustomerName(rs.getString("c.name"));
		appointments.setCustomerAddress(rs.getString("c.address"));
		appointments.setCustomerEmail(rs.getString("c.email"));
		appointments.setCustomerNumber(rs.getInt("c.number"));
		appointments.setCustomerAge(rs.getInt("c.age"));
		appointments.setDoctorName(rs.getString("d.name"));
		appointments.setDoctorNumber(rs.getInt("c.number"));

		ps.close();
		rs.close();
		return appointments;
	}

}
