package davi.hashpassword.tests;

import static davi.hashpassword.base.TestUtils.getPassword;
import static davi.hashpassword.base.TestUtils.getPassword2;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import davi.hashpassword.KeyStretchingPasswordEncoder;
import davi.hashpassword.base.AbstractKeyStretchingTest;
import davi.hashpassword.pbkdf2.impl.PBKDF2PasswordEncoder2;

public class PBKDF2PasswordEncoderTest extends AbstractKeyStretchingTest {

	@BeforeEach
	public void before() {
		alg = new PBKDF2PasswordEncoder2(10000);
	}
	
	@Test
	public void testHashSize() {
		//1000:3e004efa1a1c28392bd4db83df72027fe16343580180d890:d3d76858fa9163c113840784b46bb6dee09b44bd373c9cc442c86f28688b3a78aa93fc8ee2f8f489d2fed972eecffb37071025bf70de23a8b91989f89d9f5aeb
		
		Pattern pattern = Pattern.compile("^(\\d+)\\:([a-fA-F\\d]+)\\:([a-fA-F\\d]+)$");
		
		String hashedPassword = alg.encode(getPassword());
		System.out.println(hashedPassword);
		
		Matcher matcher = pattern.matcher(hashedPassword);
		assertTrue(matcher.matches());
		
		String iterations = matcher.group(1);
		String salt = matcher.group(2);
		String hashedKey = matcher.group(3);
		
		System.out.println(iterations);
		System.out.println(salt);
		System.out.println(hashedKey);
		
		assertEquals(24 * 2, salt.length());
		assertEquals(64 * 2, hashedKey.length());
	}

	@Override
	public void differentHashsForSamePasswordWithDifferentRoundsTest() {
		KeyStretchingPasswordEncoder alg1 = new PBKDF2PasswordEncoder2(10000);
		KeyStretchingPasswordEncoder alg2 = new PBKDF2PasswordEncoder2(15000);
		KeyStretchingPasswordEncoder alg3 = new PBKDF2PasswordEncoder2(30000);
		
		String hashed1 = alg1.encode(getPassword());
		String hashed2 = alg2.encode(getPassword());
		
		assertNotEquals(hashed1, hashed2);
		
		assertTrue(alg1.matches(getPassword(), hashed1));
		assertTrue(alg1.matches(getPassword(), hashed2));
		assertTrue(alg2.matches(getPassword(), hashed1));
		assertTrue(alg2.matches(getPassword(), hashed2));
		assertTrue(alg3.matches(getPassword(), hashed1));
		assertTrue(alg3.matches(getPassword(), hashed2));

		assertFalse(alg1.matches(getPassword2(), hashed1));
		assertFalse(alg1.matches(getPassword2(), hashed2));
		assertFalse(alg2.matches(getPassword2(), hashed1));
		assertFalse(alg2.matches(getPassword2(), hashed2));
		assertFalse(alg3.matches(getPassword2(), hashed1));
		assertFalse(alg3.matches(getPassword2(), hashed2));
	}
}
