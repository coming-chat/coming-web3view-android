package coming.web3.enity.repository.ethereum;

import static coming.web3.enity.repository.EthereumNetworkBase.COVALENT;

import android.net.Uri;

import androidx.annotation.Nullable;

import coming.web3.enity.repository.EthereumNetworkRepository;
import coming.web3.util.Utils;

public class EthereumNetworkInfo  extends NetworkInfo{
    private final String BLOCKSCOUT_API = "blockscout";
    private final String MATIC_API = "polygonscan";
    private final String PALM_API = "explorer.palm";

    public  String backupNodeUrl = null;
    public  String etherscanAPI = null; //This is used by the API call to fetch transactions

    public EthereumNetworkInfo(
            String name,
            String symbol,
            String rpcServerUrl,
            String etherscanUrl,
            long chainId,
            String backupNodeUrl,
            String etherscanAPI,
            boolean isCustom) {
        super(name, symbol, rpcServerUrl, etherscanUrl, chainId, isCustom);
        this.backupNodeUrl = backupNodeUrl;
        this.etherscanAPI = etherscanAPI;
    }

    public EthereumNetworkInfo(
            String name,
            String symbol,
            String rpcServerUrl,
            String etherscanUrl,
            long chainId,
            String backupNodeUrl,
            String etherscanAPI) {
        super(name, symbol, rpcServerUrl, etherscanUrl, chainId, false);
        this.backupNodeUrl = backupNodeUrl;
        this.etherscanAPI = etherscanAPI;
    }

    public EthereumNetworkInfo(String name, String symbol, String rpcServerUrl, String etherscanUrl, long chainId, boolean isCustom) {
        super(name, symbol, rpcServerUrl, etherscanUrl, chainId, isCustom);
    }

    public String getShortName()
    {
        int index = this.name.indexOf(" (Test)");
        if (index > 0) return this.name.substring(0, index);
        else if (this.name.length() > 10) return this.symbol;
        else return this.name;
    }

    public boolean usesSeparateNFTTransferQuery()
    {
        return (etherscanAPI != null && !etherscanAPI.contains(BLOCKSCOUT_API) && !etherscanAPI.contains(MATIC_API)
                && !etherscanAPI.contains(COVALENT) && !etherscanAPI.contains(PALM_API));
    }

    @Nullable
    public Uri getEtherscanUri(String transactionHash) {
        if (etherscanUrl != null)
        {
            return Uri.parse(etherscanUrl)
                    .buildUpon()
                    .appendEncodedPath(transactionHash)
                    .build();
        }
        else
        {
            return Uri.EMPTY;
        }
    }

    public Uri getEtherscanAddressUri(String value)
    {
        if (etherscanUrl != null)
        {
            String explorer = etherscanUrl;
            if (Utils.isAddressValid(value))
            {
                explorer = explorer.substring(0, explorer.lastIndexOf("tx/"));
                explorer += "address/";
            }
            else if (!Utils.isTransactionHash(value))
            {
                return Uri.EMPTY;
            }

            return Uri.parse(explorer)
                    .buildUpon()
                    .appendEncodedPath(value)
                    .build();
        }
        else
        {
            return Uri.EMPTY;
        }
    }

    public boolean hasRealValue()
    {
        return EthereumNetworkRepository.hasRealValue(this.chainId);
    }

}
