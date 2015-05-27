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

import c.brew.Brewing;

@SuppressWarnings("serial")
@Entity
@Table(name = "Yeast")
public class Yeast implements java.io.Serializable {

	@Id
	@Column(name = "ID")
	@GeneratedValue
	private Long id;

	@Column
	private String name;

	@Column
	private String description;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "yeast")
	private List<Brewing> brewing = new ArrayList<Brewing>();

	public Yeast(String name, String description) {
		this.name = name;
		this.description = description;
	}

	protected Yeast() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
