package org.dasher.speed.views.Main;

import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;


public class TestItem {
	
	private final String name;
	private final int quantity;
	private final double price;
	private final boolean available;
	private final LocalDate date;
	private String description;
	
	public TestItem( String name, int quantity, double price, boolean available, LocalDate date ) {
		this.name = name;
		this.quantity = quantity;
		this.price = price;
		this.available = available;
		this.date = date;
	}
	
	public String getName() {
		return name;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public double getPrice() {
		return price;
	}
	
	public boolean isAvailable() {
		return available;
	}
	
	public LocalDate getDate() {
		return date;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription( String description ) {
		this.description = description;
	}
	
	public static TestItem createRandom() {
		String name = "Item " + ThreadLocalRandom.current().nextInt( 1, 101 );
		int quantity = ThreadLocalRandom.current().nextInt( 1, 100 );
		double price = ThreadLocalRandom.current().nextDouble( 1.0, 100.0 );
		boolean available = ThreadLocalRandom.current().nextBoolean();
		LocalDate date = LocalDate.ofEpochDay( ThreadLocalRandom.current().nextLong( LocalDate.of( 2000, 1, 1 ).toEpochDay(), LocalDate.of( 2023, 12, 31 ).toEpochDay() ) );
		String description = "Random description " + ThreadLocalRandom.current().nextInt( 1, 101 );
		TestItem item = new TestItem( name, quantity, price, available, date );
		item.setDescription( description );
		return item;
	}
	
}
