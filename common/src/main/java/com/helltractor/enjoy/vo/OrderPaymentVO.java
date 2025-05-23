package com.helltractor.enjoy.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderPaymentVO implements Serializable {

    // 随机字符串
    private String nonceStr;

    // 签名
    private String paySign;

    // 时间戳
    private String timeStamp;

    // 签名算法
    private String signType;

    // 统一下单接口返回的 prepay_id 参数值
    private String packageStr;

}
