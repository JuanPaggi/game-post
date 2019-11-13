export class JuegoItem {
	id_juego: number;
	titulo: String;
	descripcion: String;
	genero: String;
	tipo: String;
	desarrollador: String;
	fecha_lanzamiento: Date;
	analisis_positivos: number;
	analisis_negativos: number;
	id_requisitos: number;
	id_admin_creado: number;
    nombreImagen: String;
    archivoImagen: String[];
	tags: number[];
	modos: number[];
	analisis: number[];
}
