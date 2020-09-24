package com.melo.left.tree.ancestor;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ImDepartmentVo {
    //组织机构编号
    private String orgNo;
    //组织名称
    private String orgName;
    //上级组织编码
    private String parentOrgNo;
    //安全责任人
    private String safetyPerson;
    //注释
    private String note;

    //子节点
    private List<ImDepartmentVo> childs;
}