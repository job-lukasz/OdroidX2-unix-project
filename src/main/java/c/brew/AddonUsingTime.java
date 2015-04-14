package c.brew;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "AddonUsingTime")
public class AddonUsingTime implements java.io.Serializable {
	@Id
	@Column(name = "ID")
	@GeneratedValue
	private Long addonUsingId;

	@Column
	private String timeName;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "usingTime")
	private List<BrewAddon> brewAddons = new ArrayList<BrewAddon>();
	
	public List<BrewAddon> getBrewAddons() {
		return brewAddons;
	}

	public void setBrewAddons(List<BrewAddon> brewAddons) {
		this.brewAddons = brewAddons;
	}

	public Long getAddonUsingId() {
		return addonUsingId;
	}

	public void setAddonUsingId(Long addonUsingId) {
		this.addonUsingId = addonUsingId;
	}

	public String getTimeName() {
		return timeName;
	}

	public void setTimeName(String timeName) {
		this.timeName = timeName;
	}
}
