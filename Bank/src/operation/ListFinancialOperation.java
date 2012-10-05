package operation;

import bank.Account;
import bank.Operator;
import bank.TypeOperator;
import financialItem.FinancialItem;
import java.util.Iterator;

public class ListFinancialOperation extends Operation
{

    @Override
    public void doOperation(Account ref, Object[] objs, Operator oper)
            throws InvalidArgumentException, InvalidOperationException, InvalidPermissionException
    {
        int	num_elem = 0;
		Iterator<FinancialItem> it = null;

		if((oper.getType().equals(TypeOperator.AGENT)) ||
		   (oper.getType().equals(TypeOperator.ADMINISTRATOR)))
		{
			it = (Iterator<FinancialItem>) objs[0];

			System.out.println("## Financial Item \t| Owner \t\t| tax rate \t| Interest");

            FinancialItem fi = null;
			while (it.hasNext()) {
				fi = it.next();

				System.out.println("#> "+ fi.getFinancialValue().getStringValue() + "\t\t| " +
									      fi.getOwner().getAccountHolder()        + "\t\t\t| " +
										  fi.getTaxRate()  						  + "\t| "   +
										  fi.getInterest());
				num_elem++;
			}
			System.out.println("\n## Number of financial items: " + num_elem);
		} else {
			throw new InvalidPermissionException("Error: user not allow to execute this operation");
		}
    }

}
