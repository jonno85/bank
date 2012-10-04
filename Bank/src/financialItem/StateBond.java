package financialItem;

import java.math.BigInteger;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

import bank.Account;

public class StateBond extends FinancialItem
{
	private Timer			period		= null;
	private StateBondValues value		= null;
	private Integer 		life 		= null;
	private Float 			taxRate 	= null;
	private Float 			interest 	= null;
	
	public StateBond(Account owner, StateBondValues value, Integer life, Float taxRate)
	{
		super(owner);
		this.value		= value;
		this.life		= life;
		this.taxRate	= taxRate;
		this.interest	= new Float(0.0);
		this.period		= new Timer();
		
		startRateInterest();
	}

	public StateBondValues getStateBondValue()
	{
		return value;
	}
	
	protected void startRateInterest() 
	{
		life *= 2; //mean every six month a fixed tax_rate is calculated
		final Long six_month = new Long(15552000);
		period.scheduleAtFixedRate(new TimerTask() {
			
			@Override
			public void run() {
				interest +=  taxRate * value.getIntegerValue();
				life--;
				if(life == 0)
					this.cancel();
			}
		}, 0, six_month);
	}
	
	public Float getInterest()
	{
		return interest;
	}

}
