package davi.hashpassword.base;

import static davi.hashpassword.base.TestUtils.getPassword;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import davi.hashpassword.PasswordEncoder;

public abstract class AbstractHashFunctionTest extends AbstractPasswordEncoderTest<PasswordEncoder> {

	@Override
	@Test
	public void sameHashForEqualPasswordsTest() {
		String hashed1 = alg.encode(getPassword());
		String hashed2 = alg.encode(getPassword());
		
		assertEquals(hashed1, hashed2);
	
		assertTrue(alg.matches(getPassword(), hashed1));
		assertTrue(alg.matches(getPassword(), hashed2));
	}

}
