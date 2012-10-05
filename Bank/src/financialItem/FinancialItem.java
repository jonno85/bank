package financialItem;

import bank.Account;
import bank.Utils;
import java.util.Timer;

public abstract class FinancialItem {

	protected Account      		   	owner          = null;
    protected Timer           		period         = null;
	protected FinancialItemValues   fi_value       = null;
	protected Integer         		life    	   = null;
	protected Float 		   		taxRate        = null;
	protected Float 		   		interest       = null;

	protected FinancialItem(Account owner)
	{
		this.owner = owner;
	}
	
	/**
	 * complete constructor
	 * @param owner
	 * @param period
	 * @param fi_value
	 * @param life
	 * @param tax_rate
	 * @param interest
	 */
	protected FinancialItem(Account owner, Timer period, FinancialItemValues fi_value,
							Integer life, Float tax_rate, Float interest)
	{
		this.owner     = owner;
		this.period    = period;
		this.fi_value  = fi_value;
		this.life      = life;
		this.taxRate   = tax_rate;
		this.interest  = interest;
	}

	/**
	 * @return the owner
	 */
	public Account getOwner() {
		return owner;
	}

	/**
	 * @param owner the owner to set
	 */
	protected void setOwner(Account owner) {
		this.owner = owner;
	}

	protected abstract void startRateInterest();

    /**
     * @return the value
     */
    public FinancialItemValues getFinancialValue()
    {
    	return fi_value;
    }

    /**
     * @return the life
     */
    public Integer getLife() {
        return life;
    }

    /**
     * @return the taxRate
     */
    public Float getTaxRate() {
        return taxRate;
    }

    /**
     * @return the interest
     */
    public Float getInterest() {
        return interest;
    }

    /**
     * 
     * @param interest
     */
    public void setInterest(Float interest){
    	this.interest = interest;
    }
}
