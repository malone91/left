package com.melo.left.dubbo.server;

import com.melo.api.QueryService;
import com.melo.api.model.ProgramLanguage;
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
