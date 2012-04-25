package com.jike.mobile.sna.common.thrift;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Connection {
	
	Logger log = LoggerFactory.getLogger(Connection.class);
	
	private TTransport transport;
	private KvService.Client client;
	
	private String addr;
	private int port;
	private String dbName;
	
	public Connection(String addr, int port, String dbName) {
		this.addr = addr;
		this.port = port;
		this.dbName = dbName;
	}
	
	public void open() throws TTransportException {
		TTransport tSocket = new TSocket(addr, port);
		transport = new TFramedTransport(tSocket);
		transport.open();
		
		TProtocol tProtocol = new TBinaryProtocol(transport);
		client = new KvService.Client(tProtocol);
	}
	
	public void close() {
		transport.close();
	}
	
	public int put(String id, String value, int expire_time) throws TException {
		if(client == null) open();
//		log.info("id: " + id);
//		log.info("value: " + value);
//		log.info("expire_time: " + expire_time);
		return (client.put(dbName, id, value, expire_time)).getValue();
	}
	
	public String read(String id) throws TException {
		return client.read(dbName, id);
	}
}
