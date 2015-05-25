package c.brew;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import c.beerSources.Hop;

@SuppressWarnings("serial")
@Entity
@Table(name = "BrewHop")
public class BrewHop implements java.io.Serializable {
	@Id
	@Column(name = "ID")
	@GeneratedValue
	private Long id;

	@ManyToMany(mappedBy = "hops")
	private Set<Brewing> brewing = new HashSet<Brewing>();
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "hopId", nullable = false)
	private Hop hop;
	
	@Column
	private int startMinute;
	
	@Column
	private double quantity;
	
	public BrewHop(Hop hop, int startMinute, double quantity){
		this.hop = hop;
		this.startMinute = startMinute;
		this.quantity = quantity;
	}
	
	protected BrewHop(){
		
	}
	
	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Hop getHop() {
		return hop;
	}

	public void setHop(Hop hop) {
		this.hop = hop;
	}

	public int getStartMinute() {
		return startMinute;
	}

	public void setStartMinute(int startMinute) {
		this.startMinute = startMinute;
	}
	
	public Set<Brewing> getBrewing() {
		return brewing;
	}

	public void setBrewing(Set<Brewing> brewing) {
		this.brewing = brewing;
	}
}
