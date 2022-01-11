package coming.web3.enity.repository;

import coming.web3.enity.repository.ethereum.EthereumNetworkInfo;

public interface OnNetworkChangeListener {
	void onNetworkChanged(EthereumNetworkInfo networkInfo);
}
