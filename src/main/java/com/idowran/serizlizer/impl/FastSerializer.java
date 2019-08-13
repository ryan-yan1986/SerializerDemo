package com.idowran.serizlizer.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import org.nustaq.serialization.FSTConfiguration;

import com.idowran.serizlizer.ObjectSerializer;

public class FastSerializer implements ObjectSerializer {
	
	public static final FSTConfiguration conf = FSTConfiguration.createDefaultConfiguration();
	
	static {
		// 不能注册这个类，会报NoClassDefException
//		conf.registerClass(InvocationHandler.class);
		conf.registerClass(HashMap.class);
		conf.registerClass(ArrayList.class);
	}
	
	public byte[] serialize(Object obj) throws IOException {
		// 线程安全的快速序列化方式
		return conf.asByteArray((Serializable)obj);
	}

	public void serialize(Object obj, OutputStream os) throws IOException {
		conf.getObjectOutput().writeObject(os);
	}

	@SuppressWarnings("unchecked")
	public <T> T deserialize(byte[] bytes, Class<T> clazz) throws IOException, ClassNotFoundException {
		// 线程安全的工厂模式序列化方式
		return (T) conf.getObjectInput(bytes).readObject();
	}

	@SuppressWarnings("unchecked")
	public <T> T deserialize(InputStream is, Class<T> clazz) throws IOException, ClassNotFoundException {
		return (T) this.deserialize(is);
	}

	public Object deserialize(InputStream is) throws IOException, ClassNotFoundException {
		// 线程安全的工厂模式反序列化方式
		return conf.getObjectInput(is).readObject();
	}

	public Object deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
		return conf.getObjectInput(bytes).readObject();
	}

	@SuppressWarnings("unchecked")
	public <T> T deserialize(byte[] bytes, T co) throws IOException, ClassNotFoundException {
		return (T) deserialize(bytes, co.getClass());
	}

}
