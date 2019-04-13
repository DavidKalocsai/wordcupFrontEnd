package com.intland.eurocup.model;

import lombok.Getter;
import lombok.ToString;

/**
 * Status of an order. It can be active, or deleted. On delete order is only set to deleted, but not
 * removed.
 */
@Getter @ToString
public enum DrawStatus {
  NO_DRAW("nodraw", "Not yet drawn!"), WINNER("winner", "Winner"), LOSER("loser", "Loser");

  private String code;
  private String description;

  DrawStatus(final String code, final String description) {
    this.code = code;
    this.description = description;
  }

  public static DrawStatus getEnumFromCode(final String code) {
    DrawStatus orderStatus = null;
    for (final DrawStatus o : DrawStatus.values()) {
      if (orderStatus == null && o.getCode().equals(code)) {
        orderStatus = o;
      }
    }
    return orderStatus;
  }
}
