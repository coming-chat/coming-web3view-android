package coming.web3.token.util;

import java.util.TimeZone;

import coming.web3.token.entity.NonFungibleToken;

/**
 * Created by James on 11/02/2019.
 * Stormbird in Singapore
 */
class GeneralDateTime extends DateTime
{
    GeneralDateTime(NonFungibleToken.Attribute timeAttr)
    {
        this.timezone = TimeZone.getTimeZone("GMT");
        time = timeAttr.value.longValue()*1000;
    }

    GeneralDateTime(String time)
    {
        this.timezone = TimeZone.getTimeZone("GMT");
        Long timeConv = Long.valueOf(time);
        this.time = timeConv*1000;
    }
}
