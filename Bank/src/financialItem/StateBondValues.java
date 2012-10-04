package financialItem;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class StateBondValues {
	
	private Integer value	= null;
	private String 	name 	= null;
	
	public StateBondValues(Integer value, String name)
	{
		this.value = value;
		this.name  = name;
	}
	
	private static Map<Integer, StateBondValues> values;
	static {
		values = new HashMap<Integer, StateBondValues >();
		values.put(new Integer(1), new StateBondValues(new Integer(0),		"EURO_NULL" ));
		values.put(new Integer(1), new StateBondValues(new Integer(1000),	"EURO_1000" ));
		values.put(new Integer(2), new StateBondValues(new Integer(2000),	"EURO_2000" ));
		values.put(new Integer(3), new StateBondValues(new Integer(5000),	"EURO_5000" ));
		values.put(new Integer(4), new StateBondValues(new Integer(10000),	"EURO_10000"));
		values.put(new Integer(5), new StateBondValues(new Integer(20000),	"EURO_20000"));
		values.put(new Integer(6), new StateBondValues(new Integer(50000),	"EURO_50000"));
		values = Collections.unmodifiableMap(values);
	}
	
	/**
	 * State Bond Values
	 * @param number
	 */
	public static StateBondValues getValue(int number)
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
		return value;
	}
}
