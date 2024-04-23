package com.code.studio.allvideodownui.model;

import androidx.annotation.Keep;
import java.io.Serializable;

@Keep
public class WaModel implements Serializable {
    public String fileName;
    public String fileUri;

    public WaModel(String str, String str2) {
        this.fileName = str;
        this.fileUri = str2;
    }

    public String getFileName() {
        return this.fileName;
    }

    public String getFileUri() {
        return this.fileUri;
    }
}
