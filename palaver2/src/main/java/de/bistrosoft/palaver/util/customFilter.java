/**
 * 
 */
package de.bistrosoft.palaver.util;

/**
 * @author Android
 *
 */
import org.tepi.filtertable.FilterGenerator;

import com.vaadin.data.Container.Filter;
import com.vaadin.data.util.filter.Compare;
import com.vaadin.ui.AbstractField;

public class customFilter implements FilterGenerator {

	@Override
	public Filter generateFilter(Object propertyId, Object value) {
		if ("id".equals(propertyId)) {
			/* Create an 'equals' filter for the ID field */
			if (value != null && value instanceof String) {
				try {
					return new Compare.Equal(propertyId,
							Integer.parseInt((String) value));
				} catch (NumberFormatException ignored) {
					// If no integer was entered, just generate default filter
				}
			}
		}
		// For other properties, use the default filter
		return null;
	}

	@Override
	public AbstractField getCustomFilterComponent(Object propertyId) {
		return null;
	}

	@Override
	public void filterRemoved(Object propertyId) {

	}

	@Override
	public void filterAdded(Object propertyId,
			Class<? extends Filter> filterType, Object value) {

	}
}
