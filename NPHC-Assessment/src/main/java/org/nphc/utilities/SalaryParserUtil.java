package org.nphc.utilities;

import java.math.BigDecimal;

import org.json.JSONException;
import org.json.JSONObject;
import org.nphc.exceptions.SalaryFormatException;

/**
 * This is a utility class used to convert strings to BigDecimal as well as retrieve salary from JSON objects
 * Salary has been parsed as BigDecimals with a scale of 3 to preserve more precise decimal values like 0.999
 *  A tradeoff is that storing salaries with less precise decimal values like 15.0 results in it becoming 15.000
 */


public class SalaryParserUtil {
	
	/**
	 * Returns a public BigDecimal based on the String salString passed to it. 
	 * Will throw a SalaryFormatException if it is unable to parse to BigDecimal.
	 */
	public BigDecimal salary(String salString) throws SalaryFormatException {
		try {
			return new BigDecimal(salString).setScale(3);
		} catch (Exception e){
			throw new SalaryFormatException("Invalid salary");
		}
	}
	
	/**
	 * Returns a public BigDecimal based on the JSONObject passed to it.
	 * Requires the JSONObject to have a key labeled "salary".
	 * Will throw a SalaryFormatException if the JSONObject does not contain the key.
	 */
	public BigDecimal salaryJSON(JSONObject obj) throws SalaryFormatException {
		try {
			return obj.getBigDecimal("salary").setScale(3);
		} catch (JSONException e) {
			throw new SalaryFormatException("Invalid salary");
		}
		
	
	}
	
}
