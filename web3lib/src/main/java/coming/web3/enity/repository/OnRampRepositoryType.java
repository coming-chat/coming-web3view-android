package coming.web3.enity.repository;

import coming.web3.enity.repository.tokens.Token;

public interface OnRampRepositoryType {
    String getUri(String address, Token token);

    OnRampContract getContract(Token token);
}
