package c.measurement;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import c.account.Account;

@SuppressWarnings("serial")
@Entity
@Table(name = "Measurement")
public class Measurement implements java.io.Serializable {

	@Id
	@Column(name = "MeasureId")
	@GeneratedValue
	private Long id;

	@Column()
	private Timestamp date;

	@ManyToOne(cascade={CascadeType.ALL})
	@JoinColumn(name="AccountId")
	private Account acc;
	
	@Column(name = "DATA", unique = false, length = 100000)
	private byte[] data;
	protected Measurement(){
		
	};
	public Measurement(byte data[], Account user) {
		date = new Timestamp(System.currentTimeMillis());
		setAcc(user);
		this.setData(data);
	}

	public Long getId() {
		return id;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public Account getAcc() {
		return acc;
	}

	public void setAcc(Account acc) {
		this.acc = acc;
	}
}
