/*
 * !++
 * QDS - Quick Data Signalling Library
 * !-
 * Copyright (C) 2002 - 2018 Devexperts LLC
 * !-
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 * !__
 */
package com.dxfeed.ondemand.impl.event;

import java.util.Arrays;

import com.devexperts.qd.*;
import junit.framework.TestCase;

public class MDREventUtilTestTest extends TestCase {
	public static final DataScheme SCHEME = QDFactory.getDefaultScheme();

	public void testExchangeAndType() {
		assertEquals('\0', MDREventUtil.getExchange(SCHEME.findRecordByName("Quote")));
		assertEquals('X', MDREventUtil.getExchange(SCHEME.findRecordByName("Quote&X")));
		assertEquals('Q', MDREventUtil.getType(SCHEME.findRecordByName("Quote")));
		assertEquals('Q', MDREventUtil.getType(SCHEME.findRecordByName("Quote&X")));

		assertEquals('\0', MDREventUtil.getExchange(SCHEME.findRecordByName("Trade")));
		assertEquals('X', MDREventUtil.getExchange(SCHEME.findRecordByName("Trade&X")));
		assertEquals('T', MDREventUtil.getType(SCHEME.findRecordByName("Trade")));
		assertEquals('T', MDREventUtil.getType(SCHEME.findRecordByName("Trade&X")));
	}

	public void testRegionalTimeAndSale() {
		DataRecord tns = SCHEME.findRecordByName("TimeAndSale&X");
		assertEquals('H', MDREventUtil.getType(tns));

		// Map regional TnS to composite exchange (and remap to proper record later)
		assertEquals('\0', MDREventUtil.getExchange(tns));
		assertTrue(Arrays.equals(new DataRecord[] { tns }, MDREventUtil.getRecords('H', 'X')));
	}
}
