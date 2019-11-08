export class AdminDto {
    AdminId?: number;
    elementesPerPage?: number;
    page?: number;

    constructor(AdminId?: number, elementsPerPage?: number, page?: number) {
        this.AdminId = AdminId;
        this.elementesPerPage = elementsPerPage;
        this.page = page;
    }
}
