package com.costar.core.utils.cr;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by cloud on 2019/12/19.
 * https://blog.csdn.net/wwwdc1012/article/details/82780560
 * <p>
 * 适配器模式 可以理解为对switch case的扩展 根据条件找到对应的适配器 对元数据或元对象进行处理
 */
public class AdapterRegistry {

    private List<ChannelPayAccessAdapter> adapters = new LinkedList<ChannelPayAccessAdapter>();

    public AdapterRegistry() {
        this.adapters.add(new AliPay());
    }

    public AdapterRegistry registryMoreChannelPayAccessAdapter(ChannelPayAccessAdapter other) {
        this.adapters.add(other);
        return this;
    }

    // 根据电压找合适的变压器
    public void doChannelPayAccess(ChargeRequest charge) {
        ChannelPayAccessAdapter adapter = null;
        for (ChannelPayAccessAdapter ad : this.adapters) {
            if (ad.support(charge)) {
                adapter = ad;
                adapter.doChannelPay(charge);
                break;
            }
        }
        if (adapter == null) {
            throw new IllegalArgumentException("没有找到合适的变压适配器");
        }
    }

    public static void main(String[] args) {

        AdapterRegistry adapterRegistry = new AdapterRegistry();

        ChargeRequest chargeRequest = new ChargeRequest() {

            @Override
            public String getChannel() {
                return "alipay";
            }

            @Override
            public String getChannelAppId() {
                return "2018092561551342";
            }

            @Override
            public String getChannelPublicKey() {
                return "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAon0IY4Tu9YtEtA2uOEYLdR2dseH64B2X3U0+ZyOHGghvHQC4MEOp+dNwHqT361838AtWKjfQ4Em1FJLlooA8Vxsrv3fTS+O9zMBlLxRagUX/fanOlZ/QmGIGuzcqxC5cqtkVjafzexI20iQYv+sGW/8kteTPg/o42fHA0xloQdgid5j7JO6MfZ6KwlAiXiZ8T16cXtpWTsuExc0tE7gMRP5Zd6LHWoHlhaUb5gF4Kves0hnRp7ebqB0KHbqjVXJ63p5SNB/8i+uLm9H36vVTb2ezVLQop4rCRe+7RZbVqlceiRdpvyux3J+ktnDPzp0e/EQ0qHo104IDYdNN20ey6QIDAQAB";
            }

            @Override
            public String getChannelPrivateKey() {
                return "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCX2p3hQ7caJ0cMP37D/Lb7JhwwUP8ESxRQIl2XAzvLoMX5Vtt8WVzpLjWO4wZ+lwO1ZNSh0DXUMr5iaTKV5/FmXFBmUzME5W9o4fAOqNpggu2ijbgrBxK2DXsoi44a3wETrSDyfXSItXXpObiyAjhDf3EEF7tdukKrCKl6qkJ6YZ8+Qste7D/5U26jVgeRqcU48rnyC3Hc1aUXa8XQk8hTpj+lndFQMWj09HRTVIVmnvfahlelD63Ce85xC40COTS5snZBBJIwvlaYxMIa+BC1chLUpy1uSw+Ety+U0Hukkkw9Mk8T9n686H4H0tb4VJ2tJoRI3oEgkaS5iFeU5hHrAgMBAAECggEAMs3bYFAtZDyb0oczvBJCjcf0Sl3u09rikuTMdweH69qAIaljiTjEkub42DMwKw4PGZMAaJqmR7YqZ/ZWDD0rUFm2NGXFfe/c1DQzAlSPqxZitq2/XfXh1rKv76KxCmBtlc2oZjh+0nYTWtIK7tM7S6nJf9sIvpTgVSRyvy1TTGn01GQ4wGq4PrVJcWcrGLtTwVl1zY8gfjlOgNc+9zr/rFGYY8hBWsim119M1SeVZZKpUrUi+xMMIjA32YDNCVoOvBG50+9arBFyHqIBftx353Oiq8X2RcDXaMP48tljTMOM0LDUt9aS8N6mSQwXfecrXUb1uowsC2sTRXlJL2HsQQKBgQDGVEnyVD14PJGE6umreC/KyiW4QZi6sf0SoZVC/xPriD94nL+/c4yziREn9hEuord2F3YgTazmnDiC1N9UHjt0ISw0Q+1WwvzXTLV74SgQFj7BaPzyyt+GHLOya5q/qrdvUkDl10JGtd7pfG6tOGhaefYA/H85rpFkdL6ismkvGwKBgQDEArBUKmBn1yBj9M1y0kv+cPtMeLKgmCHc/u+pn2NXC+IEaLeG4ubGGA4fRId4tFoIz0tGcnkWb5rorIjNwM+52IZgL4/5d9eb1NmWlz3KipA1PhNtDpzdSeXQEl5RKl5zyZn9pmZtOmo9tGRGBzTkvSUhCrugXyqyE46SzrJFcQKBgAavVqK9ZikEWZ9ST7gA4BOExsq0I1K/pxkWqzJL6L76o7EMD4aDfY2bpCFlsOSShX6MgeXK2HYZc+otBK4QKs7aQ2QrmfdfsSgMpN1dFH2eUlUwlb4VSn15wDDWYavuNCqjah6VJ928SZwzHoPmL9ZE4B7eY+ds0+e/66EORG9bAoGBAJhpvtKzYONd1EHlSRvPimf/UmjUMZHOugYt9g3fck9tXgIvqwWnyeqfKdwGXZDuLFRNXCsbOCzJ+5qRDo9vLhdE1rhUQ4+oMG9rwwXl9JZZyGieLFzQ0AkCPUDeB5j1aRx/1s31Zg/pLFNuwcnyMJ7stMJMpji9x2VQ77wYHWcBAoGBAJc8ocsL08f/pSNxZVXI8rXSPd+zB4PoyD2OwxQLtWBu45lH3RSm2SIpw+UsdgLlMheGpxSGXlj9MROddZ6u/opymKelZrCirgQtVM6UQKx4TC12XHPU8hb93r4XVfIs6xP6Pu6A87IZ9M35l9FKff9ijR00gOZatiRmha7T6Oxq";
            }

            @Override
            public String getMerchantNumber() {
                return null;
            }

            @Override
            public String getOutTradeNo() {
                return "789123456";
            }

            @Override
            public String getSubject() {
                return "测试充值";
            }

            @Override
            public String getBody() {
                return null;
            }

            @Override
            public Double getAmount() {
                return 1.5;
            }

            @Override
            public String getBarCode() {
                return null;
            }
        };

        adapterRegistry.doChannelPayAccess(chargeRequest);
    }



}
