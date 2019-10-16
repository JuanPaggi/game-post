package com.game.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Sha1Hasher {

	public static byte[] hashBytes(byte[] input) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-1"); 
		return md.digest(input); 
	}
	
	public static String hashString(String input) throws NoSuchAlgorithmException {
		return hashBytes2String(Sha1Hasher.hashBytes(input.getBytes()));
	}
	
	public static String hashBytes2String(byte[] input) {
		BigInteger digester = new BigInteger(1, input);
		String hashtext = digester.toString(16);
		// fully with zero:
		while (hashtext.length() < 32) { 
            hashtext = "0" + hashtext; 
        }
        return hashtext; 
	}
}
