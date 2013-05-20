/**
 * Created by Sebastian Walz
 * 02.05.2013 15:42:42
 */
package main.java.de.hska.awp.palaver2.util;

import com.vaadin.data.validator.AbstractStringValidator;

@SuppressWarnings("serial")
public class CustomDoubleValidator extends AbstractStringValidator
{

	/**
	 * @param errorMessage
	 */
	public CustomDoubleValidator(String errorMessage)
	{
		super(errorMessage);
	}

	/* (non-Javadoc)
	 * @see com.vaadin.data.validator.AbstractValidator#isValidValue(java.lang.Object)
	 */
	@Override
	protected boolean isValidValue(String value)
	{
		try {
            Double.parseDouble(value.replace(',', '.'));
            return true;
        } catch (Exception e) {
            return false;
        }
	}

}
