package operation;

import bank.Agency;

public abstract class StrictOperation extends Operation 
{
	protected Agency working_agency = null;
	
	public StrictOperation(Agency agency, TypeOperation type)
	{
		super(type);
		this.working_agency = agency;
	}

}
