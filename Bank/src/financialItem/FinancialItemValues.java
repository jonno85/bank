package financialItem;

import java.util.Collections;

import java.util.HashMap;
import java.util.Map;

public class FinancialItemValues {

	private Integer int_value	= null;
	private String 	name	 	= null;

	public FinancialItemValues(Integer value, String name)
	{
		this.int_value = value;
		this.name	   = name;
	}

	private static Map<Integer, FinancialItemValues> values;
	static {
		values = new HashMap<Integer, FinancialItemValues >();
		values.put(new Integer(0), new FinancialItemValues(new Integer(0),	  "EURO_NULL" ));
		values.put(new Integer(1), new FinancialItemValues(new Integer(1000), "EURO_1000" ));
		values.put(new Integer(2), new FinancialItemValues(new Integer(2000), "EURO_2000" ));
		values.put(new Integer(3), new FinancialItemValues(new Integer(5000), "EURO_5000" ));
		values.put(new Integer(4), new FinancialItemValues(new Integer(10000),"EURO_10000"));
		values.put(new Integer(5), new FinancialItemValues(new Integer(20000),"EURO_20000"));
		values.put(new Integer(6), new FinancialItemValues(new Integer(50000),"EURO_50000"));
		values = Collections.unmodifiableMap(values);
	}

	/**
	 * State Bond Values
	 * @param number
	 */
	public static FinancialItemValues getValue(int number)
	{
		if(number < values.size())
			return values.get(number);
		return values.get(0);
	}

	/**
	 * State Bond Integer Values
	 * @param number
	 */
	public Integer getIntegerValue()
	{
		return int_value;
	}
	
	public String getStringValue()
	{
		return name;
	}
}
