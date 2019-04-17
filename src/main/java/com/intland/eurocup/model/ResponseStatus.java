package com.intland.eurocup.model;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum ResponseStatus {
	NO("Answer not yet arrived"),
	OK("Response arrived"),
	ERROR("Error occured");
	
  private String description;

  ResponseStatus(final String description) {
    this.description = description;
  }

  public static ResponseStatus getEnumFromCode(final String description) {
  	ResponseStatus orderStatus = null;
    for (final ResponseStatus o : ResponseStatus.values()) {
      if (orderStatus == null && o.getDescription().equals(description)) {
        orderStatus = o;
      }
    }
    return orderStatus;
  }

}
