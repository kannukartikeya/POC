package poc.pbdefault.domain;

import java.util.List;

import javax.persistence.*;

@Entity
public class PDModel {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;

	private int last_fico_range_high;
	private int last_fico_range_low;
	private double revol_util;
	private int inq_last_6mths	;
	private boolean is_rent;


	
	@ManyToMany
	private List<Factors> factors;

	public PDModel() {
		super();
	}

	public PDModel(int last_fico_range_high, int last_fico_range_low, double  revol_util,int inq_last_6mths,boolean is_rent)
	{
		super();
		this.last_fico_range_high = last_fico_range_high;
		this.last_fico_range_low = last_fico_range_low;
		this.revol_util = revol_util;
		this.inq_last_6mths = inq_last_6mths;
		this.is_rent=is_rent;
	}


	public int getLast_fico_range_high() {
		return last_fico_range_high;
	}

	public void setLast_fico_range_high(int last_fico_range_high) {
		this.last_fico_range_high = last_fico_range_high;
	}

	public int getLast_fico_range_low() {
		return last_fico_range_low;
	}

	public void setLast_fico_range_low(int last_fico_range_low) {
		this.last_fico_range_low = last_fico_range_low;
	}

	public double getRevol_util() {
		return revol_util;
	}

	public void setRevol_util(double revol_util) {
		this.revol_util = revol_util;
	}

	public int getInq_last_6mths() {
		return inq_last_6mths;
	}

	public void setInq_last_6mths(int inq_last_6mths) {
		this.inq_last_6mths = inq_last_6mths;
	}

	public boolean isIs_rent() {
		return is_rent;
	}

	public void setIs_rent(boolean is_rent) {
		this.is_rent = is_rent;
	}

	public List<Factors> getFactors() {
		return factors;
	}

	public void setFactors(List<Factors> factors) {
		this.factors = factors;
	}

	/*public boolean hasDevice(Factors device) {
		for (Factors containedDevice: getDevices()) {
			if (containedDevice.getId() == device.getId()) {
				return true;
			}
		}
		return false;
	}*/

}