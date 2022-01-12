package coming.web3.widget.entity;

import java.math.BigInteger;

/**
 * Created by JB on 26/11/2020.
 */
public interface GasSettingsCallback
{
    void gasSettingsUpdate(BigInteger gasPrice, BigInteger gasLimit);
}
