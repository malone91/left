package com.melo.gatewaycenter.domain.register.service;

import com.melo.gatewaycenter.application.IRegisterManageService;
import com.melo.gatewaycenter.domain.register.model.vo.ApplicationInterfaceMethodVO;
import com.melo.gatewaycenter.domain.register.model.vo.ApplicationInterfaceVO;
import com.melo.gatewaycenter.domain.register.model.vo.ApplicationSystemVO;
import com.melo.gatewaycenter.domain.register.repository.IRegisterManageRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RegisterManageServiceImpl implements IRegisterManageService {

    @Resource
    private IRegisterManageRepository registerManageRepository;

    @Override
    public void registerApplication(ApplicationSystemVO vo) {
        registerManageRepository.registerApplication(vo);
    }

    @Override
    public void registerApplicationInterface(ApplicationInterfaceVO vo) {
        registerManageRepository.registerApplicationInterface(vo);
    }

    @Override
    public void registerApplicationInterfaceMethod(ApplicationInterfaceMethodVO vo) {
        registerManageRepository.registerApplicationInterfaceMethod(vo);
    }
}
