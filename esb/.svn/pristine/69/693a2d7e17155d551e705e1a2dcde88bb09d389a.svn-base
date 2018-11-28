package cn.edugate.esb.im.api.impl;

import org.springframework.stereotype.Repository;

import cn.edugate.esb.im.api.SendMessageAPI;
import cn.edugate.esb.im.OrgInfo;
import cn.edugate.esb.im.ResponseHandler;
import cn.edugate.esb.im.EasemobAPI;
import cn.edugate.esb.im.TokenUtil;
import io.swagger.client.ApiException;
import io.swagger.client.api.MessagesApi;
import io.swagger.client.model.Msg;

@Repository
public class EasemobSendMessage implements SendMessageAPI {
    private ResponseHandler responseHandler = new ResponseHandler();
    private MessagesApi api = new MessagesApi();
    @Override
    public Object sendMessage(final Object payload) {
        return responseHandler.handle(new EasemobAPI() {
            @Override
            public Object invokeEasemobAPI() throws ApiException {
                return api.orgNameAppNameMessagesPost(OrgInfo.ORG_NAME,OrgInfo.APP_NAME,TokenUtil.getAccessToken(), (Msg) payload);
            }
        });
    }
}
