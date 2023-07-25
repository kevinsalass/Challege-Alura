
create table reserva(
	id int not null auto_increment,
    fechaEntrada Date,
    fechaSalida Date,
    valor int not null,
    formaDePago varchar(100),
    primary key(id)
);

CREATE TABLE huespedes (
    id INT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    fechaDeNacimiento DATE,
    nacionalidad VARCHAR(100) NOT NULL,
    telefono INT NOT NULL, 
    idReserva INT,
    FOREIGN KEY (idReserva) REFERENCES reserva(id),
    PRIMARY KEY (id)
);