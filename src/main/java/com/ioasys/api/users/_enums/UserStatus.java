package com.ioasys.api.users._enums;

public enum UserStatus {
    ABLE,
    DISABLE;

    public static boolean contains(String type){
        for(UserStatus u : UserStatus.values()){
            if(u.toString().equalsIgnoreCase(type)) return true;
        }
        return false;
    }
}
