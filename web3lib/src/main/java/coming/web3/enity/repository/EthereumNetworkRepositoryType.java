package coming.web3.enity.repository;

import org.web3j.protocol.Web3j;

import java.math.BigInteger;
import java.util.List;

import coming.web3.enity.repository.ethereum.EthereumNetworkInfo;
import coming.web3.enity.repository.tokens.Token;
import io.reactivex.Single;

public interface EthereumNetworkRepositoryType {

    EthereumNetworkInfo getActiveBrowserNetwork();

    void setActiveBrowserNetwork(EthereumNetworkInfo networkInfo);

    EthereumNetworkInfo getNetworkByChain(long chainId);

    Single<BigInteger> getLastTransactionNonce(Web3j web3j, String walletAddress);

    EthereumNetworkInfo[] getAvailableNetworkList();
    EthereumNetworkInfo[] getAllActiveNetworks();

    void addOnChangeDefaultNetwork(OnNetworkChangeListener onNetworkChanged);

    String getNameById(long chainId);

    List<Long> getFilterNetworkList();
    List<Long> getSelectedFilters(boolean isMainNet);
    Long getDefaultNetwork(boolean isMainNet);

    void setFilterNetworkList(Long[] networkList);

    List<ContractLocator> getAllKnownContracts(List<Long> networkFilters);

    Single<Token[]> getBlankOverrideTokens(Wallet wallet);

    Token getBlankOverrideToken();

    Token getBlankOverrideToken(EthereumNetworkInfo networkInfo);

    KnownContract readContracts();

    boolean getIsPopularToken(long chainId, String address);

    String getCurrentWalletAddress();
    boolean hasSetNetworkFilters();
    void setHasSetNetworkFilters();
    boolean isMainNetSelected();
    void setActiveMainnet(boolean isMainNet);

    void addCustomRPCNetwork(String networkName, String rpcUrl, long chainId, String symbol, String blockExplorerUrl, String explorerApiUrl, boolean isTestnet, Long oldChainId);
    void removeCustomRPCNetwork(long chainId);

    boolean isChainContract(long chainId, String address);
    boolean hasLockedGas(long chainId);
}
