package com.jike.mobile.sna.model.view;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.ser.std.SerializerBase;

import com.jike.mobile.sna.model.Info;

public class InfoSerializer extends SerializerBase<Info>{

	protected InfoSerializer() {
		super(Info.class);
	}

	@Override
	public void serialize(Info value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonGenerationException {
		jgen.writeStartObject();
		jgen.writeStringField("version", value.getVersion());
		jgen.writeStringField("versionCode", value.getVersionCode());
		jgen.writeStringField("downloadUrl", value.getDownloadUrl());
		jgen.writeStringField("versionDesc", value.getVersionDesc());
		jgen.writeEndObject();
	}
}
