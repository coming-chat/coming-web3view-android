package coming.web3.enity.repository;


import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;

import coming.web3.enity.repository.ethereum.EthereumNetworkInfo;
import coming.web3.token.tools.Numeric;

/**
 * Created by JB on 21/10/2020.
 */

public class EtherscanEvent
{
    public String blockNumber;
    public long timeStamp;
    public String hash;
    public int nonce;
    String blockHash;
    public String from;
    public String contractAddress;
    public String to;
    public String tokenID;
    public String value;
    public String tokenName;
    public String tokenSymbol;
    public String tokenDecimal;
    public String input;
    String gas;
    String gasPrice;
    String gasUsed;

    public Transaction createTransaction(@NotNull EthereumNetworkInfo networkInfo)
    {
        BigInteger valueBI = BigInteger.ZERO;
        if (value != null && value.length() > 0 && Character.isDigit(value.charAt(0)))
        {
            valueBI = new BigInteger(value);
        }

        String input = Numeric.toHexString(TokenRepository.createTokenTransferData(to, valueBI)); //write the input to the transaction to ensure this is correctly handled elsewhere in the wallet

        return new Transaction(hash, "0", blockNumber, timeStamp, nonce, from, contractAddress, "0", gas, gasPrice, input,
                gasUsed, networkInfo.chainId, false);
    }

    public Transaction createNFTTransaction(@NotNull EthereumNetworkInfo networkInfo)
    {
        BigInteger tokenId = BigInteger.ONE;
        try
        {
            tokenId = new BigInteger(tokenID);
        }
        catch (Exception e)
        {
            //no action, default to '1'
        }

        String input = Numeric.toHexString(TokenRepository.createERC721TransferFunction(from, to, contractAddress, tokenId)); //write the input to the transaction to ensure this is correctly handled elsewhere in the wallet

        return new Transaction(hash, "0", blockNumber, timeStamp, nonce, from, contractAddress, "0", gas, gasPrice, input,
                gasUsed, networkInfo.chainId, false);
    }
}
