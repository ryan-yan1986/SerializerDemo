package com.idowran.serizlizer.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;

import com.idowran.serizlizer.ObjectSerializer;

public class JavaSerializer implements ObjectSerializer {

	public byte[] serialize(Object obj) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		try {
			oos.writeObject(obj);
			oos.flush();
			return baos.toByteArray();
		} finally {
			oos.close();
		}
	}

	public void serialize(Object obj, OutputStream os) throws IOException {
		ObjectOutputStream oos = new ObjectOutputStream(os);
		oos.writeObject(obj);
		oos.flush();		
	}
	
	public <T> T deserialize(ByteBuffer buf, Class<T> clazz) throws IOException, ClassNotFoundException{
		return deserialize(buf.array(), clazz);
	}
	
	public <T> T deserialize(byte[] bytes, Class<T> clazz) throws IOException, ClassNotFoundException {
		return deserialize(new ByteArrayInputStream(bytes), clazz);
	}

	@SuppressWarnings("unchecked")
	public <T> T deserialize(InputStream is, Class<T> clazz) throws IOException, ClassNotFoundException {
		return (T) this.deserialize(is);
	}

	public Object deserialize(InputStream is) throws IOException, ClassNotFoundException {
		ObjectInputStream ois = null;
		if (is instanceof ObjectInputStream) {
			ois = (ObjectInputStream) is;
		}else {
			ois = new ObjectInputStream(is);
		}
		try {
			return ois.readObject();
		} finally {
			ois.close();
		}
	}

	public Object deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
		return deserialize(new ByteArrayInputStream(bytes));
	}

	@SuppressWarnings("unchecked")
	public <T> T deserialize(byte[] bytes, T co) throws IOException, ClassNotFoundException {
		return (T) deserialize(bytes, co.getClass());
	}
}
