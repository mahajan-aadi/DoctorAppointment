package com.amdocs.dao;

import java.sql.SQLException;
import java.util.List;

import com.amdocs.entity.Customer;
import com.amdocs.exceptions.CustomerNotFoundException;

public interface CustomerDao 
{
	boolean register() throws SQLException, NumberFormatException;
	Customer modify() throws CustomerNotFoundException, SQLException, NumberFormatException;
	void delete() throws CustomerNotFoundException, SQLException,NumberFormatException;
	Customer singleDisplay() throws SQLException, CustomerNotFoundException, NumberFormatException;
	List<Customer> displayAll() throws SQLException;
}