package br.com.ticketservice.search;

public class Restriction {

	public String name;
	
	public Restriction(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object obj) {
		if ( obj == null )
			return false;
		return this.name.equals( ((Restriction) obj).name);
	}
	
}
