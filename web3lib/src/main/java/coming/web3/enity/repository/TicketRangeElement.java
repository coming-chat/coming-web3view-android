package coming.web3.enity.repository;

import java.math.BigInteger;
import java.util.Collections;
import java.util.List;
import coming.web3.enity.repository.tokens.Token;
import coming.web3.service.AssetDefinitionService;
import coming.web3.token.entity.TokenScriptResult;

public class TicketRangeElement
{
    public final BigInteger id;
    public final long time;

    public TicketRangeElement(AssetDefinitionService assetService, Token token, BigInteger v)
    {
        TokenScriptResult.Attribute timeAttr = assetService.getAttribute(token, v, "time");
        id = v;

        if (timeAttr != null)
        {
            time = timeAttr.value.longValue();
        }
        else
        {
            time = 0;
        }
    }

    public static void sortElements(List<TicketRangeElement> elementList)
    {
        Collections.sort(elementList, (e1, e2) -> {
            long w1 = e1.time;
            long w2 = e2.time;
            if (e1.time == 0 && e2.time == 0)
            {
                w1 = e1.id.longValue();
                w2 = e2.id.longValue();
            }
            return Long.compare(w1, w2);
        });
    }
}
