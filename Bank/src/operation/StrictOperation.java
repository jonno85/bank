package operation;

import bank.Agency;

public abstract class StrictOperation extends Operation 
{
	protected Agency working_agency = null;
	
	public StrictOperation(Agency agency)
	{
		this.type = TypeOperation.NEW_ACCOUNT;
		this.working_agency = agency;
	}

}
