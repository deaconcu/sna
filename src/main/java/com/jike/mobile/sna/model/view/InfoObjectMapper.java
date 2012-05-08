package com.jike.mobile.sna.model.view;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ser.CustomSerializerFactory;

import com.jike.mobile.sna.model.Info;

public class InfoObjectMapper extends ObjectMapper{
	public InfoObjectMapper() {
        CustomSerializerFactory sf = new CustomSerializerFactory();
        sf.addSpecificMapping(Info.class, new InfoSerializer());
        this.setSerializerFactory(sf);
    }
}
