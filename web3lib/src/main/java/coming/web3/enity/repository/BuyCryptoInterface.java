package coming.web3.enity.repository;


import coming.web3.enity.repository.tokens.Token;

public interface BuyCryptoInterface {
    void handleBuyFunction(Token token);
    void handleGeneratePaymentRequest(Token token);
}
