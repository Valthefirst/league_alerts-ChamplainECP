package com.calerts.computer_alertsbe.utils;


import com.calerts.computer_alertsbe.readerservice.dataaccesslayer.Reader;
import com.calerts.computer_alertsbe.readerservice.presentationlayer.ReaderResponseModel;
import org.springframework.beans.BeanUtils;

public class EntityModelUtil {

    public static ReaderResponseModel toReaderResponseModel(Reader reader) {
        ReaderResponseModel readerResponseModel = new ReaderResponseModel();
        BeanUtils.copyProperties(reader, readerResponseModel);
        return readerResponseModel;
    }
}
