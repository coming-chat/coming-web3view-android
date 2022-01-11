package coming.web3.enity.repository;

import coming.web3.enity.repository.cryptokeys.KeyEncodingType;
import coming.web3.service.KeyService;

public interface ImportWalletCallback
{
    void walletValidated(String address, KeyEncodingType type, KeyService.AuthenticationLevel level);
}
