export class ComentariosDto {
    ComentaioId?: number;
    elementesPerPage?: number;
    page?: number;

    constructor(ComentaioId?: number, elementsPerPage?: number, page?: number) {
        this.ComentaioId = ComentaioId;
        this.elementesPerPage = elementsPerPage;
        this.page = page;
    }
}
