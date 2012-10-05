package financialItem;

import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

import bank.Account;
import bank.HeadQuarter;

public class StateBond extends FinancialItem
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6766703393593742307L;

	public StateBond(Integer index, Account owner, FinancialItemValues fi_value, Integer life, Float taxRate)
	{
		super(index, owner, fi_value, life, taxRate, new Float(0.0));
		
		//startRateInterest();
	}

	protected void startRateInterest()
	{
		life *= 2; //mean every six month a fixed tax_rate is calculated
		final Long six_month = new Long(15552000);
		HeadQuarter.periodic_operation.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				interest +=  getTaxRate() * getFinancialValue().getIntegerValue();
				life--;
				if(getLife() == 0)
					this.cancel();
			}
		}, 0, six_month);
	}


}
