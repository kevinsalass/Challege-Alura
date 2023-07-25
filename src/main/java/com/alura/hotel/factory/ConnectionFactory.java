package com.alura.hotel.factory;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class ConnectionFactory {
	
	private DataSource datasource;
	
	public ConnectionFactory(){ 
		 var combopooledDataSource = new ComboPooledDataSource();
	     combopooledDataSource.setJdbcUrl("jdbc:mysql://localhost/registro_de_hotel?useTimeZone=true&serverTimeZone=UTC");
	     combopooledDataSource.setUser("root");
	     combopooledDataSource.setPassword("K#v$ntfk530");
	     combopooledDataSource.setMaxPoolSize(10);
	     
	     this.datasource = combopooledDataSource; 
	}
	
	public Connection recuperaConexion(){
		try {
			return this.datasource.getConnection();
		}catch (SQLException e){
			throw new RuntimeException(e);
		}
	}
	
}
