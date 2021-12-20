# Coming Web3View

Coming Web3View 是coming的web3浏览器接口协议，支持以太坊生态、polkadot生态的应用。

参考AlphaWallet以及TrustWallet的实现，抽离出核心功能，支持AndroidX

\- AlphaWallet

<https://github.com/AlphaWallet/alpha-wallet-android>

## 接口测试网址
https://danfinlay.github.io/js-eth-personal-sign-examples/

## init.js 和注入js
webview注入js: https://github.com/coming-chat/coming-web3-provider/blob/main/dist/Coming-min.js

init.js: https://github.com/coming-chat/coming-web3view-android/blob/main/lib/src/main/res/raw/init.js

## 什么是dapp

DApp是（Decentralized Application）的缩写，中文直译为去中心化应用，也可以理解为分布式应用。

DApp基于区块链，主要出现在以太坊、Polkadot等分布式平台或网络上，不依赖任何中心服务器，实现去中心化的目的。与传统的APP不同，DApp坚决强调去中心化，必须运行在分布式的操作系统，而不能工作在iOS及Android这些传统平台上。尽管不依靠任何中心节点，但DApp却离不开智能合约。也可以说，依托智能合约的约束，使得DApp无需听命于任何中心化服务器或节点，实现自治。除此之外，DApp还必须做到安全存储，保障隐私。

通俗讲，DAPP之于区块链，就像APP之于IOS和Android。即：DAPP=前端+智能合约。前端可以使用任何语言编写，只要能够充分展示用户界面，调用后端的代码即可。这里面，智能合约就是连接DAPP和数据库的桥梁，相当于传统互联网APP中的API连接器，其结构：

**前端→智能合约→区块链**

**Dapp 又叫 web3应用**

## 什么是Web3 浏览器

**DApp浏览器又叫Web3浏览器。**

DApp浏览器，顾名思义，它的主要功能就是以浏览器的形式直接访问DApp，从而让DApp使用起来更为便捷。它在整个DApp生态中，充当了用户与去中心化应用交互的入口。

DApp浏览器与传统浏览器相比有何不同？它的原理是什么？

与传统浏览器相比，目前的DApp浏览器并不是一个独立的入口，它需要结合或者内嵌在[数字钱包](https://www.zhihu.com/search?q=数字钱包&search_source=Entity&hybrid_search_source=Entity&hybrid_search_extra={"sourceType"%3A"article"%2C"sourceId"%3A48471305})中，才能为用户提供与DApp交互的入口。而传统浏览器无论是在PC时代，还是在移动互联网时代，都是一个独立的流量入口。

![image-20211208140800609](https://tva1.sinaimg.cn/large/008i3skNgy1gx6e838z8jj30u00usdhu.jpg)

## ComingChat 集成web3浏览器

![image-20211208141648408](https://tva1.sinaimg.cn/large/008i3skNgy1gx6eg2x8awj31g40a8dgs.jpg)

Web3View 通过注入 web3对象到加载的前端js文件中，前端应用持有 window.web3 对象注入的一系列接口与

钱包进行交互，比如：

* 注入钱包地址

- 发起交易

- 签名消息

  ....

### web3view jsBridge接口

- 发起交易

```java
     @JavascriptInterface
     public void signTransaction(
        int callbackId,
        String recipient,
        String value,
        String nonce,
        String gasLimit,
        String gasPrice,
        String payload) {
       Web3Transaction transaction = new Web3Transaction(
                     TextUtils.isEmpty(recipient) ? Address.EMPTY : new Address(recipient),
                     null,
                     Hex.hexToBigInteger(value),
                     Hex.hexToBigInteger(gasPrice, BigInteger.ZERO),
                     Hex.hexToBigInteger(gasLimit, BigInteger.ZERO),
                     Hex.hexToLong(nonce, -1),
                     payload,
                     callbackId);
          
         webView.post(() -> onSignTransactionListener.onSignTransaction(transaction, getUrl()));
     }
```

* 对消息进行签名

```java
  @JavascriptInterface
   public void signMessage(int callbackId, String data) {
          webView.post(() -> onSignMessageListener.onSignMessage(new Message(data, getUrl(), callbackId, SignMessageType.SIGN_MESSAGE)));
      }
```

...

### SDK使用方式

添加网络访问权限到 AndroidManifest.xml

```xml
<uses-permission android:name="android.permission.INTERNET" />
```

定义布局文件:

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

在Fragment或者Activity中初始化设置:

Java
```java
web3.setChainId(1);
web3.setRpcUrl("https://mainnet.infura.io/v3/30c277db0eaa4085ac32ced784bc9af9");
web3.setWalletAddress(new Address("0xaa3cc54d7f10fa3a1737e4997ba27c34f330ce16"));
```

添加监听事件:

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

传递结果:

```java
web3.onSignCancel(Message|Tranasction)
web3.onSignMessageSuccessful(message, "0x....");
web3.onSignPersonalMessageSuccessful(message, "0x...");
web3.onSignTransactionSuccessful(transaction, "0x...");
web3.onSignError(Message|Transaction, "some_error");
```
## 前端接入

前端使用 ether.js 进行签名，发起交易。

```js
try {
      library
        .getSigner(account)
        .sendUncheckedTransaction({
          type: 1,
          customData: {
            method: 'comingAuction.bid',
            params: [currentCid, currentPrice],
          },
        })
        .then((signature: any) => {
          window.alert(`Success!\n\n${signature}`)
        })
        .catch((err: Error) => {
          window.alert(`Failure!${err && err.message ? `\n\n${err.message}` : ''}`)
        })
    } catch (error) {
      setIsCheckLoading(false)
      openNotification(
        'failed',
        'purchase',
        <Icon width={16} height={16} iconSrc={IconFailure} />,
        error.toString(),
      )
    }
```
