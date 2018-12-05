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
package com.dxfeed.event.candle;

import java.util.*;

import com.devexperts.qd.*;
import com.devexperts.qd.ng.RecordMapping;
import com.devexperts.qd.ng.RecordMappingFactory;
import com.devexperts.services.ServiceProvider;
import com.devexperts.util.SystemProperties;
import com.dxfeed.api.impl.*;
import com.dxfeed.event.candle.impl.CandleMapping;
import com.dxfeed.event.candle.impl.TradeHistoryMapping;
import com.dxfeed.event.market.MarketEventSymbols;

@ServiceProvider(order = -50)
public final class CandleFactoryImpl extends EventDelegateFactory implements RecordMappingFactory {
// BEGIN: CODE AUTOMATICALLY GENERATED: DO NOT MODIFY. IT IS REGENERATED BY com.dxfeed.api.codegen.ImplCodeGen
	@Override
	public void buildScheme(SchemeBuilder builder) {
		builder.addRequiredField("TradeHistory", "Time", SerialFieldType.TIME, SchemeFieldTime.FIRST_TIME_INT_FIELD);
		builder.addRequiredField("TradeHistory", "Sequence", SerialFieldType.SEQUENCE, SchemeFieldTime.SECOND_TIME_INT_FIELD);
		builder.addOptionalField("TradeHistory", "Exchange", SerialFieldType.UTF_CHAR, "Candle", "ExchangeCode", true);
		builder.addRequiredField("TradeHistory", "Price", select(SerialFieldType.DECIMAL, "dxscheme.price"));
		builder.addRequiredField("TradeHistory", "Size", select(SerialFieldType.COMPACT_INT, "dxscheme.size"));
		builder.addOptionalField("TradeHistory", "Bid", select(SerialFieldType.DECIMAL, "dxscheme.price"), "Candle", "BidPrice", true);
		builder.addOptionalField("TradeHistory", "Ask", select(SerialFieldType.DECIMAL, "dxscheme.price"), "Candle", "AskPrice", true);

		for (String suffix : SystemProperties.getProperty("com.dxfeed.event.candle.impl.Candle.suffixes", "").split("\\|")) {
			String recordName = "Candle" + suffix;
			builder.addRequiredField(recordName, "Time", SerialFieldType.TIME, SchemeFieldTime.FIRST_TIME_INT_FIELD);
			builder.addRequiredField(recordName, "Sequence", SerialFieldType.SEQUENCE, SchemeFieldTime.SECOND_TIME_INT_FIELD);
			builder.addOptionalField(recordName, "Count", select(SerialFieldType.DECIMAL), "Candle", "Count", true);
			builder.addRequiredField(recordName, "Open", select(SerialFieldType.DECIMAL, "dxscheme.price"));
			builder.addRequiredField(recordName, "High", select(SerialFieldType.DECIMAL, "dxscheme.price"));
			builder.addRequiredField(recordName, "Low", select(SerialFieldType.DECIMAL, "dxscheme.price"));
			builder.addRequiredField(recordName, "Close", select(SerialFieldType.DECIMAL, "dxscheme.price"));
			builder.addOptionalField(recordName, "Volume", select(SerialFieldType.DECIMAL, "dxscheme.volume", "dxscheme.size"), "Candle", "Volume", true);
			builder.addOptionalField(recordName, "VWAP", select(SerialFieldType.DECIMAL, "dxscheme.price"), "Candle", "VWAP", true);
			if (!suffix.matches(".*[{,]price=(bid|ask|mark|s)[,}].*"))
				builder.addOptionalField(recordName, "Bid.Volume", select(SerialFieldType.DECIMAL, "dxscheme.volume", "dxscheme.size"), "Candle", "BidVolume", true);
			if (!suffix.matches(".*[{,]price=(bid|ask|mark|s)[,}].*"))
				builder.addOptionalField(recordName, "Ask.Volume", select(SerialFieldType.DECIMAL, "dxscheme.volume", "dxscheme.size"), "Candle", "AskVolume", true);
			builder.addOptionalField(recordName, "ImpVolatility", select(SerialFieldType.DECIMAL), "Candle", "ImpVolatility", true);
		}

		for (String suffix : SystemProperties.getProperty("com.dxfeed.event.candle.impl.Trade.suffixes", "133ticks|144ticks|233ticks|333ticks|400ticks|512ticks|1600ticks|3200ticks|1min|2min|3min|4min|5min|6min|10min|12min|15min|20min|30min|1hour|2hour|3hour|4hour|6hour|8hour|12hour|Day|2Day|3Day|4Day|Week|Month|OptExp").split("\\|")) {
			String recordName = "Trade." + suffix;
			builder.addRequiredField(recordName, "Time", SerialFieldType.TIME, SchemeFieldTime.FIRST_TIME_INT_FIELD);
			builder.addRequiredField(recordName, "Sequence", SerialFieldType.SEQUENCE, SchemeFieldTime.SECOND_TIME_INT_FIELD);
			builder.addOptionalField(recordName, "Count", select(SerialFieldType.DECIMAL), "Candle", "Count", true);
			builder.addRequiredField(recordName, "Open", select(SerialFieldType.DECIMAL, "dxscheme.price"));
			builder.addRequiredField(recordName, "High", select(SerialFieldType.DECIMAL, "dxscheme.price"));
			builder.addRequiredField(recordName, "Low", select(SerialFieldType.DECIMAL, "dxscheme.price"));
			builder.addRequiredField(recordName, "Close", select(SerialFieldType.DECIMAL, "dxscheme.price"));
			builder.addOptionalField(recordName, "Volume", select(SerialFieldType.DECIMAL, "dxscheme.volume", "dxscheme.size"), "Candle", "Volume", true);
			builder.addOptionalField(recordName, "VWAP", select(SerialFieldType.DECIMAL, "dxscheme.price"), "Candle", "VWAP", true);
			if (!suffix.matches(".*[{,]price=(bid|ask|mark|s)[,}].*"))
				builder.addOptionalField(recordName, "Bid.Volume", select(SerialFieldType.DECIMAL, "dxscheme.volume", "dxscheme.size"), "Candle", "BidVolume", true);
			if (!suffix.matches(".*[{,]price=(bid|ask|mark|s)[,}].*"))
				builder.addOptionalField(recordName, "Ask.Volume", select(SerialFieldType.DECIMAL, "dxscheme.volume", "dxscheme.size"), "Candle", "AskVolume", true);
			builder.addOptionalField(recordName, "ImpVolatility", select(SerialFieldType.DECIMAL), "Candle", "ImpVolatility", true);
			if (suffix.matches(".*Day|Week|Month|OptExp"))
				builder.addOptionalField(recordName, "OpenInterest", select(SerialFieldType.DECIMAL), "DailyCandle", "OpenInterest", true);
		}
	}

	@Override
	public Collection<EventDelegate<?>> createDelegates(DataRecord record) {
		Collection<EventDelegate<?>> result = new ArrayList<>();
		if (record.getMapping(TradeHistoryMapping.class) != null) {
			result.add(new CandleByTradeHistoryDelegate(record, QDContract.TICKER, EnumSet.of(EventDelegateFlags.SUB, EventDelegateFlags.PUB)));
			result.add(new CandleByTradeHistoryDelegate(record, QDContract.STREAM, EnumSet.of(EventDelegateFlags.PUB, EventDelegateFlags.WILDCARD)));
			result.add(new CandleByTradeHistoryDelegate(record, QDContract.HISTORY, EnumSet.of(EventDelegateFlags.SUB, EventDelegateFlags.PUB, EventDelegateFlags.TIME_SERIES)));
		} else if (record.getMapping(CandleMapping.class) != null) {
			result.add(new CandleDelegate(record, QDContract.TICKER, EnumSet.of(EventDelegateFlags.SUB, EventDelegateFlags.PUB)));
			result.add(new CandleDelegate(record, QDContract.STREAM, EnumSet.of(EventDelegateFlags.PUB, EventDelegateFlags.WILDCARD)));
			result.add(new CandleDelegate(record, QDContract.HISTORY, EnumSet.of(EventDelegateFlags.SUB, EventDelegateFlags.PUB, EventDelegateFlags.TIME_SERIES)));
			result.add(new DailyCandleDelegate(record, QDContract.TICKER, EnumSet.of(EventDelegateFlags.SUB, EventDelegateFlags.PUB)));
			result.add(new DailyCandleDelegate(record, QDContract.STREAM, EnumSet.of(EventDelegateFlags.PUB, EventDelegateFlags.WILDCARD)));
			result.add(new DailyCandleDelegate(record, QDContract.HISTORY, EnumSet.of(EventDelegateFlags.SUB, EventDelegateFlags.PUB, EventDelegateFlags.TIME_SERIES)));
		}
		return result;
	}

	@Override
	public Collection<EventDelegate<?>> createStreamOnlyDelegates(DataRecord record) {
		Collection<EventDelegate<?>> result = new ArrayList<>();
		if (record.getMapping(TradeHistoryMapping.class) != null) {
			result.add(new CandleByTradeHistoryDelegate(record, QDContract.STREAM, EnumSet.of(EventDelegateFlags.SUB, EventDelegateFlags.PUB, EventDelegateFlags.WILDCARD)));
		} else if (record.getMapping(CandleMapping.class) != null) {
			result.add(new CandleDelegate(record, QDContract.STREAM, EnumSet.of(EventDelegateFlags.SUB, EventDelegateFlags.PUB, EventDelegateFlags.WILDCARD)));
			result.add(new DailyCandleDelegate(record, QDContract.STREAM, EnumSet.of(EventDelegateFlags.SUB, EventDelegateFlags.PUB, EventDelegateFlags.WILDCARD)));
		}
		return result;
	}

	@Override
	public RecordMapping createMapping(DataRecord record) {
		String baseRecordName = getBaseRecordName(record.getName());
		if (baseRecordName.equals("TradeHistory"))
			return new TradeHistoryMapping(record);
		if (baseRecordName.equals("Candle"))
			return new CandleMapping(record);
		if (baseRecordName.equals("Trade."))
			return new CandleMapping(record);
		return null;
	}
// END: CODE AUTOMATICALLY GENERATED

	@Override
	protected String getBaseRecordName(String recordName) {
		String s = MarketEventSymbols.getBaseSymbol(recordName);
		int i = s.indexOf('.');
		return i > 0 ? s.substring(0, i + 1) : s;
	}
}
