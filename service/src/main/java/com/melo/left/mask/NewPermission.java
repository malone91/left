package com.melo.left.mask;

public class NewPermission {

    public static final int ALLOW_SELECT = 1 << 0; // 0001
    public static final int ALLOW_INSERT = 1 << 1; // 0010
    public static final int ALLOW_UPDATE = 1 << 2; // 0100
    public static final int ALLOW_DELETE = 1 << 3; // 1000

    // 存储目前的权限状态
    private int flag;

    // 重新设置权限
    public void setPermission(int permission) {
        flag = permission;
    }

    // 添加一项或多项权限，比如当前只有select（0001），增加insert（0010）
    // 0001
    // 0010 ｜
    // 0011 = 即可以select也可以insert
    public void enable(int permission) {
        flag |= permission;
    }

    // 删除一项或多项权限，比如当前可以select、insert(0011)，去掉insert()
    // 0010
    // 1101 ~
    // 0011 &
    // 0001 = 即只可以select了
    public void disable(int permission) {
        flag &= ~permission;
    }

    // 是否拥某些权限
    public boolean isAllow(int permission) {
        return (flag & permission) == permission;
    }

    // 是否禁用了某些权限
    public boolean isNotAllow(int permission) {
        return (flag & permission) == 0;
    }

    // 是否仅仅拥有某些权限
    public boolean isOnlyAllow(int permission) {
        return flag == permission;
    }
}
