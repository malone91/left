package com.melo.left.tree.ancestor;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.util.Assert;

import java.util.*;
import java.util.stream.Collectors;

public class DepartmentTreeHelper {

    public static ImDepartmentVo transfrTreeFromList(List<ImDepartmentVo> list) {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }

        ImDepartmentVo ancestor = null;
        //剔除顶层节点
        List<ImDepartmentVo> notNullParentCodeList = new ArrayList<>();
        for (ImDepartmentVo ele : list) {
            if (ele.getParentOrgNo() == null) {
                ancestor = ele;
                continue;
            }
            notNullParentCodeList.add(ele);
        }
        Assert.notNull(ancestor, "最顶层节点不能为空");
        Map<String, List<ImDepartmentVo>> parentMap = notNullParentCodeList.stream().collect(Collectors.groupingBy(ImDepartmentVo::getParentOrgNo));
        //按 自身编码 分组，orgNo不会重复，所以30行只get(0)
        Map<String, List<ImDepartmentVo>> allMap = list.stream().collect(Collectors.groupingBy(ImDepartmentVo::getOrgNo));

        Iterator<Map.Entry<String, List<ImDepartmentVo>>> parentIterator = parentMap.entrySet().iterator();
        //遍历有子节点的父节点，分别设置子节点
        while (parentIterator.hasNext()) {
            Map.Entry<String, List<ImDepartmentVo>> pNext = parentIterator.next();
            //有子节点集合的部门编码
            String key = pNext.getKey();
            //给每个组织节点设置子节点集合，部门的编码不会重复，所以get0
            allMap.get(key).get(0).setChilds(pNext.getValue());
        }
        List<ImDepartmentVo> level2 = new ArrayList<>();
        for (ImDepartmentVo ele : list) {
            if (Objects.equals(ancestor.getOrgNo(), ele.getParentOrgNo())) {
                level2.add(ele);
            }
        }
        ancestor.setChilds(level2);

        return ancestor;
    }

    public static void main(String[] args) {
        String a = "a";
        String b = "b";
        String c = "c";
        String d = "d";
        String e = "e";
        String f = "f";
        String g = "g";
        String h = "h";
        ImDepartmentVo a_n = new ImDepartmentVo();
        ImDepartmentVo b_n = new ImDepartmentVo();
        ImDepartmentVo c_n = new ImDepartmentVo();
        ImDepartmentVo d_n = new ImDepartmentVo();
        ImDepartmentVo e_n = new ImDepartmentVo();
        ImDepartmentVo f_n = new ImDepartmentVo();
        ImDepartmentVo g_n = new ImDepartmentVo();
        ImDepartmentVo h_n = new ImDepartmentVo();
        //设置编码
        a_n.setOrgNo(a);
        b_n.setOrgNo(b);
        c_n.setOrgNo(c);
        d_n.setOrgNo(d);
        e_n.setOrgNo(e);
        f_n.setOrgNo(f);
        g_n.setOrgNo(g);
        h_n.setOrgNo(h);

        //设置父编码
        b_n.setParentOrgNo(a);
        c_n.setParentOrgNo(a);
        d_n.setParentOrgNo(a);
        e_n.setParentOrgNo(b);
        f_n.setParentOrgNo(b);
        g_n.setParentOrgNo(d);
        h_n.setParentOrgNo(d);

        List<ImDepartmentVo> list = new ArrayList<>();
        list.add(a_n);
        list.add(b_n);
        list.add(c_n);
        list.add(d_n);
        list.add(e_n);
        list.add(f_n);
        list.add(g_n);
        list.add(h_n);

        System.out.println("最顶层节点是" + transfrTreeFromList(list).getOrgNo());
    }
}