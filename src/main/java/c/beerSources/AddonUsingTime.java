package c.beerSources;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import c.brew.BrewAddon;

@SuppressWarnings("serial")
@Entity
@Table(name = "AddonUsingTime")
public class AddonUsingTime implements java.io.Serializable {
	@Id
	@Column(name = "ID")
	@GeneratedValue
	private Long id;

	@Column
	private String name;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "addonUsingTime")
	private List<BrewAddon> brewAddons = new ArrayList<BrewAddon>();
	
	public Long getId() {
		return id;
	}

	public void setId(Long addonUsingId) {
		this.id = addonUsingId;
	}

	public String getName(){
		return name;
	}

	public void setName(String timeName) {
		this.name = timeName;
	}
	
	public AddonUsingTime(String timeName){
		this.name=timeName;
	}
	protected AddonUsingTime(){};
}
