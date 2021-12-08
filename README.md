# Web3View
Add internet permission to AndroidManifest.xml
```xml
<uses-permission android:name="android.permission.INTERNET" />
```
Define a view in your layout file:
```xml
<?xml version="1.0" encoding="utf-8"?>
...
    <coming.web3.Web3View
        android:id="@+id/web3view"
        android:layout_below="@+id/go"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        />
...
```
And add following code to your activity or fragment for setup:
Java
```java
web3.setChainId(1);
web3.setRpcUrl("https://mainnet.infura.io/v3/30c277db0eaa4085ac32ced784bc9af9");
web3.setWalletAddress(new Address("0xaa3cc54d7f10fa3a1737e4997ba27c34f330ce16"));
```
Add listeners:

```java
web3.setOnSignMessageListener(message -> {
    Toast.makeText(this, "Message: " + message.value, Toast.LENGTH_LONG).show();
    web3.onSignCancel(message);
});
web3.setOnSignPersonalMessageListener(message -> {
    Toast.makeText(this, "Personal message: " + message.value, Toast.LENGTH_LONG).show();
    web3.onSignCancel(message);
});
web3.setOnSignTransactionListener(transaction -> {
    Toast.makeText(this, "Transaction: " + transaction.value, Toast.LENGTH_LONG).show();
    web3.onSignCancel(transaction);
});
```
Send results:

```java
web3.onSignCancel(Message|Tranasction)
web3.onSignMessageSuccessful(message, "0x....");
web3.onSignPersonalMessageSuccessful(message, "0x...");
web3.onSignTransactionSuccessful(transaction, "0x...");
web3.onSignError(Message|Transaction, "some_error");
```
