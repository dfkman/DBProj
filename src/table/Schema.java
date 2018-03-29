package table;

import java.util.ArrayList;

public class Schema
{
	private ArrayList<Property> properties;
	private ArrayList<Customer> customers;
	private ArrayList<Employee> employees;
	private ArrayList<Sale> sales;
	private ArrayList<Appointment> appointments;
	
	public Schema(){
		load();
		//create schema here
	}
	
	
	private void load(){
		//Load from sql here
	}
	
}
