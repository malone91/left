package com.melo.left.dubbo.server;

import com.melo.left.dubbo.api.QueryService;
import com.melo.left.model.ProgramLanguage;
import org.springframework.stereotype.Service;

@Service
public class QueryServiceImpl implements QueryService {

    @Override
    public ProgramLanguage select() {
        ProgramLanguage language = new ProgramLanguage();
        language.setDesc("Java");
        language.setType("dynamic");
        language.setId(23);
        return language;
    }
}
