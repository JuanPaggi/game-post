export class JuegoItem {
	id_juego: number;
	titulo: String;
	descripcion: String;
	genero: String;
	desarrollador: String;
	fecha_lanzamiento: Date;
	analisis_positivos: number;
	analisis_negativos: number;
	id_requisitos: number;
	id_usuario_juego: number;
    nombreImagen: String;
    archivoImagen: String[];
	tags: number[];
	modos: number[];
	analisis: number[];
	sistema_operativo: String;
	procesador: String;
	memoria: String;
	grafica: String;
	almacenamiento: String;
}
