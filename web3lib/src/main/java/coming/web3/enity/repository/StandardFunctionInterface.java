package coming.web3.enity.repository;


import java.math.BigInteger;
import java.util.List;

import coming.web3.token.entity.TSAction;

public interface StandardFunctionInterface
{
    default void selectRedeemTokens(List<BigInteger> selection) { }

    default void sellTicketRouter(List<BigInteger> selection) { }

    default void showTransferToken(List<BigInteger> selection) { }

    default void showSend() { }

    default void showReceive() { }

    default void updateAmount() { }

    default void displayTokenSelectionError(TSAction action) { }

    default void handleClick(String action, int actionId) { }

    default void handleTokenScriptFunction(String function, List<BigInteger> selection) { }

    default void showWaitSpinner(boolean show) { }

    default void handleFunctionDenied(String denialMessage) { }
}
