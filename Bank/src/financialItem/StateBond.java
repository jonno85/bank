package financialItem;

import java.util.Observable;
import bank.Account;

public class StateBond extends FinancialItem
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6766703393593742307L;

	public StateBond(Integer index, Account owner, FinancialItemValues fi_value, Integer life, Float taxRate)
	{
		
		super(index, owner, fi_value, life*2, taxRate, new Float(0.0));
		// life * 2 mean every six month a fixed tax_rate is calculated
	}


	@Override
	protected void startRateInterest(Observable arg0, Object arg1)
	{
		interest +=  (getTaxRate() * getFinancialValue().getIntegerValue()) / 100;
	}

}
