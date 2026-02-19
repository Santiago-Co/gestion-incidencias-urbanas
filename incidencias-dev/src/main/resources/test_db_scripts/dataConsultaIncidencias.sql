delete from usuario;
delete from incidencia;

insert into usuario(dni,correo,rol) values 
	('12345678A','paco@gmail.com','Ciudadano'),
	('87654321Z','maria@hotmail.com','Ciudadano');

insert into incidencia(idUsuario,tipo,descripcion,fechaHora,estado) values 
	(1,'Alumbrado','Una farola de la calle YYY no se enciende por la noche',"2025-11-21 22:18:00",'En proceso'),
	(1,'Limpieza','El contenedor de basura de la calle XXX esta roto',"2026-01-15 18:43:00",'Nueva'),
	(2,'Limpieza','El camion de basura no pasa por la calle WWW',"2025-12-21 14:33:00",'Validada'),
	(2,'Calzada','Hay un socavon en la calle ZZZ',"2026-02-05 12:01:00",'Nueva');