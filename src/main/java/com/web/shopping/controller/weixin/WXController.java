package com.web.shopping.controller.weixin;

import com.web.shopping.beans.WXDecodeBean;
import com.web.shopping.beans.WXUserInfoBean;
import com.web.shopping.constant.WXConstants;
import com.web.shopping.model.Customer;
import com.web.shopping.repository.CustomerRepository;
import com.web.shopping.util.AesCbcUtil;
import com.web.shopping.util.KeyUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

@Controller
@RequestMapping("/weixin")
public class WXController {

    @Autowired
    private CustomerRepository customerRepository;


    @ResponseBody
    @RequestMapping(value = "/decodeUserInfo", method = RequestMethod.GET)
    public WXUserInfoBean decodeUserInfo(String encryptedData, String iv, String code) {

        WXUserInfoBean userInfo = null;
        //登录凭证不能为空
        if (code == null || code.length() == 0) {
            return userInfo;
        }

        //小程序唯一标识   (在微信小程序管理后台获取)
        String wxspAppid = WXConstants.WX_LOGIN_APPID;
        //小程序的 app secret (在微信小程序管理后台获取)
        String wxspSecret = WXConstants.WX_LOGIN_SECRET;
        //授权（必填）
        String grant_type = WXConstants.WX_LOGIN_GRANT_TYPE;
        // 请求参数
        String params = "?appid=" + wxspAppid + "&secret=" + wxspSecret + "&js_code=" + code + "&grant_type="
                + grant_type;

        // 发送请求
        RestTemplate restTemplate = new RestTemplate();
        String decodeBeanStr = restTemplate.getForObject(WXConstants.WX_LOGIN_URL + params, String.class);

        // JSON字符串转对象
        // oHI2g4gWAqnZWJp0naOplydaK8u8
        JSONObject jsonObject = JSONObject.fromObject(decodeBeanStr);
        WXDecodeBean decodeBean = (WXDecodeBean) JSONObject.toBean(jsonObject,WXDecodeBean.class);

        System.out.println("微信返回数据是：" + decodeBeanStr);

        //对encryptedData加密数据进行AES解密
        try {
            String result = AesCbcUtil.decrypt(encryptedData, decodeBean.getSession_key(), iv, "UTF-8");
            System.out.println("解密后的数据:"+result);
            if (null != result && result.length() > 0) {
                JSONObject userInfoJSON = JSONObject.fromObject(result);
                userInfo = (WXUserInfoBean) JSONObject.toBean(userInfoJSON,WXUserInfoBean.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 解密之后 根据openid是否查得到用户 如果没有，那么创建用户
        Customer user  = customerRepository.findByOpenid(userInfo.getOpenId());
        if(user == null){
            System.out.println("根据openid没有查到用户,正在创建用户");
            user = new Customer();
            user.setId(KeyUtil.genUniqueKey());
            user.setCreatetime(new Date());
//            user.setNickname(userInfo.getNickName());
            user.setNickname(userInfo.getOpenId());
            user.setRealname("");
            user.setPassword("123456");
            user.setStatus(1);
            user.setOpenid(userInfo.getOpenId());
            customerRepository.save(user);
        }

        return userInfo;

    }
}
