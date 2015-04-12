package c.beerSources;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "Addons")
public class Addons implements java.io.Serializable {
	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	@Id
	@Column(name = "ID")
	@GeneratedValue
	private Long id;

	@Column
	private String name;

	@Column
	private String info;
	
	@Column
	private String description;
	
	public Addons(String name, String info){
		this.name=name;
		this.info = info;
		this.description = "";
	}
	
	protected Addons(){
		
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
