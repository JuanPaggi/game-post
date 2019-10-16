export class TagsDto {
    TagId?: number;
    elementesPerPage?: number;
    page?: number;

    constructor(TagId?: number, elementsPerPage?: number, page?: number) {
        this.TagId = TagId;
        this.elementesPerPage = elementsPerPage;
        this.page = page;
    }
}
