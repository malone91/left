package com.melo.gatewaycenter.infrastructure.repository;

import com.melo.gatewaycenter.domain.register.model.vo.ApplicationInterfaceMethodVO;
import com.melo.gatewaycenter.domain.register.model.vo.ApplicationInterfaceVO;
import com.melo.gatewaycenter.domain.register.model.vo.ApplicationSystemVO;
import com.melo.gatewaycenter.domain.register.repository.IRegisterManageRepository;
import com.melo.gatewaycenter.infrastructure.mapper.ApplicationInterfaceMapper;
import com.melo.gatewaycenter.infrastructure.mapper.ApplicationInterfaceMethodMapper;
import com.melo.gatewaycenter.infrastructure.mapper.ApplicationSystemMapper;
import com.melo.gatewaycenter.infrastructure.po.ApplicationInterface;
import com.melo.gatewaycenter.infrastructure.po.ApplicationInterfaceMethod;
import com.melo.gatewaycenter.infrastructure.po.ApplicationSystem;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class RegisterManageRepository implements IRegisterManageRepository {

    @Resource
    private ApplicationSystemMapper systemMapper;
    @Resource
    private ApplicationInterfaceMapper interfaceMapper;
    @Resource
    private ApplicationInterfaceMethodMapper methodMapper;

    @Override
    public void registerApplication(ApplicationSystemVO vo) {
        ApplicationSystem system = new ApplicationSystem();
        system.setSystemId(vo.getSystemId());
        system.setSystemName(vo.getSystemName());
        system.setSystemType(vo.getSystemType());
        system.setSystemRegistry(vo.getSystemRegistry());
        systemMapper.insert(system);
    }

    @Override
    public void registerApplicationInterface(ApplicationInterfaceVO vo) {
        ApplicationInterface anInterface = new ApplicationInterface();
        anInterface.setSystemId(vo.getSystemId());
        anInterface.setInterfaceId(vo.getInterfaceId());
        anInterface.setInterfaceName(vo.getInterfaceName());
        anInterface.setInterfaceVersion(vo.getInterfaceVersion());
        interfaceMapper.insert(anInterface);
    }

    @Override
    public void registerApplicationInterfaceMethod(ApplicationInterfaceMethodVO vo) {
        ApplicationInterfaceMethod method = new ApplicationInterfaceMethod();
        method.setSystemId(vo.getSystemId());
        method.setInterfaceId(vo.getInterfaceId());
        method.setMethodId(vo.getMethodId());
        method.setMethodName(vo.getMethodName());
        method.setParameterType(vo.getParameterType());
        method.setUri(vo.getUri());
        method.setHttpCommandType(vo.getHttpCommandType());
        method.setAuth(vo.getAuth());
        methodMapper.insert(method);
    }
}
