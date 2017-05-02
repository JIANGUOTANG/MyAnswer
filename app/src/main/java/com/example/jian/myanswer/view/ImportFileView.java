package com.example.jian.myanswer.view;

import com.example.jian.myanswer.bean.localFileInfo;

/**
 * Created by jian on 2017/4/3.
 * MVP模式中的View
 */

public interface ImportFileView {

    void toImportScene();
    void toChoiceFileSecne();
    void upDateList(localFileInfo localFileInfo);
    void stopAnim();
}
