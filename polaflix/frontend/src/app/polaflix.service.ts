import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PolaflixService {
  constructor(private http: HttpClient) { }

  entrar(username: string): Observable<any> {
    return this.http.get(`/usuarios/${username}`);
  }

  getSeries(): Observable<any[]> {
    return this.http.get<any[]>('/series');
  }

  getSerie(idSerie: number): Observable<any> {
    return this.http.get(`/series/${idSerie}`);
  }

  marcarComoVisto(username: string, idCapitulo: number): Observable<any> {
    return this.http.put(`/usuarios/${username}/capitulos-vistos/${idCapitulo}`, {});
  }
}