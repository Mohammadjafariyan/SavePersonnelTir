package service;

import main.ui.GlobalUi;

public class SharedDataHolderSingeton {

    private static GlobalUi globalUi;


    public static GlobalUi getGlobalUi() {
        return globalUi;
    }

    public static void setGlobalUi(GlobalUi _globalUi) throws Exception {
        if (globalUi != null)
            throw new Exception("این المنت قبلا مقدار دهی شده است");


        globalUi = _globalUi;

    }
}
