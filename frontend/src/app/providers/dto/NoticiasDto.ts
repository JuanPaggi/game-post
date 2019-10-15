export class NoticiasDto {
    NoticiaId?: number;
    elementesPerPage?: number;
    page?: number;

    constructor(NoticiaId?: number, elementsPerPage?: number, page?: number) {
        this.NoticiaId = NoticiaId;
        this.elementesPerPage = elementsPerPage;
        this.page = page;
    }
}
