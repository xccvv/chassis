package com.kixeye.chassis.transport.serde;

/*
 * #%L
 * Java Transport API
 * %%
 * Copyright (C) 2014 KIXEYE, Inc
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import com.kixeye.chassis.transport.serde.converter.BsonJacksonMessageSerDe;
import com.kixeye.chassis.transport.serde.converter.JsonJacksonMessageSerDe;
import com.kixeye.chassis.transport.serde.converter.YamlJacksonMessageSerDe;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kixeye.chassis.transport.serde.converter.ProtobufMessageSerDe;
import com.kixeye.chassis.transport.serde.converter.XmlMessageSerDe;

/**
 * Configuration for the SerDe.
 * 
 * @author ebahtijaragic
 */
@Configuration
@ComponentScan(basePackageClasses=SerDeConfiguration.class)
public class SerDeConfiguration {
	@Bean
	public BsonJacksonMessageSerDe bsonMessageSerDe() {
		return new BsonJacksonMessageSerDe();
	}
	
	@Bean
	@Autowired(required=false)
	public JsonJacksonMessageSerDe jsonMessageSerDe(ApplicationContext applicationContext) {
		ObjectMapper jacksonScalaObjectMapper = null;
		
		try {
			jacksonScalaObjectMapper = applicationContext.getBean("jacksonScalaObjectMapper", ObjectMapper.class);
		} catch (NoSuchBeanDefinitionException e) {
			// ignore
		}
		
		if (jacksonScalaObjectMapper == null) {
			return new JsonJacksonMessageSerDe();
		} else {
			return new JsonJacksonMessageSerDe(jacksonScalaObjectMapper);
		}
	}
	
	@Bean
	public ProtobufMessageSerDe protobufMessageSerDe() {
		return new ProtobufMessageSerDe();
	}

	@Bean
	public XmlMessageSerDe xmlMessageSerDe() {
		return new XmlMessageSerDe();
	}

	@Bean
	public YamlJacksonMessageSerDe yamlMessageSerDe() {
		return new YamlJacksonMessageSerDe();
	}
}
