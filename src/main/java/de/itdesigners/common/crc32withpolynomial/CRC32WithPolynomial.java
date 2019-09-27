/*
 * Computes CRC32 data checksum of a data stream
 *    Copyright (C) 1999. 2000, 2001 Free Software Foundation, Inc.
 * 
 * This file was originally part of GNU Classpath (CRC32.java).
 * 
 * GNU Classpath is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2, or (at your option)
 * any later version.
 * 
 * GNU Classpath is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with GNU Classpath; see the file COPYING.  If not, write to the
 * Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA
 * 02110-1301 USA.
 * 
 * Linking this library statically or dynamically with other modules is
 * making a combined work based on this library.  Thus, the terms and
 * conditions of the GNU General Public License cover the whole
 * combination.
 * 
 * As a special exception, the copyright holders of this library give you
 * permission to link this library with independent modules to produce an
 * executable, regardless of the license terms of these independent
 * modules, and to copy and distribute the resulting executable under
 * terms of your choice, provided that you also meet, for each linked
 * independent module, the terms and conditions of the license of that
 * module.  An independent module is a module which is not derived from
 * or based on this library.  If you modify this library, you may extend
 * this exception to your version of the library, but you are not
 * obligated to do so.  If you do not wish to do so, delete this
 * exception statement from your version. 
 */
/*
 * Modified work Copyright 2019 IT-Designers
 */
package de.itdesigners.common.crc32withpolynomial;

/**
 * Class used to calculate the CRC modifiable polynomial.
 */
public class CRC32WithPolynomial {
	/**
	 * The crc data checksum so far.
	 */
	private int crc = 0;
	private static final int SIZE = 256;
	private static final int NUMBER_OF_BITS_IN_BYTES = 8;
	/**
	 * The polynomial used for CRC32 calculations.
	 */
	private int polynomial;
	/**
	 * The fast CRC table. Computed once when the CRC32 class is instantiated.
	 */
	protected final int[] crcTable;

	/**
	 * Constructor with polynomial.
	 * 
	 * @param polynomial the polynomial
	 */
	public CRC32WithPolynomial(int polynomial) {
		this.polynomial = polynomial;
		crcTable = makeCrcTable();
	}

	/**
	 * The polynomial used by this instance of {@link CRC32WithPolynomial} to
	 * generate CRC32 checksums.
	 * 
	 * @return the polynomial
	 */
	public int getPolynomial() {
		return polynomial;
	}

	/**
	 * Make the table for a fast CRC.
	 */
	private int[] makeCrcTable() {
		int[] tmpCrcTable = new int[SIZE];
		for (int n = 0; n < SIZE; n++) {
			int c = n;
			for (int k = NUMBER_OF_BITS_IN_BYTES; --k >= 0;) {
				if ((c & 1) != 0) {
					c = polynomial ^ (c >>> 1);
				} else {
					c = c >>> 1;
				}
			}
			tmpCrcTable[n] = c;
		}
		return tmpCrcTable;
	}

	/**
	 * Returns the CRC32 data checksum computed so far.
	 */
	public long getValue() {
		return crc & 0xffffffffL;
	}

	/**
	 * Resets the CRC32 data checksum as if no update was ever called.
	 */
	public void reset() {
		crc = 0;
	}

	/**
	 * Updates the checksum with the int bval.
	 *
	 * @param bval (the byte is taken as the lower 8 bits of bval)
	 */

	public void update(int bval) {
		int c = ~crc;
		c = crcTable[(c ^ bval) & 0xff] ^ (c >>> NUMBER_OF_BITS_IN_BYTES);
		crc = ~c;
	}

	/***
	 * Adds the byte array to the data checksum.*
	 *
	 *
	 * @param buf the buffer which contains the data
	 * @param off the offset in the buffer where the data starts
	 * @param len the length of the data
	 */
	public void update(byte[] buf, int off, int len) {
		int c = ~crc;
		while (--len >= 0) {
			c = crcTable[(c ^ buf[off++]) & 0xff] ^ (c >>> NUMBER_OF_BITS_IN_BYTES);
		}
		crc = ~c;
	}

	/**
	 * Adds the complete byte array to the data checksum.
	 * 
	 * @param buf buf
	 */
	public void update(byte[] buf) {
		update(buf, 0, buf.length);
	}
}
