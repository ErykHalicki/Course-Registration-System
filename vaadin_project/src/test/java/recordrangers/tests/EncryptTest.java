package recordrangers.tests;
import java.security.NoSuchAlgorithmException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import recordrangers.services.Encrypt;

public class EncryptTest {

    private Encrypt encrypt;

    @Before
    public void init() {
        encrypt = new Encrypt();
    }

    @Test
    public void testCorrectOutput() throws NoSuchAlgorithmException{
        String password = "superSecurePassword";
        String result = encrypt.getHash(password);
        // Expected output from here: https://emn178.github.io/online-tools/sha256.html
        String expectedOutput = "2690c8a7b4c1059c611f94bc1e50db9365ebac4a2677abc290e6487afc1286d4"; 
        
        Assert.assertEquals(result, expectedOutput);
    }

}
