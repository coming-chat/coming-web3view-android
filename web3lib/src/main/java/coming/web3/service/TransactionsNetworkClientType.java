package coming.web3.service;


import coming.web3.enity.repository.Transaction;
import coming.web3.enity.repository.TransactionMeta;
import coming.web3.enity.repository.ethereum.EthereumNetworkInfo;
import coming.web3.enity.repository.ethereum.NetworkInfo;
import io.reactivex.Single;

public interface TransactionsNetworkClientType {
    Single<Transaction[]> storeNewTransactions(String walletAddress, EthereumNetworkInfo networkInfo, String tokenAddress, long lastBlock);
    Single<TransactionMeta[]> fetchMoreTransactions(String walletAddress, EthereumNetworkInfo network, long lastTxTime);
    Single<Integer> readTransfers(String currentAddress, EthereumNetworkInfo networkByChain, TokensService tokensService, boolean nftCheck);
    void checkRequiresAuxReset(String walletAddr);
}
