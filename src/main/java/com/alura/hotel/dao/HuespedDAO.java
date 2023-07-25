package com.alura.hotel.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.alura.hotel.factory.ConnectionFactory;
import com.alura.hotel.modelo.Huesped;

//import com.mysql.cj.jdbc.result.CachedResultSetMetaData;


public class HuespedDAO {

	private Connection con;

	public HuespedDAO(Connection con) {
		this.con = con;
	}
	
	public void guardar(Huesped huesped) {
		try{
			final PreparedStatement statement = 
					con.prepareStatement("INSERT INTO huesped(nombre, apellido, fechaDeNacimiento, nacionalidad, telefono, idReserva)"
							+ " VALUES(?, ?, ?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
			try(statement){
				
				ejecutar(huesped, statement);
			}
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	private void ejecutar(Huesped huesped, PreparedStatement statement) throws SQLException {
		statement.setString(1,huesped.getNombre());
		statement.setString(2, huesped.getApellido());
		statement.setDate(3, huesped.getFechaDeNacimiento());
		statement.setString(4, huesped.getNacionalidad());
		statement.setString(5, huesped.getTelefono());
		statement.setInt(6, huesped.getIdReserva());
		
		statement.execute();
		
		final ResultSet resulSet = statement.getGeneratedKeys();
	
		try(resulSet){
			while(resulSet.next()) {
				huesped.setId(resulSet.getInt(1));
				
				System.out.println(String.format("El Huesped fue registrado: %s", huesped));
			}
		}
	}
	
	public List<Huesped> listarHuespedes(String campo){
		List<Huesped> resultado = new ArrayList<>();
		
		ConnectionFactory factory = new ConnectionFactory();
		
		final Connection con = factory.recuperaConexion();
		
		try(con){
			var querySelect = "SELECT id, nombre, apellido, fechaDeNacimiento, nacionalidad, telefono, idReserva FROM huesped ";
			
			if(!campo.isEmpty()) {
				querySelect += "WHERE apellido LIKE ? ";
			}
			
			querySelect += "ORDER BY id DESC; ";
			
			final PreparedStatement statement = con.prepareStatement(querySelect);
			
			try(statement){
				if(!campo.isEmpty()) {
					statement.setString(1, "%" +  campo + "%");
				}
				
				statement.execute();
				
				final ResultSet resultSet = statement.getResultSet();
				
				try(resultSet){
					while(resultSet.next()) {
						Huesped fila = new Huesped(
								resultSet.getInt("id"),
								resultSet.getString("nombre"),
								resultSet.getString("apellido"),
								resultSet.getDate("fechaDeNacimiento"),
								resultSet.getString("nacionalidad"),
								resultSet.getString("telefono"),
								resultSet.getInt("idReserva")
								);
						
						resultado.add(fila);
					}
				}
			}
			
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
		return resultado;
	}
	
	public int modificar(String nombre, String apellido, Date fechaDeNacimiento, String nacionalidad, String telefono, Integer id) {
		try {
			final PreparedStatement statement = con.prepareStatement("UPDATE huesped SET "
					+ "nombre = ?, "
					+ "apellido = ?, "
					+ "fechaDeNacimiento = ?, "
					+ "nacionalidad = ?, "
					+ "telefono = ? "
					+ "WHERE id = ?");
					
			try(statement){
				statement.setString(1, nombre);
				statement.setString(2, apellido);
				statement.setDate(3, fechaDeNacimiento);
				statement.setString(4, nacionalidad);
				statement.setString(5, telefono);
				statement.setInt(6, id);
				
				statement.execute();
				
				int updateCount = statement.getUpdateCount();
				
				return updateCount;
			}
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public int eliminar(Integer id) {
		try {
			final PreparedStatement statement = con.prepareStatement("DELETE FROM huesped WHERE id = ?");
			
			try(statement){
				statement.setInt(1, id);
				statement.execute();
				
				int updateCount = statement.getUpdateCount();
				
				return updateCount;
			}
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public int eliminarIdReserva(Integer idReserva) {
		try {
			final PreparedStatement statement = con.prepareStatement("DELETE FROM huesped WHERE id = ?");
			
			try(statement){
				statement.setInt(1, idReserva);
				statement.execute();
				
				int updateCount = statement.getUpdateCount();
				
				return updateCount;
			}
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	
}
