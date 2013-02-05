package models;

import java.util.ArrayList;

/*
 * Base64 code table.
 * 
0 	A 	16 	Q 	32 	g 	48 	w
1 	B 	17 	R 	33 	h 	49 	x
2 	C 	18 	S 	34 	i 	50 	y
3 	D 	19 	T 	35 	j 	51 	z
4 	E 	20 	U 	36 	k 	52 	0
5 	F 	21 	V 	37 	l 	53 	1
6 	G 	22 	W 	38 	m 	54 	2
7 	H 	23 	X 	39 	n 	55 	3
8 	I 	24 	Y 	40 	o 	56 	4
9 	J 	25 	Z 	41 	p 	57 	5
10 	K 	26 	a 	42 	q 	58 	6
11 	L 	27 	b 	43 	r 	59 	7
12 	M 	28 	c 	44 	s 	60 	8
13 	N 	29 	d 	45 	t 	61 	9
14 	O 	30 	e 	46 	u 	62 	+
15 	P 	31 	f 	47 	v 	63 	/	
*/

public class Base64 {

	private final static int base64encodeMask = 0x3f;
	private final static int base64decodeMask = 0xff0;

	private final static byte[] base64decodeTable = { -71, -65, 4, 19, 16 };
	private final static char[] base64encodeTable = {
			'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
			'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f',
			'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
			'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'
	};
		
	public Base64() {
	}

	public static byte[] encode(byte[] data) {
		ArrayList<Byte> base64encoded = new ArrayList<Byte>();
		
		int i, j, ch = 0, length = data.length, mask = base64encodeMask;

		if ( (length & 1) != 0 )
			length++;
			
		length += data.length/3;
		
		for( i = 0, j = 0; i < data.length; i++) {
			int shift = (1 + j % 4) << 1;
			
			mask = mask << 2;
			
			ch = (char)((ch << 8) | (data[i] & 0xff));
			base64encoded.add((byte)base64encodeTable[(ch & mask) >> shift]);
			j++;
			
			if ( mask == 0xfc0 ) {
				mask = base64encodeMask;
				base64encoded.add((byte)base64encodeTable[(ch & mask)]);
				j++;
			}
		}

		if ( length + 1 > base64encoded.size() ) {
			int shift = (1 + j % 4) << 1;
			
			mask = mask << 2; ch <<= 8;
			base64encoded.add((byte)base64encodeTable[((ch & mask) >> shift)]);
		}

		byte[] encoded = new byte[base64encoded.size()];
		for( i = 0; i < encoded.length; i++ )
			encoded[i] = base64encoded.get(i);
		
		return encoded;
	}

	
	private static char decodeBase64(char ch) {
		int value;
		for(int i = 0; i < base64decodeTable.length; i++ ) {
			value = (char)(ch + base64decodeTable[i]);
			if ( value >= 0 && value <= 63 && ch == base64encodeTable[value] ) {
				return (char)value;
			}
		}
		
		throw new RuntimeException("Bad Base64 value: " + ch);
	}
	
	
	public static byte[] decode(byte[] data) {
		int i, ch, shift = 4, mask = base64decodeMask;
		ArrayList<Byte> base64decoded = new ArrayList<Byte>();
				
		ch = decodeBase64((char)data[0]);
		for( i = 1; i < data.length ; ) {
			
			ch = (char) ((ch << 6) + decodeBase64((char)data[i++]));
			base64decoded.add((byte)((ch & mask) >> shift));
			mask = mask >> 2;
			shift -= 2;
			
			if ( mask == 0xff && i < data.length ) {
				ch = (char) ((ch << 6) + decodeBase64((char)data[i++]));
				base64decoded.add((byte)(ch & mask));
				
				if ( i < data.length ) {
					ch = decodeBase64((char)data[i++]);
					mask = base64decodeMask;
					shift = 4;
				}
			}
		}
		
		if ( i < data.length ) {
			if ( shift < 0 )
				shift = 0;
			
			ch = (char) ((ch << 6) + decodeBase64((char)data[i++]));
			base64decoded.add((byte)((ch & mask) >> shift));
		}

		byte[] decoded = new byte[base64decoded.size()];
		for( i = 0; i < decoded.length; i++ )
			decoded[i] = base64decoded.get(i);
		
		return decoded;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String message = new String("1234567890!#¤%&/()=?");

		byte[] encoded = Base64.encode(message.getBytes());
		byte[] decoded = Base64.decode(encoded);
		
		System.out.println("encoded: " + new String(encoded));
		System.out.println("decoded: " + new String(decoded));
		System.out.println("message: " + message);
	}

}
