/**
 *  Catroid: An on-device visual programming system for Android devices
 *  Copyright (C) 2010-2013 The Catrobat Team
 *  (<http://developer.catrobat.org/credits>)
 *  
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Affero General Public License as
 *  published by the Free Software Foundation, either version 3 of the
 *  License, or (at your option) any later version.
 *  
 *  An additional term exception under section 7 of the GNU Affero
 *  General Public License, version 3, is available at
 *  http://developer.catrobat.org/license_additional_term
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU Affero General Public License for more details.
 *  
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.catrobat.musicdroid.note.symbol;

import junit.framework.TestCase;

import org.catrobat.musicdroid.note.MockDataFactory;

import java.util.List;

public class TrackToSymbolsConverterTest extends TestCase {

	public void testConvertTrack1() {
		TrackToSymbolsConverter trackConverter = new TrackToSymbolsConverter();

		List<AbstractSymbol> expectedSymbols = MockDataFactory.createSymbolList1();
		List<AbstractSymbol> actualSymbols = trackConverter.convertTrack(MockDataFactory.createTrack1());

		assertEquals(expectedSymbols.size(), actualSymbols.size());
		assertEquals(expectedSymbols, actualSymbols);
	}

	public void testConvertTrack2() {
		TrackToSymbolsConverter trackConverter = new TrackToSymbolsConverter();

		List<AbstractSymbol> expectedSymbols = MockDataFactory.createSymbolList2();
		List<AbstractSymbol> actualSymbols = trackConverter.convertTrack(MockDataFactory.createTrack2());

		assertEquals(expectedSymbols.size(), actualSymbols.size());
		assertEquals(expectedSymbols, actualSymbols);
	}

	public void testConvertTrack3() {
		TrackToSymbolsConverter trackConverter = new TrackToSymbolsConverter();

		List<AbstractSymbol> expectedSymbols = MockDataFactory.createSymbolList3();
		List<AbstractSymbol> actualSymbols = trackConverter.convertTrack(MockDataFactory.createTrack3());

		assertEquals(expectedSymbols.size(), actualSymbols.size());
		assertEquals(expectedSymbols, actualSymbols);
	}

	public void testConvertTrack4() {
		TrackToSymbolsConverter trackConverter = new TrackToSymbolsConverter();

		List<AbstractSymbol> expectedSymbols = MockDataFactory.createSymbolList4();
		List<AbstractSymbol> actualSymbols = trackConverter.convertTrack(MockDataFactory.createTrack4());

		assertEquals(expectedSymbols.size(), actualSymbols.size());
		assertEquals(expectedSymbols, actualSymbols);
	}
}
