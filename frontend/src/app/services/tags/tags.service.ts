import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { TagsDto } from '../../providers/dto/TagsDto';
import { TagItem } from '../../providers/entities/TagItem.entity';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})

export class TagsService {

  constructor(private http: HttpClient) { }

  public getAllTags(body: TagsDto): Observable<TagItem[]> {
    let headers = {};
    return this.http.get<TagItem[]>(
        environment.apiEndpoint + '/tags',
        headers
    );
  }
  
}
