package davi.hashpassword.sha512;

import java.security.MessageDigest;

public class SHA512 {

	public static String convertByteToHex(byte data[]) {
		StringBuilder hexData = new StringBuilder();
		for (int byteIndex = 0; byteIndex < data.length; byteIndex++) {
			hexData.append(Integer.toString((data[byteIndex] & 0xff) + 0x100, 16).substring(1));
		}
		return hexData.toString();
	}

	public static String hashText(String textToHash) throws Exception {
		final MessageDigest sha512 = MessageDigest.getInstance("SHA-512");
		sha512.update(textToHash.getBytes());

		return convertByteToHex(sha512.digest());
	}
}