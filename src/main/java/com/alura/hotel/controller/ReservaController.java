package com.alura.hotel.controller;

import java.sql.Date;
import java.util.List;

import com.alura.hotel.dao.ReservaDAO;
import com.alura.hotel.factory.ConnectionFactory;
import com.alura.hotel.modelo.Reserva;

public class ReservaController {

	private final ReservaDAO reservaDAO;
	
	public ReservaController() {
		this.reservaDAO = new ReservaDAO(new ConnectionFactory().recuperaConexion());
	}
	
	public void guardar(Reserva reserva) {
		reservaDAO.guardar(reserva);
	}
	
	public List<Reserva> listar(String campo){
		return reservaDAO.listarReserva(campo);
	}
	
	public int modificar(Date fechaEntrada, Date fechaSalida, Integer valor, String formaDePago, Integer id) {
		return reservaDAO.modificar(fechaEntrada, fechaSalida, valor, formaDePago, id);
	}
	
	public int eliminar(Integer id) {
		return reservaDAO.eliminar(id);
	}
}
