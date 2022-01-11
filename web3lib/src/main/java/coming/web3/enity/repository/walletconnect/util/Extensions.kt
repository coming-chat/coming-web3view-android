package coming.web3.enity.repository.walletconnect.entity

import coming.web3.enity.webview.Numeric


fun ByteArray.toHexString(): String {
    return Numeric.toHexString(this, 0, this.size, false)
}

fun String.toByteArray(): ByteArray {
    return Numeric.hexStringToByteArray(this)
}