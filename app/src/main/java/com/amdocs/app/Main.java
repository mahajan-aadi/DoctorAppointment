package com.amdocs.app;

import java.sql.SQLException;
import java.util.*;

import com.amdocs.dao.AppointmentDao;
import com.amdocs.dao.CustomerDao;
import com.amdocs.dao.impl.AppointmentImpl;
import com.amdocs.dao.impl.CustomerImpl;
import com.amdocs.entity.Appointments;
import com.amdocs.entity.Customer;
import com.amdocs.exceptions.AppointmentAlreadyThereException;
import com.amdocs.exceptions.AppointmentNotFoundException;
import com.amdocs.exceptions.CustomerNotFoundException;
import com.amdocs.exceptions.DoctorAlreadyBookedException;
import com.amdocs.exceptions.DoctorNotFoundException;

public class Main 
{
	private static Scanner scanner=new Scanner(System.in);
	private static CustomerDao customerdao=new CustomerImpl();
	private static AppointmentDao appointmenrdao=new AppointmentImpl();
    public static void main( String[] args )
    {
        mainFunction();
    }

	public static void mainFunction() 
	{
		while(true)
		{
		System.out.print("1. Customer\n2. Appointment \n3. View Appointment\n0. Exit\n");
        int i=Integer.parseInt(scanner.nextLine());
        if(i==1)
        	customer();
        else if(i==2)
        	appointment();
        else if(i==0)
        {
        	System.out.println("Exiting");
        	System.exit(0);
        }
        else
        	System.out.print("ENTER VALID INPUT\n");
		}

	}
	static void appointment() 
	{
		while(true)
		{
			System.out.print("1. Book\n2. Modify \n3. Delete\n4. View Single \n5. View All \n0. Exit\n");
	        int i=Integer.parseInt(scanner.nextLine());
	        switch (i) 
	        {
			case 1:
				System.out.println("****Booking started****");
				bookAppointment();
				System.out.println("****Booking ended****");
				break;
			case 2:
				System.out.println("****Modifying started****");
				modifyAppointment();
				System.out.println("****Modifying ended****");
				break;
			case 3:
				System.out.println("****Deleting started****");
				deleteAppointment();
				System.out.println("****Deleting ended****");
				break;
			case 4:
				System.out.println("Displaying Appointments");
				displaySingleAppointment();
				break;
			case 5:
				System.out.println("Displaying All Appointments");
				displayAllAppointment();
				break;

			case 0:
				mainFunction();
				break;
			default:
				break;
			}
		}
	}
	private static void displayAllAppointment()
	{
		try {
			List<Appointments> displayAll = appointmenrdao.displayAll();
			for (Appointments appointments : displayAll) {
				System.out.println(appointments);
			}
		} catch (SQLException e) {
			System.err.println(e);
		}

		
	}

	private static void displaySingleAppointment() 
	{
		try {
			Appointments appointments = appointmenrdao.displaySingle();
			System.out.println(appointments);
		} catch (NumberFormatException|CustomerNotFoundException | AppointmentNotFoundException|SQLException e) {
			System.out.println(e);
		}

		
	}

	private static void deleteAppointment() {
		try {
			appointmenrdao.delete();
			System.out.println("Deleted");
		} catch (NumberFormatException|AppointmentNotFoundException|CustomerNotFoundException |SQLException e) {
			System.err.println(e);
		}
		
	}

	private static void modifyAppointment() {
		try {
			Appointments appointment=appointmenrdao.modify();
			System.out.println(appointment);
		} catch ( NumberFormatException|DoctorAlreadyBookedException|AppointmentNotFoundException|CustomerNotFoundException | DoctorNotFoundException|SQLException e) {
			System.err.println(e);
		}

	}

	private static void bookAppointment() 
	{
		try {
			Appointments appointment=appointmenrdao.booking();
			System.out.println(appointment);
		} catch (CustomerNotFoundException|AppointmentAlreadyThereException| NumberFormatException|SQLException e) {
			System.err.println(e);
		}
	}

	private static void customer() 
	{
		while(true)
		{
		System.out.print("1. Register\n2. Modify \n3. Delete\n4. View Single \n5. View All \n0. Exit\n");
		int i=Integer.parseInt(scanner.nextLine());
		
		switch(i)
		{
		case 1:
			System.out.println("****Adding Customer Started****");
			addCustomer();
			System.out.println("****Adding Customer Ended****");
			break;
		case 2:
			System.out.println("****Modifying Started****");
			modifyCustomer();
			System.out.println("****Modifying Ended****");
			break;
		case 3:
			customerDelete();
			break;
		case 4:
			System.out.println("Displaying Customer");
			displayCustomer();
			break;
		case 5:
			System.out.println("Displaying All Customers");
			displayAllCustomers();
			break;
		case 0:
			mainFunction();
			break;
		default:
			continue;
		}

		}
	}

	private static void customerDelete() {
		try {
			customerdao.delete();
			System.out.println("Deleted");
		} catch (CustomerNotFoundException |NumberFormatException|SQLException e) {
			System.err.println(e);
		}

		
	}

	private static void displayCustomer() {
		try {
			Customer customer = customerdao.singleDisplay();
				System.out.println(customer);
		} catch (CustomerNotFoundException | NumberFormatException |SQLException e) {
			System.out.println(e);
		}
		
	}

	private static void modifyCustomer()
	{
		try {
			Customer modifiedCustomer=customerdao.modify();
			System.out.println(modifiedCustomer);
		} catch (CustomerNotFoundException |NumberFormatException| SQLException e) {
			System.err.println(e);
		}
	}

	private static void addCustomer()
	{
		try {
			if(customerdao.register()) {
			System.out.println("Inserted");
			}
		}
		catch (SQLException |NumberFormatException e) {
			System.err.println(e);
		}
		
	}
	private static void displayAllCustomers() {
		try {
			List<Customer> displayAll = customerdao.displayAll();
			for (Customer customer : displayAll) {
				System.out.println(customer);
			}
		} catch (SQLException e) {
			System.err.println(e);
		}
	}

}