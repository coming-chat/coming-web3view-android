package coming.web3.service;

import static coming.web3.enity.repository.ServiceErrorException.ServiceErrorCode.KEY_IS_GONE;

import android.content.Context;
import android.security.keystore.UserNotAuthenticatedException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import coming.web3.R;
import coming.web3.enity.repository.ServiceErrorException;

public class LegacyKeystore
{
    private static final String LEGACY_CIPHER_ALGORITHM = "AES/CBC/PKCS7Padding";
    private static final String ANDROID_KEY_STORE = "AndroidKeyStore";

    public static synchronized byte[] getLegacyPassword(
            final Context context,
            String keyName)
            throws ServiceErrorException
    {
        KeyStore keyStore;
        String encryptedDataFilePath = KeyService.getFilePath(context, keyName);
        try
        {
            keyStore = KeyStore.getInstance(ANDROID_KEY_STORE);
            keyStore.load(null);
            SecretKey secretKey = (SecretKey) keyStore.getKey(keyName, null);
            if (secretKey == null)
            {
                /* no such key, the key is just simply not there */
                boolean fileExists = new File(encryptedDataFilePath).exists();
                if (!fileExists)
                {
                    throw new ServiceErrorException(KEY_IS_GONE, context.getString(R.string.cannot_read_encrypt_file));
                }
                else
                {
                    throw new ServiceErrorException(KEY_IS_GONE, context.getString(R.string.cannot_read_encrypt_file));
                }
            }

            String keyIV = keyName + "iv";
            boolean ivExists = new File(KeyService.getFilePath(context, keyIV)).exists();
            boolean aliasExists = new File(KeyService.getFilePath(context, keyName)).exists();
            if (!ivExists || !aliasExists)
            {
                throw new ServiceErrorException(ServiceErrorException.ServiceErrorCode.IV_OR_ALIAS_NO_ON_DISK, context.getString(R.string.cannot_read_encrypt_file));
            }

            byte[] iv = KeyService.readBytesFromFile(KeyService.getFilePath(context, keyIV));
            if (iv == null || iv.length == 0)
            {
                throw new NullPointerException(context.getString(R.string.cannot_read_encrypt_file));
            }
            Cipher outCipher = Cipher.getInstance(LEGACY_CIPHER_ALGORITHM);
            outCipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(iv));
            CipherInputStream cipherInputStream = new CipherInputStream(new FileInputStream(encryptedDataFilePath), outCipher);
            return KeyService.readBytesFromStream(cipherInputStream);
        } catch (Exception e) {
            throw new ServiceErrorException(ServiceErrorException.ServiceErrorCode.USER_NOT_AUTHENTICATED, context.getString(R.string.authentication_error));
        }
    }
}
