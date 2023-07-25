package com.alura.hotel.controller;

import java.sql.Date;
import java.util.List;

import com.alura.hotel.dao.HuespedDAO;
import com.alura.hotel.factory.ConnectionFactory;
import com.alura.hotel.modelo.Huesped;

public class HuespedController {
 
	private final HuespedDAO huespedDAO;
	
	public HuespedController(){
		this.huespedDAO = new HuespedDAO(new ConnectionFactory().recuperaConexion());
	}
	
	public void guardar(Huesped huesped) {
		huespedDAO.guardar(huesped);
	}
	
	public List<Huesped> listar(String campo){
		return huespedDAO.listarHuespedes(campo);
	}
	
	public int modificar(String nombre, String apellido, Date fechaDeNacimiento, String nacionalidad, String telefono, Integer id) {
		return huespedDAO.modificar(nombre, apellido, fechaDeNacimiento, nacionalidad, telefono, id);
	}
	
	public int eliminar(Integer id) {
		return huespedDAO.eliminar(id);
	}
	
	public int eliminarIdReserva(Integer idReserva) {
		return huespedDAO.eliminarIdReserva(idReserva);
	}
}
