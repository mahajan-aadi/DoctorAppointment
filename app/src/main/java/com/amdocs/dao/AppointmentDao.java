package com.amdocs.dao;

import java.sql.SQLException;
import java.util.List;

import com.amdocs.entity.Appointments;
import com.amdocs.exceptions.AppointmentAlreadyThereException;
import com.amdocs.exceptions.AppointmentNotFoundException;
import com.amdocs.exceptions.CustomerNotFoundException;
import com.amdocs.exceptions.DoctorAlreadyBookedException;
import com.amdocs.exceptions.DoctorNotFoundException;

public interface AppointmentDao 
{	
	Appointments booking() throws SQLException,CustomerNotFoundException, AppointmentAlreadyThereException,NumberFormatException;
	Appointments modify() throws SQLException, AppointmentNotFoundException, CustomerNotFoundException,DoctorNotFoundException, NumberFormatException,DoctorAlreadyBookedException;
	void delete() throws SQLException,CustomerNotFoundException, AppointmentNotFoundException,NumberFormatException;
	Appointments displaySingle() throws SQLException, AppointmentNotFoundException,CustomerNotFoundException,NumberFormatException;
	List<Appointments> displayAll() throws SQLException;
}
