import { Component, OnInit, signal, computed } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { PolaflixService } from '../polaflix.service';
import { forkJoin } from 'rxjs';

@Component({
  selector: 'app-inicio',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './inicio.component.html',
  styleUrls: ['./inicio.component.css']
})
export class InicioComponent implements OnInit {
  username = signal<string>('');
  usuarioData = signal<any>(null);
  seriesData = signal<any[]>([]);

  empezadas = computed(() => this.filtrarSeries('EMPEZADA'));
  pendientes = computed(() => this.filtrarSeries('PENDIENTE'));
  terminadas = computed(() => this.filtrarSeries('TERMINADA'));

  constructor(
    private polaflixService: PolaflixService,
    private router: Router
  ) {}

  ngOnInit() {
    const u = sessionStorage.getItem('usuario') || 'carlosalarcon';
    if (!u) {
      this.router.navigate(['/']);
      return;
    }
    this.username.set(u);
    
    forkJoin({
      usuario: this.polaflixService.entrar(this.username()),
      series: this.polaflixService.getSeries()
    }).subscribe(({usuario, series}) => {
      this.usuarioData.set(usuario);
      this.seriesData.set(series);
    });
  }

  filtrarSeries(estadoDeseado: string) {
    const u = this.usuarioData();
    const s = this.seriesData();
    if (!u || !s) return [];
    
    return s.filter(serie => {
      const estaEnEstado = u.estadoSeries?.[serie.id] === estadoDeseado;
      const estaArchivada = u.seriesArchivadas?.includes(serie.id);
      return estaEnEstado && !estaArchivada;
    });
  }

  archivar(idSerie: number, event: Event) {
    event.preventDefault();
    event.stopPropagation();
    this.polaflixService.archivarSerie(this.username(), idSerie).subscribe(() => {
      
      const u = this.usuarioData();
      if (u) {
        if (!u.seriesArchivadas) u.seriesArchivadas = [];
        u.seriesArchivadas.push(idSerie);
        this.usuarioData.set({ ...u });
      }
    });
  }
}


//ahorrarme el archivar aqui (linea 66)
//De la linea 30 ahorrarme la comprobacion del isplatfrombrowser
//Lineas 45-53 quitrlas porque se puede hacer con lo q esta cargado antes en archivo.component.ts
//Lineas 4 y 54 quitarlas y poner una excepcion mia de no usuario en UsuarioServicejava
