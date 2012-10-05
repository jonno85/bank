package financialItem;

import bank.Account;

import java.io.Serializable;
import java.util.Observable;
import java.util.Observer;

public abstract class FinancialItem implements Serializable, Observer
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6255731841616230494L;
	
	private Integer			     	index		   = null;
	protected Account      		   	owner          = null;
    /*protected Timer           		period         = null;*/
	protected FinancialItemValues   fi_value       = null;
	protected Integer         		life    	   = null;
	protected Float 		   		taxRate        = null;
	protected Float 		   		interest       = null;

	protected FinancialItem(Account owner)
	{
		this.owner = owner;
	}
	
	/**
	 * 
	 * @param index
	 * @param owner
	 * @param fi_value
	 * @param life
	 * @param tax_rate
	 * @param interest
	 */
	protected FinancialItem(Integer index, Account owner, /*Timer period,*/ FinancialItemValues fi_value,
							Integer life, Float tax_rate, Float interest)
	{
		this.index	   = index;
		this.owner     = owner;
		/*this.period    = period;*/
		this.fi_value  = fi_value;
		this.life      = life;
		this.taxRate   = tax_rate;
		this.interest  = interest;
	}

	@Override
	public void update(Observable o, Object arg)
	{
		startRateInterest(o, arg);
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
	public void setOwner(Account owner) {
		this.owner = owner;
	}

	protected abstract void startRateInterest(Observable arg0, Object arg1);

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
     * 
     * @param life
     */
    public void setLife(Integer life) {
        this.life = life;
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

	/**
	 * @return the index
	 */
	public Integer getIndex() {
		return index;
	}
}
