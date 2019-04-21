package com.intland.eurocup.service.converter;

import java.beans.PropertyEditorSupport;

import com.intland.eurocup.common.model.Territory;
import com.intland.eurocup.service.converter.exception.UnkownTerritoryException;

public class TerritoryConverter extends PropertyEditorSupport {

	public void setAsText(final String territoryCode) throws IllegalArgumentException {
		final Territory territory = Territory.getEnumFromCode(territoryCode.toUpperCase());
		validate(territory);
		setValue(territory);
	}
	
	private void validate(final Territory territory) {
		if (territory == null) {
			throw new UnkownTerritoryException();
		}
	}
}