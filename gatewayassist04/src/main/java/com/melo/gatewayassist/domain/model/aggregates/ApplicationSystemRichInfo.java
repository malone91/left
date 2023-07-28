package com.melo.gatewayassist.domain.model.aggregates;

import com.melo.gatewayassist.domain.model.vo.ApplicationSystemVO;

import java.util.List;

public class ApplicationSystemRichInfo {

    private String gatewayId;
    private List<ApplicationSystemVO> list;

    public ApplicationSystemRichInfo(String gatewayId, List<ApplicationSystemVO> list) {
        this.gatewayId = gatewayId;
        this.list = list;
    }

    public String getGatewayId() {
        return gatewayId;
    }

    public void setGatewayId(String gatewayId) {
        this.gatewayId = gatewayId;
    }

    public List<ApplicationSystemVO> getList() {
        return list;
    }

    public void setList(List<ApplicationSystemVO> list) {
        this.list = list;
    }
}
