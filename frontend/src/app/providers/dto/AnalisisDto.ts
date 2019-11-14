export class AnalisisDto {
    AnalisisId?: number;
    elementesPerPage?: number;
    page?: number;

    constructor(AnalisisId?: number, elementsPerPage?: number, page?: number) {
        this.AnalisisId = AnalisisId;
        this.elementesPerPage = elementsPerPage;
        this.page = page;
    }
}
