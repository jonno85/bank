package operation;

import java.util.Iterator;

import bank.Account;
import bank.Operator;
import bank.TypeOperator;

public class ShowHistoryOperation extends Operation {

	@Override
	public void doOperation(Account ref, Object[] objs, Operator oper)
			throws InvalidArgumentException, InvalidOperationException,
			InvalidPermissionException
	{
		if((oper.getType().equals(TypeOperator.AGENT)) || 
		   (oper.getType().equals(TypeOperator.ADMINISTRATOR)))
		{
			Iterator<Operation> it = oper.getHistoryOperator();
			Operation 			op = null;
			while (it.hasNext())
			{
				op = it.next();
				System.out.println("# " + op.getInfo());
			}
		} else {
			throw new InvalidPermissionException("Error: user not allow to execute this operation");
		}
	}

}
