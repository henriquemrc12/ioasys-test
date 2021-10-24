package com.ioasys.api.users._enums;

public enum UserType {
    ADMIN,
    COMMON;

    public static boolean contains(String type){
        for(UserType u : UserType.values()){
            if(u.toString().equalsIgnoreCase(type)) return true;
        }
        return false;
    }
}
