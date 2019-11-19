export class CrearJuegoDto {
	titulo: String;
	descripcion: String;
	genero: String;
	desarrollador: String;
	fecha_lanzamiento: Date;
	analisis_positivos: number;
	analisis_negativos: number;
	id_usuario_juego: number;
    nombreImagen: String;
    archivoImagen: number[][];
	tags: number[];
	modos: number[];
	sistema_operativo: String;
	procesador: String;
	memoria: String;
	grafica: String;
	almacenamiento: String;
}
