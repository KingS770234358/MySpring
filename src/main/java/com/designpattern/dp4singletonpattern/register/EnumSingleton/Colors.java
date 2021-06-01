package com.designpattern.dp4singletonpattern.register.EnumSingleton;

public enum Colors {
    // 枚举使用","分割
    RED(1),GREEN(2),BLUE(3){
        public boolean isRest(){
            return true;
        }
    };
    private int value;
    private Colors(int i) {
        this.value = i;
    }
    public int getValue(){
        return this.value;
    }
    public boolean isRest(){
        return false;
    }

    public static Colors getColorById(int id){
        for (Colors value : Colors.values()) {
            if(value.getValue() == id){
                return value;
            }
        }
        return null;
    }
}
