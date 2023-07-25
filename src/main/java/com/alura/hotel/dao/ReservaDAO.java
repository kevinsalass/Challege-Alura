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
import com.alura.hotel.modelo.Reserva;

public class ReservaDAO {

	private Connection con;

	public ReservaDAO(Connection con) {
		this.con = con;
	}

	public void guardar(Reserva reserva) {
		try {
			final PreparedStatement statement =
					con.prepareStatement(
						"INSERT INTO reserva(fechaEntrada, fechaSalida, valor, formaDePago)"
						+ " VALUES (?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
					
			try(statement){
						ejecutar(reserva, statement);
			}		
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	private void ejecutar(Reserva reserva, PreparedStatement statement) throws SQLException {
		
		statement.setDate(1, reserva.getFechaEntrada());
		statement.setDate(2, reserva.getFechaSalida());
		statement.setInt(3, reserva.getValor());
		statement.setString(4, reserva.getFormaDePago());
		
		statement.execute();
		
		final ResultSet resultSet = statement.getGeneratedKeys();
		
		try(resultSet){
			while(resultSet.next()) {
				reserva.setId(resultSet.getInt(1));
				
				System.out.println(String.format("La reserva fue registrada: %S", reserva));
			}
		}
	}
	
	public List<Reserva> listarReserva(String campo){
		List<Reserva> resultado = new ArrayList<>();
		
		ConnectionFactory factory = new ConnectionFactory();
		final Connection con = factory.recuperaConexion();
		
		
		try(con){
			var querySelect = "SELECT id, fechaEntrada, fechaSalida, valor, formaDePago FROM reserva ";
			
			if(!campo.isEmpty()) {
				querySelect += "WHERE id = ? ";
			}
			querySelect += "ORDER BY id DESC; ";
			
			final PreparedStatement statement = con.prepareStatement(querySelect);
			
			try(statement){
				if(!campo.isEmpty()) {
					statement.setLong(1, Long.valueOf(campo));
				}
				
				statement.execute();
				
				final ResultSet resultSet = statement.getResultSet();
				
				try(resultSet){
					while(resultSet.next()) {
						Reserva fila = new Reserva(
								resultSet.getInt("id"),
								resultSet.getDate("fechaEntrada"),
								resultSet.getDate("fechaSalida"),
								resultSet.getInt("valor"),
								resultSet.getString("formaDePago")
								);
						resultado.add(fila);
					}
				}
			}
		}catch(SQLException e) {
			throw new RuntimeException();
		}
		return resultado;
	}
	
	public int modificar(Date fechaEntrada, Date fechaSalida, Integer valor, String formaDePago, Integer id ) {
		
		try {
			final PreparedStatement statement = con.prepareStatement("UPDATE reserva SET "
					+ "fechaEntrada = ?, "
					+ "fechaSalida = ?, "
					+ "valor = ?, "
					+ "formaDePago = ? "
					+ "WHERE id = ? ");
			try(statement){
				statement.setDate(1, fechaEntrada);
				statement.setDate(2, fechaSalida);
				statement.setInt(3, valor);
				statement.setString(4, formaDePago);
				statement.setInt(5, id);
				
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
			final PreparedStatement statement = con.prepareStatement("DELETE FROM reserva WHERE id = ?");
			
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
}
