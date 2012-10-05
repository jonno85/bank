package operation;


import financialItem.FinancialItem;
import financialItem.StateBond;

import bank.Account;
import bank.Operator;
import bank.TypeOperator;

public class BuyOperation extends Operation {

	public BuyOperation() {
		super(TypeOperation.BUY_FINANCIAL_ITEM);
	}
			
	@Override
	public void doOperation(Account ref, Object[] objs, Operator oper)
			throws InvalidArgumentException, InvalidOperationException,
			InvalidPermissionException
	{
		if((oper.getType().equals(TypeOperator.AGENT)) 		   || 
		   (oper.getType().equals(TypeOperator.ADMINISTRATOR)))
		{	
			if((ref != null) && (ref.getActiveStatus() == true))
			{
				FinancialItem fi = (FinancialItem)objs[0];
				if(ref.getAccountBalance() >= fi.getFinancialValue().getIntegerValue())
				{
					if(!fi.getOwner().equals(ref))
					{
						fi.setOwner(ref);
						
						Object[] p = new String[1];	//re-build for the withdrawal operation
						p[0] = fi.getFinancialValue().getIntegerValue().toString();
						
						new WithdrawalOperation().doOperation(ref, p, oper);
						
						new DepositOperation().doOperation(oper.getBankAccount(), p, oper);
					} else {
						throw new InvalidOperationException("Financial Item already bought");
					}
				} else {
					throw new InvalidOperationException("Impossible to buy bond due to insufficient balance");
				}
			} else {
				throw new InvalidOperationException("Impossible to buy bond due to disabled account or not selected");
			}
		} else {
			throw new InvalidPermissionException("Error: user not allow to execute this operation");
		}	
	}
}
