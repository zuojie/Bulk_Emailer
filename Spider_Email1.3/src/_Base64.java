import java.io.BufferedInputStream;
import java.util.Scanner;

import sun.misc.BASE64Encoder;


public class _Base64 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner pz = new Scanner(new BufferedInputStream(System.in));
		BASE64Encoder encoder = new BASE64Encoder();
		System.out.println("Input string to be encoded:");
		String chr = pz.next();
		System.out.println(encoder.encode(chr.getBytes()));
	}
}
