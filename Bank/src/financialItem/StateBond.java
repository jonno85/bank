package financialItem;

import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

import bank.Account;

public class StateBond extends FinancialItem
{
	public StateBond(Account owner, FinancialItemValues fi_value, Integer life, Float taxRate)
	{
		super(owner,new Timer(), fi_value, life, taxRate, new Float(0.0));
		
		//startRateInterest();
	}

	protected void startRateInterest()
	{
		life *= 2; //mean every six month a fixed tax_rate is calculated
		final Long six_month = new Long(15552000);
		period.scheduleAtFixedRate(new TimerTask() {

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
