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
package com.dxfeed.event.market;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;

import com.devexperts.qd.DataRecord;
import com.devexperts.qd.QDContract;
import com.devexperts.qd.SerialFieldType;
import com.devexperts.qd.ng.RecordMapping;
import com.devexperts.qd.ng.RecordMappingFactory;
import com.devexperts.services.ServiceProvider;
import com.devexperts.util.SystemProperties;
import com.dxfeed.api.impl.EventDelegate;
import com.dxfeed.api.impl.EventDelegateFactory;
import com.dxfeed.api.impl.EventDelegateFlags;
import com.dxfeed.api.impl.SchemeBuilder;
import com.dxfeed.api.impl.SchemeFieldTime;
import com.dxfeed.event.market.impl.BookMapping;
import com.dxfeed.event.market.impl.FundamentalMapping;
import com.dxfeed.event.market.impl.MarketMakerMapping;
import com.dxfeed.event.market.impl.OrderBaseMapping;
import com.dxfeed.event.market.impl.OrderMapping;
import com.dxfeed.event.market.impl.ProfileMapping;
import com.dxfeed.event.market.impl.QuoteMapping;
import com.dxfeed.event.market.impl.SpreadOrderMapping;
import com.dxfeed.event.market.impl.SummaryMapping;
import com.dxfeed.event.market.impl.TimeAndSaleMapping;
import com.dxfeed.event.market.impl.TradeETHMapping;
import com.dxfeed.event.market.impl.TradeMapping;

@ServiceProvider(order = -50)
public final class MarketFactoryImpl extends EventDelegateFactory implements RecordMappingFactory {
// BEGIN: CODE AUTOMATICALLY GENERATED: DO NOT MODIFY. IT IS REGENERATED BY com.dxfeed.api.codegen.ImplCodeGen
	@Override
	public void buildScheme(SchemeBuilder builder) {
		builder.addOptionalField("Quote", "Sequence", SerialFieldType.SEQUENCE, "Quote", "Sequence", false);
		builder.addOptionalField("Quote", "TimeNanoPart", SerialFieldType.COMPACT_INT, "Quote", "TimeNanoPart", false);
		builder.addOptionalField("Quote", "Bid.Time", SerialFieldType.TIME, "Quote", "BidTime", true);
		builder.addOptionalField("Quote", "Bid.Exchange", SerialFieldType.UTF_CHAR, "Quote", "BidExchangeCode", true);
		builder.addRequiredField("Quote", "Bid.Price", SerialFieldType.DECIMAL);
		builder.addRequiredField("Quote", "Bid.Size", SystemProperties.getProperty("dxscheme.size", "").equalsIgnoreCase("decimal") ? SerialFieldType.DECIMAL : SerialFieldType.COMPACT_INT);
		builder.addOptionalField("Quote", "Ask.Time", SerialFieldType.TIME, "Quote", "AskTime", true);
		builder.addOptionalField("Quote", "Ask.Exchange", SerialFieldType.UTF_CHAR, "Quote", "AskExchangeCode", true);
		builder.addRequiredField("Quote", "Ask.Price", SerialFieldType.DECIMAL);
		builder.addRequiredField("Quote", "Ask.Size", SystemProperties.getProperty("dxscheme.size", "").equalsIgnoreCase("decimal") ? SerialFieldType.DECIMAL : SerialFieldType.COMPACT_INT);
		for (char exchange : SystemProperties.getProperty("com.dxfeed.event.market.impl.Quote.exchanges", "ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray()) {
			String recordName = "Quote&" + exchange;
			builder.addOptionalField(recordName, "Sequence", SerialFieldType.SEQUENCE, "Quote", "Sequence", false);
			builder.addOptionalField(recordName, "TimeNanoPart", SerialFieldType.COMPACT_INT, "Quote", "TimeNanoPart", false);
			builder.addOptionalField(recordName, "Bid.Time", SerialFieldType.TIME, "Quote", "BidTime", true);
			builder.addRequiredField(recordName, "Bid.Price", SerialFieldType.DECIMAL);
			builder.addRequiredField(recordName, "Bid.Size", SystemProperties.getProperty("dxscheme.size", "").equalsIgnoreCase("decimal") ? SerialFieldType.DECIMAL : SerialFieldType.COMPACT_INT);
			builder.addOptionalField(recordName, "Ask.Time", SerialFieldType.TIME, "Quote", "AskTime", true);
			builder.addRequiredField(recordName, "Ask.Price", SerialFieldType.DECIMAL);
			builder.addRequiredField(recordName, "Ask.Size", SystemProperties.getProperty("dxscheme.size", "").equalsIgnoreCase("decimal") ? SerialFieldType.DECIMAL : SerialFieldType.COMPACT_INT);
		}

		if (SystemProperties.getBooleanProperty("reuters.phantom", false)) {
			builder.addRequiredField("Quote2", "Bid.Price", SerialFieldType.DECIMAL);
			builder.addRequiredField("Quote2", "Bid.Size", SerialFieldType.DECIMAL);
			builder.addRequiredField("Quote2", "Ask.Price", SerialFieldType.DECIMAL);
			builder.addRequiredField("Quote2", "Ask.Size", SerialFieldType.DECIMAL);
			builder.addRequiredField("Quote2", "Bid.Price.Timestamp", SerialFieldType.TIME);
			builder.addRequiredField("Quote2", "Bid.Size.Timestamp", SerialFieldType.TIME);
			builder.addRequiredField("Quote2", "Ask.Price.Timestamp", SerialFieldType.TIME);
			builder.addRequiredField("Quote2", "Ask.Size.Timestamp", SerialFieldType.TIME);
		}

		builder.addOptionalField("Trade", "Last.Time", SerialFieldType.TIME, "Trade", "Time", true);
		builder.addOptionalField("Trade", "Last.Sequence", SerialFieldType.SEQUENCE, "Trade", "Sequence", true);
		builder.addOptionalField("Trade", "Last.TimeNanoPart", SerialFieldType.COMPACT_INT, "Trade", "TimeNanoPart", false);
		builder.addOptionalField("Trade", "Last.Exchange", SerialFieldType.UTF_CHAR, "Trade", "ExchangeCode", true);
		builder.addRequiredField("Trade", "Last.Price", SerialFieldType.DECIMAL);
		builder.addRequiredField("Trade", "Last.Size", SystemProperties.getProperty("dxscheme.size", "").equalsIgnoreCase("decimal") ? SerialFieldType.DECIMAL : SerialFieldType.COMPACT_INT);
		builder.addOptionalField("Trade", "Last.Tick", SerialFieldType.COMPACT_INT, "Trade", "Tick", true);
		builder.addOptionalField("Trade", "Last.Change", SerialFieldType.DECIMAL, "Trade", "Change", true);
		builder.addOptionalField("Trade", "Last.Flags", SerialFieldType.COMPACT_INT, "Trade", "Flags", true);
		builder.addOptionalField("Trade", "Volume", SerialFieldType.DECIMAL, "Trade", "DayVolume", true);
		builder.addOptionalField("Trade", "DayTurnover", SerialFieldType.DECIMAL, "Trade", "DayTurnover", true);
		if (SystemProperties.getBooleanProperty("reuters.phantom", false)) {
			builder.addRequiredField("Trade", "Date", SerialFieldType.COMPACT_INT);
			builder.addRequiredField("Trade", "Operation", SerialFieldType.COMPACT_INT);
		}
		for (char exchange : SystemProperties.getProperty("com.dxfeed.event.market.impl.Trade.exchanges", "ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray()) {
			String recordName = "Trade&" + exchange;
			builder.addOptionalField(recordName, "Last.Time", SerialFieldType.TIME, "Trade", "Time", true);
			builder.addOptionalField(recordName, "Last.Sequence", SerialFieldType.SEQUENCE, "Trade", "Sequence", true);
			builder.addOptionalField(recordName, "Last.TimeNanoPart", SerialFieldType.COMPACT_INT, "Trade", "TimeNanoPart", false);
			builder.addRequiredField(recordName, "Last.Price", SerialFieldType.DECIMAL);
			builder.addRequiredField(recordName, "Last.Size", SystemProperties.getProperty("dxscheme.size", "").equalsIgnoreCase("decimal") ? SerialFieldType.DECIMAL : SerialFieldType.COMPACT_INT);
			builder.addOptionalField(recordName, "Last.Tick", SerialFieldType.COMPACT_INT, "Trade", "Tick", true);
			builder.addOptionalField(recordName, "Last.Change", SerialFieldType.DECIMAL, "Trade", "Change", true);
			builder.addOptionalField(recordName, "Last.Flags", SerialFieldType.COMPACT_INT, "Trade", "Flags", true);
			builder.addOptionalField(recordName, "Volume", SerialFieldType.DECIMAL, "Trade", "DayVolume", true);
			builder.addOptionalField(recordName, "DayTurnover", SerialFieldType.DECIMAL, "Trade", "DayTurnover", true);
		}

		builder.addOptionalField("TradeETH", "ETHLast.Time", SerialFieldType.TIME, "TradeETH", "Time", true);
		builder.addOptionalField("TradeETH", "ETHLast.Sequence", SerialFieldType.SEQUENCE, "TradeETH", "Sequence", true);
		builder.addOptionalField("TradeETH", "Last.TimeNanoPart", SerialFieldType.COMPACT_INT, "TradeETH", "TimeNanoPart", false);
		builder.addOptionalField("TradeETH", "ETHLast.Exchange", SerialFieldType.UTF_CHAR, "TradeETH", "ExchangeCode", true);
		builder.addRequiredField("TradeETH", "ETHLast.Price", SerialFieldType.DECIMAL);
		builder.addRequiredField("TradeETH", "ETHLast.Size", SystemProperties.getProperty("dxscheme.size", "").equalsIgnoreCase("decimal") ? SerialFieldType.DECIMAL : SerialFieldType.COMPACT_INT);
		builder.addRequiredField("TradeETH", "ETHLast.Flags", SerialFieldType.COMPACT_INT);
		builder.addOptionalField("TradeETH", "ETHVolume", SerialFieldType.DECIMAL, "TradeETH", "DayVolume", true);
		builder.addOptionalField("TradeETH", "ETHDayTurnover", SerialFieldType.DECIMAL, "TradeETH", "DayTurnover", true);
		for (char exchange : SystemProperties.getProperty("com.dxfeed.event.market.impl.TradeETH.exchanges", "ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray()) {
			String recordName = "TradeETH&" + exchange;
			builder.addOptionalField(recordName, "ETHLast.Time", SerialFieldType.TIME, "TradeETH", "Time", true);
			builder.addOptionalField(recordName, "ETHLast.Sequence", SerialFieldType.SEQUENCE, "TradeETH", "Sequence", true);
			builder.addOptionalField(recordName, "Last.TimeNanoPart", SerialFieldType.COMPACT_INT, "TradeETH", "TimeNanoPart", false);
			builder.addRequiredField(recordName, "ETHLast.Price", SerialFieldType.DECIMAL);
			builder.addRequiredField(recordName, "ETHLast.Size", SystemProperties.getProperty("dxscheme.size", "").equalsIgnoreCase("decimal") ? SerialFieldType.DECIMAL : SerialFieldType.COMPACT_INT);
			builder.addRequiredField(recordName, "ETHLast.Flags", SerialFieldType.COMPACT_INT);
			builder.addOptionalField(recordName, "ETHVolume", SerialFieldType.DECIMAL, "TradeETH", "DayVolume", true);
			builder.addOptionalField(recordName, "ETHDayTurnover", SerialFieldType.DECIMAL, "TradeETH", "DayTurnover", true);
		}

		builder.addRequiredField("Summary", "DayId", SerialFieldType.DATE);
		builder.addRequiredField("Summary", "DayOpen.Price", SerialFieldType.DECIMAL);
		builder.addRequiredField("Summary", "DayHigh.Price", SerialFieldType.DECIMAL);
		builder.addRequiredField("Summary", "DayLow.Price", SerialFieldType.DECIMAL);
		builder.addOptionalField("Summary", "DayClose.Price", SerialFieldType.DECIMAL, "Summary", "DayClosePrice", true);
		builder.addRequiredField("Summary", "PrevDayId", SerialFieldType.DATE);
		builder.addRequiredField("Summary", "PrevDayClose.Price", SerialFieldType.DECIMAL);
		builder.addOptionalField("Summary", "PrevDayVolume", SerialFieldType.DECIMAL, "Summary", "PrevDayVolume", true);
		builder.addOptionalField("Summary", "OpenInterest", SerialFieldType.COMPACT_INT, "Summary", "OpenInterest", true);
		builder.addOptionalField("Summary", "Flags", SerialFieldType.COMPACT_INT, "Summary", "Flags", true);
		for (char exchange : SystemProperties.getProperty("com.dxfeed.event.market.impl.Summary.exchanges", "ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray()) {
			String recordName = "Summary&" + exchange;
			builder.addRequiredField(recordName, "DayId", SerialFieldType.DATE);
			builder.addRequiredField(recordName, "DayOpen.Price", SerialFieldType.DECIMAL);
			builder.addRequiredField(recordName, "DayHigh.Price", SerialFieldType.DECIMAL);
			builder.addRequiredField(recordName, "DayLow.Price", SerialFieldType.DECIMAL);
			builder.addOptionalField(recordName, "DayClose.Price", SerialFieldType.DECIMAL, "Summary", "DayClosePrice", true);
			builder.addRequiredField(recordName, "PrevDayId", SerialFieldType.DATE);
			builder.addRequiredField(recordName, "PrevDayClose.Price", SerialFieldType.DECIMAL);
			builder.addOptionalField(recordName, "PrevDayVolume", SerialFieldType.DECIMAL, "Summary", "PrevDayVolume", true);
			builder.addOptionalField(recordName, "Flags", SerialFieldType.COMPACT_INT, "Summary", "Flags", true);
		}

		builder.addRequiredField("Fundamental", "Open.Price", SerialFieldType.DECIMAL);
		builder.addRequiredField("Fundamental", "High.Price", SerialFieldType.DECIMAL);
		builder.addRequiredField("Fundamental", "Low.Price", SerialFieldType.DECIMAL);
		builder.addRequiredField("Fundamental", "Close.Price", SerialFieldType.DECIMAL);
		builder.addOptionalField("Fundamental", "OpenInterest", SerialFieldType.COMPACT_INT, "null", "OpenInterest", true);
		for (char exchange : SystemProperties.getProperty("com.dxfeed.event.market.impl.Fundamental.exchanges", "ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray()) {
			String recordName = "Fundamental&" + exchange;
			builder.addRequiredField(recordName, "Open.Price", SerialFieldType.DECIMAL);
			builder.addRequiredField(recordName, "High.Price", SerialFieldType.DECIMAL);
			builder.addRequiredField(recordName, "Low.Price", SerialFieldType.DECIMAL);
			builder.addRequiredField(recordName, "Close.Price", SerialFieldType.DECIMAL);
		}

		for (char exchange : SystemProperties.getProperty("com.dxfeed.event.market.impl.Book.exchanges", "I").toCharArray()) {
			String recordName = "Book&" + exchange;
			builder.addRequiredField(recordName, "ID", SerialFieldType.COMPACT_INT, SchemeFieldTime.FIRST_TIME_INT_FIELD);
			builder.addRequiredField(recordName, "Sequence", SerialFieldType.VOID, SchemeFieldTime.SECOND_TIME_INT_FIELD);
			builder.addRequiredField(recordName, "Time", SerialFieldType.TIME);
			builder.addRequiredField(recordName, "Type", SerialFieldType.UTF_CHAR);
			builder.addRequiredField(recordName, "Price", SerialFieldType.DECIMAL);
			builder.addRequiredField(recordName, "Size", SystemProperties.getProperty("dxscheme.size", "").equalsIgnoreCase("decimal") ? SerialFieldType.DECIMAL : SerialFieldType.COMPACT_INT);
			builder.addRequiredField(recordName, "TimeInForce", SerialFieldType.UTF_CHAR);
			builder.addRequiredField(recordName, "Symbol", SerialFieldType.UTF_CHAR_ARRAY);
		}

		builder.addOptionalField("Profile", "Beta", SerialFieldType.DECIMAL, "Profile", "Beta", true);
		builder.addOptionalField("Profile", "Eps", SerialFieldType.DECIMAL, "Profile", "Eps", true);
		builder.addOptionalField("Profile", "DivFreq", SerialFieldType.COMPACT_INT, "Profile", "DivFreq", true);
		builder.addOptionalField("Profile", "ExdDiv.Amount", SerialFieldType.DECIMAL, "Profile", "ExdDivAmount", true);
		builder.addOptionalField("Profile", "ExdDiv.Date", SerialFieldType.DATE, "Profile", "ExdDivDate", true);
		builder.addOptionalField("Profile", "52High.Price", SerialFieldType.DECIMAL, "Profile", "HighPrice52", true);
		builder.addOptionalField("Profile", "52Low.Price", SerialFieldType.DECIMAL, "Profile", "LowPrice52", true);
		builder.addOptionalField("Profile", "Shares", SerialFieldType.DECIMAL, "Profile", "Shares", true);
		builder.addOptionalField("Profile", "FreeFloat", SerialFieldType.DECIMAL, "Profile", "FreeFloat", true);
		builder.addOptionalField("Profile", "HighLimitPrice", SerialFieldType.DECIMAL, "Profile", "HighLimitPrice", true);
		builder.addOptionalField("Profile", "LowLimitPrice", SerialFieldType.DECIMAL, "Profile", "LowLimitPrice", true);
		builder.addOptionalField("Profile", "Halt.StartTime", SerialFieldType.TIME, "Profile", "HaltStartTime", true);
		builder.addOptionalField("Profile", "Halt.EndTime", SerialFieldType.TIME, "Profile", "HaltEndTime", true);
		builder.addOptionalField("Profile", "Flags", SerialFieldType.COMPACT_INT, "Profile", "Flags", true);
		builder.addRequiredField("Profile", "Description", SerialFieldType.UTF_CHAR_ARRAY);
		builder.addOptionalField("Profile", "StatusReason", SerialFieldType.UTF_CHAR_ARRAY, "Profile", "StatusReason", true);

		for (String suffix : SystemProperties.getProperty("com.dxfeed.event.market.impl.Order.suffixes", "|#NTV|#NFX|#ESPD|#DEA|#DEX|#BYX|#BZX|#IST|#ISE|#BATE|#CHIX|#BXTR|#GLBX|#XEUR|#ICE|#CFE").split("\\|")) {
			String recordName = "Order" + suffix;
			builder.addRequiredField(recordName, "Void", SerialFieldType.VOID, SchemeFieldTime.FIRST_TIME_INT_FIELD);
			builder.addRequiredField(recordName, "Index", SerialFieldType.COMPACT_INT, SchemeFieldTime.SECOND_TIME_INT_FIELD);
			builder.addRequiredField(recordName, "Time", SerialFieldType.TIME);
			builder.addOptionalField(recordName, "TimeNanoPart", SerialFieldType.COMPACT_INT, "Order", "TimeNanoPart", false);
			builder.addRequiredField(recordName, "Sequence", SerialFieldType.SEQUENCE);
			builder.addRequiredField(recordName, "Price", SerialFieldType.DECIMAL);
			builder.addRequiredField(recordName, "Size", SystemProperties.getProperty("dxscheme.size", "").equalsIgnoreCase("decimal") ? SerialFieldType.DECIMAL : SerialFieldType.COMPACT_INT);
			if (suffix.matches(SystemProperties.getProperty("com.dxfeed.event.order.impl.Order.suffixes.count", "")))
				builder.addOptionalField(recordName, "Count", SerialFieldType.COMPACT_INT, "Order", "Count", true);
			builder.addRequiredField(recordName, "Flags", SerialFieldType.COMPACT_INT);
			if (suffix.matches(SystemProperties.getProperty("com.dxfeed.event.order.impl.Order.suffixes.mmid", "|#NTV|#BATE|#CHIX|#BXTR")))
				builder.addOptionalField(recordName, "MMID", SerialFieldType.SHORT_STRING, "Order", "MarketMaker", true);
		}

		for (String suffix : SystemProperties.getProperty("com.dxfeed.event.market.impl.SpreadOrder.suffixes", "|#ISE").split("\\|")) {
			String recordName = "SpreadOrder" + suffix;
			builder.addRequiredField(recordName, "Void", SerialFieldType.VOID, SchemeFieldTime.FIRST_TIME_INT_FIELD);
			builder.addRequiredField(recordName, "Index", SerialFieldType.COMPACT_INT, SchemeFieldTime.SECOND_TIME_INT_FIELD);
			builder.addRequiredField(recordName, "Time", SerialFieldType.TIME);
			builder.addOptionalField(recordName, "TimeNanoPart", SerialFieldType.COMPACT_INT, "SpreadOrder", "TimeNanoPart", false);
			builder.addRequiredField(recordName, "Sequence", SerialFieldType.SEQUENCE);
			builder.addRequiredField(recordName, "Price", SerialFieldType.DECIMAL);
			builder.addRequiredField(recordName, "Size", SystemProperties.getProperty("dxscheme.size", "").equalsIgnoreCase("decimal") ? SerialFieldType.DECIMAL : SerialFieldType.COMPACT_INT);
			if (suffix.matches(SystemProperties.getProperty("com.dxfeed.event.order.impl.SpreadOrder.suffixes.count", "")))
				builder.addOptionalField(recordName, "Count", SerialFieldType.COMPACT_INT, "SpreadOrder", "Count", true);
			builder.addRequiredField(recordName, "Flags", SerialFieldType.COMPACT_INT);
			builder.addRequiredField(recordName, "SpreadSymbol", SerialFieldType.UTF_CHAR_ARRAY);
		}

		builder.addRequiredField("MarketMaker", "MMExchange", SerialFieldType.UTF_CHAR, SchemeFieldTime.FIRST_TIME_INT_FIELD);
		builder.addRequiredField("MarketMaker", "MMID", SerialFieldType.SHORT_STRING, SchemeFieldTime.SECOND_TIME_INT_FIELD);
		builder.addOptionalField("MarketMaker", "MMBid.Time", SerialFieldType.TIME, "Order", "BidTime", true);
		builder.addRequiredField("MarketMaker", "MMBid.Price", SerialFieldType.DECIMAL);
		builder.addRequiredField("MarketMaker", "MMBid.Size", SystemProperties.getProperty("dxscheme.size", "").equalsIgnoreCase("decimal") ? SerialFieldType.DECIMAL : SerialFieldType.COMPACT_INT);
		builder.addOptionalField("MarketMaker", "MMBid.Count", SerialFieldType.COMPACT_INT, "Order", "BidCount", true);
		builder.addOptionalField("MarketMaker", "MMAsk.Time", SerialFieldType.TIME, "Order", "AskTime", true);
		builder.addRequiredField("MarketMaker", "MMAsk.Price", SerialFieldType.DECIMAL);
		builder.addRequiredField("MarketMaker", "MMAsk.Size", SystemProperties.getProperty("dxscheme.size", "").equalsIgnoreCase("decimal") ? SerialFieldType.DECIMAL : SerialFieldType.COMPACT_INT);
		builder.addOptionalField("MarketMaker", "MMAsk.Count", SerialFieldType.COMPACT_INT, "Order", "AskCount", true);

		builder.addRequiredField("TimeAndSale", "Time", SerialFieldType.TIME, SchemeFieldTime.FIRST_TIME_INT_FIELD);
		builder.addRequiredField("TimeAndSale", "Sequence", SerialFieldType.SEQUENCE, SchemeFieldTime.SECOND_TIME_INT_FIELD);
		builder.addOptionalField("TimeAndSale", "TimeNanoPart", SerialFieldType.COMPACT_INT, "TimeAndSale", "TimeNanoPart", false);
		builder.addRequiredField("TimeAndSale", "Exchange", SerialFieldType.UTF_CHAR);
		builder.addRequiredField("TimeAndSale", "Price", SerialFieldType.DECIMAL);
		builder.addRequiredField("TimeAndSale", "Size", SystemProperties.getProperty("dxscheme.size", "").equalsIgnoreCase("decimal") ? SerialFieldType.DECIMAL : SerialFieldType.COMPACT_INT);
		builder.addRequiredField("TimeAndSale", "Bid.Price", SerialFieldType.DECIMAL);
		builder.addRequiredField("TimeAndSale", "Ask.Price", SerialFieldType.DECIMAL);
		builder.addRequiredField("TimeAndSale", "ExchangeSaleConditions", SerialFieldType.SHORT_STRING);
		builder.addRequiredField("TimeAndSale", "Flags", SerialFieldType.COMPACT_INT);
		builder.addOptionalField("TimeAndSale", "Buyer", SerialFieldType.UTF_CHAR_ARRAY, "TimeAndSale", "Buyer", false);
		builder.addOptionalField("TimeAndSale", "Seller", SerialFieldType.UTF_CHAR_ARRAY, "TimeAndSale", "Seller", false);
		for (char exchange : SystemProperties.getProperty("com.dxfeed.event.market.impl.TimeAndSale.exchanges", "ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray()) {
			String recordName = "TimeAndSale&" + exchange;
			builder.addRequiredField(recordName, "Time", SerialFieldType.TIME, SchemeFieldTime.FIRST_TIME_INT_FIELD);
			builder.addRequiredField(recordName, "Sequence", SerialFieldType.SEQUENCE, SchemeFieldTime.SECOND_TIME_INT_FIELD);
			builder.addOptionalField(recordName, "TimeNanoPart", SerialFieldType.COMPACT_INT, "TimeAndSale", "TimeNanoPart", false);
			builder.addRequiredField(recordName, "Exchange", SerialFieldType.UTF_CHAR);
			builder.addRequiredField(recordName, "Price", SerialFieldType.DECIMAL);
			builder.addRequiredField(recordName, "Size", SystemProperties.getProperty("dxscheme.size", "").equalsIgnoreCase("decimal") ? SerialFieldType.DECIMAL : SerialFieldType.COMPACT_INT);
			builder.addRequiredField(recordName, "Bid.Price", SerialFieldType.DECIMAL);
			builder.addRequiredField(recordName, "Ask.Price", SerialFieldType.DECIMAL);
			builder.addRequiredField(recordName, "ExchangeSaleConditions", SerialFieldType.SHORT_STRING);
			builder.addRequiredField(recordName, "Flags", SerialFieldType.COMPACT_INT);
			builder.addOptionalField(recordName, "Buyer", SerialFieldType.UTF_CHAR_ARRAY, "TimeAndSale", "Buyer", false);
			builder.addOptionalField(recordName, "Seller", SerialFieldType.UTF_CHAR_ARRAY, "TimeAndSale", "Seller", false);
		}
	}

	@Override
	public Collection<EventDelegate<?>> createDelegates(DataRecord record) {
		Collection<EventDelegate<?>> result = new ArrayList<>();
		if (record.getMapping(QuoteMapping.class) != null) {
			result.add(new QuoteDelegate(record, QDContract.TICKER, EnumSet.of(EventDelegateFlags.SUB, EventDelegateFlags.PUB)));
			result.add(new QuoteDelegate(record, QDContract.STREAM, EnumSet.of(EventDelegateFlags.PUB, EventDelegateFlags.WILDCARD)));
			result.add(new OrderByQuoteBidDelegate(record, QDContract.TICKER, EnumSet.of(EventDelegateFlags.SUB)));
			result.add(new OrderByQuoteBidDelegate(record, QDContract.STREAM, EnumSet.of(EventDelegateFlags.WILDCARD)));
			result.add(new OrderByQuoteAskDelegate(record, QDContract.TICKER, EnumSet.of(EventDelegateFlags.SUB)));
			result.add(new OrderByQuoteAskDelegate(record, QDContract.STREAM, EnumSet.of(EventDelegateFlags.WILDCARD)));
		} else if (record.getMapping(TradeMapping.class) != null) {
			result.add(new TradeDelegate(record, QDContract.TICKER, EnumSet.of(EventDelegateFlags.SUB, EventDelegateFlags.PUB)));
			result.add(new TradeDelegate(record, QDContract.STREAM, EnumSet.of(EventDelegateFlags.PUB, EventDelegateFlags.WILDCARD)));
		} else if (record.getMapping(TradeETHMapping.class) != null) {
			result.add(new TradeETHDelegate(record, QDContract.TICKER, EnumSet.of(EventDelegateFlags.SUB, EventDelegateFlags.PUB)));
			result.add(new TradeETHDelegate(record, QDContract.STREAM, EnumSet.of(EventDelegateFlags.PUB, EventDelegateFlags.WILDCARD)));
		} else if (record.getMapping(SummaryMapping.class) != null) {
			result.add(new SummaryDelegate(record, QDContract.TICKER, EnumSet.of(EventDelegateFlags.SUB, EventDelegateFlags.PUB)));
			result.add(new SummaryDelegate(record, QDContract.STREAM, EnumSet.of(EventDelegateFlags.PUB, EventDelegateFlags.WILDCARD)));
		} else if (record.getMapping(ProfileMapping.class) != null) {
			result.add(new ProfileDelegate(record, QDContract.TICKER, EnumSet.of(EventDelegateFlags.SUB, EventDelegateFlags.PUB)));
			result.add(new ProfileDelegate(record, QDContract.STREAM, EnumSet.of(EventDelegateFlags.PUB, EventDelegateFlags.WILDCARD)));
		} else if (record.getMapping(OrderMapping.class) != null) {
			result.add(new OrderDelegate(record, QDContract.STREAM, EnumSet.of(EventDelegateFlags.PUB, EventDelegateFlags.WILDCARD)));
			result.add(new OrderDelegate(record, QDContract.HISTORY, EnumSet.of(EventDelegateFlags.SUB, EventDelegateFlags.PUB)));
		} else if (record.getMapping(SpreadOrderMapping.class) != null) {
			result.add(new SpreadOrderDelegate(record, QDContract.STREAM, EnumSet.of(EventDelegateFlags.PUB, EventDelegateFlags.WILDCARD)));
			result.add(new SpreadOrderDelegate(record, QDContract.HISTORY, EnumSet.of(EventDelegateFlags.SUB, EventDelegateFlags.PUB)));
		} else if (record.getMapping(MarketMakerMapping.class) != null) {
			result.add(new OrderByMarketMakerBidDelegate(record, QDContract.STREAM, EnumSet.of(EventDelegateFlags.WILDCARD)));
			result.add(new OrderByMarketMakerBidDelegate(record, QDContract.HISTORY, EnumSet.of(EventDelegateFlags.SUB)));
			result.add(new OrderByMarketMakerAskDelegate(record, QDContract.STREAM, EnumSet.of(EventDelegateFlags.WILDCARD)));
			result.add(new OrderByMarketMakerAskDelegate(record, QDContract.HISTORY, EnumSet.of(EventDelegateFlags.SUB)));
		} else if (record.getMapping(TimeAndSaleMapping.class) != null) {
			result.add(new TimeAndSaleDelegate(record, QDContract.STREAM, EnumSet.of(EventDelegateFlags.SUB, EventDelegateFlags.PUB, EventDelegateFlags.WILDCARD)));
			result.add(new TimeAndSaleDelegate(record, QDContract.HISTORY, EnumSet.of(EventDelegateFlags.SUB, EventDelegateFlags.PUB, EventDelegateFlags.TIME_SERIES)));
		}
		return result;
	}

	@Override
	public Collection<EventDelegate<?>> createStreamOnlyDelegates(DataRecord record) {
		Collection<EventDelegate<?>> result = new ArrayList<>();
		if (record.getMapping(QuoteMapping.class) != null) {
			result.add(new QuoteDelegate(record, QDContract.STREAM, EnumSet.of(EventDelegateFlags.SUB, EventDelegateFlags.PUB, EventDelegateFlags.WILDCARD)));
			result.add(new OrderByQuoteBidDelegate(record, QDContract.STREAM, EnumSet.of(EventDelegateFlags.SUB, EventDelegateFlags.WILDCARD)));
			result.add(new OrderByQuoteAskDelegate(record, QDContract.STREAM, EnumSet.of(EventDelegateFlags.SUB, EventDelegateFlags.WILDCARD)));
		} else if (record.getMapping(TradeMapping.class) != null) {
			result.add(new TradeDelegate(record, QDContract.STREAM, EnumSet.of(EventDelegateFlags.SUB, EventDelegateFlags.PUB, EventDelegateFlags.WILDCARD)));
		} else if (record.getMapping(TradeETHMapping.class) != null) {
			result.add(new TradeETHDelegate(record, QDContract.STREAM, EnumSet.of(EventDelegateFlags.SUB, EventDelegateFlags.PUB, EventDelegateFlags.WILDCARD)));
		} else if (record.getMapping(SummaryMapping.class) != null) {
			result.add(new SummaryDelegate(record, QDContract.STREAM, EnumSet.of(EventDelegateFlags.SUB, EventDelegateFlags.PUB, EventDelegateFlags.WILDCARD)));
		} else if (record.getMapping(ProfileMapping.class) != null) {
			result.add(new ProfileDelegate(record, QDContract.STREAM, EnumSet.of(EventDelegateFlags.SUB, EventDelegateFlags.PUB, EventDelegateFlags.WILDCARD)));
		} else if (record.getMapping(OrderMapping.class) != null) {
			result.add(new OrderDelegate(record, QDContract.STREAM, EnumSet.of(EventDelegateFlags.SUB, EventDelegateFlags.PUB, EventDelegateFlags.WILDCARD)));
		} else if (record.getMapping(SpreadOrderMapping.class) != null) {
			result.add(new SpreadOrderDelegate(record, QDContract.STREAM, EnumSet.of(EventDelegateFlags.SUB, EventDelegateFlags.PUB, EventDelegateFlags.WILDCARD)));
		} else if (record.getMapping(MarketMakerMapping.class) != null) {
			result.add(new OrderByMarketMakerBidDelegate(record, QDContract.STREAM, EnumSet.of(EventDelegateFlags.SUB, EventDelegateFlags.WILDCARD)));
			result.add(new OrderByMarketMakerAskDelegate(record, QDContract.STREAM, EnumSet.of(EventDelegateFlags.SUB, EventDelegateFlags.WILDCARD)));
		} else if (record.getMapping(TimeAndSaleMapping.class) != null) {
			result.add(new TimeAndSaleDelegate(record, QDContract.STREAM, EnumSet.of(EventDelegateFlags.SUB, EventDelegateFlags.PUB, EventDelegateFlags.WILDCARD)));
		}
		return result;
	}

	@Override
	public RecordMapping createMapping(DataRecord record) {
		String baseRecordName = getBaseRecordName(record.getName());
		if (baseRecordName.equals("Quote"))
			return new QuoteMapping(record);
		if (baseRecordName.equals("Trade"))
			return new TradeMapping(record);
		if (baseRecordName.equals("TradeETH"))
			return new TradeETHMapping(record);
		if (baseRecordName.equals("Summary"))
			return new SummaryMapping(record);
		if (baseRecordName.equals("Fundamental"))
			return new FundamentalMapping(record);
		if (baseRecordName.equals("Book"))
			return new BookMapping(record);
		if (baseRecordName.equals("Profile"))
			return new ProfileMapping(record);
		if (baseRecordName.equals("Order"))
			return new OrderMapping(record);
		if (baseRecordName.equals("SpreadOrder"))
			return new SpreadOrderMapping(record);
		if (baseRecordName.equals("MarketMaker"))
			return new MarketMakerMapping(record);
		if (baseRecordName.equals("TimeAndSale"))
			return new TimeAndSaleMapping(record);
		return null;
	}
// END: CODE AUTOMATICALLY GENERATED

	@Override
	protected String getBaseRecordName(String recordName) {
		String s = MarketEventSymbols.getBaseSymbol(recordName);
		int i = s.lastIndexOf(OrderBaseMapping.SOURCE_ID_SEPARATOR);
		return i < 0 ? s : s.substring(0, i);
	}

}
