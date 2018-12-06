package com.example.wechat.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.RandomUtil;
import com.example.wechat.api.AuthorizeApi;
import com.example.wechat.api.WechatUserApi;
import com.example.wechat.common.bean.ArticleItem;
import com.example.wechat.common.bean.AuthorizeAccessToken;
import com.example.wechat.common.bean.UserInfo;
import com.example.wechat.common.bean.WechatContant;
import com.example.wechat.common.utils.WechatUtil;
import com.example.wechat.common.vo.UserInfoVo;
import com.example.wechat.service.WechatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author zhangjf
 * @Date 2018/11/28  14:47
 */
@Service
public class WechatServiceImpl implements WechatService {

    @Autowired
    private WechatUserApi wechatUserApi;
    @Autowired
    private AuthorizeApi authorizeApi;


    public String processRequest(HttpServletRequest request) {
        // xml格式的消息数据
        String respXml = null;
        // 默认返回的文本消息内容
        String respContent;
        try {
            // 调用parseXml方法解析请求消息
            Map<String,String> requestMap = WechatUtil.parseXml(request);
            // 消息类型
            String msgType = (String) requestMap.get(WechatContant.MsgType);
            String mes = null;
            // 文本消息
            if (msgType.equals(WechatContant.REQ_MESSAGE_TYPE_TEXT)) {
                mes =requestMap.get(WechatContant.Content).toString();
                if(mes!=null&&mes.length()<2){
                    List<ArticleItem> items = new ArrayList<>();
                    ArticleItem item = new ArticleItem();
                    /*item.setTitle("照片墙");
                    item.setDescription("阿狸照片墙");
                    item.setPicUrl("http://changhaiwx.pagekite.me/photo-wall/a/iali11.jpg");
                    item.setUrl("http://changhaiwx.pagekite.me/page/photowall");
                    items.add(item);

                    item = new ArticleItem();
                    item.setTitle("哈哈");
                    item.setDescription("一张照片");
                    item.setPicUrl("http://changhaiwx.pagekite.me/images/me.jpg");
                    item.setUrl("http://changhaiwx.pagekite.me/page/index");
                    items.add(item);*/
/*
                    item = new ArticleItem();
                    item.setTitle("小游戏2048");
                    item.setDescription("小游戏2048");
                    item.setPicUrl("http://changhaiwx.pagekite.me/images/2048.jpg");
                    item.setUrl("http://changhaiwx.pagekite.me/page/game2048");
                    items.add(item);*/

                    item = new ArticleItem();
                    item.setTitle("百度");
                    item.setDescription("百度一下");
                    item.setPicUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1505100912368&di=69c2ba796aa2afd9a4608e213bf695fb&imgtype=0&src=http%3A%2F%2Ftx.haiqq.com%2Fuploads%2Fallimg%2F170510%2F0634355517-9.jpg");
                    item.setUrl("http://www.baidu.com");
                    items.add(item);

                    respXml = WechatUtil.sendArticleMsg(requestMap, items);
                }else if("我的信息".equals(mes)){
                    UserInfo userInfo = wechatUserApi.getWechatUserInfo(requestMap.get(WechatContant.FromUserName));
                    System.out.println(userInfo.toString());
                    String nickname = userInfo.getNickname();
                    String city = userInfo.getCity();
                    String province = userInfo.getProvince();
                    String country = userInfo.getCountry();
                    String headimgurl = userInfo.getHeadimgurl();
                    List<ArticleItem> items = new ArrayList<>();
                    ArticleItem item = new ArticleItem();
                    item.setTitle("你的信息");
                    item.setDescription("昵称:"+nickname+"  地址:"+country+" "+province+" "+city);
                    item.setPicUrl(headimgurl);
                    item.setUrl("http://www.baidu.com");
                    items.add(item);

                    respXml = WechatUtil.sendArticleMsg(requestMap, items);
                }
            }
            // 图片消息
            else if (msgType.equalsIgnoreCase(WechatContant.REQ_MESSAGE_TYPE_IMAGE)) {
                respContent = "您发送的是图片消息！";
                respXml = WechatUtil.sendTextMsg(requestMap, respContent);
            }
            // 语音消息
            else if (msgType.equalsIgnoreCase(WechatContant.REQ_MESSAGE_TYPE_VOICE)) {
                //respContent = "您发送的是语音消息！";
                //respXml = WechatUtil.sendTextMsg(requestMap, respContent);

                List<ArticleItem> items = new ArrayList<>();
                ArticleItem item = new ArticleItem();
                item.setTitle("照片墙");
                item.setDescription("阿狸照片墙");
                item.setPicUrl("http://changhaiwx.pagekite.me/photo-wall/a/iali11.jpg");
                item.setUrl("http://changhaiwx.pagekite.me/page/photowall");
                items.add(item);

                item = new ArticleItem();
                item.setTitle("哈哈");
                item.setDescription("一张照片");
                item.setPicUrl("http://changhaiwx.pagekite.me/images/me.jpg");
                item.setUrl("http://changhaiwx.pagekite.me/page/index");
                items.add(item);

                item = new ArticleItem();
                item.setTitle("小游戏2048");
                item.setDescription("小游戏2048");
                item.setPicUrl("http://changhaiwx.pagekite.me/images/2048.jpg");
                item.setUrl("http://changhaiwx.pagekite.me/page/game2048");
                items.add(item);

                item = new ArticleItem();
                item.setTitle("百度");
                item.setDescription("百度一下");
                item.setPicUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1505100912368&di=69c2ba796aa2afd9a4608e213bf695fb&imgtype=0&src=http%3A%2F%2Ftx.haiqq.com%2Fuploads%2Fallimg%2F170510%2F0634355517-9.jpg");
                item.setUrl("http://www.baidu.com");
                items.add(item);

                respXml = WechatUtil.sendArticleMsg(requestMap, items);
            }
            // 视频消息
            else if (msgType.equalsIgnoreCase(WechatContant.REQ_MESSAGE_TYPE_VIDEO)) {
                respContent = "您发送的是视频消息！";
                respXml = WechatUtil.sendTextMsg(requestMap, respContent);
            }
            // 地理位置消息
            else if (msgType.equalsIgnoreCase(WechatContant.REQ_MESSAGE_TYPE_LOCATION)) {
                respContent = "您发送的是地理位置消息！";
                respXml = WechatUtil.sendTextMsg(requestMap, respContent);
            }
            // 链接消息
            else if (msgType.equalsIgnoreCase(WechatContant.REQ_MESSAGE_TYPE_LINK)) {
                respContent = "您发送的是链接消息！";
                respXml = WechatUtil.sendTextMsg(requestMap, respContent);
            }
            // 事件推送
            else if (msgType.equals(WechatContant.REQ_MESSAGE_TYPE_EVENT)) {
                // 事件类型
                String eventType = (String) requestMap.get(WechatContant.Event);
                // 关注
                if (eventType.equalsIgnoreCase(WechatContant.EVENT_TYPE_SUBSCRIBE)) {
                    respContent = "谢谢您的关注！";
                    //respXml = WechatUtil.sendTextMsg(requestMap, respContent);
                    respXml = "<xml><Articles><item><Description><![CDATA[阿狸照片墙]]></Description><Title><![CDATA[照片墙]]></Title><PicUrl><![CDATA[http://changhaiwx.pagekite.me/photo-wall/a/iali11.jpg]]></PicUrl><Url><![CDATA[http://changhaiwx.pagekite.me/page/photowall]]></Url></item><item><Description><![CDATA[一张照片]]></Description><Title><![CDATA[哈哈]]></Title><PicUrl><![CDATA[http://changhaiwx.pagekite.me/images/me.jpg]]></PicUrl><Url><![CDATA[http://changhaiwx.pagekite.me/page/index]]></Url></item><item><Description><![CDATA[小游戏2048]]></Description><Title><![CDATA[小游戏2048]]></Title><PicUrl><![CDATA[http://changhaiwx.pagekite.me/images/2048.jpg]]></PicUrl><Url><![CDATA[http://changhaiwx.pagekite.me/page/game2048]]></Url></item><item><Description><![CDATA[百度一下]]></Description><Title><![CDATA[百度]]></Title><PicUrl><![CDATA[https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1505100912368&di=69c2ba796aa2afd9a4608e213bf695fb&imgtype=0&src=http%3A%2F%2Ftx.haiqq.com%2Fuploads%2Fallimg%2F170510%2F0634355517-9.jpg]]></PicUrl><Url><![CDATA[http://www.baidu.com]]></Url></item></Articles><CreateTime><![CDATA[1543475323815]]></CreateTime><ArticleCount><![CDATA[4]]></ArticleCount><ToUserName><![CDATA[ogk2v0bl7YGxV9zSf4eYFquPIcs0]]></ToUserName><FromUserName><![CDATA[gh_8bd0f24e58aa]]></FromUserName><MsgType><![CDATA[news]]></MsgType></xml>";
                }
                // 取消关注
                else if (eventType.equalsIgnoreCase(WechatContant.EVENT_TYPE_UNSUBSCRIBE)) {
                    // TODO 取消订阅后用户不会再收到公众账号发送的消息，因此不需要回复
                }
                // 扫描带参数二维码
                else if (eventType.equalsIgnoreCase(WechatContant.EVENT_TYPE_SCAN)) {
                    // TODO 处理扫描带参数二维码事件
                }
                // 上报地理位置
                else if (eventType.equalsIgnoreCase(WechatContant.EVENT_TYPE_LOCATION)) {
                    // TODO 处理上报地理位置事件
                }
                // 自定义菜单
                else if (eventType.equalsIgnoreCase(WechatContant.EVENT_TYPE_CLICK)) {
                    // TODO 处理菜单点击事件
                }
            }
            mes = mes == null ? "不知道你在干嘛" : mes;
            if(respXml == null)
                respXml = WechatUtil.sendTextMsg(requestMap, mes);
            System.out.println(respXml);
            return respXml;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";

    }

    @Override
    public UserInfoVo getOpenid(String code) {
        AuthorizeAccessToken accessToken = authorizeApi.getAccessToken(code);
        UserInfo userInfo = authorizeApi.getUserInfo(accessToken.getAccess_token(), accessToken.getOpenid());
        UserInfoVo userInfoVo = new UserInfoVo();
        userInfoVo.setOpenid(accessToken.getOpenid());
        userInfoVo.setNickname(userInfo.getNickname());

        //获取用户身份
        int random = RandomUtil.randomInt(1, 3);
        userInfoVo.setIdentity(random);
        System.out.println(userInfoVo);
        return userInfoVo;
    }
}
