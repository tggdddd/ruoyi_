package cn.iocoder.yudao.framework.pay.core.client.impl.alipay;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.Method;
import cn.iocoder.yudao.framework.pay.core.client.PayCommonResult;
import cn.iocoder.yudao.framework.pay.core.client.dto.order.PayOrderUnifiedReqDTO;
import cn.iocoder.yudao.framework.pay.core.client.dto.order.PayOrderUnifiedRespDTO;
import cn.iocoder.yudao.framework.pay.core.enums.PayChannelEnum;
import cn.iocoder.yudao.framework.pay.core.enums.PayDisplayModeEnum;
import com.alipay.api.AlipayApiException;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeWapPayResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * 支付宝【手机 网站】的 PayClient 实现类
 *
 * 文档：https://opendocs.alipay.com/apis/api_1/alipay.trade.wap.pay
 *
 * @author 芋道源码
 */
@Slf4j
public class AlipayWapPayClient extends AbstractAlipayClient {

    public AlipayWapPayClient(Long channelId, AlipayPayClientConfig config) {
        super(channelId, PayChannelEnum.ALIPAY_WAP.getCode(), config,
                new AlipayPayCodeMapping());
    }

    @Override
    public PayCommonResult<PayOrderUnifiedRespDTO> doUnifiedOrder(PayOrderUnifiedReqDTO reqDTO) {
        // 1.1 构建 AlipayTradeWapPayModel 请求
        AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
        // ① 通用的参数
        model.setOutTradeNo(reqDTO.getMerchantOrderId());
        model.setSubject(reqDTO.getSubject());
        model.setBody(reqDTO.getBody());
        model.setTotalAmount(formatAmount(reqDTO.getAmount()));
        model.setProductCode("QUICK_WAP_PAY"); // 销售产品码. 目前 Wap 支付场景下仅支持 QUICK_WAP_PAY
        // ② 个性化的参数【无】
        // ③ 支付宝 Wap 支付只有一种展示，考虑到前端可能希望二维码扫描后，手机打开
        String displayMode = ObjectUtil.defaultIfNull(reqDTO.getDisplayMode(),
                PayDisplayModeEnum.URL.getMode());

        // 1.2 构建 AlipayTradeWapPayRequest 请求
        AlipayTradeWapPayRequest request = new AlipayTradeWapPayRequest();
        request.setBizModel(model);
        request.setNotifyUrl(reqDTO.getNotifyUrl());
        request.setReturnUrl(reqDTO.getReturnUrl()); // TODO 芋艿，待搞
        model.setQuitUrl(reqDTO.getReturnUrl()); // TODO 芋艿，待搞

        // 2.1 执行请求
        AlipayTradeWapPayResponse response;
        try {
            response = client.pageExecute(request, Method.GET.name());
        } catch (AlipayApiException e) {
            return PayCommonResult.build(e.getErrCode(), e.getErrMsg(), null, codeMapping);
        }
        System.out.println(response.getBody());

        // 2.2 处理结果
        PayOrderUnifiedRespDTO respDTO = new PayOrderUnifiedRespDTO()
                .setDisplayMode(displayMode).setDisplayContent(response.getBody());
        return PayCommonResult.build(StrUtil.blankToDefault(response.getCode(),"10000"),
                response.getMsg(), respDTO, codeMapping);
    }

}
