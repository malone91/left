package com.melo.gatewaycenter.application;

import com.melo.gatewaycenter.domain.register.model.vo.ApplicationInterfaceMethodVO;
import com.melo.gatewaycenter.domain.register.model.vo.ApplicationInterfaceVO;
import com.melo.gatewaycenter.domain.register.model.vo.ApplicationSystemVO;

public interface IRegisterManageService {

    void registerApplication(ApplicationSystemVO vo);

    void registerApplicationInterface(ApplicationInterfaceVO vo);

    void registerApplicationInterfaceMethod(ApplicationInterfaceMethodVO vo);
}
