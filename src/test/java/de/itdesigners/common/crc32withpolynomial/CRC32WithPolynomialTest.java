/*
 * Copyright (c) 2014, 	 and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package de.itdesigners.common.crc32withpolynomial;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Test for class {@link CRC32WithPolynomial}
 */
@RunWith(Parameterized.class)
public class CRC32WithPolynomialTest {
	private static final int REFLECTED_POLYNOMIAL = 0xc8df352f;

	private byte[] input;
	private long expected;

	public CRC32WithPolynomialTest(byte[] input, long expected) {
		this.input = input;
		this.expected = expected;
	}

	@Parameters(name = "Test {index}: crcValue({0})={1}")
	public static Collection<Object[]> data() {
		return Arrays.asList(
				new Object[][] { { new byte[] { (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00 }, 0x6FB32240L },
						{ new byte[] { (byte) 0xF2, (byte) 0x01, (byte) 0x83 }, 0x4F721A25L },
						{ new byte[] { (byte) 0x0F, (byte) 0xAA, (byte) 0x00, (byte) 0x55 }, 0x20662DF8L },
						{ new byte[] { (byte) 0x00, (byte) 0xFF, (byte) 0x55, (byte) 0x11 }, 0x9BD7996EL },
						{ new byte[] { (byte) 0x33, (byte) 0x22, (byte) 0x55, (byte) 0xAA, (byte) 0xBB, (byte) 0xCC,
								(byte) 0xDD, (byte) 0xEE, (byte) 0xFF }, 0xA65A343DL },
						{ new byte[] { (byte) 0x92, (byte) 0x6B, (byte) 0x55, }, 0xEE688A78L },
						{ new byte[] { (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF }, 0xFFFFFFFFL } });
	}

	@Test
	public void test() {
		final CRC32WithPolynomial crc = new CRC32WithPolynomial(REFLECTED_POLYNOMIAL);
		crc.update(input);
		assert expected == crc.getValue();
	}
}